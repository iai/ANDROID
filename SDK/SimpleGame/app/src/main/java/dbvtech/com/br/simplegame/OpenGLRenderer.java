package dbvtech.com.br.simplegame;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Paulo on 27/07/2015.
 */
public class OpenGLRenderer extends Thread implements GLSurfaceView.Renderer{

    private SurfaceHolder surfaceHolder;
    private Context context;
    private Handler handler;
    private Vibrator vibrator;
    private Activity activity;
    public GameThread gameThread;
    private float scale = 1;
    private boolean gluOrthoFlag = false;

    public OpenGLRenderer(SurfaceHolder surfaceHolder, Context context, Handler handler,
                          Activity activity, Vibrator vibrator){
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.handler = handler;
        this.activity = activity;
        this.vibrator = vibrator;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        if(gameThread != null){
            gameThread.setRunning(false);
        }

        gameThread = new GameThread(surfaceHolder, context, handler, activity, vibrator);
        gluOrthoFlag = false;

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glLineWidth(2.0f);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glDisable(GL10.GL_DITHER);
        gl.glDisable(GL10.GL_LIGHTING);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0,0,width,height);
        if(!gluOrthoFlag){
            gl.glMatrixMode(GL10.GL_PROJECTION);
            GLU.gluOrtho2D(gl, 0, width, height, 0);
            gameThread.init(gl);
            gluOrthoFlag = true;
        }

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        scale = height / 15f / 32f;
        gameThread.setWindowSize(width, height, scale);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(0.4f, 0.4f, 0.4f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gameThread.draw(gl);
    }
}
