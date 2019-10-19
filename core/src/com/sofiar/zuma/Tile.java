package com.sofiar.zuma;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;



public class Tile<D> extends Neighborhood implements Matcher{

    //INTERFACE

    private Vector2 position;
    private Texture tileTexture;
    private Rectangle tileMask;
    private Sprite tileSprite;
    public Vector2 velocity = new Vector2();

    public Tile(int x, int y){
        position = new Vector2(x,y);
        tileMask = new Rectangle(x, y, 25, 25);
        tileSprite = new Sprite(getTexture());
    }

    public void update(float dt){

        position.add(velocity.x * dt, velocity.y * dt);
       tileMask.setPosition(getPosition().x, getPosition().y);

    }

    public Texture getTexture(){

        if (bead == null){
            tileTexture = new Texture("Tile.png");
        }
        else{
            tileTexture = bead.getTexture();
        }
        return tileTexture;
    }

    public Vector2 getPosition(){
        return position;
    }

    public Rectangle getTileMask(){
        return tileMask;
    }

    public Sprite getTileSprite(){
        return tileSprite;
    }

    public boolean collides( Rectangle mask){
        return mask.overlaps(tileMask);
    }

    ///LOGIC////

    public Neighborhood neighbors;
    public Bead bead;

    public Tile getNeighbour(D direction){
        return neighbors.get(direction);
    }

    public Bead getBead(){
        return bead;
    }

    public void assignBead(Tile tile, Bead bead){
        tile.bead = bead;
    }


    //moves beads one space forward from given tile
        //goes to the head of the path and from there starts placing the bead of the tile = to the back one
    private void move(Tile tile){
        if (tile.neighbors.get(Path.LEFT) != null){
            tile = tile.neighbors.get(Path.LEFT);
            move(tile);
            if (tile.neighbors.get(Path.RIGHT) != null){
                tile.bead = tile.neighbors.get(Path.RIGHT).bead;
            }
        }
    }

    //move beads one space backwards
        //takes the bead of the next tile and places it on his own
    private void fill(Tile tile){
        while (tile.neighbors.get(Path.LEFT) != null){
            tile.bead = tile.neighbors.get(Path.LEFT).bead;
            tile = tile.neighbors.get(Path.LEFT);
        }
    }


    //after explode beads move backwards
        //goes to the first null bead and starts filling backwards
    private int search(Tile tile){
        int counter =1;
        //search the next tile no null
        while (tile.bead == null){
            //border case: head of path
            if (tile.neighbors.get(Path.LEFT) == null) {
                break;
            }
            if (tile.neighbors.get(Path.LEFT).bead == null){
                tile = tile.neighbors.get(Path.LEFT);
            }
            else{
                break;
            }
        }
        //moves the beads back
        while(tile.bead == null) {
            fill(tile);
            if(tile.neighbors.get(Path.RIGHT) != null){
                tile = tile.neighbors.get(Path.RIGHT);
            }

            //TODO: end of game properly
            counter ++;
            if (counter > 100){
                return 1;

            }
        }
        System.out.print("g");
        return 0;
    }



    //MATCHER

    //looks if exist match to the right
        //border case: looks that my tile its no null
        //if bead is same color that im searching add 1 to counter
        //if I have a bead to the right pass message
    public int checkNeighbourhoodR(Tile tile, Bead bead, int count){
        while(true){
            if (tile.bead != null){
                if(bead.getColorID() == tile.bead.getColorID() ){
                    count += 1;
                    if (tile.neighbors.get(Path.RIGHT) != null){
                        if (tile.neighbors.get(Path.RIGHT).bead != null){
                            tile = tile.neighbors.get(Path.RIGHT);

                        }else{
                            return count;
                        }
                    }
                    else{
                        return count;
                    }

                }
                else{
                    return count;
                }
            }else{
                return count;
            }

        }

    }

    //looks if exist match to the left
        //border case: tile is null
        //if bead is same color as the one im searching ++counter
        // if exists bead to the left pass message
    public int checkNeighbourhoodL(Tile tile, Bead bead, int count){
        while(true){
            if (tile == null){
                return count;
            }
            if (tile.bead != null){
                if(bead.getColorID() == tile.bead.getColorID() ){
                    count += 1;
                    if(tile.neighbors.get(Path.LEFT) != null){
                        if ( tile.neighbors.get(Path.LEFT).bead != null){
                            tile = tile.neighbors.get(Path.LEFT);
                        }else{
                            return count;
                        }
                    }
                    else {
                        return count;
                    }

                }
                else{
                    return count;
                }
            }
            else {
                return count;
            }

        }
    }

    //explode beads
        //border case: tile is null
        //if bead is same color as the chain convert to null
        //pass message to the right or left
    public void turnNullRight(Tile tile, int color){
        if(tile != null) {
            if (tile.bead != null) {
                if (tile.bead.getColorID() == color) {
                    turnNullRight(tile.neighbors.get(Path.RIGHT), color);
                    if (tile.bead == null) {
                    }
                    tile.bead = null;
                }
            }
        }
    }

    public void turnNullLeft(Tile tile, int color){
        if(tile != null){
            if (tile.bead != null){
                if (tile.bead.getColorID() == color){
                    turnNullLeft(tile.neighbors.get(Path.LEFT), color);
                    tile.bead = null;
                }
            }
        }
    }

    //manages which action trigger given a certain decision
        //checks if from the tile to the left or right are at least 2 of the same color (match 3)
        //if match:
            //keeps the color of the match
            //turn null right and left
            //makes himself null
            //with search moves backwards all the tiles ahead of the match
        //if not match:
            // moves tiles one space forward to make space for the new bead
            //assign bead to the corresponding tile
    public int trigger(Tile tile, Bead bead) {
        if (checkNeighbourhoodR(tile, bead, 0) + checkNeighbourhoodL(tile.neighbors.get(Path.LEFT),  bead, 0) >= 2){
            //MATCH
            System.out.print("MATCH");
            int color = tile.bead.getColorID();
            turnNullRight(tile.neighbors.get(Path.RIGHT), color);
            turnNullLeft(tile.neighbors.get(Path.LEFT),color);
            tile.bead = null;
            if (search(tile) == 1){
                return 1;
            }
            return 0;
        }
        else{
            //NO MATCH
            tile.move(tile);
            assignBead(tile, bead);
            System.out.print("NO MATCH");
        }
        return 0;
    }

}

