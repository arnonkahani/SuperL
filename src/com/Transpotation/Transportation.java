package com.Transpotation;

import com.Common.ITransportation;

public class Transportation implements ITransportation {
    public static Transportation instance;

    public static Transportation getInstance() {
        if(instance == null)
            instance = new Transportation();
        return instance;
    }
}
