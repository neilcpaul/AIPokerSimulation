package Core.Player.Types;

import Core.Game;
import Core.Player.Player;
import Core.Player.PlayerState;
import javafx.scene.control.SplitPane;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 19/12/12
 * Time: 07:35
 * To change this template use File | Settings | File Templates.
 */
public class DrawNPC extends Player {
    ArrayList<Player> drawingPlayers;

    public DrawNPC () {
        super("NPC", 0);
        drawingPlayers = new ArrayList<Player>();
    }

    public void addDrawPlayer(Player player)
    {
        drawingPlayers.add(player);
    }

    public void addDrawPlayer(ArrayList<Player> players)
    {
        this.drawingPlayers = players;
    }

    @Override
    public void addToCash(int winnings)
    {
        int splitPot = winnings/drawingPlayers.size();
        for (Player p:drawingPlayers)
        {
            p.addToCash(splitPot);
        }
    }

    @Override
    public boolean isSplitPot()
    {
        return true;
    }

    @Override
    public int processTurn()
    {
        System.err.println("NPE Draw Player should not be processing a turn.");
        return 0;
    }

    @Override
    public String getCanonicalName()
    {
        String drawingPlayers = "";

        for(Player p:this.drawingPlayers)
        { drawingPlayers += p.getPlayerName() + " "; }

        return "Split pot: \n"
                + "Players: " + drawingPlayers;
    }
}
