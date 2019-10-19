package com.sofiar.zuma;

import com.sun.org.apache.xpath.internal.operations.Bool;

public interface Matcher {
     int trigger(Tile tile, Bead bead);
     int checkNeighbourhoodL(Tile tile, Bead bead, int count);
     int checkNeighbourhoodR(Tile tile, Bead bead, int count);
     void turnNullRight(Tile tile, int color);
     void turnNullLeft(Tile tile, int color);

}
