package Enums;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 15/12/12
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public enum Suit {
    SPADES(0)
            {
                @Override
                public String suitName()
                {
                    return "Spades";
                }
            },
    HEARTS(1)
            {
                @Override
                public String suitName()
                {
                    return "Hearts";
                }
            },
    CLUBS(2)
            {
                @Override
                public String suitName()
                {
                    return "Clubs";
                }
            },
    DIAMONDS(3)
            {
                @Override
                public String suitName()
                {
                    return "Diamonds";
                }
            },
    NULLSUIT(4)
            {
                @Override
                public String suitName()
                {
                    return "Null";
                }
            };

    private int suitID;
    public abstract String suitName();

    private Suit(int id) {
        suitID = id;
    }

    public int getSuitID() {
        return suitID;
    }

    public static Suit[] SuitIndexed = new Suit[]{SPADES, HEARTS, CLUBS, DIAMONDS, NULLSUIT};
}
