package Enums;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 15/12/12
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public enum HandRank {
    NORANK(0)
            {
                @Override
                public String rankName()
                {
                    return "Not Ranked";
                }
            },
    HIGHCARD(1)
            {
                @Override
                public String rankName()
                {
                    return "High Card";
                }
            },
    PAIR(2)
            {
                @Override
                public String rankName()
                {
                    return "Pair";
                }
            },
    TWOPAIR(3)
            {
                @Override
                public String rankName()
                {
                    return "Two Pair";
                }
            },
    THREEOFAKIND(4)
            {
                @Override
                public String rankName()
                {
                    return "Three of a Kind";
                }
            },
    STRAIGHT(5)
            {
                @Override
                public String rankName()
                {
                    return "Straight";
                }
            },
    FULLHOUSE(7)
            {
                @Override
                public String rankName()
                {
                    return "Full House";
                }
            },
    FLUSH(6)
            {
                @Override
                public String rankName()
                {
                    return "Flush";
                }
            },
    FOUROFAKIND(8)
            {
                @Override
                public String rankName()
                {
                    return "Four of a Kind";
                }
            },
    STRAIGHTFLUSH(9)
            {
                @Override
                public String rankName()
                {
                    return "Straight Flush";
                }
            },
    ROYALFLUSH(10)
            {
                @Override
                public String rankName()
                {
                    return "Royal Flush";
                }
            };

    private int rank;

    public abstract String rankName();

    private HandRank(int r) {
        rank = r;
    }

    public int getRank() {
        return rank;
    }

    public static HandRank[] RanksIndexed =
            new HandRank[]{NORANK, HIGHCARD, PAIR, TWOPAIR, THREEOFAKIND, STRAIGHT, FLUSH, FULLHOUSE, FOUROFAKIND, STRAIGHTFLUSH, ROYALFLUSH};
}
