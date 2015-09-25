package dbvtech.com.br.simplegame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

/**
 * Created by Paulo on 27/07/2015.
 */
public class Sprite {

    private static int BINDED_TEXTURE = -1;

    private int width = 0;
    private int height = 0;
    private int halfWidth = 0;
    private int halfHeight = 0;
    private float widthScale = 0;
    private float heightScale = 0;
    private int textureID = -1;
    private boolean rotatable = false;
    private boolean flipable = false;

    private FloatBuffer vertexBuffer, flipBuffer;
    private FloatBuffer textureBuffer;
    private ByteBuffer indexBuffer;

    private float[] vertices, flipVertices;
    private float[] texture =
            {
                    0.0f, 0.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f
            };
    private byte[] indices = {0, 1, 2, 3, 2, 1};

    public Sprite(int id, int width, int height, boolean rotatable, boolean flipable) {
        this.textureID = id;
        this.width = width;
        this.height = height;
        this.rotatable = rotatable;
        this.flipable = flipable;

        this.halfWidth = width / 2;
        this.halfHeight = height / 2;

        this.widthScale = width;
        this.heightScale = height;

        if (rotatable || flipable) {
            if (flipable) {
                flipVertices = new float[8];
                flipVertices[0] = 0.0f;
                flipVertices[1] = 0.0f;
                flipVertices[2] = -width;
                flipVertices[3] = 0.0f;
                flipVertices[4] = 0.0f;
                flipVertices[5] = height;
                flipVertices[6] = -width;
                flipVertices[7] = height;
            }

            vertices = new float[8];
            vertices[0] = 0.0f;
            vertices[1] = 0.0f;
            vertices[2] = width;
            vertices[3] = 0.0f;
            vertices[4] = 0.0f;
            vertices[5] = height;
            vertices[6] = width;
            vertices[7] = height;

            vertexBuffer =
                    ByteBuffer.allocateDirect(flipVertices.length * 4)
                            .order(ByteOrder.nativeOrder()).asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);

            textureBuffer =
                    ByteBuffer.allocateDirect(texture.length * 4)
                            .order(ByteOrder.nativeOrder()).asFloatBuffer();
            textureBuffer.put(texture);
            textureBuffer.position(0);

            indexBuffer = ByteBuffer.allocateDirect(indices.length);
            indexBuffer.put(indices);
            indexBuffer.position(0);
        }
    }

    public void stretch(int width, int height){
        vertices[2] = width;
        vertices[5] = height;
        vertices[6] = width;
        vertices[7] = height;

        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    public void draw(GL10 gl, float x, float y, int winWidth, int winHeght, float winScale){
        bindTexture(gl);

        if(rotatable || flipable){
            gl.glPushMatrix();

            gl.glTranslatef(x * winScale, y * winScale, 1);
            if(winScale !=  1){
                gl.glScalef(winScale, winScale, winScale);
            }

            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
            gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);

            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glPopMatrix();
        }
        else{
            drawTexture(gl, x, y, winWidth, winHeght, winScale);
        }
    }

    public void bindTexture(GL10 gl){
        if(BINDED_TEXTURE != textureID){
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
            BINDED_TEXTURE = textureID;
        }

        gl.glColor4f(1f, 1f, 1f, 1f);
    }

    public void drawTexture(GL10 gl, float x, float y, int winWidth, int winHeight, float winScale){
        ((GL11Ext) gl).glDrawTexfOES(x * winScale, y * winScale - (y * heightScale), 0, widthScale, heightScale);
    }

    public void setScale(float scale){
        widthScale = width * scale;
        heightScale = height * scale;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getHalfWidth() {
        return halfWidth;
    }
    public int getHalfHeight() {
        return halfHeight;
    }
    public boolean getRotatable() {
        return rotatable;
    }
    public boolean getFlippable() {
        return flipable;
    }
}