package com.sofiar.zuma;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BeadSprite implements Shooter{

    public Bead bead;

    private Vector2 position;
    private Texture beadTexture;
    private Sprite spriteBead;
    private Rectangle beadMask;

    public float speedMax = 500;


    public Vector2 velocity = new Vector2();

    public BeadSprite(float x, float y){
        bead = new Bead();
        position = new Vector2(x,y);
        spriteBead = new Sprite(getTexture());
        beadMask = new Rectangle(spriteBead.getX(), spriteBead.getY(), 30, 30);
    }

    //updates the position vector of the shooter per each delta time
    //updates the position of the bead mask to match with the bead
    public void update(float dt){
        position.add(velocity.x * dt, velocity.y * dt);
        beadMask.setPosition(getPosition().x, getPosition().y);
    }

    public Vector2 getPosition(){
        return position;
    }

    public Sprite getSprite(){
        return spriteBead;
    }

    public Texture getTexture(){
        if (bead.getColorID() == 0){
            beadTexture = new Texture("blue.png");
        }
        else if (bead.getColorID() == 1){
            beadTexture = new Texture("pink.png");
        }
        else if (bead.getColorID() == 2){
            beadTexture = new Texture("white.png");
        }
        else if (bead.getColorID() == 3){
            beadTexture = new Texture("orange.png");
        }
        return beadTexture;
    }

    public Rectangle getBeadMask(){
        return beadMask;
    }

    //creates vector from our position to the target and scale the value for the speed
    public void shootToward(float targetX, float targetY) {
        velocity.set(targetX - position.x, targetY - position.y);
    }

}
