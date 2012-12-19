package Core;

import Core.Game.GameState;
import Core.Game.Round;
import Core.Player.*;
import Core.Player.Types.CollusionPlayer;
import Core.Player.Types.EmotionPlayer;
import Core.Player.Types.PercentagePlayer;
import Core.Player.Types.RecklessPlayer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class Session {
    private static ArrayList<Player> players;
    private static Queue<Player> blindQueue;
    public static volatile Properties prop;
    private static int numberOfPlayers;
    private static int roundLimit;

    public Session()
    {
        initGame();
    }

    public static void initGame()
    {
        //load settings
        try {
           prop = new Properties();
           prop.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\Core\\BaseLogic\\game.properties"));
            numberOfPlayers =  Integer.parseInt(prop.getProperty("game.players"));
            roundLimit =  Integer.parseInt(prop.getProperty("round.limit"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        players = new ArrayList<Player>();

        //generate players
        for (int playerNumber = 0 ; playerNumber < numberOfPlayers ; playerNumber++)
        {
            String playerNameRetrieval = "player." + (playerNumber);
            String playerName = prop.get(playerNameRetrieval).toString();
            switch (new Random().nextInt(3))
            {
                case 0:  players.add(new CollusionPlayer(playerName));
                    break;
                case 1:  players.add(new RecklessPlayer(playerName));
                    break;
                case 2:  players.add(new EmotionPlayer(playerName));
                    break;
                case 3:  players.add(new PercentagePlayer(playerName));
                    break;
            }
        }

        //generate blind queue
        blindQueue = new LinkedList<Player>(players);
    }

    public static void updatePlayers(ArrayList<Player> current)
    {
        players = current;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void updateAndProgressBlindQueue()
    {
        Player lastBlind = blindQueue.poll();
        blindQueue.add(lastBlind);
        blindQueue.retainAll(getPlayers());
    }

    public static boolean belowRoundLimit()
    {
        return roundLimit==0?true:GameState.roundCount < roundLimit;
    }

    public static void run()
    {
        new Session();
        GameState gs = new GameState();

        Player winner = new Player();

        boolean continuePlaying = true;
        while(continuePlaying && belowRoundLimit())
        {
            System.out.println("Round " + (GameState.getRoundCount()+1));
            Round r = new Round(players, gs.calculateBlinds());
            r.updateBlindQueue(blindQueue);
            r.playRound();
            updatePlayers(r.getCurrentPlayers());
            winner = r.checkWinner();

            if (r.isGameOver())
            {
                continuePlaying = false;
            } else
                GameState.incrementRoundCount();


            updateAndProgressBlindQueue();
        }

        System.out.println(winner.toWinnerString());

    }
}
