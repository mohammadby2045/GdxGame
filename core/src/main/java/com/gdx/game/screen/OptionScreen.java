package com.gdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;
import com.gdx.game.GdxGame;
import com.gdx.game.manager.RessourceManager;

import java.util.ArrayList;

public class OptionScreen extends BaseScreen {

    private RessourceManager ressourceManager;
    private Table table;
    private Stage optionStage = new Stage();
    private Stage backgroundStage = new Stage();
    private Screen previousScreen;
    private Image previousScreenAsImg;
    private float stateTime;

    private VfxManager vfxManager;
    private GaussianBlurEffect vfxEffect;

    public OptionScreen(GdxGame gdxGame, Screen previousScreen, RessourceManager ressourceManager) {
        super(gdxGame);
        this.previousScreen = previousScreen;
        this.ressourceManager = ressourceManager;

        loadContents();
    }

    public OptionScreen(GdxGame gdxGame, Screen previousScreen, Image previousScreenAsImg, RessourceManager ressourceManager) {
        super(gdxGame);
        this.previousScreen = previousScreen;
        this.previousScreenAsImg = previousScreenAsImg;
        this.ressourceManager = ressourceManager;

        loadContents();
    }

    private void loadContents() {
        vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
        vfxEffect = new GaussianBlurEffect();
        vfxManager.addEffect(vfxEffect);

        createTable();
        handleBackground();
        handleControlButton();
        handleMusicButton();
        handleBackButton();
    }

    private void createTable() {
        table = new Table();
        table.setBounds(0,0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
    }

    private void handleBackground() {
    }

    private void handleControlButton() {
        createButton("Control",0,table.getHeight()/10);

        Actor controlButton = table.getCells().get(0).getActor();
        controlButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                //TODO: To be completed
            }
        });

    }

    private void handleMusicButton() {
        createButton("Music",0,table.getHeight()/15);

        Actor musicButton = table.getCells().get(1).getActor();
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                //TODO: To be completed
            }
        });
    }

    private void handleBackButton() {
        createButton("Back",0,table.getHeight()/5);

        Actor musicButton = table.getCells().get(2).getActor();
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                setScreenWithTransition(gdxGame.getScreen(), previousScreen, new ArrayList<>());
            }
        });
    }

    private void createButton(String buttonName, float posX, float posY) {
        TextureRegion[][] playButtons = ressourceManager.button;

        BitmapFont pixel10 = ressourceManager.pixel10;

        TextureRegionDrawable imageUp = new TextureRegionDrawable(playButtons[0][0]);
        TextureRegionDrawable imageDown = new TextureRegionDrawable(playButtons[1][0]);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(imageUp, imageDown, null, pixel10);
        TextButton button = new TextButton(buttonName, buttonStyle);
        button.getLabel().setColor(new Color(79 / 255.f, 79 / 255.f, 117 / 255.f, 1));

        table.add(button).padLeft(posX).padTop(posY);
        table.row();
    }

    @Override
    public void show() {
        optionStage.addActor(table);
        Gdx.input.setInputProcessor(optionStage);
    }

    @Override
    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();

        vfxManager.cleanUpBuffers();
        vfxManager.beginInputCapture();

        if (previousScreen != null && previousScreenAsImg == null) {
            previousScreen.render(stateTime);
        }
        if (previousScreenAsImg != null) {
            backgroundStage.addActor(previousScreenAsImg);
            backgroundStage.draw();
        }

        vfxManager.endInputCapture();
        vfxManager.applyEffects();
        vfxManager.renderToScreen();

        optionStage.act(delta);
        optionStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        table.remove();
        vfxManager.dispose();
        vfxEffect.dispose();
    }
}