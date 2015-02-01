package com.neschur.kb2.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.neschur.kb2.app.I18n;
import com.neschur.kb2.app.ImageCache;
import com.neschur.kb2.app.R;
import com.neschur.kb2.app.controllers.PlayerViewsController;
import com.neschur.kb2.app.controllers.ViewController;
import com.neschur.kb2.app.models.GameGrid;
import com.neschur.kb2.app.models.Magics;
import com.neschur.kb2.app.warriors.WarriorFactory;

import java.util.HashMap;

class MagicView extends View {
    private Magics magics;
    private int mode = 0;
    private final HashMap<Integer, Integer> armyIdCache = new HashMap<>();

    public MagicView(PlayerViewsController playerViewsController) {
        super(playerViewsController);
        this.magics = playerViewsController.getPlayer().getMagics();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (mode == 0) {
            int item = (int) event.getY() / menuItemHeight();
            if (item == 4) {
                mode = 1;
            } else {
                viewController.viewClose();
            }
        } else if (mode == 1) {
            int x = (int) event.getX() / stepX() + 1;
            int y = (int) event.getY() / stepY() + 1;
            System.out.println(armyIdCache.get(x*y));
        }
        drawThread.refresh();
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if (mode != 1) {
            for (int i = 1; i < 8; i++) {
                canvas.drawText(I18n.translate("magic_hiking_magic" + i) + ": "
                                + magics.getMagic(i - 1),
                        0, i * menuItemHeight(), getDefaultPaint());
            }
        } else {
            ImageCache imageCache = ImageCache.getInstance(getResources(), stepX(), stepY());
            int count = 0;
            for (String army : WarriorFactory.getAllArmyTextIds()) {
                int imageId = 0;
                try {
                    imageId = R.drawable.class.getField("army_" + army).getInt(new R.string());
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                canvas.drawBitmap(imageCache.getImage(imageId), (count % GameGrid.STEP_Y) * stepX(),
                        (count / GameGrid.STEP_Y) * stepY(), null);
                count++;
                armyIdCache.put(count, imageId);
            }
        }
    }
}