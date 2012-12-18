package Core;

import Core.BaseLogic.GameState;
import Core.BaseLogic.Round;
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
public class Game {
    private static ArrayList<Player> players;
    private static Queue<Player> blindQueue;
    public static Properties prop = new Properties();
    private static int numberOfPlayers;

    public Game()
    {
        initGame();
    }

    public static void initGame()
    {
        //load settings
        try {
           prop.load(new FileInputStream("D:\\Uni\\AIpoker\\src\\Core\\BaseLogic\\game.properties"));
           numberOfPlayers =  Integer.parseInt(prop.getProperty("game.players"));
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
                case 0:  players.add(new CollusionPlayer(playerName, playerNumber));
                    break;
                case 1:  players.add(new RecklessPlayer(playerName, playerNumber));
                    break;
                case 2:  players.add(new EmotionPlayer(playerName, playerNumber));
                    break;
                case 3:  players.add(new PercentagePlayer(playerName, playerNumber));
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

    public static void run()
    {
        new Game();
        GameState gs = new GameState();

        Player winner = new Player();

        boolean continuePlaying = true;
        int maxRounds = 100;
        while(continuePlaying && GameState.roundCount < maxRounds)
        {
            Round r = new Round(players, gs.calculateBlinds());
            r.updateBlindQueue(blindQueue);
            r.playRound();
            updatePlayers(r.getCurrentPlayers());
            GameState.incrementRoundCount();
            if (r.isGameOver())
            {
                continuePlaying = false;
            }
            winner = r.checkWinner();
            updateAndProgressBlindQueue();
        }

        System.out.println(winner.toWinnerString());

    }
}
