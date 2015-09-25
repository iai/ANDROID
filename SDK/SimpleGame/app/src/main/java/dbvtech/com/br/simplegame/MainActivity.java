package dbvtech.com.br.simplegame;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private OpenGLView openGLView;

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.getData().getBoolean("do_something")){

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_main);

        openGLView = (OpenGLView) findViewById(R.id.openGL);

        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        openGLView.init(this, mHandler, vibrator);
    }


}
