package by.siarhei.kb2.app.platforms.android.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import by.siarhei.kb2.app.R;
import by.siarhei.kb2.app.platforms.android.MainView;
import by.siarhei.kb2.app.platforms.android.helpers.Click;
import by.siarhei.kb2.app.server.GameGrid;
import by.siarhei.kb2.app.server.Request;
import by.siarhei.kb2.app.server.Response;
import by.siarhei.kb2.app.server.Server;
import by.siarhei.kb2.app.server.models.battle.MapPointBattle;
import by.siarhei.kb2.app.server.models.battle.WarriorEntity;
import by.siarhei.kb2.app.platforms.android.helpers.Painter;

public class BattleView extends RootView {
    private static final int IMAGE_WIDTH = 96;
    private static final int IMAGE_HEIGHT = 82;

    private Paint countPaint;
    private Paint countPaintBg;

    public BattleView(MainView mainView) {
        super(mainView);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        Click click = getClick(event);
        int x = click.getX() / stepX();
        int y = click.getY() / stepY();

        Request request = new Request();
        request.setAction(Request.ACTION_BATTLE_MOVE);
        request.setMoveTo(x, y);
        Server.getServer().request(request);

        return true;
    }

    @Override
    public void draw(@NonNull Canvas canvas, Response response) {
        Painter painter = getPainter(canvas);
        canvas.drawColor(Color.BLACK);

        MapPointBattle[][] map = response.getBattleField().getMapPoints();
        drawLand(painter, map);

        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 5; y++) {
                if (map[x][y].isMove())
                    drawMoveCircle(painter, x, y, map);
                if (map[x][y].getEntity() != null) {
                    drawWarrior(painter, x, y, map);
                }
            }
        }

        drawSelected(painter);
    }

    private void drawLand(Painter painter, MapPointBattle[][] mapPoints) {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 5; y++) {
                painter.drawBitmap(getImageCache().getImage(mapPoints[x][y].getLand()),
                        stepX() * x, stepY() * y);
            }
        }
    }

    private void drawMoveCircle(Painter painter, int x, int y, MapPointBattle[][] map) {
        MapPointBattle point = map[x][y];
        if (point.getEntity() != null) {
            if (!point.getEntity().isFriendly())
                painter.drawBitmap(getImageCache().getImage(R.drawable.battle_attack),
                        stepX() * x, stepY() * y);
        } else {
            painter.drawBitmap(getImageCache().getImage(R.drawable.battle_move),
                    stepX() * x, stepY() * y);
        }
    }

    private void drawWarrior(Painter painter, int x, int y, MapPointBattle[][] map) {
        MapPointBattle point = map[x][y];
        WarriorEntity warrior = point.getEntity();
        Bitmap image = getImageCache().getImage(point.getEntity().getID());
        if (!warrior.isFriendly()) {
            image = flipImage(image);
        }
        painter.drawBitmap(image, stepX() * x, stepY() * y);
        painter.drawRect(stepX() * x,
                stepY() * y + stepY() - textHeight() / 3,
                stepX() * x +
                        getCountPaint().measureText(
                                Integer.toString(warrior.getCount())) + 4,
                stepY() * y + stepY(),
                getCountPaintBg());
        painter.drawText(Integer.toString(warrior.getCount()),
                stepX() * x + 2,
                stepY() * y + stepY() - 2,
                getCountPaint());
    }

    private void drawSelected(@NonNull Painter painter) {
//        if (battleController.getSelectedX() >= 0 && battleController.getSelectedY() >= 0) {
//            painter.drawBitmap(getImageCache().getImage(R.drawable.battle_select),
//                    stepX() * battleController.getSelectedX(),
//                    stepY() * battleController.getSelectedY());
//        }
    }

    private Bitmap flipImage(Bitmap src) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }

    private Paint getCountPaint() {
        if (countPaint == null) {
            countPaint = new Paint();
            countPaint.setTextSize(textHeight() / 3);
            countPaint.setColor(Color.WHITE);
        }
        return countPaint;
    }

    private Paint getCountPaintBg() {
        if (countPaintBg == null) {
            countPaintBg = new Paint();
            countPaintBg.setColor(Color.BLACK);
        }
        return countPaintBg;
    }

    public Click getClick(@NonNull MotionEvent event) {
        int[] offsets = calcOffsets();
        return new Click(event, offsets[0], offsets[1], getWidth(), getHeight());
    }

    public int[] calcOffsets() {
        double scaleX = (double) getWidth() / (IMAGE_WIDTH * GameGrid.STEP_X);
        double scaleY = (double) getHeight() / (IMAGE_HEIGHT * GameGrid.STEP_Y);
        int[] offsets = new int[2];
        if (scaleX > scaleY) {
            offsets[0] = (getWidth() - stepX() * GameGrid.STEP_X) / 2;
        } else {
            offsets[0] = (getHeight() - stepX() * GameGrid.STEP_Y) / 2;
        }

        return offsets;
    }
}
