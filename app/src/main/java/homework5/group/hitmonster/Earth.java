package homework5.group.hitmonster;

/**
 * Created by WEI-ZHE on 2016/5/12.
 */
public class Earth extends Monster {

    public Earth(GameView gameView) {
        super(gameView, R.drawable.earth);

        setMax_hp(1);
        setHp(1);
        setScore(-10);
    }
}
