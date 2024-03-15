#version 410 core

layout(location=0) in vec3 vPosition;
layout(location=1) in vec3 vNormal;
layout(location=2) in vec2 vTexCoords;

out vec3 fPosition;
out vec4 fPosEye;
out vec3 fNormal;
out vec2 fTexCoords;
//for shadow mapping
out vec4 fragPosLightSpace;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

//for shadow mapping
uniform mat4 lightSpaceTrMatrix;

void main() 
{
	gl_Position = projection * view * model * vec4(vPosition, 1.0f);
	fPosition = vPosition;
	fPosEye = view * model * vec4(vPosition, 1.0f);//added by me
	fNormal = vNormal;
	fTexCoords = vTexCoords;
       //compute shadow coordinates
	fragPosLightSpace = lightSpaceTrMatrix * model * vec4(vPosition, 1.0f);
}
