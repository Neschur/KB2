package com.neschur.kb2.app.ui.menus;

import com.neschur.kb2.app.I18n;
import com.neschur.kb2.app.models.Game;

public class CountryMenu extends Menu {
    CountryMenu(Game game) {
        super(game);
    }

    @Override
    public String getItemDescription(int i) {
        return I18n.translate("menus_country_item" + (i + 1));
    }

    @Override
    public boolean select(int i) {
        player.changeCountry(game.getWorld().getCountry(i));
        return true;
    }

    @Override
    public int getCount() {
        return player.getAvailableCountry();
    }
}
