package com.Transpotation.UI;

import com.Common.UI.Table;
import com.Common.DB.DB;
import com.Common.DB.IDBHandler;
import com.Common.UI.Editor;
import com.Common.UI.Menu;
import com.Transpotation.Models.*;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class OrderOptionsMenu extends Menu {
    DB db;
    List<OrderDocument> list;
    OrderDocument document;
    public OrderOptionsMenu(DB db, List<OrderDocument> list, OrderDocument document) {
        this.db = db;
        this.document = document;
        this.list = list;
    }

    @Override
    protected List<Pair<String, MenuItem>> getMenu() {
        List<Pair<String, MenuItem>> menu = new LinkedList<>();

        //menu.add(new Pair<>("Manage Products", this::manageProducts));
        menu.add(new Pair<>("Attach To Transportation", this::attachToTransportation));
        menu.add(new Pair<>("Attach Source", this::attachSource));
        menu.add(new Pair<>("Attach Destination", this::attachDestination));
        return menu;
    }

    /*private void manageProducts(Integer integer, String s) throws Exception {
        IDBHandler<Product> handler = db.getProductIDBHandler();
        List<Product> list = handler.select("orderID = ?", document.getID());

        Table<Product> table = new Table<>(Product.class,list);
        table.setEditAction((i,t)-> {
            Product transportation = new Editor<>(Product.class,t).edit();
            if(transportation != null)
                handler.update(transportation);
        } );
        table.setDeleteAction((i,t)->{
            handler.delete(t.getID()+"");
            list.remove(t);
        });
        table.setNewAction(()->{
            System.out.println("Enter the following info to make a new Product:");
            Product t = new Editor<>(Product.class,new Product()).edit();
            if(t != null){
                t.setOrder(document);
                handler.insert(t);
                list.clear();
                list.addAll(handler.select("orderID = ?", document.getID()));
            }
        });
        table.display();
    }*/

    private void attachToTransportation(Integer integer, String s) throws Exception {
        List<Transportation> list = db.getTransportationIDBHandler().select("area = ?", document.getSource().getShipmentArea().getAreaID());
        Transportation transportation = new Table<>(Transportation.class,list).select();
        document.setTransportation(transportation);
        db.getOrderDocumentIDBHandler().update(document);
    }

    private void attachSource(Integer integer, String s) throws Exception {
        Place p = new Table<>(Place.class,db.getPlaceDBHandler().select("ShipmentArea IS NOT NULL")).select();
        try {
            document.setSource(p);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            System.out.println("<< Selection failed >>");
        }
        db.getOrderDocumentIDBHandler().update(document);
    }

    private void attachDestination(Integer integer, String s) throws Exception {
        Place p = new Table<>(Place.class,db.getPlaceDBHandler().select("ShipmentArea IS NOT NULL")).select();
        try {
            document.setDestination(p);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            System.out.println("<< Selection failed >>");
        }
        db.getOrderDocumentIDBHandler().update(document);
    }
}
