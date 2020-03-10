package com.ray3k.slipstreamui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import space.earlygrey.shapedrawer.GraphDrawer;
import space.earlygrey.shapedrawer.ShapeDrawer;
import space.earlygrey.shapedrawer.scene2d.GraphDrawerDrawable;

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
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showVehicleStats();
			}
		});
		
		root.row();
		textButton = new TextButton("PRACTICE", skin, "menu-faded");
		root.add(textButton);
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showVehicleStats();
			}
		});
		
		root.row();
		textButton = new TextButton("RECORDS", skin, "menu-faded");
		root.add(textButton);
		
		root.row();
		textButton = new TextButton("SETTINGS", skin, "menu-faded");
		root.add(textButton);
		textButton.addListener(new ChangeListener() {
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
	
	private void showVehicleStats() {
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
		
		Label label = new Label("Blue Falcon", skin, "button");
		root.add(label).expandY().top();
		
		root.row();
		Table table = new Table();
		root.add(table);
		
		table.defaults().space(5).uniformX().left();
		label = new Label("ENGINE UNIT", skin);
		table.add(label);
		
		label = new Label(": BF-2001x4", skin);
		table.add(label);
		
		table.row();
		label = new Label("MAX POWER", skin);
		table.add(label);
		
		label = new Label(": 3200ps", skin);
		table.add(label);
		
		table.row();
		label = new Label("MAX SPEED", skin);
		table.add(label);
		
		label = new Label(": 457km-h", skin);
		table.add(label);
		
		table.row();
		label = new Label("WEIGHT", skin);
		table.add(label);
		
		label = new Label(": 1260kg", skin);
		table.add(label);
		
		root.row();
		label = new Label("ACCELERATION", skin);
		root.add(label).space(10);
		
		root.row();
		stack = new Stack();
		root.add(stack).size(150, 75);
		
		image = new Image(skin, "acceleration-grid-ten");
		stack.add(image);
		
		container = new Container();
		stack.add(container);
		
		//create graph
		final GraphDrawerDrawable graphDrawerDrawable = new GraphDrawerDrawable(new GraphDrawer(new ShapeDrawer(stage.getBatch(), skin.getRegion("white"))));
		//This controls the appearance of the graph. It can be assigned to any of the default Interpolations or you can create one with your own formula.
		graphDrawerDrawable.setInterpolation(Interpolation.sine);
		graphDrawerDrawable.setColor(Color.WHITE);
		//start domain end at 0 for the beginning of the animation.
		graphDrawerDrawable.setDomainEnd(0);
		image = new Image(graphDrawerDrawable);
		container.setActor(image);
		container.fill().padLeft(25).padBottom(10).padTop(20).padRight(5);
		//over 2 seconds, increase the domain end to 1 for a reveal from left animation.
		image.addAction(new TemporalAction(2f) {
			@Override
			protected void update(float percent) {
				graphDrawerDrawable.setDomainEnd(percent);
			}
		});
		
		root.row();
		table = new Table();
		root.add(table).expandY().bottom();
		
		ImageButton imageButton = new ImageButton(skin, "yes");
		table.add(imageButton);
		imageButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showMenu();
			}
		});
		
		imageButton = new ImageButton(skin, "no");
		table.add(imageButton);
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
