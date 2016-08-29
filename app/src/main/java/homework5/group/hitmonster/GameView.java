package homework5.group.hitmonster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WEI-ZHE on 2016/5/12.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final int GAME_INIT = 0, GAME_START = 1, GAME_STOP = 2;

    private Paint paint = null;
    private List<Monster> monsters;
    private Timer timer;
    private TimerTask timerTask;

    private int totalScore;

    private List<Integer> ovalX, ovalY, radius;
    private int ovalNum = 0, bagroundFlag = 0;

    private int gameStatus = GAME_INIT;

    private int addMonsterFlag = 0;
    private boolean mergeflag = false;

    //Sound
    private static final int SOUND_COUNT = 1;
    private int when_Attack;
    private SoundPool soundPool;

    public GameView(Context context) {
        super(context);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        paint.setTextSize(100);

        monsters = new ArrayList<Monster>();

        totalScore = 0;

        ovalX = new ArrayList<>();
        ovalY = new ArrayList<>();
        radius = new ArrayList<>();

        getHolder().addCallback(this);

        //Sound
        soundPool = new SoundPool(SOUND_COUNT, AudioManager.STREAM_MUSIC, 0);
        when_Attack = soundPool.load(MainActivity.getMainActivity(), R.raw.fastswing5, 1);

        startGameDialog();
    }

    public void startGameDialog(){
        new AlertDialog.Builder(MainActivity.getMainActivity())
                .setTitle(R.string.startDialogTitle)
                .setMessage(R.string.gameRule)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameStatus = GAME_START;
                    }
                })
                .setNegativeButton(R.string.leave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.getMainActivity().finish();
                    }
                }).show();
    }

    public void draw(){
        Canvas canvas = getHolder().lockCanvas();

        // Background
        canvas.drawColor(0xff202539);
        paint.setColor(Color.WHITE);
        if(bagroundFlag == 0){
            ovalX.clear();
            ovalY.clear();
            radius.clear();

            ovalNum = (int)((Math.random()+1)*10);
            for(int i=0; i<ovalNum; i++){
                int oX = (int)(Math.random()*getWidth());
                int oY = (int)(Math.random()*getHeight());
                int r = (int)(Math.random()*5+1);
                ovalX.add(oX);
                ovalY.add(oY);
                radius.add(r);
            }
        }
        else {
            for(int i=0; i<ovalNum; i++){
                canvas.drawOval(ovalX.get(i), ovalY.get(i), ovalX.get(i)+radius.get(i), ovalY.get(i)+radius.get(i), paint);
            }
        }
        if(bagroundFlag == 50) bagroundFlag = 0;
        else bagroundFlag++;

        if(gameStatus == GAME_START){
            // Monster
            for(Monster m : monsters){
                m.draw(canvas, paint);
            }

            // Score
            paint.setColor(Color.WHITE);
            canvas.drawText(totalScore + "", getWidth() / 10, 200, paint);

            addMonster();
            removeDeadMonster();
            combinMonster();
        }
        else if(gameStatus == GAME_STOP){
            paint.setColor(Color.WHITE);
            canvas.drawText("遊戲結束", getWidth() / 3, getHeight()/4, paint);
            canvas.drawText("得分:"+totalScore+"分", getWidth() / 3, getHeight()*2/3, paint);
        }

        if(monsters.size() >= 20 && gameStatus == GAME_START){
            gameStatus = GAME_STOP;
        }

        getHolder().unlockCanvasAndPost(canvas);
    }

    public void addMonster(){
        if(addMonsterFlag == 10){
            Monster monster = null;

            switch ((int)(Math.random()*7+1)){
                case 1:
                    monster = new Earth(this);
                    break;
                case 2:
                    monster = new BearOne(this);
                    break;
                case 3:
                    monster = new BearTwo(this);
                    break;
                case 4:
                    monster = new OctopusOne(this);
                    break;
                case 5:
                    monster = new OctopusTwo(this);
                    break;
                case 6:
                    monster = new UfoOne(this);
                    break;
                case 7:
                    monster = new UfoTwo(this);
                    break;
            }

            monsters.add(monster);

            addMonsterFlag = 0;
        }
        else addMonsterFlag++;
    }

    public void combinMonster(){
        for (int i = 0; !mergeflag && i < monsters.size(); i++) {
            for (int j = i + 1; j < monsters.size(); j++) {
                Monster m1 = monsters.get(i);
                Monster m2 = monsters.get(j);
                if (distance(m1, m2) < 60 * Math.sqrt(2)) {
                    if (m1.getClass().getName() == m2.getClass().getName()) {
                        if (m1.getHp()!=m1.getMax_hp() && m1.getHp() == m2.getHp()) {
                            float x1 = m1.getX();
                            float y1 = m1.getY();
                            float x2 = m2.getX();
                            float y2 = m2.getY();
                            int xspeed = m1.getxSpeed();
                            if(m2.getxSpeed()>xspeed){
                                xspeed = m2.getxSpeed();
                            }
                            int yspeed = m1.getySpeed();
                            if(m2.getySpeed()>yspeed){
                                yspeed = m2.getySpeed();
                            }
                            int hp = m1.getHp() + m2.getHp();
                            m1.setX((int)((x1 + x2)/2));
                            m1.setY((int)((y1 + y2)/2));
                            m1.setxSpeed(xspeed);
                            m1.setySpeed(yspeed);
                            m1.setHp(hp);
                            m1.setMax_hp(hp);

                            monsters.remove(m2);
                            mergeflag = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    public float distance(Monster m1, Monster m2) {
        float x1 = m1.getX();
        float y1 = m1.getY();
        float x2 = m2.getX();
        float y2 = m2.getY();
        float dist = (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        return dist;
    }

    public void removeDeadMonster(){
        List<Monster> deadMonster = new ArrayList<Monster>();
        for(Monster m : monsters){
            if(m.getHp() == 0){
                deadMonster.add(m);
                totalScore += m.getScore();
            }
        }
        monsters.removeAll(deadMonster);
    }
    public void startTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    draw();
                }catch (Exception e){}
            }
        };
        timer.schedule(timerTask, 100, 100);
    }

    public void stopTimer(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startTimer();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopTimer();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            soundPool.play(when_Attack, 1, 1, 0, 0, 1);

            int x = (int)event.getX();
            int y = (int)event.getY();
            for(int i=monsters.size()-1; i>=0; i--){
                boolean b = monsters.get(i).beAttacked(x, y);
                if(b) break;
            }
        }
        return super.onTouchEvent(event);
    }
}
