package homework5.group.hitmonster;

/**
 * Created by WEI-ZHE on 2016/5/13.
 */
public class BearTwo extends Monster {

    public BearTwo(GameView gameView) {
        super(gameView, R.drawable.bear2);

        setxSpeed((int)(Math.random()*30+1));
        setScore(2);
    }
}
