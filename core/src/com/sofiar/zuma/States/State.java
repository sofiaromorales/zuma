package com.sofiar.zuma.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sofiar.zuma.Zuma;


public abstract class State  {



    protected State ( GameStateManager gsm){
        this.gsm = gsm;
    }

    protected GameStateManager gsm;
    protected abstract void handleInput();
    protected abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

}
