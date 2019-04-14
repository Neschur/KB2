package by.siarhei.kb2.app.server;

public class Request {
    public static final int ACTION_OK = 1;
    public static final int ACTION_SELECT = 2;
    public static final int ACTION_EXIT = 3;
    public static final int ACTION_MOVE = 4;
    public static final int ACTION_BUY_ARMY = 5;
    public static final int ACTION_OPEN_GAME_MENU = 6;
    public static final int ACTION_GIVE_ARMY = 7;
    public static final int ACTION_ACCEPT_BATTLE = 8;
    public static final int ACTION_DECLINE_BATTLE = 9;
    public static final int ACTION_BATTLE_MOVE = 10;
//    public static final int ACTION_BATTLE_MOVE_TO = 11;
//    public static final int ACTION_BATTLE_ATTACK_AT = 12;

    private int x;
    private int y;
    private int action = 0;
    private int menuItem = 0;
    private String data;

    public Request() {

    }

    public void setMoveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setMenuItem(int menuItem) {
        this.menuItem = menuItem;
    }

    public int getMenuItem() {
        return menuItem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
