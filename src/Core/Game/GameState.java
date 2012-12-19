package Core.Game;

import Core.Session;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class GameState {

    private int initSmallBlind;
    private int blindRaiseTime;

    public static int roundCount;

    private final long startTime;

    public GameState() {
        this.startTime = System.currentTimeMillis();
        roundCount = 0;

            this.initSmallBlind = Integer.parseInt(Session.prop.getProperty("initial.blind"));
            this.blindRaiseTime = Integer.parseInt(Session.prop.getProperty("game.blindRaise"));
    }

    public static int getRoundCount() {
        return roundCount;
    }

    public static void incrementRoundCount() {
        roundCount++;
    }

    public int calculateBlinds()
    {
        int elapsedTimeMinutes = (int)(System.currentTimeMillis() - startTime)/60000;
        return ((elapsedTimeMinutes/blindRaiseTime)+1)* initSmallBlind;
    }
}
