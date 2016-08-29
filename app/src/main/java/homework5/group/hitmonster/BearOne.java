package homework5.group.hitmonster;

/**
 * Created by WEI-ZHE on 2016/5/12.
 */
public class BearOne extends Monster {

    public BearOne(GameView gameView) {
        super(gameView, R.drawable.bear1);
        setySpeed((int)(Math.random()*50+1));
    }
}
