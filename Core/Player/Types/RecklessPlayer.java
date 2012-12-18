package Core.Player.Types;

import Core.Game.GameState;
import Core.Player.Player;
import Core.Player.PlayerState;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public class RecklessPlayer extends Player {

    private int aggressiveness = 0;

    public RecklessPlayer(String playerName, int playerNumber) {
        super(playerName, playerNumber);
    }

    @Override
    public int processTurn()
    {
        setState(PlayerState.ACTIVE);
        // process logic and set state to done/fold/etc
        setState(PlayerState.DONE);
        //return pot
        return 0;
    }
}
