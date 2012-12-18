package Testing;

import Enums.Cards;
import Enums.HandRank;
import Enums.Suit;
import Core.CardImp.Card;
import Core.CardImp.Deck;
import Factories.HandRankWinCheckFactory;
import Utils.MapUtil;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 15/12/12
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public class HandRankWinCheckTest {
    public static void main(String args[])
    {
        //systematicTests();
        //randomTests(6, 5); //number of tests, test hand size
        handRankFrequencyTest(100, 5);
    }

    public static void systematicTests()
    {
        //Hand tester (7 cards)
        //Hand 1 - Royal Flush (Hearts)
        ArrayList<Card> h1 = new ArrayList<Card>();
        h1.add(new Card(Suit.HEARTS.getSuitID(), Cards.JACK.getCardRank()));
        h1.add(new Card(Suit.CLUBS.getSuitID(), Cards.FIVE.getCardRank()));
        h1.add(new Card(Suit.HEARTS.getSuitID(), Cards.KING.getCardRank()));
        h1.add(new Card(Suit.HEARTS.getSuitID(), Cards.TEN.getCardRank()));
        h1.add(new Card(Suit.SPADES.getSuitID(), Cards.FIVE.getCardRank()));
        h1.add(new Card(Suit.HEARTS.getSuitID(), Cards.QUEEN.getCardRank()));
        h1.add(new Card(Suit.HEARTS.getSuitID(), Cards.ACE.getCardRank()));

        //Hand tester (7 cards)
        //Hand 2 - High Card King (Hearts)
        ArrayList<Card> h2 = new ArrayList<Card>();
        h2.add(new Card(Suit.HEARTS.getSuitID(), Cards.JACK.getCardRank()));
        h2.add(new Card(Suit.CLUBS.getSuitID(), Cards.FIVE.getCardRank()));
        h2.add(new Card(Suit.HEARTS.getSuitID(), Cards.KING.getCardRank()));
        h2.add(new Card(Suit.HEARTS.getSuitID(), Cards.TEN.getCardRank()));
        h2.add(new Card(Suit.SPADES.getSuitID(), Cards.FIVE.getCardRank()));
        h2.add(new Card(Suit.HEARTS.getSuitID(), Cards.QUEEN.getCardRank()));
        h2.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.TWO.getCardRank()));

        //Hand tester (7 cards)
        //Hand 3 - Full house (9s full of Kings)
        ArrayList<Card> h3 = new ArrayList<Card>();
        h3.add(new Card(Suit.SPADES.getSuitID(), Cards.NINE.getCardRank()));
        h3.add(new Card(Suit.CLUBS.getSuitID(), Cards.FIVE.getCardRank()));
        h3.add(new Card(Suit.HEARTS.getSuitID(), Cards.KING.getCardRank()));
        h3.add(new Card(Suit.HEARTS.getSuitID(), Cards.NINE.getCardRank()));
        h3.add(new Card(Suit.SPADES.getSuitID(), Cards.KING.getCardRank()));
        h3.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.NINE.getCardRank()));
        h3.add(new Card(Suit.SPADES.getSuitID(), Cards.ACE.getCardRank()));

        //Hand tester (7 cards)
        //Hand 4 - Straight Flush (Hearts)
        ArrayList<Card> h4 = new ArrayList<Card>();
        h4.add(new Card(Suit.HEARTS.getSuitID(), Cards.EIGHT.getCardRank()));
        h4.add(new Card(Suit.CLUBS.getSuitID(), Cards.FIVE.getCardRank()));
        h4.add(new Card(Suit.HEARTS.getSuitID(), Cards.SIX.getCardRank()));
        h4.add(new Card(Suit.HEARTS.getSuitID(), Cards.TEN.getCardRank()));
        h4.add(new Card(Suit.SPADES.getSuitID(), Cards.FIVE.getCardRank()));
        h4.add(new Card(Suit.HEARTS.getSuitID(), Cards.NINE.getCardRank()));
        h4.add(new Card(Suit.HEARTS.getSuitID(), Cards.SEVEN.getCardRank()));

        //Hand tester (7 cards)
        //Hand 5 - Flush (Diamonds)
        ArrayList<Card> h5 = new ArrayList<Card>();
        h5.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.FIVE.getCardRank()));
        h5.add(new Card(Suit.CLUBS.getSuitID(), Cards.FIVE.getCardRank()));
        h5.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.KING.getCardRank()));
        h5.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.TEN.getCardRank()));
        h5.add(new Card(Suit.SPADES.getSuitID(), Cards.FIVE.getCardRank()));
        h5.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.QUEEN.getCardRank()));
        h5.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.TWO.getCardRank()));

        //Hand tester (7 cards)
        //Hand 6 - Straight (Mixed), with pair
        ArrayList<Card> h6 = new ArrayList<Card>();
        h6.add(new Card(Suit.SPADES.getSuitID(), Cards.NINE.getCardRank()));
        h6.add(new Card(Suit.CLUBS.getSuitID(), Cards.EIGHT.getCardRank()));
        h6.add(new Card(Suit.HEARTS.getSuitID(), Cards.SEVEN.getCardRank()));
        h6.add(new Card(Suit.HEARTS.getSuitID(), Cards.TEN.getCardRank()));
        h6.add(new Card(Suit.SPADES.getSuitID(), Cards.SIX.getCardRank()));
        h6.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.QUEEN.getCardRank()));
        h6.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.TEN.getCardRank()));

        //Hand tester (7 cards)
        //Hand 7 - 7 card straight (Mixed)
        ArrayList<Card> h7 = new ArrayList<Card>();
        h7.add(new Card(Suit.HEARTS.getSuitID(), Cards.JACK.getCardRank()));
        h7.add(new Card(Suit.CLUBS.getSuitID(), Cards.FIVE.getCardRank()));
        h7.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.SEVEN.getCardRank()));
        h7.add(new Card(Suit.HEARTS.getSuitID(), Cards.TEN.getCardRank()));
        h7.add(new Card(Suit.SPADES.getSuitID(), Cards.SIX.getCardRank()));
        h7.add(new Card(Suit.HEARTS.getSuitID(), Cards.EIGHT.getCardRank()));
        h7.add(new Card(Suit.HEARTS.getSuitID(), Cards.NINE.getCardRank()));

        //Hand tester (7 cards)
        //Hand 8 - 7 card flush (Spades)
        ArrayList<Card> h8 = new ArrayList<Card>();
        h8.add(new Card(Suit.SPADES.getSuitID(), Cards.TWO.getCardRank()));
        h8.add(new Card(Suit.SPADES.getSuitID(), Cards.FIVE.getCardRank()));
        h8.add(new Card(Suit.SPADES.getSuitID(), Cards.KING.getCardRank()));
        h8.add(new Card(Suit.SPADES.getSuitID(), Cards.TEN.getCardRank()));
        h8.add(new Card(Suit.SPADES.getSuitID(), Cards.THREE.getCardRank()));
        h8.add(new Card(Suit.SPADES.getSuitID(), Cards.QUEEN.getCardRank()));
        h8.add(new Card(Suit.SPADES.getSuitID(), Cards.EIGHT.getCardRank()));

        //Hand tester (7 cards)
        //Hand 9 - 4 of a kind (5), queen kicker
        ArrayList<Card> h9 = new ArrayList<Card>();
        h9.add(new Card(Suit.SPADES.getSuitID(), Cards.TWO.getCardRank()));
        h9.add(new Card(Suit.HEARTS.getSuitID(), Cards.FIVE.getCardRank()));
        h9.add(new Card(Suit.SPADES.getSuitID(), Cards.FIVE.getCardRank()));
        h9.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.FIVE.getCardRank()));
        h9.add(new Card(Suit.CLUBS.getSuitID(), Cards.FIVE.getCardRank()));
        h9.add(new Card(Suit.HEARTS.getSuitID(), Cards.QUEEN.getCardRank()));
        h9.add(new Card(Suit.SPADES.getSuitID(), Cards.EIGHT.getCardRank()));

        //Hand tester (7 cards)
        //Hand 10 - high card (ace) K/J/8/5 kickers
        ArrayList<Card> h10 = new ArrayList<Card>();
        h10.add(new Card(Suit.SPADES.getSuitID(), Cards.TWO.getCardRank()));
        h10.add(new Card(Suit.HEARTS.getSuitID(), Cards.FIVE.getCardRank()));
        h10.add(new Card(Suit.SPADES.getSuitID(), Cards.EIGHT.getCardRank()));
        h10.add(new Card(Suit.DIAMONDS.getSuitID(), Cards.JACK.getCardRank()));
        h10.add(new Card(Suit.CLUBS.getSuitID(), Cards.THREE.getCardRank()));
        h10.add(new Card(Suit.HEARTS.getSuitID(), Cards.ACE.getCardRank()));
        h10.add(new Card(Suit.SPADES.getSuitID(), Cards.KING.getCardRank()));

        HandRankWinCheckFactory scoreFactory = new HandRankWinCheckFactory();
        System.out.println(scoreFactory.scoreHand(h1).toString());
        System.out.println(scoreFactory.scoreHand(h2).toString());
        System.out.println(scoreFactory.scoreHand(h3).toString());
        System.out.println(scoreFactory.scoreHand(h4).toString());
        System.out.println(scoreFactory.scoreHand(h5).toString());
        System.out.println(scoreFactory.scoreHand(h6).toString());
        System.out.println(scoreFactory.scoreHand(h7).toString());
        System.out.println(scoreFactory.scoreHand(h8).toString());
        System.out.println(scoreFactory.scoreHand(h9).toString());
        System.out.println(scoreFactory.scoreHand(h10).toString());
    }

    public static void randomTests(int numberOfTests, int handSize)
    {
        if (handSize > 7)
        {
            handSize = 7;
            System.out.println("Handsize maximum size is 7. Reverting to maximum.");
        }

        System.out.println("Starting iterations...");
        for (int rep = 0 ; rep < numberOfTests ; rep++ )
        {
            System.out.println("Iteration " + rep + "/" + numberOfTests);
            Deck d1 = new Deck();
            ArrayList<Card> hand = new ArrayList<Card>();

            for (int cn = 0 ; cn < handSize ; cn++)
            {
                hand.add(d1.dealShuffledCard());
            }

            System.out.println(hand);
            System.out.println(HandRankWinCheckFactory.scoreHand(hand).toString());
            System.out.print("\n");
        }
    }

    public static void handRankFrequencyTest(int numberOfTests, int handSize)
    {
        Map<HandRank, Integer> rankingFrequencyMap = new HashMap<HandRank, Integer>();
        for (int i = 0 ; i<HandRank.RanksIndexed.length ; i++)
            rankingFrequencyMap.put(HandRank.RanksIndexed[i], 0);

        if (handSize != 5 || handSize != 7)
            handSize = 7;

        System.out.println("Processing " + numberOfTests + " hands...");
        for (int rep = 0 ; rep < numberOfTests ; rep++ )
        {
            System.out.println("Iteration " + (rep+1) + "/" + numberOfTests);

            Deck d1 = new Deck();
            ArrayList<Card> hand = new ArrayList<Card>();

            for (int cn = 0 ; cn < handSize ; cn++)
            {
                hand.add(d1.dealShuffledCard());
            }
            HandRank handRank = HandRankWinCheckFactory.scoreHand(hand).getHandRank();
            int lastFrequency = rankingFrequencyMap.get(handRank);
            rankingFrequencyMap.remove(handRank);
            rankingFrequencyMap.put(handRank, lastFrequency+1);
        }
        rankingFrequencyMap = MapUtil.reverseSortByValue(rankingFrequencyMap);
        for (Map.Entry<HandRank, Integer> e : rankingFrequencyMap.entrySet())
        {
            System.out.println(e.getValue() + "\t:\t" + e.getKey().rankName());
        }
    }

}
