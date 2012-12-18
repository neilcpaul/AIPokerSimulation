package Core.Game;

import Core.Player.Player;
import Core.Player.PlayerState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 18/12/12
 * Time: 03:56
 * To change this template use File | Settings | File Templates.
 */
public class BettingRound {
    private Queue<Player> playerQueue;
    private int smallBlindValue;
    private int pot;

    public BettingRound()
    {
        this.playerQueue = new LinkedList<Player>();
    }

    public void initBetting(Queue<Player> bq, int smallBlindValue)
    {
        for (Player p:bq)
        {
            Player tempPlayer = p;
            p.setState(PlayerState.WAITING);
            this.playerQueue.add(p);
        }
        this.smallBlindValue = smallBlindValue;
    }

    public boolean playFirstBetRound()
    {
        boolean blindsFound = false;
        boolean blindsUnpayable = false;
        String initialPlayerName = new String();

        if (playerQueue.isEmpty())
        {
            blindsFound = true;
            blindsUnpayable = true;
        } else
        {
            initialPlayerName = playerQueue.peek().getPlayerName();
        }

        while (!blindsFound)
        {
            Player tempPlayer = playerQueue.poll();
            tempPlayer = processBlind(tempPlayer, smallBlindValue);
            if (!tempPlayer.getState().equals(PlayerState.FOLD))
            {
                playerQueue.add(tempPlayer);
                boolean bigBlindFound = false;
                while (!bigBlindFound)
                {
                    Player tempBB = playerQueue.poll();
                    if (tempBB.equals(tempPlayer))
                    {
                        blindsUnpayable = true;
                        bigBlindFound = true;
                        tempPlayer.addToCash(pot);
                    } else
                    {
                        tempBB = processBlind(tempBB, smallBlindValue*2);
                        if (!tempBB.getState().equals(PlayerState.FOLD))
                        {
                            bigBlindFound = true;
                        } else playerQueue.add(tempBB);
                    }
                }
                blindsFound = true;
            } else
            {
                playerQueue.add(tempPlayer);
                if (playerQueue.peek().getPlayerName().equals(initialPlayerName))
                {
                    blindsFound = true;
                    blindsUnpayable = true;
                }
            }
        }
        return !blindsUnpayable;
    }

    public Player processBlind(Player blindGiver, int blindValue)
    {
        if (blindValue == this.smallBlindValue)
            blindGiver.setState(PlayerState.WAITING);
        else
            blindGiver.setState(PlayerState.DONE);

        if (blindGiver.betToPot(blindValue))
        {
            this.pot = this.pot + blindValue;
        } else
        {
            blindGiver.setState(PlayerState.FOLD);
            System.out.println(blindGiver.getPlayerName() + " was unable to play the blinds, and has been automatically folded.");
        }
        return blindGiver;
    }

    public void playAllBets()
    {
        while (hasPlayersInState(PlayerState.WAITING))
        {
            playNextToBet();
        }
        finishedBetRound();
    }

    public void finishedBetRound()
    {
        while (hasPlayersInState(PlayerState.DONE))
        {
            resetDoneToWaiting();
        }
    }

    public void resetDoneToWaiting()
    {
        //Get head of queue
        Player next = this.playerQueue.poll();

        //If player is finished, reset for next betting round
        if (next.getState().equals(PlayerState.DONE))
        {
            next.setState(PlayerState.WAITING);
        }

        //Rejoin end of queue
        playerQueue.add(next);
    }

    public void playNextToBet()
    {
        //Get head of queue
        Player next = this.playerQueue.poll();

        //If player is waiting to play, process turn
        if (next.getState().equals(PlayerState.WAITING))
        {
            pot+=next.processTurn();
        }

        //Rejoin end of queue
        playerQueue.add(next);
    }

    public boolean hasPlayersInState(PlayerState ps)
    {
        boolean waitingPlayers = false;

        for (Object o:playerQueue.toArray())
        {
            Player it = (Player)o;
            if (it.getState().equals(ps))
                waitingPlayers = true;
        }

        return waitingPlayers;
    }

    public int getPot() {
        return pot;
    }
}
