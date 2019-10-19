package com.sofiar.zuma;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Canon extends Object {


    //INTERFACE//

    public Vector2 position;
    private Texture canonTexture;
    private Bead bead;

    public Canon(float x, float y){
        position = new Vector2(x,y);
        canonTexture = new Texture("Tile.png");
    }

    public void update(float dt){ }

    public Vector2 getPosition(){
        return position;
    }

    public Texture getTexture(){
        return canonTexture;
    }

    public void shoot(Sprite sprite, float positionX, float positionY){ }


}

