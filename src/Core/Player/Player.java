package Core.Player;

import Core.Session;
import Factories.HandRankWinCheckFactory;
import Core.CardImp.Card;
import Core.Game.HandScore;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    private String playerName;
    private int remainingCash;
    private ArrayList<Card> hand;
    private PlayerState state;

    public Player(){};

    public Player(String playerName) {
        this.playerName = playerName;
        this.hand = new ArrayList<Card>();
        this.setInitialCash();
    }

    public Player(String playerName, int cash) {
        this.playerName = playerName;
        this.hand = new ArrayList<Card>();
        this.remainingCash = cash;
    }

    public int processTurn()
    {
        setState(PlayerState.ACTIVE);
        // process logic and set state to done/fold/etc
        setState(PlayerState.DONE);
        //return pot
        return 0;
    }

    public void setInitialCash()
    {
        this.remainingCash =  Integer.parseInt(Session.prop.getProperty("initial.cash"));
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCanonicalName()
    {
        return playerName + "(" + this.getClass().getSimpleName() + ")";
    }

    public void receiveCard(Card card)
    {
        this.hand.add(card);
    }

    public int getRemainingCash() {
        return remainingCash;
    }

    public void addToCash(int winnings)
    {
        this.remainingCash += winnings;
    }

    public boolean betToPot(int betAmount) {
        if (this.remainingCash > betAmount)
        {
            this.remainingCash = remainingCash - betAmount;
            return true;
        } else return false;
    }

    public void clearHand()
    {
        this.hand.clear();
    }

    public boolean isSplitPot()
    {
        return false;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public HandScore calculateHandScore(ArrayList<Card> tableCards)
    {
        ArrayList<Card> fullHand = new ArrayList<Card>();
        fullHand.addAll(tableCards);
        fullHand.addAll(hand);
        return HandRankWinCheckFactory.scoreHand(fullHand);
    }

    @Override
    public String toString()
    {
        return "Player Name: " + playerName
                + "\nCash: " + getRemainingCash()
                + "\n" + hand.toString();
    }

    public String toWinnerString()
    {
        return "--Game Summary--\nWinning player: \n"
                + "Player Name: " + playerName
                + "\nCash: " + getRemainingCash();
    }

    public String toCanonicalString()
    {
        return "Player Name: " + getCanonicalName()
                + "\nCash: " + getRemainingCash()
                + "\n" + hand.toString();
    }

    /**
     * Created with IntelliJ IDEA.
     * User: Neil
     * Date: 14/12/12
     * Time: 16:53
     * To change this template use File | Settings | File Templates.
     */
    public static enum PlayerAction {
        WAIT(0),
        CHECK(1),
        FOLD(2),
        BET(3);

        private int actionID;

        public int getActionID(){ return actionID; }
        private PlayerAction(int aID){ this.actionID = aID; }
    }
}
