package Testing;

import Core.Game.CheckWin;
import Core.Game.HandScore;
import Core.CardImp.Card;
import Core.Player.Player;
import Enums.Cards;
import Enums.HandRank;
import Enums.Suit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 19/12/12
 * Time: 08:06
 * To change this template use File | Settings | File Templates.
 */
public class CheckWinDrawTest {
    public static void main(String args[])
    {
        noKickerTest();
        allKickerTest();
    }

    public static void allKickerTest()
    {
        Player p1 = new Player("Alan", 100);
        Player p2 = new Player("Bobby", 100);
        Player p3 = new Player("Joe", 100);
        Player p4 = new Player("Neil", 100);
        Player p5 = new Player("Polly", 100);
        HandScore hs1 = new HandScore(HandRank.HIGHCARD, new Card[]{
                new Card(Suit.CLUBS, Cards.FIVE),
                new Card(Suit.HEARTS, Cards.THREE),
                new Card(Suit.HEARTS, Cards.EIGHT),
                new Card(Suit.DIAMONDS, Cards.JACK),
                new Card(Suit.CLUBS, Cards.SEVEN)});
        HandScore hs2 = new HandScore(HandRank.HIGHCARD, new Card[]{
                new Card(Suit.SPADES, Cards.FIVE),
                new Card(Suit.HEARTS, Cards.JACK),
                new Card(Suit.DIAMONDS, Cards.THREE),
                new Card(Suit.DIAMONDS, Cards.EIGHT),
                new Card(Suit.SPADES, Cards.SEVEN)});
        HandScore hs3 = new HandScore(HandRank.HIGHCARD, new Card[]{
                new Card(Suit.SPADES, Cards.FOUR),
                new Card(Suit.DIAMONDS, Cards.TWO),
                new Card(Suit.DIAMONDS, Cards.SEVEN),
                new Card(Suit.HEARTS, Cards.QUEEN),
                new Card(Suit.SPADES, Cards.SIX)});
        HandScore hs4 = new HandScore(HandRank.HIGHCARD, new Card[]{
                new Card(Suit.HEARTS, Cards.FIVE),
                new Card(Suit.SPADES, Cards.THREE),
                new Card(Suit.SPADES, Cards.EIGHT),
                new Card(Suit.CLUBS, Cards.JACK),
                new Card(Suit.HEARTS, Cards.SEVEN)});
        HandScore hs5 = new HandScore(HandRank.HIGHCARD, new Card[]{
                new Card(Suit.HEARTS, Cards.FOUR),
                new Card(Suit.SPADES, Cards.TWO),
                new Card(Suit.CLUBS, Cards.QUEEN),
                new Card(Suit.SPADES, Cards.SEVEN),
                new Card(Suit.HEARTS, Cards.SIX)});

        Map<Player, HandScore> tmPS = new HashMap<Player, HandScore>();
        tmPS.put(p1, hs1);
        tmPS.put(p2, hs2);
        tmPS.put(p3, hs3);
        tmPS.put(p4, hs4);
        tmPS.put(p5, hs5);

        CheckWin checkWin = new CheckWin(tmPS);
        Map.Entry<Player, HandScore> winner = checkWin.checkWin();
        System.out.println(winner.getKey().getCanonicalName());
        System.out.println("Is split pot: " + winner.getKey().isSplitPot());
        System.out.println(winner.getValue().toString());
    }

    public static void noKickerTest()
    {
        Player p1 = new Player("Alan", 100);
        Player p2 = new Player("Bobby", 100);
        Player p3 = new Player("Joe", 100);
        Player p4 = new Player("Neil", 100);
        Player p5 = new Player("Polly", 100);
        HandScore hs1 = new HandScore(HandRank.ROYALFLUSH, Suit.HEARTS);
        HandScore hs2 = new HandScore(HandRank.ROYALFLUSH, Suit.CLUBS);
        HandScore hs3 = new HandScore(HandRank.ROYALFLUSH, Suit.DIAMONDS);
        HandScore hs4 = new HandScore(HandRank.HIGHCARD, new Card[]{
                new Card(Suit.HEARTS, Cards.FIVE),
                new Card(Suit.SPADES, Cards.THREE),
                new Card(Suit.SPADES, Cards.EIGHT),
                new Card(Suit.CLUBS, Cards.JACK),
                new Card(Suit.HEARTS, Cards.SEVEN)});
        HandScore hs5 = new HandScore(HandRank.HIGHCARD, new Card[]{
                new Card(Suit.HEARTS, Cards.FOUR),
                new Card(Suit.SPADES, Cards.TWO),
                new Card(Suit.SPADES, Cards.SEVEN),
                new Card(Suit.CLUBS, Cards.QUEEN),
                new Card(Suit.HEARTS, Cards.SIX)});

        Map<Player, HandScore> tmPS = new HashMap<Player, HandScore>();
        tmPS.put(p1, hs1);
        tmPS.put(p2, hs2);
        tmPS.put(p3, hs3);
        tmPS.put(p4, hs4);
        tmPS.put(p5, hs5);

        CheckWin checkWin = new CheckWin(tmPS);
        Map.Entry<Player, HandScore> winner = checkWin.checkWin();
        System.out.println(winner.getKey().getCanonicalName());
        System.out.println("Is split pot: " + winner.getKey().isSplitPot());
        System.out.println(winner.getValue().toString());
    }
}
