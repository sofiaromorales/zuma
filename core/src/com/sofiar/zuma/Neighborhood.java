package com.sofiar.zuma;

import java.util.HashMap;

public class Neighborhood< D, C extends Tile> {
    private HashMap<D, C> mapping = new HashMap<D, C>();

    public void add(D direction, C tile){
        mapping.put(direction,tile);
    }

    public C get (D direction){
        return  mapping.get(direction);
    }

}

