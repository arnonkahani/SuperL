package com.Transpotation.UI;

import com.Common.DB.DB;
import com.Common.UI.Table;
import com.Common.UI.Menu;
import com.Transpotation.Models.Area;
import com.Transpotation.Models.Place;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class PlaceOptionsMenu extends Menu {

    DB db;
    List<Place> list;
    Place place;

    public PlaceOptionsMenu(DB db, List<Place> list, Place place) {
        this.db = db;
        this.list = list;
        this.place = place;
    }

    @Override
    protected List<Pair<String, MenuItem>> getMenu() {
        List<Pair<String, MenuItem>> menu = new LinkedList<>();

        menu.add(new Pair<>("Set Area", this::setArea));
        return menu;
    }

    private void setArea(Integer integer, String s) throws Exception {
        List<Area> list = db.getAreaDBHandler().all();
        Area t = new Table<>(Area.class,list).select();
        if(t != null){
            place.setShipmentArea(t);
            db.getPlaceDBHandler().update(place);
        }
    }
}
