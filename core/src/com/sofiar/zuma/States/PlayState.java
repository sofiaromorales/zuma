package com.sofiar.zuma.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.sofiar.zuma.BeadSprite;
import com.sofiar.zuma.Canon;
import com.sofiar.zuma.Hole;
import com.sofiar.zuma.Neighborhood;
import com.sofiar.zuma.Path;
import com.sofiar.zuma.Tile;
import com.sofiar.zuma.Zuma;

import java.util.ArrayList;


public class PlayState extends State {

    private Texture background;

    private ArrayList<Tile> Tiles;
    private ArrayList<Neighborhood> Neighborhoods;

    private BeadSprite bead;
    private Canon canon;
    private Hole hole;





    public static BeadSprite generateBead(float posX, float posY){
        BeadSprite bead = new BeadSprite(posX, posY);
        return bead;
    }


    public void initialize(){
        //creates array of tiles
        //creates neighbourhood
        //assign neighbourhood to the tiles

        Tiles = new ArrayList<Tile>();
        int x = -800;
        for (int i =0; i<27; i++){
            Tiles.add(new Tile(x, 420));
            if (i >= 17){
                Tiles.get(i).bead = null;
            }else{
                generateNewBead(Tiles.get(i));
            }
            x += 50;
        }

        Neighborhoods = new ArrayList<Neighborhood>();
        for(int i = 0; i<Tiles.size();i++){
            Neighborhoods.add(new Neighborhood());
        }

        for (int i = 0; i<Tiles.size(); i++){
            if (i == 0){
                Neighborhoods.get(0).add(Path.RIGHT, null);
                Neighborhoods.get(0).add(Path.LEFT, Tiles.get(1));
                Tiles.get(i).neighbors = Neighborhoods.get(i);
            }
            else if (i == Tiles.size() - 1){
                Neighborhoods.get(i).add(Path.RIGHT, Tiles.get(i - 1));
                Neighborhoods.get(i).add(Path.LEFT, null);
                Tiles.get(i).neighbors = Neighborhoods.get(i);

            }else{
                Neighborhoods.get(i).add(Path.RIGHT, Tiles.get(i - 1));
                Neighborhoods.get(i).add(Path.LEFT, Tiles.get(i + 1));
                Tiles.get(i).neighbors = Neighborhoods.get(i);
            }
        }
    }


    public PlayState(GameStateManager gsm) {
        super(gsm);


        background = new Texture("background.png");
        hole = new Hole(500, 50);
        canon = new Canon(200,210);
        bead = new BeadSprite(canon.getPosition().x, canon.getPosition().y);



        initialize();



    }

    private void generateNewBead(Tile tile){
        tile.assignBead(tile, generateBead(tile.getPosition().x, tile.getPosition().y).bead);
    }



    public void changeDirection(Tile tile, Float velocity){


        if (tile.getTileMask().getX() < 700 && tile.getTileMask().getY() == 420){
            tile.getTileMask().setPosition(tile.getTileMask().getX() + velocity, tile.getTileMask().getY());
           // System.out.println("here1");

        }else if ((tile.getTileMask().getY() > 50) && (tile.getTileMask().getX() >= 700) ){
            tile.getTileMask().setPosition(tile.getTileMask().getX(), tile.getTileMask().getY() - velocity);
            //System.out.println("here2");

        } else if (tile.getTileMask().getY() <= 50 && (tile.getTileMask().getX() >  652)){
            tile.getTileMask().setPosition(tile.getTileMask().getX() - velocity, tile.getTileMask().getY());
            //System.out.println("here3");
        }
        else if (tile.getTileMask().getX()  <= 652  && tile.getTileMask().getX()  > 640  && tile.getTileMask().getY() < 370){
            tile.getTileMask().setPosition(tile.getTileMask().getX() , tile.getTileMask().getY() + velocity);
            //System.out.println("here4");

        }
        else if (tile.getTileMask().getX()  > 80  && tile.getTileMask().getY() >= 370 ){
            tile.getTileMask().setPosition(tile.getTileMask().getX() - velocity , tile.getTileMask().getY());
            //System.out.println("here5");

        }
        else if (tile.getTileMask().getX()  <= 80 && tile.getTileMask().getY() > 50){
            tile.getTileMask().setPosition(tile.getTileMask().getX() , tile.getTileMask().getY() - velocity);
            //System.out.println("here6");


        }else if (tile.getTileMask().getX()  < 500 && tile.getTileMask().getY() <= 50) {
            tile.getTileMask().setPosition(tile.getTileMask().getX() + velocity, tile.getTileMask().getY());
            //System.out.println("here7");


        }

    }


    @Override
    protected void handleInput() {
    }

    @Override
    protected void update(float dt) {

        bead.update(dt);

        //if shooting bead goes off set generate a new one
        if (bead.getPosition().y > 480 || bead.getPosition().y < 0 ||bead.getPosition().x > 800 || bead.getPosition().x < 0){
            bead = new BeadSprite(canon.getPosition().x, canon.getPosition().y);
        }

        //goes tile by tile
            //changes direction of the bead to go through the path
            //check if theres a bide-tile collision
                //if the game is over trigger return nill
            //check if theres a hole-tile collision
        for (int i = 0; i < Tiles.size(); i++){

            changeDirection(Tiles.get(i), 1.1f);
            if (Tiles.get(i).collides(bead.getBeadMask()) && Tiles.get(i).getBead() != null){
                System.out.println("COLLIDE");
                if (Tiles.get(i).trigger(Tiles.get(i),bead.bead) == 1){
                    gsm.set(new WonState(gsm));
                }
                bead = new BeadSprite(canon.getPosition().x, canon.getPosition().y);

            }
            if (Tiles.get(i).collides(hole.getHoleMask())&& Tiles.get(i).getBead() != null){
                System.out.println("END");
                gsm.set(new LooseState(gsm));
            }
        }

        // Shoots the bead. Invert the the y to accommodate difference in coordinate systems
        if (Gdx.input.justTouched()) {
            bead.shootToward(Gdx.input.getX(), Gdx.graphics.getHeight()- Gdx.input.getY());
        }

        bead.update(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void render( SpriteBatch sb) {

        sb.begin();
        sb.draw(background, 0,0, Zuma.WIDTH, Zuma.HEIGHT);
        sb.draw(canon.getTexture(), canon.getPosition().x, canon.getPosition().y);
        sb.draw(hole.getTexture(), hole.getPosition().x, hole.getPosition().y);
        sb.draw(bead.getTexture(), bead.getPosition().x, bead.getPosition().y);


        for (int i = 0; i<Tiles.size(); i++){
            sb.draw(Tiles.get(i).getTexture(), Tiles.get(i).getTileMask().getX(), Tiles.get(i).getTileMask().getY());
        }

        sb.end();

    }

    @Override
    public void dispose() {

    }
}
