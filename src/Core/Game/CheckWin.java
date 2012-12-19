package Core.Game;

import Core.Player.Player;
import Core.CardImp.Card;
import Core.Player.Types.DrawNPC;
import Enums.HandRank;
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

    public Map.Entry<Player, HandScore> checkWin()
    {
        if (playerScores.isEmpty()) constructPlayerScoreMap();
        Player roundWinPlayer = new Player();
        HandScore roundWinHand = new HandScore(HandRank.NORANK);

        // First search for highest general rank
        for (Map.Entry<Player, HandScore> e:playerScores.entrySet())
        {
            if (e.getValue().getHandRank().getRank()>roundWinHand.getHandRank().getRank())
            {
                roundWinHand = e.getValue();
                roundWinPlayer = e.getKey();
            }
        }

        //Now check for other with same rank and dump into collection
        ArrayList<Map.Entry<Player, HandScore>> drawPlayers = new ArrayList<Map.Entry<Player, HandScore>>();
        for (Map.Entry<Player, HandScore> e:playerScores.entrySet())
        {
            if (e.getValue().getHandRank().getRank() == roundWinHand.getHandRank().getRank())
            {
                drawPlayers.add(e);
            }
        }

        //
        HashMap<Player, HandScore> actualDrawPlayers = new HashMap<Player, HandScore>();
        actualDrawPlayers.put(roundWinPlayer, roundWinHand);
        // Finally check for the player with the highest kickers, and flag if there is a draw
        for (Map.Entry<Player, HandScore> e:drawPlayers)
        {
            Card[] kickersHighest = roundWinHand.getKickers();
            Arrays.sort(kickersHighest, Collections.reverseOrder());
            Card[] kickersContender = e.getValue().getKickers();
            Arrays.sort(kickersContender, Collections.reverseOrder());

            boolean keepComparing = true;

            for (int i = 0; i < e.getValue().getKickers().length && keepComparing ; i++)
            {
                if (kickersHighest[i].getValue().getCardRank() >kickersContender[i].getValue().getCardRank())
                {
                    //Not higher, disregard and break from kicker compare loop
                    keepComparing = false;
                } else if (kickersHighest[i].getValue().getCardRank() < kickersContender[i].getValue().getCardRank())
                {
                    //Found a new highest, save it, break from kicker compare loop, clear the draw list and add to fresh list
                    roundWinPlayer = e.getKey();
                    roundWinHand = e.getValue();
                    keepComparing = false;
                    actualDrawPlayers.clear();
                    actualDrawPlayers.put(e.getKey(), e.getValue());
                } else
                {
                    if (i == (e.getValue().getKickers().length-1))
                    {
                        //We have reached the end of the kicker comparison and they are the same, add to draw list
                        actualDrawPlayers.put(e.getKey(), e.getValue());
                    }
                }
            }
        }

        // drawPlayers will be equal to 1 in a normal single winner game, but if >1, send back a special case 'Player'
        if (actualDrawPlayers.size()>1)
        {
            DrawNPC drawNPC = new DrawNPC();
            drawNPC.addDrawPlayer(new ArrayList<Player>(actualDrawPlayers.keySet()));
            return new AbstractMap.SimpleEntry<Player, HandScore>(drawNPC,roundWinHand);
        }
        return new AbstractMap.SimpleEntry<Player, HandScore>(roundWinPlayer,roundWinHand);
    }

}
