package com.Transpotation.UI;

import com.Common.DB.AlreadyExistsException;
import com.Common.DB.DB;
import com.Common.DB.IDBHandler;
import com.Common.DB.UsedException;
import com.Common.UI.Editor;
import com.Common.UI.Menu;
import com.Common.UI.Table;
import com.Transpotation.Models.Area;
import com.Transpotation.Models.Place;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PlacesAndAreasMenu extends Menu {

    DB db;
    public PlacesAndAreasMenu(DB db) {
        this.db = db;
    }

    @Override
    protected List<Pair<String, MenuItem>> getMenu() {
        List<Pair<String, MenuItem>> menu = new LinkedList<>();

        menu.add(new Pair<>("Manage Areas", this::manageArea));
        menu.add(new Pair<>("Manage Places", this::managePlaces));
        return menu;
    }

    private void manageArea(Integer integer, String s) throws Exception {
        IDBHandler<Area> handler = db.getAreaDBHandler();
        List<Area> list = handler.all();

        Table<Area> table = new Table<>(Area.class,list);
        table.setEditAction((i,t)-> {
            Area area = new Editor<>(Area.class,t).edit();
            if(area != null)
                handler.update(area);
        } );
        table.setDeleteAction((i,t)->{
            try{
                handler.delete(t.getAreaID()+"");
                list.remove(t);
            }
            catch (UsedException e){
                System.out.println("This place is used and referenced by another entity.");
                System.out.println("<<OPERATION CANCELED>>");
            }
        });
        table.setNewAction(()->{
            System.out.println("Enter the following info to make a new Area:");
            Area t = new Editor<>(Area.class,new Area()).edit();
            if(t != null){
                handler.insert(t);
                list.clear();
                list.addAll(handler.all());
            }
        });
        table.display();
    }

    private void managePlaces(Integer integer, String s) throws Exception {
        IDBHandler<Place> handler = db.getPlaceDBHandler();
        List<Place> list = handler.all();

        Table<Place> table = new Table<>(Place.class,list);
        table.setEditAction((i,t)-> {
            Place place = new Editor<>(Place.class,t).edit();
            if(place != null)
                handler.update(place);
        } );
        table.setDeleteAction((i,t)->{
            try{
                handler.delete(t.getAddress());
                list.remove(t);
            }
            catch (UsedException e){
                System.out.println("This place is used and referenced by another entity.");
                System.out.println("<<OPERATION CANCELED>>");
            }
        });
        table.setNewAction(()->{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the following info to make a new Place:");
            Place t = new Editor<>(Place.class,new Place()).edit();

            System.out.println("Please select an area for this place: <PRESS ENTER TO BEGIN>");
            scanner.nextLine();
            Area area = new Table<>(Area.class, db.getAreaDBHandler().all()).select();
            if(area != null)
                t.setShipmentArea(area);
            else
                return;

            try{
                handler.insert(t);
                list.clear();
                list.addAll(handler.all());
            }
            catch (AlreadyExistsException e){
                System.out.println("A Place with this Address already exists.");
                System.out.println("<<OPERATION CANCELED>>");
            }

        });
        table.setOptionsAction((i,t)->{
            new PlaceOptionsMenu(db,list,t).show();
        });
        table.display();
    }
}
