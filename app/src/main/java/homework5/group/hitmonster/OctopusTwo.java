package homework5.group.hitmonster;

/**
 * Created by WEI-ZHE on 2016/5/13.
 */
public class OctopusTwo extends Monster {

    public OctopusTwo(GameView gameView) {
        super(gameView, R.drawable.octopus2);

        setySpeed((int)((Math.random()+20)*3));
        setScore(5);
    }
}
