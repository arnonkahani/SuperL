package com.Transpotation.Models;


public class Place {

    private String Address ; //key
    private Area ShipmentArea ;
    private String PhoneNumber ;
    private String ContactPerson ;

    public Place() {
    }

    public Place(String address, Area shipmentArea, String phoneNumber, String contactPerson) {
        Address = address;
        ShipmentArea = shipmentArea;
        PhoneNumber = phoneNumber;
        ContactPerson = contactPerson;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Area getShipmentArea() {
        return ShipmentArea;
    }

    public void setShipmentArea(Area shipmentArea) {
        ShipmentArea = shipmentArea;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    @Override
    public String toString() {
        return Address + (ShipmentArea != null ? "("+ShipmentArea+")" : "no area");
    }
}
