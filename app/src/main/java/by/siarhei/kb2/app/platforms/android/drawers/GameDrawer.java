package by.siarhei.kb2.app.platforms.android.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;

import by.siarhei.kb2.app.platforms.android.views.XMainView;
import by.siarhei.kb2.app.platforms.android.views.helpers.Painter;
import by.siarhei.kb2.app.server.GameGrid;
import by.siarhei.kb2.app.server.ServerView;

public class GameDrawer {
    public static void draw(@NonNull Canvas canvas, ServerView serverView, XMainView mainView) {
        Painter painter = mainView.getPainter(canvas);
        canvas.drawColor(Color.BLACK);

        GameGrid grid = serverView.getGameGrid();

        for (int x = 0; x < GameGrid.STEP_X; x++) {
            for (int y = 0; y < GameGrid.STEP_Y; y++) {
                if (x < 5) {
                    painter.drawBitmap(mainView.getImageCache().getImage(grid.getBackgroundBuyXY(x, y)),
                            mainView.stepX() * x, mainView.stepY() * y);
                }
                if (grid.getImageBuyXY(x, y) > -1) {
                    painter.drawBitmap(mainView.getImageCache().getImage(grid.getImageBuyXY(x, y)),
                            mainView.stepX() * x, mainView.stepY() * y);
                }
            }
        }
    }
}
