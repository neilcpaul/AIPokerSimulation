package Factories;

import Enums.Cards;
import Enums.Suit;
import Core.CardImp.Card;
import Enums.HandRank;
import Core.BaseLogic.HandScore;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */
public class HandRankWinCheckFactory {

    public static HandScore scoreHand(ArrayList<Card> cc)
    {
        HandScore score = new HandScore();
        //Will take in 7 cards, we need to check for the best 5.
        //Check for 7:
        if (cc.size() == 5 || cc.size() == 7)
        {
            score = CheckRoyalFlush(cc);
            if (score.getHandRank() == HandRank.NORANK)
            {
                score = CheckStraightFlush(cc);
            }
            if (score.getHandRank() == HandRank.NORANK)
            {
                score = CheckFourOfAKind(cc);
            }
            if (score.getHandRank() == HandRank.NORANK)
            {
                score = CheckFullHouse(cc);
            }
            if (score.getHandRank() == HandRank.NORANK)
            {
                score = CheckFlush(cc);
            }
            if (score.getHandRank() == HandRank.NORANK)
            {
                score = CheckStraight(cc);
            }
            if (score.getHandRank() == HandRank.NORANK)
            {
                score = CheckThreeOfAKind(cc);
            }
            if (score.getHandRank() == HandRank.NORANK)
            {
                score = CheckTwoPair(cc);
            }
            if (score.getHandRank() == HandRank.NORANK)
            {
                score = CheckPair(cc);
            }
            if (score.getHandRank() == HandRank.NORANK)
            {
                score = CheckHighCard(cc);
            }


        }
        return score;
    }

    private synchronized static HandScore CheckRoyalFlush(ArrayList<Card> cc)
    {
        //Royal flush
        // A->10, all same suit
        ArrayList<Card> tempcc = new ArrayList<Card>();
        for (Card c:cc)
        {
            tempcc.add(c);
        }

        Suit workingSuit = Suit.NULLSUIT;
        boolean flush = false;
        //check for same suit
        int[] suit = new int[]{0,0,0,0};
        Iterator tcci = tempcc.iterator();
        for (int cci = 0 ; tcci.hasNext() && !flush ; cci ++ )
        {
            Card temp = (Card)tcci.next();
            suit[temp.getSuit().getSuitID()]++;
            // has 5 of same suit?
            for (int si = 0 ; si < Suit.SuitIndexed.length-1; si++)
            {
                if (suit[si]==5)
                {
                    flush = true;
                    workingSuit = Suit.SuitIndexed[si];
                }
            }
        }

        //if has 5 same suit, strip the non-same-suit cards and check for consecutive cards
        if (flush)
        {
            for (int cs = 0 ; cs < tempcc.size() ; cs++)
            {
                if (tempcc.get(cs).getSuit()!=workingSuit)
                    tempcc.remove(cs);
            }
            //put cards values in treeset and check for n+1
            TreeSet<Card> tempTS = new TreeSet<Card>();
            for (Card c :tempcc)
            {
                tempTS.add(c);
            }
            Cards currentCard = Cards.NULLCARD;
            boolean consecutive = false;
            for (Card c:tempTS)
            {
                if (c.getValue()== Cards.TEN)
                    currentCard = Cards.TEN;
                else if (c.getValue()==Cards.CardsIndexed[currentCard.getCardRank()+1])
                {
                    if (c.getValue()==Cards.ACE && currentCard==Cards.KING)
                        consecutive=true;
                    currentCard=c.getValue();
                }
                else break;
            }
            if (consecutive)
            {
                return new HandScore(HandRank.ROYALFLUSH, workingSuit);
            }
        }
        return new HandScore(HandRank.NORANK);
    }

    private synchronized static HandScore CheckStraightFlush(ArrayList<Card> cc)
    {
        //Straight flush
        //Any 5 consecutive, all same suit
        ArrayList<Card> tempcc = new ArrayList<Card>();
        for (Card c:cc)
        {
            tempcc.add(c);
        }

        Suit workingSuit = Suit.NULLSUIT;
        boolean flush = false;
        //check for same suit
        int[] suit = new int[]{0,0,0,0};
        Iterator tcci = tempcc.iterator();
        for (int cci = 0 ; tcci.hasNext() && !flush ; cci ++ )
        {
            Card temp = (Card)tcci.next();
            suit[temp.getSuit().getSuitID()]++;
            // has 5 of same suit?
            for (int si = 0 ; si < Suit.SuitIndexed.length-1; si++)
            {
                if (suit[si]==5)
                {
                    flush = true;
                    workingSuit = Suit.SuitIndexed[si];
                }
            }
        }

        //if has 5 same suit, strip the non-same-suit cards and check for consecutive cards
        if (flush)
        {
            for (int cs = 0 ; cs < tempcc.size() ; cs++)
            {
                if (tempcc.get(cs).getSuit()!=workingSuit)
                    tempcc.remove(cs);
            }
            //put cards values in treeset and check for n+1
            TreeSet<Card> tempTS = new TreeSet<Card>();
            for (Card c :tempcc)
            {
                tempTS.add(c);
            }
            Cards currentCard = Cards.NULLCARD;
            boolean consecutive = false;
            for (Card c:tempTS)
            {
                if (currentCard == Cards.NULLCARD)
                {
                    currentCard = c.getValue();
                } else if (c.getValue()==Cards.CardsIndexed[currentCard.getCardRank()+1])
                {
                    if (c.getValue()==Cards.CardsIndexed[tempTS.first().getValue().getCardRank()+4])
                        consecutive=true;
                    currentCard=c.getValue();
                }
                else break;
            }
            if (consecutive)
            {
                //Double check
                ArrayList<Cards> cardTrack = new ArrayList<Cards>();
                for (Card c:tempTS)
                {
                    cardTrack.add(c.getValue());
                }
                for (int it = currentCard.getCardRank() ; it > currentCard.getCardRank() - 4 ; it--)
                {
                    if (!cardTrack.contains(Cards.CardsIndexed[it]))
                        consecutive = false;
                }
                if (consecutive)
                    return new HandScore(HandRank.STRAIGHTFLUSH, workingSuit, new Card[]{tempTS.pollLast(),tempTS.pollLast(),tempTS.pollLast(),tempTS.pollLast(),tempTS.pollLast()});
            }
        }
        return new HandScore(HandRank.NORANK);
    }

    private synchronized static HandScore CheckFourOfAKind(ArrayList<Card> cc)
    {
        //Four of a kind, 1 kicker
        Suit workingSuit = Suit.NULLSUIT;

        int[] cardCount = new int[15];
        for (int init = 0 ; init < cardCount.length ; init++ )
            cardCount[init] = 0;

        TreeSet<Card> tempTS = new TreeSet<Card>();
        for (Card c:cc)
        {
            tempTS.add(c);
            cardCount[c.getValue().getCardRank()]++;
        }

        boolean hasFourOfAKind = false;
        Cards valueOf4Count = Cards.NULLCARD;
        for (int idx = 0 ; idx < cardCount.length ; idx++ )
            if (cardCount[idx]==4)
            {
                hasFourOfAKind = true;
                valueOf4Count = Cards.CardsIndexed[idx];
            }

        Card highestLTFour = tempTS.pollLast();
        highestLTFour = tempTS.pollLast().getValue().equals(valueOf4Count) ? tempTS.pollLast() : highestLTFour;

        if (hasFourOfAKind)
        {
            return new HandScore(HandRank.FOUROFAKIND, valueOf4Count, new Card[]{highestLTFour});
        }
        return new HandScore(HandRank.NORANK);
    }

    private synchronized static HandScore CheckFullHouse(ArrayList<Card> cc)
    {
        //Full house
        //3 of one value, 2 of another
        int workingSuit = -1;

        int[] cardCount = new int[15];
        for (int init = 0 ; init < cardCount.length ; init++ )
            cardCount[init] = 0;

        ArrayList<Card> tempcc = new ArrayList<Card>();
        for (Card c:cc)
        {
            tempcc.add(c);
            cardCount[c.getValue().getCardRank()]++;
        }

        boolean hasFullHouse = false;
        Cards indexOfHighest3OfAKind = Cards.NULLCARD;
        Cards indexOfHighestPair = Cards.NULLCARD;
        for (int idx = 0 ; idx < cardCount.length ; idx++ )
            if (cardCount[idx]==3)
            {
                indexOfHighest3OfAKind = Cards.CardsIndexed[idx];
            }else if (cardCount[idx]==2)
            {
                indexOfHighestPair = Cards.CardsIndexed[idx];
            }

        if (indexOfHighest3OfAKind != Cards.NULLCARD && indexOfHighestPair != Cards.NULLCARD)
        {
            hasFullHouse = true;
        }

        if (hasFullHouse)
        {
            return new HandScore(HandRank.FULLHOUSE, indexOfHighest3OfAKind, indexOfHighestPair, new Card[]{});
        }
        return new HandScore(HandRank.NORANK);
    }

    private synchronized static HandScore CheckFlush(ArrayList<Card> cc)
    {
        //Flush
        // Any card values, same suit

        ArrayList<Card> tempcc = new ArrayList<Card>();
        for (Card c:cc)
        {
            tempcc.add(c);
        }

        Suit workingSuit = Suit.NULLSUIT;
        boolean flush = false;
        //check for same suit
        int[] suit = new int[]{0,0,0,0};
        Iterator tcci = tempcc.iterator();
        for (int cci = 0 ; tcci.hasNext() && !flush ; cci ++ )
        {
            Card temp = (Card)tcci.next();
            suit[temp.getSuit().getSuitID()]++;
            // has 5 of same suit?
            for (int si = 0 ; si < Suit.SuitIndexed.length-1; si++)
            {
                if (suit[si]==5)
                {
                    flush = true;
                    workingSuit = Suit.SuitIndexed[si];
                }
            }
        }

        //if flush, get highest cards
        if (flush)
        {
            for (int cs = 0 ; cs < tempcc.size() ; cs++)
            {
                if (tempcc.get(cs).getSuit()!=workingSuit)
                    tempcc.remove(cs);
            }
            //put cards values in treeset and check for n+1
            TreeSet<Card> tempTS = new TreeSet<Card>();
            for (Card c :tempcc)
            {
                tempTS.add(c);
            }
            return new HandScore(HandRank.FLUSH, workingSuit, new Card[]{tempTS.pollLast(),tempTS.pollLast(),tempTS.pollLast(),tempTS.pollLast(),tempTS.pollLast()});
        }
        return new HandScore(HandRank.NORANK);
    }

    private synchronized static HandScore CheckStraight(ArrayList<Card> cc)
    {
        //Straight
        //5 consecutive cards, suit irrelevant

        ArrayList<Card> tempcc = new ArrayList<Card>();
        for (Card c:cc)
        {
            tempcc.add(c);
        }

        //put cards values in treeset and check for n+1
        TreeSet<Card> tempTS = new TreeSet<Card>();
        Set<Card> syncTS = Collections.synchronizedSet(tempTS);

        for (Card c :tempcc)
        {
            syncTS.add(c);
        }
        Cards currentCard = Cards.NULLCARD;
        Cards runStart = Cards.NULLCARD;
        boolean consecutive = false;
        try
        {
            Iterator<Card> itC = tempTS.iterator();
            while (itC.hasNext())
            {
                Card itN = itC.next();
                if (itN.getValue()==Cards.CardsIndexed[currentCard.getCardRank()+1])
                {
                    if (runStart!=Cards.NULLCARD && itN.getValue()==Cards.CardsIndexed[runStart.getCardRank()+4])
                        consecutive=true;
                    currentCard=itN.getValue();
                }
                else if (!consecutive)
                {
                    currentCard = itN.getValue();
                    runStart = itN.getValue();
                }
                else if (consecutive)
                {
                    itC.remove();
                }
            }
        } catch (ArrayIndexOutOfBoundsException aioob)
        {
            consecutive = false;
        }

        if (consecutive)
        {
            ArrayList<Cards> cardTrack = new ArrayList<Cards>();
            List<Cards> syncCards = Collections.synchronizedList(cardTrack);
            for (Card c:tempTS)
            {
                syncCards.add(c.getValue());
            }
            for (int it = currentCard.getCardRank() ; it > currentCard.getCardRank() - 4 ; it--)
            {
                if (!cardTrack.contains(Cards.CardsIndexed[it]))
                    consecutive = false;
            }
            if (consecutive)
            {
                return new HandScore(HandRank.STRAIGHT, new Card[]{tempTS.pollLast(),tempTS.pollLast(),tempTS.pollLast(),tempTS.pollLast(),tempTS.pollLast()});
            }
        }
        return new HandScore(HandRank.NORANK);
    }

    private synchronized static HandScore CheckThreeOfAKind(ArrayList<Card> cc)
    {
        //3 of a kind
        //3 same value, suit irrelevant, 2 kickers
        int workingSuit = -1;

        int[] cardCount = new int[15];
        for (int init = 0 ; init < cardCount.length ; init++ )
            cardCount[init] = 0;

        ArrayList<Card> tempcc = new ArrayList<Card>();
        for (Card c:cc)
        {
            tempcc.add(c);
            cardCount[c.getValue().getCardRank()]++;
        }

        boolean hasThreeOfAKind = false;
        Cards indexOfHighest3OfAKind = Cards.NULLCARD;
        for (int idx = 0 ; idx < cardCount.length ; idx++ )
            if (cardCount[idx]==3)
            {
                indexOfHighest3OfAKind = Cards.CardsIndexed[idx];
            }

        if (cardCount[indexOfHighest3OfAKind.getCardRank()] > 0)
        {
            hasThreeOfAKind = true;
        }

        TreeSet<Card> tempTS = new TreeSet<Card>();
        for (Card c :tempcc)
        {
            if (c.getValue() != indexOfHighest3OfAKind)
                tempTS.add(c);
        }

        if (hasThreeOfAKind)
        {
            return new HandScore(HandRank.THREEOFAKIND, indexOfHighest3OfAKind, new Card[]{tempTS.pollLast(), tempTS.pollLast()});
        }
        return new HandScore(HandRank.NORANK);
    }

    private synchronized static HandScore CheckTwoPair(ArrayList<Card> cc)
    {
        //2 pair
        //two pair of same value, suit irrelevant, 1 kicker
        int workingSuit = -1;

        int[] cardCount = new int[15];
        for (int init = 0 ; init < cardCount.length ; init++ )
            cardCount[init] = 0;

        ArrayList<Card> tempcc = new ArrayList<Card>();
        for (Card c:cc)
        {
            tempcc.add(c);
            cardCount[c.getValue().getCardRank()]++;
        }

        boolean hasTwoPair = false;
        TreeSet<Cards> pairSets = new TreeSet<Cards>();
        for (int idx = 0 ; idx < cardCount.length ; idx++ )
            if (cardCount[idx]>=2)
            {
                pairSets.add(Cards.CardsIndexed[idx]);
            }

        if (pairSets.size() >= 2)
        {
            hasTwoPair = true;
        }

        Card nextHighest = new Card();
        for (Card c:tempcc)
        {
            if (c.compareTo(nextHighest) == 1 && !pairSets.contains(c.getValue()))
                nextHighest = c;
        }

        if (hasTwoPair)
        {
            return new HandScore(HandRank.TWOPAIR, pairSets.pollLast(), pairSets.pollLast(), new Card[]{nextHighest});
        }
        return new HandScore(HandRank.NORANK);
    }

    private synchronized static HandScore CheckPair(ArrayList<Card> cc)
    {
        //One pair
        //suit irrelevant, 3 kickers
        int workingSuit = -1;

        int[] cardCount = new int[15];
        for (int init = 0 ; init < cardCount.length ; init++ )
            cardCount[init] = 0;

        ArrayList<Card> tempcc = new ArrayList<Card>();
        for (Card c:cc)
        {
            tempcc.add(c);
            cardCount[c.getValue().getCardRank()]++;
        }

        boolean hasPair = false;
        Cards indexOfHighestPair = Cards.NULLCARD;
        for (int idx = 0 ; idx < cardCount.length ; idx++ )
            if (cardCount[idx]==2)
            {
                indexOfHighestPair = Cards.CardsIndexed[idx];
            }

        if (indexOfHighestPair.getCardRank() > 0)
        {
            hasPair = true;
        }

        TreeSet<Card> nextHighest = new TreeSet<Card>();
        for (Card c:tempcc)
        {
            if (c.getValue().getCardRank() != indexOfHighestPair.getCardRank())
                nextHighest.add(c);
        }

        if (hasPair)
        {
            return new HandScore(HandRank.PAIR, indexOfHighestPair, new Card[]{nextHighest.pollLast(), nextHighest.pollLast(), nextHighest.pollLast()});
        }
        return new HandScore(HandRank.NORANK);
    }

    private synchronized static HandScore CheckHighCard(ArrayList<Card> cc)
    {
        //high card
        // 4 kickers

        TreeSet<Card> tempTS = new TreeSet<Card>();
        for (Card c:cc)
        {
            tempTS.add(c);
        }

        return new HandScore(HandRank.HIGHCARD, tempTS.pollLast().getValue(), new Card[]{tempTS.pollLast(), tempTS.pollLast(), tempTS.pollLast(), tempTS.pollLast()});
    }
}
