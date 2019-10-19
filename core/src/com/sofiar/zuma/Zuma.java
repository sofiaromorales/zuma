package com.sofiar.zuma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.sofiar.zuma.States.GameStateManager;
import com.sofiar.zuma.States.MenuState;


public class Zuma extends Game {

	//compiler variables
	public static final String TITLE ="Zuma";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	private GameStateManager gsm;
	public SpriteBatch batch;


	@Override
	public void create () {

		batch = new SpriteBatch();
		Gdx.gl.glClearColor(1, 0, 0, 1);

		//stack of game states
		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));

	}

	@Override
	public void render () {
		super.render();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}

	@Override
	public void dispose () {
		batch.dispose();

	}
}
