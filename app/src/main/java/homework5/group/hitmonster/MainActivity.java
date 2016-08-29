package homework5.group.hitmonster;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity = null;

    //music
    private MediaPlayer mp;
    private boolean isStoped = true;

    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        // Set Status Bar to become  translucent
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        playMusic();
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    private void playMusic() {
        if (mp == null || isStoped) {
            mp = create(MainActivity.this, R.raw.star_wars_music);
            isStoped = false;
        }
        mp.setLooping(true);
        mp.start();
    }

    private void stopMusic() {
        if (mp == null || isStoped)
            return;
        mp.stop();
        isStoped = true;

    }

    //Build environment for playing music
    public static MediaPlayer create(Context context, int resid) {
        AssetFileDescriptor afd;
        afd = context.getResources().openRawResourceFd(resid);
        if (afd == null)
            return null;
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
            return mp;
        } catch (Exception e) {
            Log.e("Play music error!", e.toString());
            return null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopMusic();
    }
}