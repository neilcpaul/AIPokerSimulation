package Core.Game;

import Core.Card;
import Core.Deck;
import Core.Player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 19:29
 * To change this template use File | Settings | File Templates.
 */
public class Round {
    private ArrayList<Card> tableCards;
    private ArrayList<Card> burnPile;
    private ArrayList<Player> currentPlayers;
    private Queue<Player> blindQueue;

    private Map.Entry<Player, HandScore> roundWinner;
    private Deck deck;
    private boolean gameOver;

    private final int playerCards = 2;
    private final int flopCards = 3;
    private final int turn = 1;
    private final int river = 1;
    private final int smallBlind;

    private int pot = 0;

    private final long roundTime;

    public Round(ArrayList<Player> currentPlayers, int smallBlind)
    {
        this.roundTime = System.currentTimeMillis();

        this.deck = new Deck();
        this.tableCards = new ArrayList<Card>();
        this.burnPile = new ArrayList<Card>();
        this.smallBlind = smallBlind;
        this.currentPlayers = currentPlayers;

    }

    public void dealToPlayer()
    {
        for (Player currentPlayer : currentPlayers) {
            currentPlayer.receiveCard(deck.dealShuffledCard());
        }
    }

    public void dealToPlayer(int numberOfCards)
    {
        for (int dealt = 0 ; dealt < numberOfCards ; dealt++)
            dealToPlayer();
    }

    public void burn()
    {
        burnPile.add(deck.dealShuffledCard());
    }

    public void dealToTable()
    {
        tableCards.add(deck.dealShuffledCard());
    }

    public void dealToTable(int numberOfCards)
    {
        for (int dealt = 0 ; dealt < numberOfCards ; dealt++)
            dealToTable();
    }

    public ArrayList<Player> getCurrentPlayers()
    {
        ArrayList<Player> eligiblePlayers = new ArrayList<Player>();
        for (Player p : currentPlayers)
        {
            if (p.getRemainingCash() > this.smallBlind)
            {
                eligiblePlayers.add(p);
            }
        }
        return eligiblePlayers;
    }

    public void updateBlindQueue(Queue<Player> blindQueue)
    {
        this.blindQueue = blindQueue;
    }

    private Map.Entry<Player, HandScore> checkWin()
    {
        return new CheckWin(currentPlayers, tableCards).checkWin();
    }

    public long calcRoundTime()
    {
        return System.currentTimeMillis() - this.roundTime;
    }

    public Map.Entry<Player, HandScore> getRoundWinner() {
        return roundWinner;
    }

    public void gatherPlayerHands()
    {
        for (Player currentPlayer : currentPlayers) {
            currentPlayer.clearHand();
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player checkWinner()
    {
        Player chipLeader = new Player();
        chipLeader.betToPot(chipLeader.getRemainingCash());
        for (Player p : currentPlayers)
        {
            if (p.getRemainingCash() > chipLeader.getRemainingCash())
                chipLeader = p;
        }

        return chipLeader;
    }

    public void playRound()
    {
        //Blinds
        BettingRound br = new BettingRound();
        br.initBetting(this.blindQueue, this.smallBlind);
        boolean continueRound = br.playFirstBetRound();

        if (continueRound)
        {
            //Deal 2 each
            dealToPlayer(playerCards);
            //Betting
            br.playAllBets();
            //Burn 1 card
            burn();
            //Deal 3 to table
            dealToTable(flopCards);
            //Betting
            br.playAllBets();
            //Burn 1 card
            burn();
            //Deal 1 to table
            dealToTable(turn);
            //Betting
            br.playAllBets();
            //Burn 1 card
            burn();
            //Deal 1 to table
            dealToTable(river);
            //Betting
            br.playAllBets();
            //Check win
            roundWinner = checkWin();
            this.pot = br.getPot();

        } else this.gameOver = true;

        longSummariseRound();
        gatherPlayerHands();
        roundWinner.getKey().addToCash(pot);
    }

    public String toString()
    {
        return toString(1) + "\nPot" + pot + "\tRound time: " + calcRoundTime() + "ms";
    }

    public String toString(int value)
    {
        return roundWinner.getKey().getCanonicalName() + ": " + roundWinner.getValue().toString();
    }

    private void longSummariseRound()
    {
        System.out.println("Round Summary: \n");
        for (Player currentPlayer : currentPlayers) {
            System.out.println(currentPlayer.toCanonicalString());
        }
        System.out.println("\nTable:");
        System.out.println(tableCards.toString());
        System.out.println("\nWinner:");
        System.out.println(roundWinner.getKey().getCanonicalName() + ": " + roundWinner.getValue().toString());
        System.out.println("Winners pot: " + pot);
        System.out.println("\nRemaining Deck: " + deck.deckSize());
    }
}
