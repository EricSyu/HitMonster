package homework5.group.hitmonster;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by WEI-ZHE on 2016/5/12.
 */
public class Monster {

    public int max_hp = 5;

    private int x, y;
    private int width, height;
    private int xSpeed, ySpeed;
    private int hp;
    private int score;

    private Bitmap bitmap = null;

    private int viewWidth, viewHeight;

    public Monster(GameView gameView, int bitmapId) {
        this.viewWidth = gameView.getWidth();
        this.viewHeight = gameView.getHeight();
        this.bitmap = BitmapFactory.decodeResource(MainActivity.getMainActivity().getResources(), bitmapId);
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.hp = max_hp;

        this.x = (int)(Math.random()*(viewWidth-width));
        this.y = (int)(Math.random()*(viewHeight-height));
        this.xSpeed = (int)(Math.random()*10+1);
        this.ySpeed = (int)(Math.random()*20+1);
        this.score = 1;
    }

    public void draw(Canvas canvas, Paint paint){
        if(bitmap != null){
            // hp
            paint.setColor(Color.RED);
            canvas.drawRect(getX(), getY(), getX() + getWidth()/max_hp*getHp(), getY()+8, paint);

            // Monster
            canvas.drawBitmap(bitmap, getX(), getY()+10, paint);
            setX(getX() + xSpeed);
            setY(getY() + ySpeed);
        }
    }

    public boolean beAttacked(int x, int y){
        if(x >= this.x && x <= this.x+this.width && y >= this.y && y <= this.y+this.height){
            if(hp > 0) hp--;
            return true;
        }
        return false;
    }

    public void setX(int x) {
        if(x < 0 || x+width > viewWidth){
            xSpeed = xSpeed * (-1);
        }
        else this.x = x;
    }

    public void setY(int y) {
        if(y < 0 || y+height > viewHeight){
            ySpeed = ySpeed * (-1);
        }
        else this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }
}
