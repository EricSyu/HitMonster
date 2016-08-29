package homework5.group.hitmonster;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by WEI-ZHE on 2016/5/13.
 */
public class UfoOne extends Monster {

    private int moveFlag = 0;

    public UfoOne(GameView gameView) {
        super(gameView, R.drawable.ufo1);

        setScore(8);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if(getBitmap() != null){
            // hp
            paint.setColor(Color.RED);
            canvas.drawRect(getX(), getY(), getX() + getWidth() / max_hp * getHp(), getY() + 8, paint);

            // Monster
            canvas.drawBitmap(getBitmap(), getX(), getY() + 10, paint);

            if(moveFlag == 10){
                setX((int)(Math.random()*(getViewWidth()-getWidth())));
                setY((int)(Math.random()*(getViewHeight()-getHeight())));
                moveFlag = 0;
            }
            else moveFlag++;
        }
    }
}
