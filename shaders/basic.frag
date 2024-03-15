#version 410 core

in vec3 fPosition;
in vec3 fNormal;
in vec2 fTexCoords;
//point light
in vec4 fPosEye;
//shadow
in vec4 fragPosLightSpace;

out vec4 fColor;

//shadow map
uniform sampler2D shadowMap;

//matrices
uniform mat4 model;
uniform mat4 view;
uniform mat3 normalMatrix;
//lighting
uniform vec3 lightDir;
uniform vec3 lightColor;
// textures
uniform sampler2D diffuseTexture;
uniform sampler2D specularTexture;

//components
vec3 ambient;
float ambientStrength = 0.2f;
vec3 diffuse;
vec3 specular;
float specularStrength = 0.5f;

//point light
uniform vec3 lightPosEye;
//lighting
uniform vec3 lightDirPoint;
uniform vec3 lightColorPoint;

vec3 ambientPoint;
float ambientStrengthPoint = 0.2f;
vec3 diffusePoint;
vec3 specularPoint;
float specularStrengthPoint = 0.5f;
float shininessPoint = 32.0f;

//coeficientii de atenuare
float constant = 1.0f;
float linear = 0.0078f;
float quadratic = 0.0094f;

float shadow;

void computeDirLight()
{
    //compute eye space coordinates
    vec4 fPosEye = view * model * vec4(fPosition, 1.0f);
    vec3 normalEye = normalize(normalMatrix * fNormal);

    //normalize light direction
    vec3 lightDirN = vec3(normalize(view * vec4(lightDir, 0.0f)));

    //compute view direction (in eye coordinates, the viewer is situated at the origin
    vec3 viewDir = normalize(- fPosEye.xyz);

    //compute ambient light
    ambient = ambientStrength * lightColor;

    //compute diffuse light
    diffuse = max(dot(normalEye, lightDirN), 0.0f) * lightColor;

    //compute specular light
    vec3 reflectDir = reflect(-lightDirN, normalEye);
    float specCoeff = pow(max(dot(viewDir, reflectDir), 0.0f), 32);
    specular = specularStrength * specCoeff * lightColor;
}

void computeLightComponents()
{		
	vec3 cameraPosEye = vec3(0.0f);//in eye coordinates, the viewer is situated at the origin
	
	//transform normal
	vec3 normalEye = normalize(fNormal);	
	
	//compute light direction
	//vec3 lightDirN = normalize(lightDir);
	vec3 lightDirN = normalize(lightPosEye - fPosEye.xyz);
 
	//compute view direction 
	vec3 viewDirN = normalize(cameraPosEye - fPosEye.xyz);

	//compute distance to light 
	float dist = length(lightPosEye - fPosEye.xyz); 

	//compute attenuation 
	float att = 1.0f / (constant + linear * dist + quadratic * (dist * dist));
	att*=5;
	//compute ambient light
	//ambientPoint = ambientStrengthPoint * lightColorPoint;
	ambientPoint = att * ambientStrengthPoint * lightColorPoint; 
	ambientPoint *= texture(diffuseTexture, fTexCoords).xyz; 
	
	//compute diffuse light
	//diffusePoint = max(dot(normalEye, lightDirN), 0.0f) * lightColorPoint;
	diffusePoint = att * max(dot(normalEye, lightDirN), 0.0f) * lightColorPoint; 
	diffusePoint *= texture(diffuseTexture, fTexCoords).xyz; 

	//compute specular light
	vec3 reflection = reflect(-lightDirN, normalEye);
	float specCoeff = pow(max(dot(viewDirN, reflection), 0.0f), shininessPoint);
	//specularPoint = specularStrengthPoint * specCoeff * lightColorPoint;
	specularPoint = att * specularStrengthPoint * specCoeff * lightColorPoint;
	specularPoint *= texture(specularTexture, fTexCoords).xyz;  
}


float computeShadow() 
{
	float bias = 0.005f;
	//perform perspective divide
	vec3 normalizedCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;

	normalizedCoords = normalizedCoords * 0.5 + 0.5;
	float closestDepth = texture(shadowMap, normalizedCoords.xy).r;
	
	if (normalizedCoords.z > 1.0f)
		return 0.0f;

	float currentDepth = normalizedCoords.z;

	//check if current pos in shadow
	return (currentDepth - bias) > closestDepth ? 1.0 : 0.0;
}

float computeFog()
{
 float fogDensity = 0.05f;
 float fragmentDistance = length(fPosEye);
 float fogFactor = exp(-pow(fragmentDistance * fogDensity, 2));

 return clamp(fogFactor, 0.0f, 1.0f);
}



void main() 
{
    computeDirLight();
   //light point
   computeLightComponents();

    //compute final vertex color
    vec3 color1 = min((ambient + diffuse) * texture(diffuseTexture, fTexCoords).rgb + specular * texture(specularTexture, fTexCoords).rgb, 1.0f);
    vec3 color2=min((ambientPoint+diffusePoint)+specularPoint,1.0f);
    vec3 color=color1+color2;

    //modulate shadow
    shadow = computeShadow();
    vec3 color3 = min((ambient + (1.0f - shadow)*diffuse) + (1.0f - shadow)*specular, 1.0f);
    //vec3 color=color3;

    //do the fog
     float fogFactor = computeFog();
     vec4 fogColor = vec4(0.5f, 0.5f, 0.5f, 1.0f);
    fColor = mix(fogColor, vec4(color, 1.0f), fogFactor); 

    //fColor = vec4(color, 1.0f);
}
