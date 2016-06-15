package com.Transpotation.Models;
import com.Common.Models.LicenseType;

public class Truck {
    private com.Common.Models.LicenseType LicenseType ;
    private String LicenseNumber ;
    private String model;
    private String color;
    private double NetWeight;
    private double MaxWeight;

    public Truck() {
    }

    public Truck(LicenseType LicenseType, String LicenseNumber, String model,
                 String color, double NetWeight, double MaxWeight )
    {
        this.LicenseType = LicenseType;
        this.color = color ;
        this.NetWeight =NetWeight;
        this.MaxWeight = MaxWeight;
        this.LicenseNumber =LicenseNumber;
        this.model =model;

    }

    public com.Common.Models.LicenseType getLicenseType() {
        return LicenseType;
    }

    public void setLicenseType(com.Common.Models.LicenseType licenseType) {
        LicenseType = licenseType;
    }

    public String getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getNetWeight() {
        return NetWeight;
    }

    public void setNetWeight(double netWeight) throws ValidationException {
        NetWeight = netWeight;
    }

    public double getMaxWeight() {
        return MaxWeight;
    }

    public void setMaxWeight(double maxWeight) throws ValidationException {
        MaxWeight = maxWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Truck truck = (Truck) o;

        if (Double.compare(truck.getNetWeight(), getNetWeight()) != 0) return false;
        if (Double.compare(truck.getMaxWeight(), getMaxWeight()) != 0) return false;
        if (getLicenseType() != truck.getLicenseType()) return false;
        if (!getLicenseNumber().equals(truck.getLicenseNumber())) return false;
        if (getModel() != null ? !getModel().equals(truck.getModel()) : truck.getModel() != null) return false;
        return getColor() != null ? getColor().equals(truck.getColor()) : truck.getColor() == null;

    }

    @Override
    public String toString() {
        return LicenseNumber;
    }
}
