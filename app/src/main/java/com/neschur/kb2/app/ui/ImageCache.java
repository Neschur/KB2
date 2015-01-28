package com.neschur.kb2.app.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.neschur.kb2.app.R;

import java.util.HashMap;

public class ImageCache {
    private static ImageCache self;
    private final int width;
    private final int height;
    private Resources resources;
    private HashMap<Integer, Bitmap> cache = new HashMap<>();

    private ImageCache(Resources resources, int width, int height) {
        this.resources = resources;
        this.width = width;
        this.height = height;
    }

    public static ImageCache getInstance(Resources resources, int width, int height) {
        if (self == null) {
            self = new ImageCache(resources, width, height);
        } else {
            if (self.width != width || self.height != height) {
                self = new ImageCache(resources, width, height);
            }
        }
        return self;
    }

    public Bitmap getImage(int id) {
        Bitmap image = cache.get(id);
        if (image == null) {
            image = BitmapFactory.decodeResource(resources, id);
            if (image == null && id != R.drawable.emo)
                return getImage(R.drawable.emo);
            image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(resources, id),
                    width, height, false);
            cache.put(id, image);
        }
        return image;
    }
}