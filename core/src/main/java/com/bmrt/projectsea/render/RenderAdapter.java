package com.bmrt.projectsea.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bmrt.projectsea.GameTime;
import com.bmrt.projectsea.domain.ActionType;
import com.bmrt.projectsea.domain.RenderPort;
import com.bmrt.projectsea.domain.SeaMap;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.Vector;
import com.bmrt.projectsea.render.spell.SpellBarUI;
import com.bmrt.projectsea.render.spell.SpellButton;

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

    @Override
    public void initRendering(SeaMap seaMap) {
        float graphicsWidth = Gdx.graphics.getWidth();
        float graphicsHeight = Gdx.graphics.getHeight();

        tiledMap = new TiledMap(seaMap);

        /* GAME CAMERA */
        float width = (graphicsWidth / graphicsHeight) * 10;
        int height = 10;
        camera = new GameCamera(width, height, seaMap.getWidth(), seaMap.getHeight());
        camera.update(0, 0);
        renderer = new OrthogonalTiledMapRenderer(tiledMap.get(), UNIT);

        /* GAME VIEW */
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
        spellBarUI = new SpellBarUI(canonShotTexture, font);

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
    public void triggerPortShoot() {
        spellBarUI.triggerPortShoot();
    }

    @Override
    public void triggerStarboardShoot() {
        spellBarUI.triggerStarboardShoot();
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
    public void add(Ship ship, boolean myShip) {
        ShipActor shipActor = new ShipActor(ship, shipTexture);
        gameStage.addActor(shipActor);
        ShipUIActor shipUIActor = new ShipUIActor(ship, gameStage.getViewport(), font, healthBarTexture);
        uiStage.addActor(shipUIActor);
        if (myShip) {
            spellBarUI.addListener(new ChangeListener() {
                public void changed(ChangeEvent event, Actor actor) {
                    if (!((SpellButton) actor).isOnCooldown() && !((SpellButton) actor).isDisabled()) {
                        ((SpellButton) actor).setCooldownTriggerTime(GameTime.getCurrentTime());
                        if (((SpellButton) actor).getActionType().equals(ActionType.PORT_SHOOT)) {
                            shipActor.triggerPortShoot();
                        } else if (((SpellButton) actor).getActionType().equals(ActionType.STARBOARD_SHOOT)) {
                            shipActor.triggerStarboardShoot();
                        }
                        ship.shoot(targetActor.getShip());
                    }
                }
            });
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
