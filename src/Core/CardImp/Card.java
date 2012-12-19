package Core.CardImp;

import Enums.Cards;
import Enums.Suit;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class Card implements Comparable{
    private Cards value;
    private Suit suit;

    public Card()
    {
        this.suit = Suit.NULLSUIT;
        this.value = Cards.NULLCARD;
    }

    public Card(Suit suit, Cards cardValue)
    {
        this.suit = suit;
        this.value = cardValue;
    }

    public Card(int suit, int value)
    {
        this.suit = Suit.SuitIndexed[suit];
        this.value = Cards.CardsIndexed[value];
    }

    public Suit getSuit() {
        return suit;
    }

    public Cards getValue() {
        return value;
    }

    private String genSpriteString()
    {
        String spriteName = String.valueOf(this.getValue().cardName().toLowerCase().toCharArray()[0]);
        String cardValue = String.valueOf(this.getValue().getCardRank());
        return spriteName.concat(cardValue);
    }

    //public

    @Override
    public String toString()
    {
        return value.cardName() + " of " + suit.suitName();
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass().equals(this.getClass()))
        {
            try
            {
                Card comparableCard = (Card)o;

                if (comparableCard.getValue().getCardRank() <= this.value.getCardRank())
                {
                    if (comparableCard.getValue().getCardRank() < this.value.getCardRank())
                        return 1;
                    else
                        return 0;
                } else
                    return -1;
            } catch (Exception e)
            {
                System.out.println("Unhandled exception. \n" + e.getMessage());
            }
        }
        else
        {
            System.out.println("Object cannot be compared with type 'Card'");
        }
        return 0;  //Should never reach, if object is of type card
    }
}
