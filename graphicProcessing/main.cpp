#if defined (__APPLE__)
    #define GLFW_INCLUDE_GLCOREARB
    #define GL_SILENCE_DEPRECATION
#else
    #define GLEW_STATIC
    #include <GL/glew.h>
#endif

#include <GLFW/glfw3.h>

#include <glm/glm.hpp> //core glm functionality
#include <glm/gtc/matrix_transform.hpp> //glm extension for generating common transformation matrices
#include <glm/gtc/matrix_inverse.hpp> //glm extension for computing inverse matrices
#include <glm/gtc/type_ptr.hpp> //glm extension for accessing the internal data structure of glm types

#include "Window.h"
#include "Shader.hpp"
#include "Camera.hpp"
#include "Model3D.hpp"
#include "SkyBox.hpp"

#include <iostream>

// window
int retina_width, retina_height;
gps::Window myWindow;


// matrices
glm::mat4 model;
glm::mat4 view;
glm::mat4 projection;
glm::mat3 normalMatrix;

// light parameters
glm::vec3 lightDir;
glm::vec3 lightColor;

// shader uniform locations
GLint modelLoc;
GLint viewLoc;
GLint projectionLoc;
GLint normalMatrixLoc;
GLint lightDirLoc;
GLint lightColorLoc;

// camera
gps::Camera myCamera(
    glm::vec3(0.0f, 0.0f, 3.0f),
    glm::vec3(0.0f, 0.0f, -10.0f),
    glm::vec3(0.0f, 1.0f, 0.0f));

GLfloat cameraSpeed = 0.1f;

GLboolean pressedKeys[1024];

// models
gps::Model3D teapot;
gps::Model3D dog;
gps::Model3D leaves1;
gps::Model3D bark1;
gps::Model3D leaves2;
gps::Model3D bark2;
gps::Model3D house1;
gps::Model3D house2;
gps::Model3D house3;
gps::Model3D cottage;
gps::Model3D dogHouse;
gps::Model3D ground;
gps::Model3D water;
gps::Model3D blackHouse;
gps::Model3D pavement;
gps:: Model3D lamp;
gps::Model3D lamp1;
gps::Model3D ball;
GLfloat angle;

// shaders
gps::Shader myBasicShader;


//skyBox
GLuint textureID;
gps::SkyBox mySkyBox;
gps::Shader skyboxShader;

float pitch = 0.0f;
float yaw = -90.0f;



glm::vec3 lightDirPoint;
GLuint lightDirPointLoc;
glm::vec3 lightColorPoint;
GLuint lightColorPointLoc;
glm::vec3 lightPosEye;
GLuint lightPosEyeLoc;

GLenum glCheckError_(const char *file, int line)
{
	GLenum errorCode;
	while ((errorCode = glGetError()) != GL_NO_ERROR) {
		std::string error;
		switch (errorCode) {
            case GL_INVALID_ENUM:
                error = "INVALID_ENUM";
                break;
            case GL_INVALID_VALUE:
                error = "INVALID_VALUE";
                break;
            case GL_INVALID_OPERATION:
                error = "INVALID_OPERATION";
                break;
            case GL_OUT_OF_MEMORY:
                error = "OUT_OF_MEMORY";
                break;
            case GL_INVALID_FRAMEBUFFER_OPERATION:
                error = "INVALID_FRAMEBUFFER_OPERATION";
                break;
        }
		std::cout << error << " | " << file << " (" << line << ")" << std::endl;
	}
	return errorCode;
}
#define glCheckError() glCheckError_(__FILE__, __LINE__)

void windowResizeCallback(GLFWwindow* window, int width, int height) {
	fprintf(stdout, "Window resized! New width: %d , and height: %d\n", width, height);
	//TODO
}

void keyboardCallback(GLFWwindow* window, int key, int scancode, int action, int mode) {
	if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
        glfwSetWindowShouldClose(window, GL_TRUE);
    }

	if (key >= 0 && key < 1024) {
        if (action == GLFW_PRESS) {
            pressedKeys[key] = true;
        } else if (action == GLFW_RELEASE) {
            pressedKeys[key] = false;
        }
    }
   
}

static double lastX, lastY = 0;
void mouseCallback(GLFWwindow* window, double xpos, double ypos) {
    //TODO
    if (lastX == 0 || lastY == 0)
    {
        lastX = xpos;
        lastY = ypos;
    }
    else
    {
        float dif = 0.005f;
        float xoffset = (xpos - lastX)*dif ;
        float yoffset = (lastY - ypos)*dif ;

        myCamera.rotate(yoffset, xoffset);
        view = myCamera.getViewMatrix();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
       // normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
       // glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
        //glUniform3fv(lightDirLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view)) * lightDir));

        lastX = xpos;
        lastY = ypos;
    }
    //glCheckError();
}

void processMovement() {
   
	if (pressedKeys[GLFW_KEY_W]) {
		myCamera.move(gps::MOVE_FORWARD, cameraSpeed);
		//update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
	}

	if (pressedKeys[GLFW_KEY_S]) {
		myCamera.move(gps::MOVE_BACKWARD, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
	}

	if (pressedKeys[GLFW_KEY_A]) {
		myCamera.move(gps::MOVE_LEFT, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
	}

	if (pressedKeys[GLFW_KEY_D]) {
		myCamera.move(gps::MOVE_RIGHT, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
	}

    if (pressedKeys[GLFW_KEY_Q]) {
        angle -= 1.0f;
        // update model matrix for teapot
        model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0, 1, 0));
        // update normal matrix for teapot
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
    }

    if (pressedKeys[GLFW_KEY_E]) {
        angle += 1.0f;
        // update model matrix for teapot
        model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0, 1, 0));
        // update normal matrix for teapot
        normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
    }

    if (pressedKeys[GLFW_KEY_T])
    {
        
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        
    }
   
    if (pressedKeys[GLFW_KEY_O])
    {
       
      glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
        
    }
 
}

void initOpenGLWindow() {
    myWindow.Create(1024, 768, "OpenGL Project Core");
}

void setWindowCallbacks() {
	glfwSetWindowSizeCallback(myWindow.getWindow(), windowResizeCallback);
    glfwSetKeyCallback(myWindow.getWindow(), keyboardCallback);
    glfwSetCursorPosCallback(myWindow.getWindow(), mouseCallback);
}

void initOpenGLState() {
	glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
	glViewport(0, 0, myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);
    glEnable(GL_FRAMEBUFFER_SRGB);
	glEnable(GL_DEPTH_TEST); // enable depth-testing
	glDepthFunc(GL_LESS); // depth-testing interprets a smaller value as "closer"
	glEnable(GL_CULL_FACE); // cull face
	glCullFace(GL_BACK); // cull back face
	glFrontFace(GL_CCW); // GL_CCW for counter clock-wise
}

void  initSkybox()
{
    std::vector<const GLchar*> faces; faces.push_back("skybox/right.tga");
    faces.push_back("skybox/left.tga");
    faces.push_back("skybox/top.tga");
    faces.push_back("skybox/bottom.tga");
    faces.push_back("skybox/back.tga");
    faces.push_back("skybox/front.tga");
    mySkyBox.Load(faces);
}

void initModels() {
    //teapot.LoadModel("models/teapot/teapot20segUT.obj");
    dog.LoadModel("models/dog/dog.obj");
    leaves1.LoadModel("models/tree/leaves1.obj");
    bark1.LoadModel("models/tree/bark1.obj");
    leaves2.LoadModel("models/tree/leaves2.obj");
    bark2.LoadModel("models/tree/bark2.obj");
    cottage.LoadModel("models/house/cottageBigger.obj");
    dogHouse.LoadModel("models/dogHouse/dogHouse2.obj");
    ground.LoadModel("models/ground/mountain.obj");
    water.LoadModel("models/water/mountainWater.obj");
    blackHouse.LoadModel("models/house/horse.obj");
    pavement.LoadModel("models/ground/pavement.obj");
    lamp.LoadModel("models/ground/poll2.obj");
    lamp1.LoadModel("models/ground/poll.obj");
    ball.LoadModel("models/ground/ball.obj");
}

void initShaders() {
	myBasicShader.loadShader("shaders/basic.vert","shaders/basic.frag");
    skyboxShader.loadShader("shaders/skyboxShader.vert", "shaders/skyboxShader.frag"); 
    skyboxShader.useShaderProgram();
  
   
}

void initUniforms() {
	myBasicShader.useShaderProgram();

    // create model matrix for teapot
    model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0.0f, 1.0f, 0.0f));
	modelLoc = glGetUniformLocation(myBasicShader.shaderProgram, "model");

	// get view matrix for current camera
	view = myCamera.getViewMatrix();
	viewLoc = glGetUniformLocation(myBasicShader.shaderProgram, "view");
	// send view matrix to shader
    glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

    // compute normal matrix for teapot
    normalMatrix = glm::mat3(glm::inverseTranspose(view*model));
	normalMatrixLoc = glGetUniformLocation(myBasicShader.shaderProgram, "normalMatrix");

	// create projection matrix
	projection = glm::perspective(glm::radians(45.0f),
                               (float)myWindow.getWindowDimensions().width / (float)myWindow.getWindowDimensions().height,
                               0.1f, 20.0f);
	projectionLoc = glGetUniformLocation(myBasicShader.shaderProgram, "projection");
	// send projection matrix to shader
	glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));	

	//set the light direction (direction towards the light)
	lightDir = glm::vec3(0.0f, 1.0f, 1.0f);
	lightDirLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightDir");
	// send light dir to shader
	glUniform3fv(lightDirLoc, 1, glm::value_ptr(lightDir));

	//set light color
	lightColor = glm::vec3(1.0f, 1.0f, 1.0f); //white light
	lightColorLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor");
	// send light color to shader
	glUniform3fv(lightColorLoc, 1, glm::value_ptr(lightColor));

    //send uniforms for the point light

    //direction of point light
    lightDirPoint = glm::vec3(0.0f, 1.0f, 1.0f);
    lightDirPointLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightDirPoint");
    glUniform3fv(lightDirPointLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view)) * lightDirPoint));

    //set the color of the point light
    lightColorPoint = glm::vec3(1.0f, 1.0f, 1.0f); //white light
    lightColorPointLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightColorPoint");
    glUniform3fv(lightColorPointLoc, 1, glm::value_ptr(lightColorPoint));

    lightPosEye = glm::vec3(0, 0, 3);
    lightPosEyeLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightPosEye");
    glUniform3fv(lightPosEyeLoc, 1, glm::value_ptr((glm::mat3(view)) * (glm::mat3(model) * lightPosEye)));
    
}





void renderTeapot(gps::Shader shader) {
    // select active shader program
    shader.useShaderProgram();

     
     //send teapot model matrix data to shader  
    glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));
    //send teapot normal matrix data to shader
        glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    
    
    // draw teapot
    //teapot.Draw(shader);
    dog.Draw(shader);
    leaves1.Draw(shader);
    bark1.Draw(shader);
    leaves2.Draw(shader);
    bark2.Draw(shader);
    cottage.Draw(shader);
    dogHouse.Draw(shader);
    ground.Draw(shader);
    water.Draw(shader);
    blackHouse.Draw(shader);
    pavement.Draw(shader);
    lamp.Draw(shader);
    lamp1.Draw(shader);
    ball.Draw(shader);
}

void renderScene() {
    
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    //render the scene
    myBasicShader.useShaderProgram();
    view = myCamera.getViewMatrix();
    glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
    normalMatrix = glm::mat3(glm::inverseTranspose(view * model));

    // render the teapot
     renderTeapot(myBasicShader);
    mySkyBox.Draw(skyboxShader, view, projection);
   
}

void cleanup() {
 
    myWindow.Delete();
    //cleanup code for your own data
}
float x = 1.0f;
int main(int argc, const char * argv[]) {

    try {
        initOpenGLWindow();
    } catch (const std::exception& e) {
        std::cerr << e.what() << std::endl;
        return EXIT_FAILURE;
    }

    initOpenGLState();
	initModels();
	initShaders();
	initUniforms();
    initSkybox();
    //initFBO();
    setWindowCallbacks();

	//glCheckError();
	// application loop
	while (!glfwWindowShouldClose(myWindow.getWindow())) {
        //glCheckError();
        processMovement();
        //glCheckError();
	    renderScene();
       
		glfwPollEvents();
		glfwSwapBuffers(myWindow.getWindow());

        //model = glm::rotate(glm::mat4(1.0f), glm::radians(x), glm::vec3(0.0f, 1.0f, 0.0f));
        //modelLoc = glGetUniformLocation(myBasicShader.shaderProgram, "model");
        //x = x + 1;

		glCheckError();
	}

	cleanup();

    return EXIT_SUCCESS;
}
