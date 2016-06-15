package com;

import com.Common.DB.DB;
import com.Common.IWorkers;
import com.Common.UI.Menu;
import com.Transpotation.UI.LoginScreen;
import com.Transpotation.UI.MainMenu;
import com.Workers.Menus.LoginMenu;
import com.Workers.Workers;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Main extends Menu {

    static DB db;
    static IWorkers iWorkers;
    public static void main(String[] args) throws Exception{
        db = DB.getInstance();
        iWorkers = Workers.getInstance();
        new Main().show();
    }

    @Override
    protected List<Pair<String, MenuItem>> getMenu() {
        List<Pair<String,MenuItem>> l = new ArrayList<>();
        l.add(new Pair<>("Transportation",this::transportation));
        l.add(new Pair<>("Workers",this::workers));
        return l;
    }

    private void transportation(Integer integer, String s) throws Exception {
        DB db = DB.getInstance();
        new LoginScreen().login();
        new MainMenu(db,iWorkers).show();
    }

    private void workers(Integer integer, String s) {
        LoginMenu.main(null);
    }
}
