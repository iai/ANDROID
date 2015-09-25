package dbvtech.com.br.simplegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

/**
 * Created by Paulo on 27/07/2015.
 */
public class ImageLoader {

    private int[] textureNameWorkspace = new int[1];
    private int[] cropWorkspace = new int[4];
    private static BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

    public ImageLoader(){}

    public Sprite loadBitmap
            (Context context, GL10 gl, int resourceID, boolean rotetable, boolean flipable){
        if(context != null && gl != null){
            gl.glGenTextures(1, textureNameWorkspace, 0);
            int textureId = textureNameWorkspace[0];
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

            gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

            InputStream inputStream = context.getResources().openRawResource(resourceID);
            Bitmap bitmap = null;

            try{

                bitmap = BitmapFactory.decodeStream(inputStream, null, bitmapOptions);
                inputStream.close();

            }
            catch (Exception ex){
                Log.e("ImageLoader", ex.getMessage());
            }

            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

            if(!rotetable){
                cropWorkspace[0] = 0;
                cropWorkspace[1] = bitmap.getHeight();
                cropWorkspace[2] = bitmap.getWidth();
                cropWorkspace[3] = -bitmap.getHeight();

                bitmap.recycle();

                ((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, cropWorkspace, 0);

                int erro = gl.glGetError();
                if(erro != GL10.GL_NO_ERROR){
                    Log.e("ImageLoader", "Erro ao carregar texture nao roteavel");
                }
            }

            return new Sprite(textureId, bitmap.getWidth(), bitmap.getHeight(), rotetable, flipable);
        }

        return  null;
    }
}
