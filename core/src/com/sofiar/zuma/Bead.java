package com.sofiar.zuma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.sofiar.zuma.Colors.BeadColor;
import com.sofiar.zuma.Colors.Blue;
import com.sofiar.zuma.Colors.Orange;
import com.sofiar.zuma.Colors.Pink;
import com.sofiar.zuma.Colors.White;

import java.util.Random;


public class Bead extends Object {

    public Texture getTexture(){
        if (getColorID() == 0){
            return new Texture("blue.png");
        }
        else if (getColorID() == 1){
            return new Texture("pink.png");
        }
        else if (getColorID() == 2){
            return new Texture("white.png");
        }
        else if (getColorID() == 3){
            return new Texture("orange.png");
        }
        return null;
    }

    //LOGIC/////

    private Random randomInt = new Random();
    private int colorRandomize = randomInt.nextInt(4); //generates number between 0 and 3
    private int id = randomInt.nextInt(100);
    public BeadColor color = assignColor(colorRandomize);


    //WARNING: violating OCP  principle

    //makes the colors of the bead be randomized
    private BeadColor assignColor(Integer randomInt){
        if (randomInt == 0) {
            return new Blue();
        }
        else if (randomInt == 1){
            return new Pink();
        }
        else if (randomInt == 2){
            return new White();
        }
        else if (randomInt == 3){
            return new Orange();
        }

        return new White();
    }

    public int getId(){
        return id;
    }

    public int getColorID(){
        return color.getId();
    }

}

