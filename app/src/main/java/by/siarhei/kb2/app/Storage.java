package by.siarhei.kb2.app;

import by.siarhei.kb2.app.server.models.Game;

public interface Storage {
    boolean saveGame(Game game, String key);

    Game loadGame(String key);
}
