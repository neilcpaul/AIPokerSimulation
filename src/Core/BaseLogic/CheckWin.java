package Core.BaseLogic;

import Core.Player.Player;
import Core.CardImp.Card;
import Utils.MapUtil;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */
public class CheckWin {
    //top down approach (check for best cards first)
    //beat highest player, as opposed to check everyone top down
    private Map<Player, HandScore> playerScores;
    private ArrayList<Player> players;
    private ArrayList<Card> tableCards;

    public CheckWin(){}

    public CheckWin(ArrayList<Player> players, ArrayList<Card> tableCards)
    {
        this.players = players;
        this.tableCards = tableCards;
        playerScores = new HashMap<Player, HandScore>();
    }

    public CheckWin(Map<Player, HandScore> playerScores)
    {
        this.playerScores = playerScores;
    }

    private void constructPlayerScoreMap()
    {
        for (Player nextPlayer : players) {
            playerScores.put(nextPlayer, nextPlayer.calculateHandScore(tableCards));
        }

    }

    private HashMap<Player, HandScore> sortScoresToHashMap(Map<Player, HandScore> scores)
    {
        return new HashMap<Player, HandScore>(MapUtil.sortByValue(scores));
    }

    public Map.Entry<Player, HandScore> checkWin()
    {
        if (playerScores.isEmpty()) constructPlayerScoreMap();
        Player roundWinPlayer = new Player();
        HandScore roundWinHand = new HandScore();
        for (Map.Entry<Player, HandScore> e:playerScores.entrySet())
        {
            if (e.getValue().compareTo(roundWinHand) == 1)
            {
                roundWinHand = e.getValue();
                roundWinPlayer = e.getKey();
            }
        }
        return new AbstractMap.SimpleEntry<Player, HandScore>(roundWinPlayer,roundWinHand);
    }

}
