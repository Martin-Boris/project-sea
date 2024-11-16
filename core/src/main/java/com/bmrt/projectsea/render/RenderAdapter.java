package com.bmrt.projectsea.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.RenderPort;
import com.bmrt.projectsea.domain.SeaMap;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.Vector;
import com.bmrt.projectsea.render.spell.ChangeSpellButtonListener;
import com.bmrt.projectsea.render.spell.SpellBarUI;

import java.util.HashMap;
import java.util.Map;

public class RenderAdapter implements RenderPort {

    public static final float UNIT = 1 / 32f;

    private Texture canonShotTexture;
    private Texture targetTexture;
    private Texture shipTexture;
    private Texture healthBarTexture;
    private BitmapFont font;

    private TargetActor targetActor;
    private SpellBarUI spellBarUI;

    private GameCamera camera;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;

    private Stage gameStage;
    private Stage uiStage;

    private Map<String, ShipActor> shipActors;

    @Override
    public void initRendering(SeaMap seaMap, GameInstance gameInstance) {
        float graphicsWidth = Gdx.graphics.getWidth();
        float graphicsHeight = Gdx.graphics.getHeight();

        tiledMap = new TiledMap(seaMap);

        /* GAME CAMERA */
        float width = (graphicsWidth / graphicsHeight) * 10;
        int height = 10;
        camera = new GameCamera(width, height, seaMap.getWidth(), seaMap.getHeight());
        renderer = new OrthogonalTiledMapRenderer(tiledMap.get(), UNIT);

        /* GAME VIEW */
        shipActors = new HashMap<>();
        gameStage = new Stage(new FitViewport(width, height, camera));
        targetTexture = new Texture(Gdx.files.internal("sprite/target.png"));
        shipTexture = new Texture(Gdx.files.internal("sprite/ship-cruise.png"));
        targetActor = new TargetActor(targetTexture, null);
        gameStage.addActor(targetActor);


        /* UI VIEW */
        healthBarTexture = new Texture(Gdx.files.internal("ui/healthbar.png"));
        canonShotTexture = new Texture(Gdx.files.internal("sprite/canonShoots.png"));
        uiStage = new Stage();
        font = new BitmapFont();
        spellBarUI = new SpellBarUI(canonShotTexture, font, gameInstance.getPortCooldown(), gameInstance.getStarboardCooldown());
        spellBarUI.addListener(new ChangeSpellButtonListener(gameInstance));

        uiStage.addActor(spellBarUI);
    }

    @Override
    public void updateView(Vector myShipPosition, float deltaTime, boolean canShootTarget) {
        camera.update(myShipPosition.getX(), myShipPosition.getY());
        renderer.setView(camera);
        renderer.render();
        gameStage.act(deltaTime);
        gameStage.draw();

        spellBarUI.update(!canShootTarget);
        uiStage.act();
        uiStage.draw();
    }

    @Override
    public void triggerPortShoot(String shipName) {
        shipActors.get(shipName).triggerPortShoot();
    }

    @Override
    public void triggerStarboardShoot(String shipName) {
        shipActors.get(shipName).triggerStarboardShoot();
    }

    @Override
    public void removeTarget() {
        targetActor.removeTarget();
    }

    @Override
    public void setTarget(Ship ship) {
        targetActor.setVisible(true);
        targetActor.setTarget(ship);
    }

    @Override
    public void add(Ship ship) {
        ShipActor shipActor = new ShipActor(ship, shipTexture);
        gameStage.addActor(shipActor);
        ShipUIActor shipUIActor = new ShipUIActor(ship, gameStage.getViewport(), font, healthBarTexture);
        uiStage.addActor(shipUIActor);
        shipActors.put(ship.getName(), shipActor);
    }

    @Override
    public void remove(String shipName) {
        for (Actor actor : gameStage.getActors()) {
            if (shipName.equals(actor.getName())) {
                actor.addAction(Actions.removeActor());
            }
        }
        for (Actor actor : uiStage.getActors()) {
            if (shipName.equals(actor.getName())) {
                actor.addAction(Actions.removeActor());
            }
        }
    }


    public Stage getGameStage() {
        return gameStage;
    }

    public Stage getUiStage() {
        return uiStage;
    }

    public void dispose() {
        tiledMap.dispose();
        renderer.dispose();
        shipTexture.dispose();
        targetTexture.dispose();
        healthBarTexture.dispose();
        canonShotTexture.dispose();
        font.dispose();
        gameStage.dispose();
        uiStage.dispose();
    }
}
