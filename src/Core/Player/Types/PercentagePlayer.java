package Core.Player.Types;

import Core.Player.Player;
import Core.Player.PlayerState;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class PercentagePlayer extends Player {
    public PercentagePlayer(String playerName) {
        super(playerName);
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
