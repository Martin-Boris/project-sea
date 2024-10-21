package com.bmrt.projectsea;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
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
import com.bmrt.projectsea.render.TargetActor;
import com.bmrt.projectsea.render.TiledMap;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class ProjectSeaMain extends ApplicationAdapter implements InputProcessor {

    /* CONSTANTS */
    public static final float UNIT = 1 / 32f;
    public static final float GAME_TICK = 1 / 60f;

    /* TEXTURES */
    private Texture targetTexture;

    /* ACTORS */
    private ShipActor shipActor;
    private TargetActor targetActor;
    private ShipActor targetedActor;


    private float accumulator = 0f;
    private SeaMap seaMap;
    private Ship myShip;
    private GameCamera camera;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private float stateTime;
    private Stage gameStage;

    @Override
    public void create() {
        seaMap = new SeaMap(25, 25);
        myShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        tiledMap = new TiledMap(seaMap);

        float width = (w / h) * 10;
        int height = 10;
        camera = new GameCamera(width, height, seaMap.getWidth(), seaMap.getHeight());
        camera.update(myShip.getPosition().getX(), myShip.getPosition().getY());
        renderer = new OrthogonalTiledMapRenderer(tiledMap.get(), UNIT);
        gameStage = new Stage(new FitViewport(width, height, camera));
        targetTexture = new Texture(Gdx.files.internal("sprite/target.png"));
        targetActor = new TargetActor(targetTexture);
        shipActor = new ShipActor(myShip, targetActor);
        gameStage.addActor(targetActor);
        gameStage.addActor(shipActor);
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
        }
        camera.update(myShip.getPosition().getX(), myShip.getPosition().getY());
        renderer.setView(camera);
        renderer.render();
        gameStage.act(deltaTime);
        gameStage.draw();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        renderer.dispose();
        shipActor.dispose();
        gameStage.dispose();
        targetTexture.dispose();
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
        if (actor != null) {
            targetActor.setVisible(true);
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
