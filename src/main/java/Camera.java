import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import structure.Church;
import structure.Door;
import things.Chair;
import utils.SkyBox;
import utils.Texture;

import java.awt.*;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.StrictMath.PI;
import static java.lang.StrictMath.sin;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

public class Camera {

    GLFWErrorCallback errorCallback;
    GLFWKeyCallback keyCallback;
    GLFWFramebufferSizeCallback fbCallback;
    GLFWCursorPosCallback cpCallback;

    /* parâmetros para criação da janela */
    long window;
    int width;
    int height;
    boolean windowed = true;

    /* Parâmetros de controle de interação */
    float mouseX, mouseY;
    boolean[] keyDown = new boolean[GLFW.GLFW_KEY_LAST + 1];
    float heightAboveGround = 1.80f;
    float movementSpeed = 2.666f;

    /* Door Control */
    Door backdoors;
    Door centralDoor;
    Door leftDoor;
    Door rightDoor;
    float door_angle = 0;

    /* Parâmetros responsáveis pela movimentação da câmera */
    Vector3f dir = new Vector3f();
    Vector3f right = new Vector3f();
    Vector3f up = new Vector3f();
    Matrix4f mat = new Matrix4f();
    FloatBuffer fb = BufferUtils.createFloatBuffer(16);
    Vector3f pos = new Vector3f(0, heightAboveGround, 0);
    float rotX = 0.0f;
    float rotY = 0.0f;

    /* Variáveis de teste */

    ArrayList<Texture> textureList;
    Texture texture;
    SkyBox skyBox;
    Chair chair;
    Church church;
    private boolean trigger = true;
    private boolean trigger1 = true;

    void run() {
        try {
            /* Pega tamanho da tela para ajustar attributos da classe */
            Dimension canvas = Toolkit.getDefaultToolkit().getScreenSize();
            width = (int) canvas.getWidth();
            height = (int) canvas.getHeight();


            init();
            createTextures();
            loop();

            glfwDestroyWindow(window);
            keyCallback.free();
            fbCallback.free();
            cpCallback.free();
        } finally {
            glfwTerminate();
            errorCallback.free();
        }
    }

    void createTextures(){

    }
    
    void setUpLighting() {
//        glEnable(GL_LIGHTING);
//        glEnable(GL_LIGHT0);
//        glLightModeli(GL_LIGHT_MODEL_AMBIENT,GL_TRUE);
        //
//        glLightModelfv(GL_LIGHT_MODEL_AMBIENT, (new float[] {0.2f, 0.2f, 0.2f, 1.0f}));
//        glLightfv(GL_LIGHT0, GL_DIFFUSE, (new float[] {0.7f, 0.7f, 0.7f, 1.0f}));
//        glLightfv(GL_LIGHT0, GL_SPECULAR, (new float[] {1.0f, 1.0f, 1.0f, 1.0f}));
        //
        float[] luzAmbiente = {0.2f, 0.2f, 0.2f, 1.0f};
        float[] luzDifusa = {0.7f,0.7f,0.7f,1.0f};	        // "cor"
        float[] luzEspecular = {1.0f, 1.0f, 1.0f, 1.0f};    // "brilho"
        float[] posicaoLuz = {0.0f, 500.0f, 1000.0f, 1.0f};

        // Capacidade de brilho do material
        float[] especularidade = {1.0f,1.0f,1.0f,1.0f};
        int especMaterial = 60;

        // Habilita o modelo de colorização de Gouraud
        glShadeModel(GL_SMOOTH);

//        // Define a refletância do material
//        glMaterialfv(GL_FRONT,GL_SPECULAR, especularidade);
//        // Define a concentração do brilho
//        glMateriali(GL_FRONT,GL_SHININESS,especMaterial);

        // Ativa o uso da luz ambiente
        glLightModelfv(GL_LIGHT_MODEL_AMBIENT, luzAmbiente);

        // Define os parâmetros da luz de número 0
        glLightfv(GL_LIGHT0, GL_AMBIENT, luzAmbiente);
        glLightfv(GL_LIGHT0, GL_DIFFUSE, luzDifusa );
        glLightfv(GL_LIGHT0, GL_SPECULAR, luzEspecular );
        glLightfv(GL_LIGHT0, GL_POSITION, posicaoLuz );

        glBegin(GL_LINES);
        glColor3f(0,0,0);
        glVertex4fv(posicaoLuz);
        glVertex3f(0,0,0);
        glEnd();

        // Habilita a definição da cor do material a partir da cor corrente
        glEnable(GL_COLOR_MATERIAL);
        //Habilita o uso de iluminação
        glDisable(GL_CULL_FACE);
        glEnable(GL_LIGHTING);
        // Habilita a luz de número 0
//        glEnable(GL_LIGHT0);
        // Habilita o depth-buffering
        glEnable(GL_DEPTH_TEST);
        glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);


        // Especifica que a cor de fundo da janela será preta
//        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
//
//
//        glLightfv(GL_LIGHT0, GL_POSITION, (new float[] {6, 5, 100, 1}));
        //
//        glClearColor(1,1,1,1);
//        glShadeModel(GL_SMOOTH);
//        glEnable(GL_COLOR_MATERIAL);
//        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

        // Habilita a definição da cor do material a partir da cor corrente
//        glEnable(GL_COLOR_MATERIAL);
        //Habilita o uso de iluminação
//        glEnable(GL_LIGHTING);
        // Habilita a luz de número 0
//        glEnable(GL_LIGHT0);
        // Habilita o depth-buffering
//        glEnable(GL_DEPTH_TEST);
    }

    void init() {
        centralDoor = new Door(door_angle);
        leftDoor = new Door(door_angle);
        rightDoor = new Door(door_angle);
        backdoors = new Door(door_angle);


        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);


        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(monitor);
        if (!windowed) {
            width = vidmode.width();
            height = vidmode.height();
        }

        window = glfwCreateWindow(width, height, "Igreja Nsa. Senhora Auxiliadora", !windowed ? monitor : NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetFramebufferSizeCallback(window, fbCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                if (w > 0 && h > 0) {
                    width = w;
                    height = h;
                }
            }
        });

        /* Função para pegar movimento do mouse */
        glfwSetCursorPosCallback(window, cpCallback = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                mouseX = (float) xpos / width;
                mouseY = (float) ypos / height;
            }
        });

        /* Função para capturar evento de teclado */
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true);
                if (action == GLFW_PRESS || action == GLFW_REPEAT)
                    keyDown[key] = true;
                else
                    keyDown[key] = false;
            }
        });

        IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
        nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
        width = framebufferSize.get(0);
        height = framebufferSize.get(1);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        glfwShowWindow(window);

    }

    void loop() {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);

        /* Verificar esse clear color */
        glClearColor(0.f, 0.f, 0.f, 1.0f);

        long lastTime = System.nanoTime();


        while (!glfwWindowShouldClose(window)) {

            long thisTime = System.nanoTime();
            float diff = (float) ((thisTime - lastTime) / 1E9);
            lastTime = thisTime;
            float move = diff * movementSpeed;

            /* Botões responsáveis pela velocidade de movimento da câmera */
            if (keyDown[GLFW_KEY_LEFT_SHIFT])
                move *= 4.0f;
            if (keyDown[GLFW_KEY_LEFT_CONTROL])
                move *= 0.5f;
            if (keyDown[GLFW_KEY_MINUS])
                movementSpeed--;
            if (keyDown[GLFW_KEY_COMMA])
                movementSpeed++;

            mat.positiveX(right).mul(move);
            mat.positiveZ(dir).mul(move);
            dir.y = 0.0f; // <- restrict movement on XZ plane
            mat.positiveY(up).mul(move);
            up.x = 0f;
            up.z = 0f;

            /* Botões responsáveis por movimentos da câmera */
            if (keyDown[GLFW_KEY_UP]) {
                pos.add(up);
            }
            if (keyDown[GLFW_KEY_DOWN]) {
                pos.sub(up);
            }
            if (keyDown[GLFW_KEY_W]) {
                pos.sub(dir);
            }
            if (keyDown[GLFW_KEY_S]) {
                pos.add(dir);
            }
            if (keyDown[GLFW_KEY_A]) {
                pos.sub(right);
            }
            if (keyDown[GLFW_KEY_D]) {
                pos.add(right);
            }

            /* Botões para alterar estado das portas */
            if (keyDown[GLFW_KEY_O]) {
                if(door_angle >= -2.814)
                    door_angle -= 0.004f;
            }
            if (keyDown[GLFW_KEY_C]) {
                if(door_angle <= 0)
                    door_angle += 0.004f;
            }

            /* Turn On/Off light */
            if(keyDown[GLFW_KEY_ENTER]){
                if(trigger1){
                    trigger1 = false;
                    glDisable(GL_LIGHT0);
                }
                else{
                    trigger1 = true;
                    glEnable(GL_LIGHT0);
                }
            }

            /* Move Light */


            rotX = mouseY;
            rotY = mouseX;

            glMatrixMode(GL_PROJECTION);
            glLoadMatrixf(mat.setPerspective((float) Math.toRadians(45), (float) width / height, 0.01f, 100.0f).get(fb));

            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            mat.identity()
                .rotateX(rotX)
                .rotateY(rotY)
                .translate(-pos.x, -pos.y, -pos.z);
            glLoadMatrixf(mat.get(fb));

            glViewport(0, 0, width, height);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glEnable(GL_TEXTURE_2D);
            /* Estou carregando as texturas aqui */
            if(trigger){
                trigger = false;
                textureList = new ArrayList<Texture>();
                texture = new Texture();
                /* Texture Order */
                /* chair        (Cadeiras, mesa, genoflexório, cadeira do padre, pulpito e crucifixo externo)
                 * wall         (toda estrutura),
                 * wall2        (Parede central do altar),
                 * wall3        (Pilastras da fachada),
                 * Vitralfachada(Textura do vitral central)
                 * vitral2      (Textura dos vitrais acima da porta)
                 * door         (Portas entrada)
                 * cruz         (Crucifixo externo, centro)
                 * ground       (Textura do piso)
                 * altarstair   (Textura do degrau do altar)
                 * ceil         (Textura do teto)
                 * window       (Textura das janelas)
                 * externalground(Textura de cimento da área externa da igreja)
                 * externalground1(Textura de solo, lado de fora da igreja)
                 * telhado      (Textura do telhado da igreja)
                 * sound        (Textura da caixa de som)
                 * flooraltar   (Textura do piso do altar)
                 * quadro       (Textura do quadro de avisos)
                 * */
                textureList.add(Texture.loadTexture("chair"));              //0     - chair
                textureList.add(Texture.loadTexture("wall"));               //1     - wall
                textureList.add(Texture.loadTexture("wall2"));              //2     - wall2
                textureList.add(Texture.loadTexture("forro"));              //3     - wall3
                textureList.add(Texture.loadTexture("vitralfachada"));      //4     - vitralfachada
                textureList.add(Texture.loadTexture("vitral2"));            //5     - vitral2
                textureList.add(Texture.loadTexture("door"));               //6     - door
                textureList.add(Texture.loadTexture("forro"));              //7     - cruz
                textureList.add(Texture.loadTexture("piso"));               //8     - ground
                textureList.add(Texture.loadTexture("altarstair"));         //9     - altarstair
                textureList.add(Texture.loadTexture("forro"));              //10    - ceil
                textureList.add(Texture.loadTexture("door"));              //11    - window
                textureList.add(Texture.loadTexture("externalground"));     //12    - externalground
                textureList.add(Texture.loadTexture("externalground1"));    //13    - externalground1
                textureList.add(Texture.loadTexture("telhado"));            //14    - telhado
                textureList.add(Texture.loadTexture("sound"));              //15    - sound
                textureList.add(Texture.loadTexture("flooraltar"));         //16    - flooraltar
                textureList.add(Texture.loadTexture("quadro"));             //17    - quadro
                skyBox = new SkyBox(texture);
                chair = new Chair(texture, 0, -10);
                church = new Church(textureList);
            }
            /* Aqui inicia a área de rendering */
//            makeGrid();




            church.drawChurch();
            centralDoor.update(0, 3, textureList, door_angle, true);
            leftDoor.update(12.5f,2.5f, textureList, door_angle, true);
            rightDoor.update(-12.5f,2.5f, textureList, door_angle, true);
            backdoors.update(18.f,3f, textureList, door_angle, false);
            setUpLighting();
            /* Aqui termina a área de rendering*/

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

    }

    public static void makeGrid(){
        int dl = glGenLists(1);
        glNewList(dl, GL_COMPILE);
        glBegin(GL_LINES);
        glColor3f(0.2f, 0.2f, 0.2f);

        int gridSize = 110;
        float ceiling = 10.0f;

        for (int i = -gridSize; i <= gridSize; i++) {
            glVertex3f(-gridSize, 0.0f, i);
            glVertex3f(gridSize, 0.0f, i);
            glVertex3f(i, 0.0f, -gridSize);
            glVertex3f(i, 0.0f, gridSize);
        }
        glColor3f(0.5f, 0.5f, 0.5f);
        for (int i = -gridSize; i <= gridSize; i++) {
            glVertex3f(-gridSize, ceiling, i);
            glVertex3f(gridSize, ceiling, i);
            glVertex3f(i, ceiling, -gridSize);
            glVertex3f(i, ceiling, gridSize);
            //
        }
        glEnd();
        glEndList();

        glCallList(dl);
    }

    public static void main(String[] args) throws IOException {
        new Camera().run();
    }
}
