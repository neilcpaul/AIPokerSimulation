package Core.Game;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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

    private Properties prop = new Properties();

    public GameState() {
        this.startTime = System.currentTimeMillis();
        roundCount = 0;

        try {
            prop.load(new FileInputStream("D:\\Uni\\AIpoker\\src\\Core\\Game\\game.properties"));

            this.initSmallBlind = Integer.parseInt(this.prop.getProperty("initial.blind"));
            this.blindRaiseTime = Integer.parseInt(this.prop.getProperty("game.blindRaise"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
