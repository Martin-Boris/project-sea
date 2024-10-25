package com.bmrt.projectsea;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.SeaMap;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.Vector;
import com.bmrt.projectsea.render.GameCamera;
import com.bmrt.projectsea.render.ShipActor;
import com.bmrt.projectsea.render.ShipUIActor;
import com.bmrt.projectsea.render.TargetActor;
import com.bmrt.projectsea.render.TiledMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class ProjectSeaMain extends ApplicationAdapter implements InputProcessor {

    /* CONSTANTS */
    public static final float UNIT = 1 / 32f;
    public static final float GAME_TICK = 1 / 60f;

    /* TEXTURES */
    private Texture targetTexture;

    /* FONT */
    private BitmapFont font;

    /* ACTORS */
    private ShipActor myShipActor;
    private List<ShipActor> otherShipActors;
    private TargetActor targetActor;
    private ShipActor targetedActor;
    private ShipUIActor myShipUIActor;

    /* STAGE */
    private Stage gameStage;
    private Stage uiStage;


    private float accumulator = 0f;
    private SeaMap seaMap;
    private Ship myShip;
    private List<Ship> otherShips;
    private GameCamera camera;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private float stateTime;

    @Override
    public void create() {
        seaMap = new SeaMap(25, 25);
        myShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "Torred");
        otherShips = Arrays.asList(
            new Ship(new Vector(10, 5), Vector.ZERO, Direction.RIGHT, "Pirate"),
            new Ship(new Vector(5, 10), Vector.ZERO, Direction.TOP, "Corsair"));

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        tiledMap = new TiledMap(seaMap);

        /* GAME VIEW */
        float width = (w / h) * 10;
        int height = 10;
        camera = new GameCamera(width, height, seaMap.getWidth(), seaMap.getHeight());
        camera.update(myShip.getPosition().getX(), myShip.getPosition().getY());
        renderer = new OrthogonalTiledMapRenderer(tiledMap.get(), UNIT);
        gameStage = new Stage(new FitViewport(width, height, camera));


        targetTexture = new Texture(Gdx.files.internal("sprite/target.png"));
        targetActor = new TargetActor(targetTexture);
        myShipActor = new ShipActor(myShip, targetActor);
        gameStage.addActor(targetActor);
        otherShipActors = new ArrayList<>();
        for (Ship otherShip : otherShips) {
            ShipActor shipActor = new ShipActor(otherShip, targetActor);
            otherShipActors.add(shipActor);
            gameStage.addActor(shipActor);
        }
        gameStage.addActor(myShipActor);

        /* UI VIEW */
        uiStage = new Stage();
        font = new BitmapFont();
        myShipUIActor = new ShipUIActor(myShip, gameStage.getViewport(), font);
        for (Ship otherShip : otherShips) {
            ShipUIActor shipUIActor = new ShipUIActor(otherShip, gameStage.getViewport(), font);
            uiStage.addActor(shipUIActor);
        }
        uiStage.addActor(myShipUIActor);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        stateTime = 0f;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        float deltaTime = Gdx.graphics.getDeltaTime();
        stateTime += deltaTime;
        accumulator += deltaTime;
        while (accumulator >= GAME_TICK) {
            accumulator -= GAME_TICK;
            myShip.update(seaMap);
            otherShips.forEach(ship -> ship.update(seaMap));
        }
        camera.update(myShip.getPosition().getX(), myShip.getPosition().getY());
        renderer.setView(camera);
        renderer.render();
        gameStage.act(deltaTime);
        gameStage.draw();
        uiStage.draw();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        renderer.dispose();
        myShipActor.dispose();
        gameStage.dispose();
        targetTexture.dispose();
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE && targetedActor != null) {
            targetedActor.setTarget(false);
            targetActor.setVisible(false);
        }
        if (keycode == Input.Keys.LEFT) {
            myShip.updateDirection(GAME_TICK, Direction.LEFT);
        }
        if (keycode == Input.Keys.RIGHT) {
            myShip.updateDirection(GAME_TICK, Direction.RIGHT);
        }
        if (keycode == Input.Keys.UP) {
            myShip.updateDirection(GAME_TICK, Direction.TOP);
        }
        if (keycode == Input.Keys.DOWN) {
            myShip.updateDirection(GAME_TICK, Direction.BOT);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean noDirectionKeyPressed = !Gdx.input.isKeyPressed(Input.Keys.LEFT)
            && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)
            && !Gdx.input.isKeyPressed(Input.Keys.UP)
            && !Gdx.input.isKeyPressed(Input.Keys.DOWN);
        if (noDirectionKeyPressed) {
            myShip.stop();
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 vector2 = gameStage.screenToStageCoordinates(new Vector2(screenX, screenY));
        Actor actor = gameStage.hit(vector2.x, vector2.y, false);
        if (actor != null && !actor.equals(myShipActor)) {
            targetActor.setVisible(true);
            if (targetedActor != null) {
                targetedActor.setTarget(false);
            }
            targetedActor = ((ShipActor) actor);
            targetedActor.setTarget(true);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
