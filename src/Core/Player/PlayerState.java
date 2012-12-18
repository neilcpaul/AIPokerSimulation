package Core.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 17/12/12
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public enum PlayerState {
    FOLD(0),
    WAITING(1),
    ACTIVE(2),
    DONE(4);

    private int state;

    public int getStateID(){ return state; };
    private PlayerState(int state) { this.state = state; };

}
