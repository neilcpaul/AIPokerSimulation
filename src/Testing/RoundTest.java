package Testing;

import Core.Game;
import Core.BaseLogic.GameState;
import Core.BaseLogic.Round;
import Enums.HandRank;
import Utils.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 17/12/12
 * Time: 12:54
 * To change this template use File | Settings | File Templates.
 */
public class RoundTest {
    public static void main(String args[])
    {
        int rounds = 5000000;
        gameFrequencyTest(rounds);
    }

    public static void gameFrequencyTest(int roundsToPlay)
    {
        //new Game.initGame();
        GameState gs = new GameState();

        long cumulativeRoundTime = 0;

        Map<HandRank, Integer> rankingFrequencyMap = new HashMap<HandRank, Integer>();
        for (int i = 0 ; i<HandRank.RanksIndexed.length ; i++)
            rankingFrequencyMap.put(HandRank.RanksIndexed[i], 0);

        while(roundsToPlay > 0)
        {
            Round r = new Round(Game.getPlayers(), gs.calculateBlinds());
            r.playRound();
            Game.updatePlayers(r.getCurrentPlayers());

            int lastFrequency = rankingFrequencyMap.get(r.getRoundWinner().getValue().getHandRank());
            rankingFrequencyMap.remove(r.getRoundWinner().getValue().getHandRank());
            rankingFrequencyMap.put(r.getRoundWinner().getValue().getHandRank(), (lastFrequency+1));
            r.gatherPlayerHands();
            GameState.incrementRoundCount();
            cumulativeRoundTime += r.calcRoundTime();
            roundsToPlay--;
        }
        rankingFrequencyMap = MapUtil.reverseSortByValue(rankingFrequencyMap);
        for (Map.Entry<HandRank, Integer> e : rankingFrequencyMap.entrySet())
        {
            System.out.println(e.getValue() + "\t:\t" + e.getKey().rankName());
        }
        System.out.println("Cumulative round time: " + (cumulativeRoundTime>2000?cumulativeRoundTime/1000 + "s":cumulativeRoundTime+"ms"));
    }

    // 5mil rounds of 6 Person texas holdem: winning hand frequencies
    //   1562353:	Two Pair
    //   837587	:	Pair
    //   750346	:	Straight
    //   710550	:	Three of a Kind
    //   543094	:	Full House
    //   540344	:	Flush
    //   44205	:	Four of a Kind
    //   8172	:	Straight Flush
    //   2807	:	High Card
    //   542	:	Royal Flush
}
