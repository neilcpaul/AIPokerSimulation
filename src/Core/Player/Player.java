package Core.Player;

import Factories.HandRankWinCheckFactory;
import Core.Card;
import Core.Game.GameState;
import Core.Game.HandScore;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    private String playerName;
    private int playerNumber;
    private int remainingCash;
    private ArrayList<Card> hand;
    private Properties prop = new Properties();
    private PlayerState state;

    public Player(){};

    public Player(String playerName, int playerNumber) {
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.hand = new ArrayList<Card>();
        this.setInitialCash();
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
        try {
            this.prop.load(new FileInputStream("D:\\Uni\\AIpoker\\src\\Core\\Game\\game.properties"));
            this.remainingCash =  Integer.parseInt(this.prop.getProperty("initial.cash"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        String temp = "Player Name: " + playerName
                + "\nCash: " + getRemainingCash()
                + "\n" + hand.toString();
        return temp;
    }

    public String toWinnerString()
    {
        String temp = "Winning player: \n"
                + "Player Name: " + playerName
                + "\nCash: " + getRemainingCash();
        return temp;
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
