package dbvtech.com.br.simplegame;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Created by Paulo on 27/07/2015.
 */
public class OpenGLView extends GLSurfaceView{

    private Context context;
    private SurfaceHolder holder;
    private OpenGLRenderer renderer;
    private Touch touch;
    private Activity activity;

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
    }

    public void init(Activity activity, Handler handler, Vibrator vibrator){
        this.holder = getHolder();
        holder.addCallback(this);
        this.activity = activity;

        renderer = new OpenGLRenderer(holder, context, handler, activity, vibrator);

        setRenderer(renderer);

        try{
            if(Float.parseFloat(Build.VERSION.SDK) >= 5){
                touch = new MultiTouch(renderer);
            }
            else{
                touch = new Touch(renderer);
            }
        }
        catch (Exception ex){

        }

        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if(renderer != null && renderer.gameThread != null){
            touch.useTouch(event);
        }

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {

        if(renderer != null){
            GameThread gameThread = renderer.gameThread;
            if(gameThread != null){
                gameThread.setRunning(false);
                gameThread.saveGame();
            }
        }

        super.onPause();
    }
}

class Touch{
    protected  OpenGLRenderer renderer;

    public Touch(OpenGLRenderer renderer){
        this.renderer = renderer;
    }

    public void useTouch(MotionEvent event){
        GameThread gameThread = renderer.gameThread;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                gameThread.setTouchEvent(true, event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                gameThread.setTouchEvent(false, 0, 0);
                break;
        }
    }
}

class MultiTouch extends Touch{

    public MultiTouch(OpenGLRenderer renderer) {
        super(renderer);
    }

    @Override
    public void useTouch(MotionEvent event) {
        GameThread gameThread = renderer.gameThread;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                for(int i=0; i < event.getPointerCount(); i++){
                    gameThread.setTouchEvent(true, event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                gameThread.setTouchEvent(false, 0, 0);
                break;
        }
    }
}
