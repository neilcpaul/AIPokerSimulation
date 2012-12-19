package Core.Game;

import Enums.Cards;
import Enums.HandRank;
import Enums.Suit;
import Core.CardImp.Card;
import sun.security.util.Debug;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 15/12/12
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public class HandScore implements Comparable{
    private Debug dbg = new Debug();
    private HandRank handRank;
    private Card[] kickers;
    private Suit suit;
    private Cards primaryCard = Cards.NULLCARD;
    private Cards secondaryCard = Cards.NULLCARD;

    public HandScore()
    {
        this.handRank = HandRank.NORANK;
        this.kickers = new Card[]{new Card()};
        this.suit = Suit.NULLSUIT;
    }

    public HandScore(HandRank handRank)
    {
        this.handRank = handRank;
        this.suit = Suit.NULLSUIT;
        this.kickers = new Card[]{new Card()};
    }

    public HandScore(HandRank handRank, Suit workingSuit)
    {
        this.handRank = handRank;
        this.suit = workingSuit;
        this.kickers = new Card[]{new Card()};
    }

    public HandScore(HandRank handRank, Cards primaryCard, Card[] kickers)
    {
        this.handRank = handRank;
        this.primaryCard = primaryCard;
        this.kickers = kickers;
    }

    public HandScore(HandRank handRank, Cards primaryCard, Cards secondaryCard, Card[] kickers)
    {
        this.handRank = handRank;
        this.primaryCard = primaryCard;
        this.secondaryCard = secondaryCard;
        this.kickers = kickers;
    }

    public HandScore(HandRank handRank, Card[] kickers)
    {
        this(handRank,Suit.NULLSUIT, kickers);
    }

    public HandScore(HandRank handRank, Suit workingSuit, Card[] kickers) {
        this.handRank = handRank;
        this.suit = workingSuit;
        this.kickers = kickers;
    }

    public HandRank getHandRank() {
        return handRank;
    }

    public void setHandRank(HandRank handRank) {
        this.handRank = handRank;
    }

    public Suit getWorkingSuit() {
        return suit;
    }

    public void setWorkingSuit(Suit workingSuit) {
        this.suit = workingSuit;
    }

    public Card[] getKickers() {
        return kickers;
    }

    public void setKickers(Card[] kickers) {
        this.kickers = kickers;
    }

    @Override
    public String toString()
    {
        if (handRank.getRank() == 0)
        {
            return "No rank.";
        }

        boolean hasKicker = (kickers.length > 0);
        String kickerText = " with ";
        if (kickers.length > 0)
            for (int ki = 0 ; ki < kickers.length ; ki++)
            {
                try
                {
                    kickerText = kickerText + kickers[ki].getValue().cardName();
                    if (ki < kickers.length-1) kickerText = kickerText.concat(", ");
                } catch (NullPointerException npe)
                {
                    System.err.println("Kicker #" + ki + " cannot be found.");
                }

            }
        kickerText = kickerText.concat(" kickers.");

        boolean showKickers = hasKicker && (handRank == HandRank.STRAIGHTFLUSH ||
                                handRank == HandRank.FOUROFAKIND ||
                                handRank == HandRank.THREEOFAKIND ||
                                handRank == HandRank.STRAIGHT ||
                                handRank == HandRank.TWOPAIR ||
                                handRank == HandRank.PAIR ||
                                handRank == HandRank.HIGHCARD);

        boolean showPrimary = primaryCard != Cards.NULLCARD;
        boolean showSecondary = secondaryCard != Cards.NULLCARD;

        String prisecText = " (".concat(showPrimary ? primaryCard.cardName() : "");
        prisecText = prisecText.concat((showSecondary? ", " + secondaryCard.cardName() + ")":")"));

        boolean showSuit = suit != Suit.NULLSUIT &&
                        (handRank == HandRank.ROYALFLUSH ||
                        handRank == HandRank.STRAIGHTFLUSH ||
                        handRank == HandRank.FLUSH);

        String temp = handRank.rankName();
        temp = temp.concat(showSuit ? " (" + suit.suitName() + ") " : "");
        temp = temp.concat(showPrimary || showSecondary ? prisecText : "");
        temp = temp.concat((showKickers)? kickerText:"");
        return temp;
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass().equals(this.getClass()))
        {
            HandScore comparableScore = (HandScore)o;
            if (this.getHandRank().getRank() > comparableScore.getHandRank().getRank())
                return 1;
            else if (this.getHandRank().getRank() < comparableScore.getHandRank().getRank())
                return -1;
            else if (this.getHandRank().getRank() == comparableScore.getHandRank().getRank()
                    && this.kickers.length > 0 && comparableScore.kickers.length > 0)
            {
                try{
                    for (int ki = 0 ; ki < kickers.length ; ki++)
                    {
                        if (this.kickers[ki].getValue().getCardRank() > comparableScore.kickers[ki].getValue().getCardRank())
                            return 1;
                        else if (this.kickers[ki].getValue().getCardRank() < comparableScore.kickers[ki].getValue().getCardRank())
                            return -1;
                    }
                } catch (NullPointerException npe)
                {
                    System.out.println("Non-fatal error: " + npe.getMessage());
                }   catch (ArrayIndexOutOfBoundsException aioobe)
                {
                    System.out.println("Fatal ArrayIndexOutOfBoundsException caught. Not enough kickers to compare.\n" + aioobe.getStackTrace().toString());
                }
            }


        }
        return 0;
    }
}
