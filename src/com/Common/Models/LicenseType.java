package com.Common.Models;


public enum LicenseType{
    A,B,C;

    public boolean isBigger(LicenseType other){
        return !(this.compareTo(other) < 0);
    }
}
