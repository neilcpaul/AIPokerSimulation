package Enums;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 15/12/12
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public enum Cards {

    NULLCARD(0)
            {
                @Override
                public String cardName()
                {
                    return "Null";
                }
            },

    TWO(2)
            {
                @Override
                public String cardName()
                {
                    return "Two";
                }
            },

    THREE(3)
            {
                @Override
                public String cardName()
                {
                    return "Three";
                }
            },

    FOUR(4)
            {
                @Override
                public String cardName()
                {
                    return "Four";
                }
            },

    FIVE(5)
            {
                @Override
                public String cardName()
                {
                    return "Five";
                }
            },

    SIX(6)
            {
                @Override
                public String cardName()
                {
                    return "Six";
                }
            },

    SEVEN(7)
            {
                @Override
                public String cardName()
                {
                    return "Seven";
                }
            },
    EIGHT(8)
            {
                @Override
                public String cardName()
                {
                    return "Eight";
                }
            },
    NINE(9)
            {
                @Override
                public String cardName()
                {
                    return "Nine";
                }
            },
    TEN(10)
            {
                @Override
                public String cardName()
                {
                    return "Ten";
                }
            },
    JACK(11)
            {
                @Override
                public String cardName()
                {
                    return "Jack";
                }
            },
    QUEEN(12)
            {
                @Override
                public String cardName()
                {
                    return "Queen";
                }
            },
    KING(13)
            {
                @Override
                public String cardName()
                {
                    return "King";
                }
            },
    ACE(14)
            {
                @Override
                public String cardName()
                {
                    return "Ace";
                }
            };

    private int cardRank;
    public abstract String cardName();

    private Cards(int id) {
        cardRank = id;
    }

    public int getCardRank() {
        return cardRank;
    }

    public static Cards[] CardsIndexed = new Cards[]{NULLCARD, null, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};
}
