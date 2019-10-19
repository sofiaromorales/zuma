package com.sofiar.zuma;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class Hole  {

    //INTERFACE

    public Vector2 position;
    private Texture holeTexture;
    private Rectangle holeMask;

    public Hole(float x, float y){
        position = new Vector2(x,y);
        holeTexture = new Texture("hole.png");
        holeMask = new Rectangle(getPosition().x, getPosition().y, 60, 60);
    }

    public void update(float dt){ }

    public Vector2 getPosition(){
        return position;
    }

    public Texture getTexture(){
        return holeTexture;
    }
    public Rectangle getHoleMask(){
        return holeMask;
    }

    public void shoot(Sprite sprite, float positionX, float positionY){ }

    //LOGIC
}
