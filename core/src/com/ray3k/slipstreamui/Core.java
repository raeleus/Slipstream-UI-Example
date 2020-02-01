package com.ray3k.slipstreamui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Core extends ApplicationAdapter {
	private Skin skin;
	private Stage stage;
	
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("slipstream-ui.json"));
		stage = new Stage(new StretchViewport(256, 224));
		Gdx.input.setInputProcessor(stage);
		
		showMenu();
	}
	
	private void showMenu() {
		stage.clear();
		
		Stack stack = new Stack();
		stack.setFillParent(true);
		stage.addActor(stack);
		
		Image image = new Image(skin, "white");
		stack.add(image);
		
		Table root = new Table();
		stack.add(root);
		
		TextButton textButton = new TextButton("GRAND PRIX", skin, "menu");
		root.add(textButton);
		
		root.row();
		textButton = new TextButton("PRACTICE", skin, "menu-faded");
		root.add(textButton);
		
		root.row();
		textButton = new TextButton("RECORDS", skin, "menu-faded");
		root.add(textButton);
		
		root.row();
		textButton = new TextButton("SETTINGS", skin, "menu-faded");
		root.add(textButton);
		root.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showSettings();
			}
		});
	}
	
	private void showSettings() {
		stage.clear();
		
		Stack stack = new Stack();
		stack.setFillParent(true);
		stage.addActor(stack);
		
		Image image = new Image(skin, "black");
		stack.add(image);
		
		Container container = new Container();
		stack.add(container);
		
		image = new Image(skin, "box-ten");
		container.setActor(image);
		container.pad(10);
		container.fill();
		
		Table root = new Table();
		stack.add(root);
		
		Label label = new Label("Settings", skin, "button");
		root.add(label).expandY().top();
		
		root.row();
		SelectBox selectBox = new SelectBox(skin);
		selectBox.setItems("Keyboard", "Mouse", "Controller");
		root.add(selectBox);
		
		root.row();
		TextButton textButton = new TextButton("move left: left, a, q", skin);
		root.add(textButton);
		
		root.row();
		textButton = new TextButton("move right: right, d", skin);
		root.add(textButton);
		
		root.row();
		textButton = new TextButton("jump: up, w, z", skin);
		root.add(textButton);
		
		root.row();
		textButton = new TextButton("down: down, s", skin);
		root.add(textButton);
		
		root.row();
		ImageButton imageButton = new ImageButton(skin, "ok");
		root.add(imageButton).expandY().bottom();
		imageButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showMenu();
			}
		});
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose () {
		skin.dispose();
	}
}
