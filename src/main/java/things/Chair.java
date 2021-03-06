package things;

import utils.Texture;

import java.io.IOException;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Chair {

    float x;
    float z;
    Texture texture;
    static final float thickness = 0.05f;

    public Chair(Texture texture, float x, float z){
        this.texture = texture;
        this.x = x;
        this.z = z;
    }

    public void drawChair(){
        glPushMatrix();
        texture.bind();
        glColor4f(0.6f,0.432f,0.264f,1.f);
        float[] chairColor = {0.6f, 0.432f, 0.364f, 1.0f};
        float[] specref = {1.0f, 1.0f, 1.0f, 1.0f};
//        glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, chairColor);
//        glMaterialfv(GL_FRONT, GL_SPECULAR,specref);
//        glMateriali(GL_FRONT,GL_SHININESS,20);
        hasteChairCenter();
        hasteChair();
        assento();
        encosto();
        apoio();
        hasteOratorio();
        glPopMatrix();
    }

    private void encosto() {
        /* costa */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(0,0,1);
        glTexCoord2f(0,0);
        glVertex3f(x - 3 - thickness, 2.7f, z );
        glTexCoord2f(1,0);
        glVertex3f(x + 3 + thickness, 2.7f, z );
        glTexCoord2f(1,1);
        glVertex3f(x + 3 + thickness, 2, z );
        glTexCoord2f(0,1);
        glVertex3f(x - 3 - thickness, 2, z );
        glEnd();
        glPopMatrix();

        /* frente */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(0,0,-1);
        glTexCoord2f(0,0);
        glVertex3f(x - 3 - thickness, 2.7f, z - 2*thickness);
        glTexCoord2f(1,0);
        glVertex3f(x + 3 + thickness, 2.7f, z - 2*thickness);
        glTexCoord2f(1,1);
        glVertex3f(x + 3 + thickness, 2, z - 2*thickness);
        glTexCoord2f(0,1);
        glVertex3f(x - 3 - thickness, 2, z - 2*thickness);
        glEnd();
        glPopMatrix();

        /* cima */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(0,1,0);
        glTexCoord2f(0,0);
        glVertex3f(x - 3 - thickness, 2.7f, z - 2*thickness);
        glTexCoord2f(1,0);
        glVertex3f(x - 3 - thickness, 2.7f, z );
        glTexCoord2f(1,1);
        glVertex3f(x + 3 + thickness, 2.7f, z );
        glTexCoord2f(0,1);
        glVertex3f(x + 3 + thickness, 2.7f, z - 2*thickness);
        glEnd();
        glPopMatrix();

        /* baixo */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(0,-1,0);
        glTexCoord2f(0,0);
        glVertex3f(x - 3 - thickness, 2f, z - 2*thickness);
        glTexCoord2f(1,0);
        glVertex3f(x - 3 - thickness, 2f, z );
        glTexCoord2f(1,1);
        glVertex3f(x + 3 + thickness, 2f, z );
        glTexCoord2f(0,1);
        glVertex3f(x + 3 + thickness, 2f, z - 2*thickness);
        glEnd();
        glPopMatrix();

    }

    private void assento(){

        /* Cima */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(0,1,0);
        glTexCoord2f(0,0);
        glVertex3f(x-3+thickness, 1.5f-thickness, z-2f);
        glTexCoord2f(0,1);
        glVertex3f(x-3+thickness, 1.5f-thickness, z-0.1f);
        glTexCoord2f(1,1);
        glVertex3f(x+3-thickness, 1.5f-thickness, z-0.1f);
        glTexCoord2f(1,0);
        glVertex3f(x+3-thickness, 1.5f-thickness, z-2f);
        glEnd();
        glPopMatrix();

        /* baixo */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(0,-1,0);
        glTexCoord2f(0,0);
        glVertex3f(x-3+thickness, 1.5f-3*thickness, z-2f);
        glTexCoord2f(0,1);
        glVertex3f(x-3+thickness, 1.5f-3*thickness, z-0.1f);
        glTexCoord2f(1,1);
        glVertex3f(x+3-thickness, 1.5f-3*thickness, z-0.1f);
        glTexCoord2f(1,0);
        glVertex3f(x+3-thickness, 1.5f-3*thickness, z-2f);
        glEnd();
        glPopMatrix();

        /* trás */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(0,0,1);
        glTexCoord2f(0,0);
        glVertex3f(x-3+thickness, 1.5f-thickness, z-0.1f);
        glTexCoord2f(0,1);
        glVertex3f(x-3+thickness, 1.5f-3*thickness, z-0.1f);
        glTexCoord2f(1,1);
        glVertex3f(x+3-thickness, 1.5f-3*thickness, z-0.1f);
        glTexCoord2f(1,0);
        glVertex3f(x+3-thickness, 1.5f-thickness, z-0.1f);
        glEnd();
        glPopMatrix();

        /* frente */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(0,0,-1);
        glNormal3f(0,0,-1);
        glTexCoord2f(0,0);
        glVertex3f(x-3+thickness, 1.5f-thickness, z-2f);
        glTexCoord2f(0,1);
        glVertex3f(x-3+thickness, 1.5f-3*thickness, z-2f);
        glTexCoord2f(1,1);
        glVertex3f(x+3-thickness, 1.5f-3*thickness, z-2f);
        glTexCoord2f(1,0);
        glVertex3f(x+3-thickness, 1.5f-thickness, z-2f);
        glEnd();
        glPopMatrix();
    }

    private void hasteChairCenter() {
        glPushMatrix();
        glBegin(GL_POLYGON);
        glNormal3f(-1,0,0);
        glTexCoord2f(0,1);
        glVertex3f(x-thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x-thickness, 0,z-2);
        glTexCoord2f(1,0.4f);
        glVertex3f(x-thickness,1.5f-3*thickness,z-2 );
        glTexCoord2f(0.2f,0.4f);
        glVertex3f(x-thickness, 1.5f-3*thickness,z-0.1f);
        glTexCoord2f(0.2f,0);
        glVertex3f(x-thickness,2.7f,z-0.1f);
        glTexCoord2f(0,0);
        glVertex3f(x-thickness,2.7f,z);
        glEnd();
        glPopMatrix();

        glPushMatrix();
        glBegin(GL_POLYGON);
        glNormal3f(1,0,0);
        glTexCoord2f(0,1);
        glVertex3f(x+thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x+thickness, 0,z-2);
        glTexCoord2f(1,0.4f);
        glVertex3f(x+thickness,1.5f-3*thickness,z-2 );
        glTexCoord2f(0.2f,0.4f);
        glVertex3f(x+thickness, 1.5f-3*thickness,z-0.1f);
        glTexCoord2f(0.2f,0);
        glVertex3f(x+thickness,2.7f,z-0.1f);
        glTexCoord2f(0,0);
        glVertex3f(x+thickness,2.7f,z);
        glEnd();
        glPopMatrix();

        glBegin(GL_QUAD_STRIP);
        glNormal3f(0,-1,0);         //1
        glTexCoord2f(0, 1);
        glVertex3f(x-thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x+thickness, 0, z);

        glNormal3f(0,0,-1);         //2
        glTexCoord2f(0,0.25f);
        glVertex3f(x-thickness, 0,z-2);
        glTexCoord2f(1,0.25f);
        glVertex3f(x+thickness, 0,z-2);

        glNormal3f(0,1,0);          //3
        glTexCoord2f(0,0.5f);
        glVertex3f(x-thickness,1.5f-3*thickness,z-2 );
        glTexCoord2f(1,0.5f);
        glVertex3f(x+thickness,1.5f-3*thickness,z-2 );

        glNormal3f(0,0,-1);         //4
        glTexCoord2f(0,0.75f);
        glVertex3f(x-thickness, 1.5f-3*thickness,z-0.1f);
        glTexCoord2f(1,0.75f);
        glVertex3f(x+thickness, 1.5f-3*thickness,z-0.1f);

        glNormal3f(0,1,0);          //5
        glTexCoord2f(0,0.8f);
        glVertex3f(x-thickness,2.f,z-0.1f);
        glTexCoord2f(1,0.8f);
        glVertex3f(x+thickness,2.f,z-0.1f);

        glTexCoord2f(0,0.9f);                //6
        glVertex3f(x-thickness,2.f,z);
        glTexCoord2f(0,0.9f);
        glVertex3f(x+thickness,2.f,z);

        glTexCoord2f(0,1.f);
        glVertex3f(x-thickness,0.f,z);
        glTexCoord2f(0,1.f);
        glVertex3f(x+thickness,0.f,z);
        glEnd();

    }

    private void hasteChair(){
/*--------------- Haste da esquerda -------------------------*/
        /* esquerda */
        glPushMatrix();
        glBegin(GL_POLYGON);
        glNormal3f(-1,0,0);
        glTexCoord2f(0,1);
        glVertex3f(x-3-thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x-3-thickness, 0,z-2);
        glTexCoord2f(1,0.4f);
        glVertex3f(x-3-thickness,1.5f,z-2 );
        glTexCoord2f(0.2f,0.4f);
        glVertex3f(x-3-thickness, 1.5f,z-0.5f);
        glTexCoord2f(0.2f,0);
        glVertex3f(x-3-thickness,3,z-0.5f);
        glTexCoord2f(0,0);
        glVertex3f(x-3-thickness,3,z);
        glEnd();
        glPopMatrix();

        /* direita */
        glPushMatrix();
        glBegin(GL_POLYGON);
        glNormal3f(1,0,0);
        glTexCoord2f(0,1);
        glVertex3f(x-3+thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x-3+thickness, 0,z-2);
        glTexCoord2f(1,0.4f);
        glVertex3f(x-3+thickness,1.5f,z-2 );
        glTexCoord2f(0.2f,0.4f);
        glVertex3f(x-3+thickness, 1.5f,z-0.5f);
        glTexCoord2f(0.2f,0);
        glVertex3f(x-3+thickness,3,z-0.5f);
        glTexCoord2f(0,0);
        glVertex3f(x-3+thickness,3,z);
        glEnd();
        glPopMatrix();

        /* tira entre laterais */
        glBegin(GL_QUAD_STRIP);
        glNormal3f(0,-1,0);
        glTexCoord2f(0,1);                  //1
        glVertex3f(x-3-thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x-3+thickness, 0, z);

        glNormal3f(0,0,-1);
        glTexCoord2f(0,0.8f);               //2
        glVertex3f(x-3-thickness, 0,z-2);
        glTexCoord2f(1,0.8f);
        glVertex3f(x-3+thickness, 0,z-2);

        glNormal3f(0,1,0);
        glTexCoord2f(0,0.6f);               //3
        glVertex3f(x-3-thickness,1.5f,z-2 );
        glTexCoord2f(1,0.6f);
        glVertex3f(x-3+thickness,1.5f,z-2 );

        glNormal3f(0,0,-1);
        glTexCoord2f(0,0.4f);               //4
        glVertex3f(x-3-thickness, 1.5f,z-0.5f);
        glTexCoord2f(1,0.4f);
        glVertex3f(x-3+thickness, 1.5f,z-0.5f);

        glNormal3f(0,1,0);
        glTexCoord2f(0,0.2f);               //5
        glVertex3f(x-3-thickness,3,z-0.5f);
        glTexCoord2f(1,0.2f);
        glVertex3f(x-3+thickness,3,z-0.5f);

        glNormal3f(0,0,1);
        glTexCoord2f(0,0f);                 //6
        glVertex3f(x-3-thickness,3,z);
        glTexCoord2f(1,0f);
        glVertex3f(x-3+thickness,3,z);
        glTexCoord2f(0,1);
        glVertex3f(x-3-thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x-3+thickness, 0, z);
        glEnd();

        /*--------------- Haste da direita -------------------------*/
        /* direita */
        glPushMatrix();
        glBegin(GL_POLYGON);
        glNormal3f(1,0,0);
        glTexCoord2f(0,1);
        glVertex3f(x+3+thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x+3+thickness, 0,z-2);
        glTexCoord2f(1,0.4f);
        glVertex3f(x+3+thickness,1.5f,z-2 );
        glTexCoord2f(0.2f,0.4f);
        glVertex3f(x+3+thickness, 1.5f,z-0.5f);
        glTexCoord2f(0.2f,0);
        glVertex3f(x+3+thickness,3,z-0.5f);
        glTexCoord2f(0,0);
        glVertex3f(x+3+thickness,3,z);
        glEnd();
        glPopMatrix();

        /* esquerda */
        glPushMatrix();
        glBegin(GL_POLYGON);
        glNormal3f(-1,0,0);
        glTexCoord2f(0,1);
        glVertex3f(x+3-thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x+3-thickness, 0,z-2);
        glTexCoord2f(1,0.4f);
        glVertex3f(x+3-thickness,1.5f,z-2 );
        glTexCoord2f(0.2f,0.4f);
        glVertex3f(x+3-thickness, 1.5f,z-0.5f);
        glTexCoord2f(0.2f,0);
        glVertex3f(x+3-thickness,3,z-0.5f);
        glTexCoord2f(0,0);
        glVertex3f(x+3-thickness,3,z);
        glEnd();
        glPopMatrix();

        /* tira entre laterais */
        glBegin(GL_QUAD_STRIP);
        glNormal3f(0,-1,0);
        glTexCoord2f(0,1);                          //1
        glVertex3f(x+3-thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x+3+thickness, 0, z);

        glNormal3f(0,0,-1);
        glTexCoord2f(0,0.8f);                       //2
        glVertex3f(x+3-thickness, 0,z-2);
        glTexCoord2f(1,0.8f);
        glVertex3f(x+3+thickness, 0,z-2);

        glNormal3f(0,1,0);
        glTexCoord2f(0,0.6f);                       //3
        glVertex3f(x+3-thickness,1.5f,z-2 );
        glTexCoord2f(1,0.6f);
        glVertex3f(x+3+thickness,1.5f,z-2 );

        glNormal3f(0,0,-1);
        glTexCoord2f(0,0.4f);                       //4
        glVertex3f(x+3-thickness, 1.5f,z-0.5f);
        glTexCoord2f(1,0.4f);
        glVertex3f(x+3+thickness, 1.5f,z-0.5f);

        glNormal3f(0,1,0);
        glTexCoord2f(0,0.2f);                       //5
        glVertex3f(x+3-thickness,3,z-0.5f);
        glTexCoord2f(1,0.2f);
        glVertex3f(x+3+thickness,3,z-0.5f);

        glNormal3f(0,0,1);
        glTexCoord2f(0,0f);                         //6
        glVertex3f(x+3-thickness,3,z);
        glTexCoord2f(1,0f);
        glVertex3f(x+3+thickness,3,z);
        glTexCoord2f(0,1);
        glVertex3f(x+3-thickness, 0, z);
        glTexCoord2f(1,1);
        glVertex3f(x+3+thickness, 0, z);
        glEnd();
    }

    private void apoio(){

        glPushMatrix();
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0,0);
        glVertex3f(x-3+thickness, 0.6f-2*thickness, z+.5f);
        glTexCoord2f(1,0);
        glVertex3f(x+3-thickness, 0.6f-2*thickness, z+.5f);
        glTexCoord2f(0,0.25f);
        glVertex3f(x-3+thickness, 0.6f-thickness, z+.5f);
        glTexCoord2f(1,0.25f);
        glVertex3f(x+3-thickness, 0.6f-thickness, z+.5f);
        glTexCoord2f(0,0.5f);
        glVertex3f(x-3+thickness, 0.6f-thickness, z);
        glTexCoord2f(1,0.5f);
        glVertex3f(x+3-thickness, 0.6f-thickness, z);
        glTexCoord2f(0,0.75f);
        glVertex3f(x-3+thickness, 0.6f-2*thickness, z);
        glTexCoord2f(1,0.75f);
        glVertex3f(x+3-thickness, 0.6f-2*thickness, z);
        glTexCoord2f(0,1f);
        glVertex3f(x-3+thickness, 0.6f-2*thickness, z+.5f);
        glTexCoord2f(1,1f);
        glVertex3f(x+3-thickness, 0.6f-2*thickness, z+.5f);
        glEnd();
        glPopMatrix();

    }

    private void hasteOratorio(){
/* -------------------- Esquerda ------------------------------- */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(-1,0,0);
        glTexCoord2f(0,0f);
        glVertex3f(x-3-thickness, 0, z+.5f);
        glTexCoord2f(0,1f);
        glVertex3f(x-3-thickness, 0,z);
        glTexCoord2f(1,1f);
        glVertex3f(x-3-thickness,0.6f-thickness,z );
        glTexCoord2f(1,0f);
        glVertex3f(x-3-thickness, 0.6f-thickness,z+.5f);
        glEnd();
        glPopMatrix();

        glPushMatrix();
        glBegin(GL_POLYGON);
        glNormal3f(1,0,0);
        glTexCoord2f(0,0);
        glVertex3f(x-3+thickness, 0, z+.5f);
        glTexCoord2f(0,1);
        glVertex3f(x-3+thickness, 0,z);
        glTexCoord2f(1,1);
        glVertex3f(x-3+thickness,0.6f-thickness,z );
        glTexCoord2f(1,0);
        glVertex3f(x-3+thickness, 0.6f-thickness,z+.5f);
        glEnd();
        glPopMatrix();

        glPushMatrix();
        glBegin(GL_QUAD_STRIP);
        glNormal3f(0,-1,0);
        glTexCoord2f(0,0);
        glVertex3f(x-3-thickness, 0, z+.5f);
        glTexCoord2f(1,0);
        glVertex3f(x-3+thickness, 0, z+.5f);

        glNormal3f(0,0,-1);
        glTexCoord2f(0,0.25f);
        glVertex3f(x-3-thickness, 0,z);
        glTexCoord2f(1,0.25f);
        glVertex3f(x-3+thickness, 0,z);

        glNormal3f(0,1,0);
        glTexCoord2f(0,0.5f);
        glVertex3f(x-3-thickness,0.6f-thickness,z );
        glTexCoord2f(1,0.5f);
        glVertex3f(x-3+thickness,0.6f-thickness,z );

        glNormal3f(0,0,1);
        glTexCoord2f(0,0.75f);
        glVertex3f(x-3-thickness, 0.6f-thickness,z+.5f);
        glTexCoord2f(1,0.75f);
        glVertex3f(x-3+thickness, 0.6f-thickness,z+.5f);
        glTexCoord2f(0,0);
        glVertex3f(x-3-thickness, 0, z+.5f);
        glTexCoord2f(1,0);
        glVertex3f(x-3+thickness, 0, z+.5f);
        glEnd();
        glPopMatrix();

        /* -------------------- direita ------------------------------- */
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3f(-1,0,0);
        glTexCoord2f(0,0f);
        glVertex3f(x+3-thickness, 0, z+.5f);
        glTexCoord2f(0,1f);
        glVertex3f(x+3-thickness, 0,z);
        glTexCoord2f(1,1f);
        glVertex3f(x+3-thickness,0.6f-thickness,z );
        glTexCoord2f(1,0f);
        glVertex3f(x+3-thickness, 0.6f-thickness,z+.5f);
        glEnd();
        glPopMatrix();

        glPushMatrix();
        glBegin(GL_POLYGON);
        glNormal3f(1,0,0);
        glTexCoord2f(0,0);
        glVertex3f(x+3+thickness, 0, z+.5f);
        glTexCoord2f(0,1);
        glVertex3f(x+3+thickness, 0,z);
        glTexCoord2f(1,1);
        glVertex3f(x+3+thickness,0.6f-thickness,z );
        glTexCoord2f(1,0);
        glVertex3f(x+3+thickness, 0.6f-thickness,z+.5f);
        glEnd();
        glPopMatrix();

        glPushMatrix();
        glBegin(GL_QUAD_STRIP);
        glNormal3f(0,-1,0);
        glTexCoord2f(0,0);
        glVertex3f(x+3-thickness, 0, z+.5f);
        glTexCoord2f(1,0);
        glVertex3f(x+3+thickness, 0, z+.5f);

        glNormal3f(0,0,-1);
        glTexCoord2f(0,0.25f);
        glVertex3f(x+3-thickness, 0,z);
        glTexCoord2f(1,0.25f);
        glVertex3f(x+3+thickness, 0,z);

        glNormal3f(0,1,0);
        glTexCoord2f(0,0.5f);
        glVertex3f(x+3-thickness,0.6f-thickness,z );
        glTexCoord2f(1,0.5f);
        glVertex3f(x+3+thickness,0.6f-thickness,z );

        glNormal3f(0,0,1);
        glTexCoord2f(0,0.75f);
        glVertex3f(x+3-thickness, 0.6f-thickness,z+.5f);
        glTexCoord2f(1,0.75f);
        glVertex3f(x+3+thickness, 0.6f-thickness,z+.5f);
        glTexCoord2f(0,0);
        glVertex3f(x+3-thickness, 0, z+.5f);
        glTexCoord2f(1,0);
        glVertex3f(x+3+thickness, 0, z+.5f);
        glEnd();
        glPopMatrix();
    }

}
