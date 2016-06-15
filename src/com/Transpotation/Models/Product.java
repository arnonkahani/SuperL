package com.Transpotation.Models;

public class Product {
    Integer ID;
    String name;
    String description;
    double weight;
    OrderDocument order;

    public Product() {
    }

    public Product(Integer ID, String name, String description, double weight, OrderDocument order) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.order = order;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) throws ValidationException {
        if(weight < 0)
            throw new ValidationException("Weight cannot be negative");
        this.weight = weight;
    }

    public OrderDocument getOrder() {
        return order;
    }

    public void setOrder(OrderDocument order) {
        this.order = order;
    }
}
