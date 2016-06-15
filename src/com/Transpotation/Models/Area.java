package com.Transpotation.Models;


public class Area {
    private Integer areaID = null;
    private String name ;

    public Area() {
    }

    public Area(String name, int id) {
        this.name = name;
        areaID =id;
    }

    public Integer getAreaID() {
        return areaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Area area = (Area) o;

        if (!areaID.equals(area.areaID)) return false;
        return name.equals(area.name);

    }
}
