package com.Transpotation.UI;

import com.Common.UI.Table;
import com.Common.DB.DB;
import com.Common.UI.Menu;
import com.Transpotation.Models.Transportation;
import javafx.util.Pair;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ArchiveMenu extends Menu {

    DB db;
    public ArchiveMenu(DB db) {
        this.db = db;
    }

    @Override
    protected List<Pair<String, MenuItem>> getMenu() {
        List<Pair<String, MenuItem>> menu = new LinkedList<>();

        menu.add(new Pair<>("Past Transportations", this::pastTransportations));
        return menu;
    }

    private void pastTransportations(Integer integer, String s) throws Exception {
        List<Transportation> list = db.getTransportationIDBHandler().select("EndTime < ?", new Date());
        Table<Transportation> table = new Table<>(Transportation.class,list);
        table.display();
    }
}
