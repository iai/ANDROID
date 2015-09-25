package dbvtech.com.br.simplegame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Paulo on 27/07/2015.
 */
public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Context context;
    private Handler handler;
    private Activity activity;
    private Vibrator vibrator;
    private SharedPreferences savedGame;

    private static final String PREF_NAME = "TemplateBranco";
    private static final String PREF_COINS = "Saved Coins";

    private boolean running = true;

    private long lastTime = 0;
    private int elapsedTime = 0;
    private float percent = 0;
    private final int PRECISION_MIllIS = 1000000;
    private final int DELTA_TIME_LIMIT = 20;
    private final float MILLIS = 1000;

    private int win_width = 0;
    private int win_heigth = 0;
    private float scale = 0;
    private int winWidthHalf = 0;
    private int winHeighHalf = 0;

    private float winScale = 0;
    private float winWidthScale = 0;
    private float winCentX = 0;
    private float winHeigthScale = 0;
    private float winCentY = 0;

    public static int game_state = -1;
    public static int last_state = 0;
    public static final int SPLASH_SCREEN = 0;
    public static final int MAIN_MENU = 1;
    public static final int PLAYING = 2;

    private ImageLoader imageLoader = new ImageLoader();

    private Sprite sprite;
    private float xPos = 0;
    private float yPos = 0;
    private float xVel = 500;
    private float yVol = 500;

    private boolean touching = false;
    private float touchX = 0;
    private float touchY = 0;

    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler,
                      Activity activity, Vibrator vibrator){
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.handler = handler;
        this.activity = activity;
        this.vibrator = vibrator;

        game_state = SPLASH_SCREEN;
    }

    public void init(GL10 gl){
        sprite = imageLoader.loadBitmap(context, gl, R.raw.star, false, false);
        loadGame();
        lastTime = System.nanoTime();
        start();
    }

    @Override
    public void run() {
        super.run();

        while(running){
            if(lastTime != 0){
                elapsedTime();

                switch (game_state){
                    case SPLASH_SCREEN:
                        break;
                    case MAIN_MENU:
                        break;
                    case PLAYING:

                        xPos += xVel * percent;
                        yPos += yVol * percent;

                        if(xPos < 0){
                            xPos = 0;
                            xVel *= -1;
                        }
                        else if(xPos > win_width * scale){
                            xPos = win_width * scale;
                            xVel *= -1;
                        }

                        if(yPos < 0){
                            yPos = 0;
                            yVol *= -1;
                        }
                        else if(yPos > win_heigth * scale){
                            yPos = win_heigth * scale;
                            yVol *= -1;
                        }

                        if(touching &&
                                touchX >= xPos && touchX <= xPos + sprite.getWidth() * scale &&
                                touchY >= yPos && touchY <= yPos + sprite.getHeight() * scale){
                            xVel += -1;
                            yVol += -1;
                            xPos = (float) Math.random() * win_width * scale;
                            yPos = (float) Math.random() * win_heigth * scale;
                            vibrator.vibrate(22);
                        }

                        break;
                }

                elapsedTime();
            }

            try{
                Thread.sleep(10);
            }catch (Exception ex){

            }
        }
    }

    public void draw(GL10 gl){
        if(lastTime == 0){
            return;
        }

        switch (game_state){
            case SPLASH_SCREEN:
                gl.glClearColor(0.4f, 0.4f, 0.4f, 0.4f);
                gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
                sprite.drawTexture(gl, xPos, yPos, win_width, win_heigth, winScale);
                break;
            case MAIN_MENU:
                gl.glClearColor(1f, 0.4f, 0.4f, 0.4f);
                gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
                sprite.drawTexture(gl, xPos, yPos, win_width, win_heigth, winScale);
                break;
            case PLAYING:
                if(touching){
                    gl.glClearColor(0.4f, 1f, 1f, 1f);
                }
                else{
                    gl.glClearColor(0.4f, 1f, 0.4f, 1f);
                }

                gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
                sprite.drawTexture(gl, xPos, yPos, win_width, win_heigth, winScale);
                break;
        }
    }

    private void elapsedTime(){
        long curTime = System.nanoTime();
        elapsedTime = (int) ((curTime - lastTime) / PRECISION_MIllIS);
        lastTime = curTime;

        if(elapsedTime > DELTA_TIME_LIMIT){
            percent = DELTA_TIME_LIMIT / MILLIS;
        }
        else{
            percent = elapsedTime / MILLIS;
        }
    }

    public void saveGame(){
        synchronized (surfaceHolder){
            if(savedGame != null){
                SharedPreferences.Editor editor = savedGame.edit();
                editor.putInt(PREF_COINS, 123);
                editor.commit();
            }
        }
    }

    public void loadGame(){
        savedGame = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        game_state = SPLASH_SCREEN;
    }

    public void setTouchEvent(boolean value, float x, float y){
        synchronized (surfaceHolder){
            switch (game_state){
                case SPLASH_SCREEN:
                    game_state = MAIN_MENU;
                    break;
                case MAIN_MENU:
                    game_state = PLAYING;
                    break;
                case PLAYING:
                    touching = value;
                    touchX = x;
                    touchY = y;
                    break;
            }
        }
    }

    public void setWindowSize(int width, int height, float scale){
        synchronized (surfaceHolder){
            win_width = width;
            win_heigth = height;
            this.scale = scale;

            winWidthHalf = width / 2;
            winHeighHalf = height / 2;
            winScale = 1 / scale;

            winWidthScale = width * winScale;
            winHeigthScale = height * winScale;
            winCentX = winWidthScale / 2;
            winCentY = winHeigthScale / 2;

            sprite.setScale(scale);
        }
    }

    public void setRunning(boolean running){
        synchronized (surfaceHolder){
            this.running = running;
        }
    }
}
