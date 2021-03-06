package structure;

import things.*;
import lighting.Light;
import utils.ExternPlaces;
import utils.Texture;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;
import static org.lwjgl.opengl.GL11.*;

public class Church {

    /* Dimensions */
    private final float XSIZE = 20; //25
    private final float YSIZE = 10; //10
    private final float ZSIZE = 30; //30
    private final float thickness = 0.025f;
    private final Pulpit pulpit;
    private final Table table;
    private final Altar altar;
    private final ExternPlaces externPlaces;

    /* Objects inside */
    private List<Texture> textures;
    private List<Chair> chairs;
    private List<Light> lights;
    private Facade facade;
    private SoundBox soundBoxes;
    private AltarChair altarChair;

    public Church(List<Texture> textures){
        this.textures = textures;
        this.chairs = new ArrayList<Chair>();
        this.lights = new ArrayList<Light>();

        // Cria as cadeiras
        for(int i=0, j=-5; i<17; i++, j-=4 ){
            chairs.add(new Chair(textures.get(0),-6, j));
            chairs.add(new Chair(textures.get(0),-15, j));
            chairs.add(new Chair(textures.get(0),6, j));
            chairs.add(new Chair(textures.get(0),15, j));
        }

        // Cria os pontos de luz
        for(int i = 1, j=-5; i<3; i++, j-=16) {
            lights.add(new Light(-6, j,i));
//            lights.add(wadd(new Light(15, j));
        }

        this.facade = new Facade(textures);
        this.soundBoxes = new SoundBox(textures);
        this.pulpit = new Pulpit(textures);
        this.table = new Table(textures);
        this.altar = new Altar(textures);
        this.altarChair = new AltarChair(textures);
        this.externPlaces = new ExternPlaces(textures);
    }

    public void drawChurch(){
        glPushMatrix();
        textures.get(1).bind();
        glColor4f(0.5f, 0.5f, 0.4f,1);
        structure();
        putPilars();
        altarArch();
        putTelhado();
        drawGroundChurch();
        drawWindows();

        facade.criarFachada();
        soundBoxes.putSoundBoxes();
        pulpit.createPulpit();
        table.createTable();
        altar.createAltar();
        altarChair.altarChair();
        externPlaces.makeExternalArea();
        putChairs();
//        putLights();

        drawCeil();
        glPopMatrix();
    }

    private void drawCeil(){
        float[] ceilColor = {1.0f, 1.f, 1.f, 1.0f};
        for(int i = (int) -XSIZE; i<XSIZE; i+=4) {
            glPushMatrix();
            glColor4f(1,1,1,0);
            textures.get(10).bind();
            glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, ceilColor);
            glBegin(GL_QUADS);
            glNormal3f(0,-1,0);
            glTexCoord2f(0,1);
            glVertex3f(i,YSIZE, 0);
            glTexCoord2f(1,1);
            glVertex3f(i+4,YSIZE, 0);
            glTexCoord2f(1,0);
            glVertex3f(i+4,YSIZE, -3.5f*ZSIZE);
            glTexCoord2f(0,0);
            glVertex3f(i,YSIZE, -3.5f*ZSIZE);
            glEnd();
            glPopMatrix();
        }
    }

    private void putPilars(){
        for (int i=0, j=-12 ; i<8; i++,j-=12){
            drawPilar((float) j);
        }
    }

    public void drawWindows(){
        float detail = 0.04f;
        float[] alt = {3,6};
        for (int i=0, z=-18 ; i<6 ; i++, z-=12){

            if(i==3) {
                alt[0] = 0;
            }else{
                alt[0] = 3;
            }
            /* Dentro Direita*/
            glPushMatrix();
            textures.get(6).bind();
            glBegin(GL_QUAD_STRIP);
            glTexCoord2f(0,0.f);
            glVertex3f(XSIZE-thickness-detail, alt[1],z-2.5f);
            glTexCoord2f(0,1.f);
            glVertex3f(XSIZE-thickness-detail, alt[0],z-2.5f);
            glTexCoord2f(1,0.f);
            glVertex3f(XSIZE-thickness-detail, alt[1],z);
            glTexCoord2f(1,1.f);
            glVertex3f(XSIZE-thickness-detail, alt[0],z);
            glEnd();

            glBegin(GL_QUAD_STRIP);
            glTexCoord2f(0,0.f);
            glVertex3f(XSIZE-thickness-detail, alt[1],z);
            glTexCoord2f(0,1.f);
            glVertex3f(XSIZE-thickness-detail, alt[0],z);
            glTexCoord2f(1,0.f);
            glVertex3f(XSIZE-thickness-detail, alt[1],z+2.5f);
            glTexCoord2f(1,1.f);
            glVertex3f(XSIZE-thickness-detail, alt[0],z+2.5f);
            glEnd();

            /* Fora */
            glPushMatrix();
            textures.get(6).bind();
            glBegin(GL_QUAD_STRIP);
            glTexCoord2f(0,0.f);
            glVertex3f(XSIZE+thickness+detail, alt[1],z-2.5f);
            glTexCoord2f(0,1.f);
            glVertex3f(XSIZE+thickness+detail, alt[0],z-2.5f);
            glTexCoord2f(1,0.f);
            glVertex3f(XSIZE+thickness+detail, alt[1],z);
            glTexCoord2f(1,1.f);
            glVertex3f(XSIZE+thickness+detail, alt[0],z);
            glEnd();

            glBegin(GL_QUAD_STRIP);
            glTexCoord2f(0,0.f);
            glVertex3f(XSIZE+thickness+detail, alt[1],z);
            glTexCoord2f(0,1.f);
            glVertex3f(XSIZE+thickness+detail, alt[0],z);
            glTexCoord2f(1,0.f);
            glVertex3f(XSIZE+thickness+detail, alt[1],z+2.5f);
            glTexCoord2f(1,1.f);
            glVertex3f(XSIZE+thickness+detail, alt[0],z+2.5f);
            glEnd();

            /*Direita*/
            textures.get(5).bind();
            glBegin(GL_POLYGON);
            for (int k = 0; k < 360; k++) {
                float angle = (float) (2*Math.PI*k/360);
                if(sin(angle) >= 0 ){
                    glTexCoord2f( 0.2f + ((float)cos(angle)+1)/3.25f,0.5f + 0.35f*(float)sin(angle));
                    glVertex3f(XSIZE-thickness-detail , alt[1] + (float)sin(angle)*0.75f ,  z -(float)cos(angle)*2.5f);
                }
            }
            glEnd();
            glBegin(GL_LINE_LOOP);
            for (int k = 0; k < 360; k++) {
                float angle = (float) (2*Math.PI*k/360);
                if(sin(angle) >= 0 ){
                    glVertex3f( XSIZE-thickness-detail, alt[1] + (float)sin(angle)*0.75f,z - (float)cos(angle)*2.5f);
                }
            }
            glEnd();
            glBegin(GL_POLYGON);
            for (int k = 0; k < 360; k++) {
                float angle = (float) (2*Math.PI*k/360);
                if(sin(angle) >= 0 ){
                    glTexCoord2f( 0.2f + ((float)cos(angle)+1)/3.25f,0.5f + 0.35f*(float)sin(angle));
                    glVertex3f(XSIZE+thickness+detail , alt[1] + (float)sin(angle)*0.75f ,  z -(float)cos(angle)*2.5f);
                }
            }
            glEnd();
            glBegin(GL_LINE_LOOP);
            for (int k = 0; k < 360; k++) {
                float angle = (float) (2*Math.PI*k/360);
                if(sin(angle) >= 0 ){
                    glVertex3f( XSIZE+thickness+detail, alt[1] + (float)sin(angle)*0.75f,z - (float)cos(angle)*2.5f);
                }
            }
            glEnd();

            if(i!=5){
                textures.get(6).bind();
                /* Dentro esquerda*/
                glPushMatrix();
                glBegin(GL_QUAD_STRIP);
                glTexCoord2f(0,0.f);
                glVertex3f(-XSIZE-thickness-detail, alt[1],z-2.5f);
                glTexCoord2f(0,1.f);
                glVertex3f(-XSIZE-thickness-detail, alt[0],z-2.5f);
                glTexCoord2f(1,0.f);
                glVertex3f(-XSIZE-thickness-detail, alt[1],z);
                glTexCoord2f(1,1.f);
                glVertex3f(-XSIZE-thickness-detail, alt[0],z);
                glEnd();

                glBegin(GL_QUAD_STRIP);
                glTexCoord2f(0,0.f);
                glVertex3f(-XSIZE-thickness-detail, alt[1],z);
                glTexCoord2f(0,1.f);
                glVertex3f(-XSIZE-thickness-detail, alt[0],z);
                glTexCoord2f(1,0.f);
                glVertex3f(-XSIZE-thickness-detail, alt[1],z+2.5f);
                glTexCoord2f(1,1.f);
                glVertex3f(-XSIZE-thickness-detail, alt[0],z+2.5f);
                glEnd();

                /* Fora */
                glPushMatrix();
                textures.get(6).bind();
                glBegin(GL_QUAD_STRIP);
                glTexCoord2f(0,0.f);
                glVertex3f(-XSIZE+thickness+detail, alt[1],z-2.5f);
                glTexCoord2f(0,1.f);
                glVertex3f(-XSIZE+thickness+detail, alt[0],z-2.5f);
                glTexCoord2f(1,0.f);
                glVertex3f(-XSIZE+thickness+detail, alt[1],z);
                glTexCoord2f(1,1.f);
                glVertex3f(-XSIZE+thickness+detail, alt[0],z);
                glEnd();

                glBegin(GL_QUAD_STRIP);
                glTexCoord2f(0,0.f);
                glVertex3f(-XSIZE+thickness+detail, alt[1],z);
                glTexCoord2f(0,1.f);
                glVertex3f(-XSIZE+thickness+detail, alt[0],z);
                glTexCoord2f(1,0.f);
                glVertex3f(-XSIZE+thickness+detail, alt[1],z+2.5f);
                glTexCoord2f(1,1.f);
                glVertex3f(-XSIZE+thickness+detail, alt[0],z+2.5f);
                glEnd();

                textures.get(5).bind();
                /* Esquerda */
                glBegin(GL_POLYGON);
                for (int k = 0; k < 360; k++) {
                    float angle = (float) (2*Math.PI*k/360);
                    if(sin(angle) >= 0 ){
                        glTexCoord2f( 0.2f + ((float)cos(angle)+1)/3.25f,0.5f + 0.35f*(float)sin(angle));
                        glVertex3f(-XSIZE-thickness-detail , alt[1] + (float)sin(angle)*0.75f ,  z -(float)cos(angle)*2.5f);
                    }
                }
                glEnd();

                glBegin(GL_LINE_LOOP);
                for (int k = 0; k < 360; k++) {
                    float angle = (float) (2*Math.PI*k/360);
                    if(sin(angle) >= 0 ){
                        glVertex3f( -XSIZE-thickness-detail, alt[1] + (float)sin(angle)*0.75f,z - (float)cos(angle)*2.5f);
                    }
                }
                glEnd();

                glBegin(GL_POLYGON);
                for (int k = 0; k < 360; k++) {
                    float angle = (float) (2*Math.PI*k/360);
                    if(sin(angle) >= 0 ){
                        glTexCoord2f( 0.2f + ((float)cos(angle)+1)/3.25f,0.5f + 0.35f*(float)sin(angle));
                        glVertex3f(-XSIZE+thickness+detail , alt[1] + (float)sin(angle)*0.75f ,  z -(float)cos(angle)*2.5f);
                    }
                }
                glEnd();
                glBegin(GL_LINE_LOOP);
                for (int k = 0; k < 360; k++) {
                    float angle = (float) (2*Math.PI*k/360);
                    if(sin(angle) >= 0 ){
                        glVertex3f( -XSIZE+thickness+detail, alt[1] + (float)sin(angle)*0.75f,z - (float)cos(angle)*2.5f);
                    }
                }
                glEnd();
            }

        }

        textures.get(17).bind();
        glBegin(GL_QUADS);
        glNormal3f(1,0,0);
        glTexCoord2f(0.03f,0.03f);
        glVertex3f(XSIZE-thickness-detail, 6, -9);
        glTexCoord2f(0.03f,0.97f);
        glVertex3f(XSIZE-thickness-detail, 3, -9);
        glTexCoord2f(0.98f,0.97f);
        glVertex3f(XSIZE-thickness-detail, 3, -3);
        glTexCoord2f(0.98f,0.03f);
        glVertex3f(XSIZE-thickness-detail, 6, -3);
        glEnd();
    }

    public void drawPilar(float z){

        glPushMatrix();
        textures.get(1).bind();
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(XSIZE, 0,z-0.5f);
        glTexCoord2f(1,0f);
        glVertex3f(XSIZE, YSIZE,z-0.5f);
        glTexCoord2f(0,0.25f);
        glVertex3f(XSIZE, 0, z+0.5f);
        glTexCoord2f(1,0.25f);
        glVertex3f(XSIZE,YSIZE,z+0.5f);
        glTexCoord2f(0,0.5f);
        glVertex3f(XSIZE-0.5f,0,z+0.5f);
        glTexCoord2f(1,0.5f);
        glVertex3f(XSIZE-0.5f,YSIZE,z+0.5f);
        glTexCoord2f(0,0.75f);
        glVertex3f(XSIZE-0.5f,0,z-0.5f);
        glTexCoord2f(1,0.75f);
        glVertex3f(XSIZE-0.5f,YSIZE,z-0.5f);
        glTexCoord2f(0,1);
        glVertex3f(XSIZE, 0,z-0.5f);
        glTexCoord2f(1,1);
        glVertex3f(XSIZE, YSIZE,z-0.5f);
        glEnd();

        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE, 0,z-0.5f);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE, YSIZE,z-0.5f);
        glTexCoord2f(0,0.25f);
        glVertex3f(-XSIZE, 0, z+0.5f);
        glTexCoord2f(1,0.25f);
        glVertex3f(-XSIZE,YSIZE,z+0.5f);
        glTexCoord2f(0,0.5f);
        glVertex3f(-XSIZE+0.5f,0,z+0.5f);
        glTexCoord2f(1,0.5f);
        glVertex3f(-XSIZE+0.5f,YSIZE,z+0.5f);
        glTexCoord2f(0,0.75f);
        glVertex3f(-XSIZE+0.5f,0,z-0.5f);
        glTexCoord2f(1,0.75f);
        glVertex3f(-XSIZE+0.5f,YSIZE,z-0.5f);
        glTexCoord2f(0,1.f);
        glVertex3f(-XSIZE, 0,z-0.5f);
        glTexCoord2f(1,1.f);
        glVertex3f(-XSIZE, YSIZE,z-0.5f);
        glEnd();
    }

    private void altarArch(){
        /* Arco, espaço do padre */
        textures.get(2).bind();
        glBegin(GL_QUAD_STRIP);
        for (int i = 0, j=0; i < 360; i++, j+=0.5) {
            float angle = (float) (2 * Math.PI * i / 360);
            if (-cos(angle) * ZSIZE / 2 - 2.5f * ZSIZE - 4.5f >= -3f * ZSIZE + 10 && sin(angle) * ZSIZE / 2 >= -13 && sin(angle) * ZSIZE / 2 <= 13) {
                glTexCoord2f(j,0);
                glVertex3f((float) sin(angle) * ZSIZE / 3f, 0, (float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f);
                glTexCoord2f(j, 1);
                glVertex3f((float) sin(angle) * ZSIZE / 3f, YSIZE, (float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f);
            }
            if(j==2) j=0;
        }
        glEnd();

        for (int i = 0, j=0; i < 360; i++, j+=0.5) {
            textures.get(18).bind();
            float angle = (float) (2 * Math.PI * i / 360);
            if ((float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f == -ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f ) {
               glBegin(GL_QUADS);
                glTexCoord2f(0,0);
                glVertex3f(((float) sin(angle) * ZSIZE / 3f) - 1.f, 4, ((float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f) + 0.5f );
                glTexCoord2f(1,0);
                glVertex3f(((float) sin(angle) * ZSIZE / 3f) + 1.f, 4, ((float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f) + 0.5f );
                glTexCoord2f(1,1);
                glVertex3f(((float) sin(angle) * ZSIZE / 3f) + 1.f, 6, ((float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f) + 0.5f );
                glTexCoord2f(0,1);
                glVertex3f(((float) sin(angle) * ZSIZE / 3f) - 1.f, 6, ((float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f) + 0.5f );
                glEnd();

                textures.get(19).bind();
                glBegin(GL_QUADS);
                glTexCoord2f(0,0);
                glVertex3f(((float) sin(angle) * ZSIZE / 3f) - 1.f, 6, ((float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f) + 0.5f );
                glTexCoord2f(1,0);
                glVertex3f(((float) sin(angle) * ZSIZE / 3f) + 1.f, 6, ((float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f) + 0.5f );
                glTexCoord2f(1,1);
                glVertex3f(((float) sin(angle) * ZSIZE / 3f) + 1.f, 8, ((float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f) + 0.5f );
                glTexCoord2f(0,1);
                glVertex3f(((float) sin(angle) * ZSIZE / 3f) - 1.f, 8, ((float) cos(angle) * ZSIZE / 3f - 3.5f * ZSIZE + 3 * ZSIZE / 4 - 0.5f) + 0.5f );
                glEnd();
            }
            if(j==2) j=0;
        }


    }

    public void structure() {
        /* Parede lateral direita */
        glPushMatrix();
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0);
        glVertex3f(XSIZE - thickness, 0, -thickness);
        glTexCoord2f(1,0);
        glVertex3f(XSIZE - thickness, 0, -3.5f * ZSIZE + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(XSIZE - thickness, YSIZE, -thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(XSIZE - thickness, YSIZE, -3.5f * ZSIZE + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(XSIZE + thickness, YSIZE, thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(XSIZE + thickness, YSIZE, -3.5f * ZSIZE - thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(XSIZE + thickness, 0, thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(XSIZE + thickness, 0, -3.5f * ZSIZE - thickness);
        glTexCoord2f(0,0);
        glVertex3f(XSIZE - thickness, 0, -thickness);
        glTexCoord2f(1,0);
        glVertex3f(XSIZE - thickness, 0, -3.5f * ZSIZE + thickness);
        glEnd();
        glPopMatrix();

        /* Parede lateral esquerda */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0);
        glVertex3f(-XSIZE - thickness, 0, thickness);
        glTexCoord2f(1,0);
        glVertex3f(-XSIZE - thickness, 0, -2.5f * ZSIZE + thickness);
        glTexCoord2f(0,0.2f);
        glVertex3f(-XSIZE - thickness, YSIZE, thickness);
        glTexCoord2f(1,0.2f);
        glVertex3f(-XSIZE - thickness, YSIZE, -2.5f * ZSIZE + thickness);
        glTexCoord2f(0,0.4f);
        glVertex3f(-XSIZE + thickness, YSIZE, -thickness);
        glTexCoord2f(1,0.4f);
        glVertex3f(-XSIZE + thickness, YSIZE, -2.5f * ZSIZE - thickness);
        glTexCoord2f(0,0.6f);
        glVertex3f(-XSIZE + thickness, 0, -thickness);
        glTexCoord2f(1,0.6f);
        glVertex3f(-XSIZE + thickness, 0, -2.5f * ZSIZE - thickness);
        glTexCoord2f(0,0);
        glVertex3f(-XSIZE - thickness, 0, thickness);
        glTexCoord2f(1,0);
        glVertex3f(-XSIZE - thickness, 0, -2.5f * ZSIZE + thickness);

        glEnd();

        /* Parede salinha esquerda */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0);
        glVertex3f(-XSIZE - thickness, 0, -2.5f * ZSIZE + thickness);
        glTexCoord2f(1,0);
        glVertex3f(-2 * XSIZE - thickness, 0, -2.5f * ZSIZE + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(-XSIZE - thickness, YSIZE, -2.5f * ZSIZE + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(-2 * XSIZE - thickness, YSIZE, -2.5f * ZSIZE + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(-XSIZE + thickness, YSIZE, -2.5f * ZSIZE - thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(-2 * XSIZE + thickness, YSIZE, -2.5f * ZSIZE - thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(-XSIZE + thickness, 0, -2.5f * ZSIZE - thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(-2 * XSIZE + thickness, 0, -2.5f * ZSIZE - thickness);
        glTexCoord2f(0,0);
        glVertex3f(-XSIZE - thickness, 0, -2.5f * ZSIZE + thickness);
        glTexCoord2f(1,0);
        glVertex3f(-2 * XSIZE - thickness, 0, -2.5f * ZSIZE + thickness);
        glEnd();

        /* Parede salinha direita */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE - thickness, 0, -3f * ZSIZE + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-2 * XSIZE - thickness, 0, -3f * ZSIZE + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(-XSIZE - thickness, YSIZE, -3f * ZSIZE + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(-2 * XSIZE - thickness, YSIZE, -3f * ZSIZE + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(-XSIZE + thickness, YSIZE, -3f * ZSIZE - thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(-2 * XSIZE + thickness, YSIZE, -3f * ZSIZE - thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(-XSIZE + thickness, 0, -3f * ZSIZE - thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(-2 * XSIZE + thickness, 0, -3f * ZSIZE - thickness);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE - thickness, 0, -3f * ZSIZE + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-2 * XSIZE - thickness, 0, -3f * ZSIZE + thickness);
        glEnd();

        /* Parede fundão */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(XSIZE - thickness, 0, -3.5f * ZSIZE + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE + thickness, 0, -3.5f * ZSIZE + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(XSIZE - thickness, YSIZE, -3.5f * ZSIZE + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(-XSIZE + thickness, YSIZE, -3.5f * ZSIZE + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(XSIZE + thickness, YSIZE, -3.5f * ZSIZE - thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(-XSIZE - thickness, YSIZE, -3.5f * ZSIZE - thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(XSIZE + thickness, 0, -3.5f * ZSIZE - thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(-XSIZE - thickness, 0, -3.5f * ZSIZE - thickness);
        glTexCoord2f(0,0.f);
        glVertex3f(XSIZE - thickness, 0, -3.5f * ZSIZE + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE + thickness, 0, -3.5f * ZSIZE + thickness);
        glEnd();

        /* Parede salinha fundo */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(-2 * XSIZE - thickness, 0, -2.5f * ZSIZE + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-2 * XSIZE - thickness, 0, -3f * ZSIZE - thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(-2 * XSIZE - thickness, YSIZE, -2.5f * ZSIZE + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(-2 * XSIZE - thickness, YSIZE, -3f * ZSIZE - thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(-2 * XSIZE + thickness, YSIZE, -2.5f * ZSIZE - thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(-2 * XSIZE + thickness, YSIZE, -3f * ZSIZE + thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(-2 * XSIZE + thickness, 0, -2.5f * ZSIZE - thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(-2 * XSIZE + thickness, 0, -3 * ZSIZE + thickness);
        glTexCoord2f(0,0.f);
        glVertex3f(-2 * XSIZE - thickness, 0, -2.5f * ZSIZE + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-2 * XSIZE - thickness, 0, -3f * ZSIZE - thickness);
        glEnd();

        /* Parede interna esquerda*/
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE - thickness, 0, -3.5f * ZSIZE);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE - thickness, YSIZE, -3.5f * ZSIZE);
        glTexCoord2f(0,0.25f);
        glVertex3f(-XSIZE - thickness, 0, -3.5f * ZSIZE + 27);
        glTexCoord2f(1,0.25f);
        glVertex3f(-XSIZE - thickness, YSIZE, -3.5f * ZSIZE + 27);
        glTexCoord2f(0,0.5f);
        glVertex3f(-XSIZE + thickness, 0, -3.5f * ZSIZE + 27);
        glTexCoord2f(1,0.5f);
        glVertex3f(-XSIZE + thickness, YSIZE, -3.5f * ZSIZE + 27);
        glTexCoord2f(0,0.75f);
        glVertex3f(-XSIZE + thickness, 0, -3.5f * ZSIZE);
        glTexCoord2f(1,0.75f);
        glVertex3f(-XSIZE + thickness, YSIZE, -3.5f * ZSIZE);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE - thickness, 0, -3.5f * ZSIZE);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE - thickness, YSIZE, -3.5f * ZSIZE);
        glEnd();

        /* cima porta oratório */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE - thickness, 5, -2.5f * ZSIZE);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE - thickness, YSIZE, -2.5f * ZSIZE);
        glTexCoord2f(0,0.1f);
        glVertex3f(-XSIZE - thickness, 5, -3.5f * ZSIZE + 27);
        glTexCoord2f(1,0.1f);
        glVertex3f(-XSIZE - thickness, YSIZE, -3.5f * ZSIZE + 27);
        glTexCoord2f(0,0.2f);
        glVertex3f(-XSIZE + thickness, 5, -3.5f * ZSIZE + 27);
        glTexCoord2f(1,0.2f);
        glVertex3f(-XSIZE + thickness, YSIZE, -3.5f * ZSIZE + 27);
        glTexCoord2f(0,0.3f);
        glVertex3f(-XSIZE + thickness, 5, -2.5f * ZSIZE);
        glTexCoord2f(1,0.3f);
        glVertex3f(-XSIZE + thickness, YSIZE, -2.5f * ZSIZE);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE - thickness, 5, -2.5f * ZSIZE);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE - thickness, YSIZE, -2.5f * ZSIZE);
        glEnd();

        /* Parede esquerda portinha esquerda */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0);
        glVertex3f(-XSIZE, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0);
        glVertex3f(-XSIZE, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(-XSIZE + 2, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(-XSIZE + 2, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(-XSIZE + 2, 0, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(-XSIZE + 2, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(-XSIZE, 0, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(-XSIZE, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,0);
        glVertex3f(-XSIZE, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0);
        glVertex3f(-XSIZE, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glEnd();

        /* Parede cima portinha direita */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(XSIZE - 2, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(XSIZE - 5, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(XSIZE - 2, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(XSIZE - 5, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(XSIZE - 2, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(XSIZE - 5, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(XSIZE - 2, 5f, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(XSIZE - 5, 5f, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,1.f);
        glVertex3f(XSIZE - 2, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,1.f);
        glVertex3f(XSIZE - 5, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.f);
        glVertex3f(XSIZE - 2, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(XSIZE - 5, 5f, -3.5f * ZSIZE + 17 + thickness);
        glEnd();

        /* Parede cima portinha esquerda */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE + 2, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE + 5, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(-XSIZE + 2, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(-XSIZE + 5, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(-XSIZE + 2, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(-XSIZE + 5, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(-XSIZE + 2, 5f, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(-XSIZE + 5, 5f, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,1.f);
        glVertex3f(-XSIZE + 2, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,1.f);
        glVertex3f(-XSIZE + 5, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE + 2, 5f, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE + 5, 5f, -3.5f * ZSIZE + 17 + thickness);
        glEnd();

        /* Parede direita portinha esquerda */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE + 11.4f, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE + 11.4f, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(-XSIZE + 5, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(-XSIZE + 5, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(-XSIZE + 5, 0, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(-XSIZE + 5, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(-XSIZE + 11.4f, 0, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(-XSIZE + 11.4f, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,1.f);
        glVertex3f(-XSIZE + 11.4f, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,1.f);
        glVertex3f(-XSIZE + 11.4f, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.f);
        glVertex3f(-XSIZE + 11.4f, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(-XSIZE + 11.4f, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glEnd();

        /* Parede direita portinha direita */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(XSIZE, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(XSIZE, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(XSIZE - 2, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(XSIZE - 2, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(XSIZE - 2, 0, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(XSIZE - 2, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(XSIZE, 0, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(XSIZE, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,0.f);
        glVertex3f(XSIZE, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(XSIZE, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glEnd();

        /* Parede esquerda portinha direita */
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0.f);
        glVertex3f(XSIZE - 11.4f, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(XSIZE - 11.4f, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.25f);
        glVertex3f(XSIZE - 5, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.25f);
        glVertex3f(XSIZE - 5, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.5f);
        glVertex3f(XSIZE - 5, 0, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.5f);
        glVertex3f(XSIZE - 5, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,0.75f);
        glVertex3f(XSIZE - 11.4f, 0, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(1,0.75f);
        glVertex3f(XSIZE - 11.4f, YSIZE, -3.5f * ZSIZE + 17 - 5 * thickness);
        glTexCoord2f(0,1.f);
        glVertex3f(XSIZE - 11.4f, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,1.f);
        glVertex3f(XSIZE - 11.4f, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(0,0.f);
        glVertex3f(XSIZE - 11.4f, 0, -3.5f * ZSIZE + 17 + thickness);
        glTexCoord2f(1,0.f);
        glVertex3f(XSIZE - 11.4f, YSIZE, -3.5f * ZSIZE + 17 + thickness);
        glEnd();

        /* Frente Portas (cima)*/
        glBegin(GL_POLYGON);
        glTexCoord2f(0,1f);
        glVertex3f(-XSIZE, 5.5f, 0);
        glTexCoord2f(0,0.5f);
        glVertex3f(-XSIZE, YSIZE, 0);
        glTexCoord2f(0.5f,0.f);
        glVertex3f(0, YSIZE + 5, 0);
        glTexCoord2f(1,0.5f);
        glVertex3f(XSIZE, YSIZE, 0);
        glTexCoord2f(1,1.f);
        glVertex3f(XSIZE, 5.5f, 0);

        glEnd();

        /* Frente portas esquerda*/
        glBegin(GL_QUADS);
        glTexCoord2f(0,0);
        glVertex3f(-XSIZE, 0, 0);
        glTexCoord2f(0,0.08f);
        glVertex3f(-XSIZE + 5, 0, 0);
        glTexCoord2f(1,0.08f);
        glVertex3f(-XSIZE + 5, 6, 0);
        glTexCoord2f(1,0f);
        glVertex3f(-XSIZE, 6, 0);

        glEnd();

        /* Frente portas direita*/
        glBegin(GL_QUADS);
        glTexCoord2f(0,0.0f);
        glVertex3f(XSIZE, 0, 0);
        glTexCoord2f(0,0.08f);
        glVertex3f(XSIZE - 5, 0, 0);
        glTexCoord2f(1,0.08f);
        glVertex3f(XSIZE - 5, 6, 0);
        glTexCoord2f(1,0.0f);
        glVertex3f(XSIZE, 6, 0);

        glEnd();


        glBegin(GL_QUADS);
        glTexCoord2f(0,0.0f);
        glVertex3f(XSIZE - 10, 0, 0);
        glTexCoord2f(0,0.08f);
        glVertex3f(XSIZE - 17, 0, 0);
        glTexCoord2f(1,0.08f);
        glVertex3f(XSIZE - 17, 6, 0);
        glTexCoord2f(1,0.0f);
        glVertex3f(XSIZE - 10, 6, 0);

        glEnd();

        glBegin(GL_QUADS);
        glTexCoord2f(0,0.0f);
        glVertex3f(-XSIZE + 10, 0, 0);
        glTexCoord2f(0,0.08f);
        glVertex3f(-XSIZE + 17, 0, 0);
        glTexCoord2f(1,0.08f);
        glVertex3f(-XSIZE + 17, 6, 0);
        glTexCoord2f(1,0.0f);
        glVertex3f(-XSIZE + 10, 6, 0);

        glEnd();

    }

    private void drawGroundChurch(){
        glColor3f(0.75f,0.75f,0.75f);
        textures.get(8).bind();

        for(int i=(int) -XSIZE; i< XSIZE; i+=4 ) {
            for (int j = 0; j >= -3.5f*ZSIZE; j -= 4) {
                glBegin(GL_QUADS);
                glNormal3f(0,1,0);
                glTexCoord2f(0, 0);
                glVertex3f(i, 0.f, j-5);
                glTexCoord2f(0, 1);
                glVertex3f(i, 0.f, j);
                glTexCoord2f(1, 1);
                glVertex3f(i+4, 0.0f, j);
                glTexCoord2f(1, 0);
                glVertex3f(i+4, 0.f, j-5);
                glEnd();
            }
        }

        for(int i = (int) (-2*XSIZE); i< -XSIZE; i+=4 ) {
            for (int j = (int) (-2.5f*ZSIZE); j >= -3.f*ZSIZE; j -= 5) {
                glBegin(GL_QUADS);
                glNormal3f(0,1,0);
                glTexCoord2f(0, 0);
                glVertex3f(i, 0.f, j-5);
                glTexCoord2f(0, 1);
                glVertex3f(i, 0.f, j);
                glTexCoord2f(1, 1);
                glVertex3f(i+4, 0.0f, j);
                glTexCoord2f(1, 0);
                glVertex3f(i+4, 0.f, j-5);
                glEnd();
            }
        }
        glLineWidth(1);
        for(int i = (int) (-XSIZE); i<= XSIZE; i+=4 ) {
            glBegin(GL_LINES);
            glVertex3f(i, 0.01f, 0);
            glVertex3f(i, 0.01f, -3.5f*ZSIZE);
            glEnd();
        }
        for (int j = (int) 0; j >= -3.5f*ZSIZE; j -= 5) {
            glBegin(GL_LINES);
            glVertex3f(-XSIZE, 0.01f, j);
            glVertex3f(XSIZE, 0.01f, j);
            glEnd();
        }


    }

    private void putTelhado(){
        textures.get(14).bind();
        glColor4f(0.226f,0.114f,0.091f,1);
        float[] color = {0.226f,0.114f,0.091f, .0f};
//        int k=15;
        for(int i=0, k=15; i<XSIZE; i+=4, k--){
            for(int j=0; j>-3.5*ZSIZE ; j-=5){
//                glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, color);
                //right
                glBegin(GL_QUADS);
                glNormal3f(1,1,0);
                glTexCoord2f(0,0);
                glVertex3f(i, k,j);
                glTexCoord2f(1,0);
                glVertex3f(i, k, j-5);
                glTexCoord2f(1,1);
                glVertex3f(i+4, k-1,j-5);
                glTexCoord2f(0,1);
                glVertex3f(i+4, k-1, j);
                glEnd();

                //left
                glBegin(GL_QUADS);
                glNormal3f(-1,1,0);
                glTexCoord2f(0,0);
                glVertex3f(-i, k,j);
                glTexCoord2f(0,1);
                glVertex3f(-i-4, k-1, j);
                glTexCoord2f(1,1);
                glVertex3f(-i-4, k-1,j-5);
                glTexCoord2f(1,0);
                glVertex3f(-i, k, j-5);
                glEnd();
            }
        }
    }

    private void putChairs(){
        for(Chair chair : chairs){
            chair.drawChair();
        }
    }
//
    private void putLights(){
        for(Light light : lights) {
            light.drawLights();
        }
    }

}
