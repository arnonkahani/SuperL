package com.Transpotation.UI;

import com.Common.ISupplierStorage;
import com.Common.UI.Menu;
import com.Common.UI.Table;
import com.SupplierStorage.BE.OrderProduct;
import com.SupplierStorage.SupplierStorage;
import com.Transpotation.Models.OrderDocument;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class OrderDocumentOptionsMenu extends Menu {

    ISupplierStorage supplierStorage = SupplierStorage.getInstance();
    OrderDocument orderDocument;
    public OrderDocumentOptionsMenu(OrderDocument orderDocument) {
        this.orderDocument = orderDocument;
    }

    @Override
    protected List<Pair<String, MenuItem>> getMenu() {
        List<Pair<String, MenuItem>> menu = new LinkedList<>();

        menu.add(new Pair<>("View Products", this::viewProducts));
        return menu;
    }

    private void viewProducts(Integer integer, String s){
        List<OrderProduct> products = supplierStorage.getOrder(orderDocument.getOrderID()+"").get_amountProduct();
        try {
            new Table<>(OrderProduct.class, products).display();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
