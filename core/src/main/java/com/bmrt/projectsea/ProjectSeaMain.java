package com.bmrt.projectsea;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bmrt.projectsea.domain.Cooldown;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.SeaMap;
import com.bmrt.projectsea.render.RenderAdapter;
import com.bmrt.projectsea.render.ShipActor;
import com.bmrt.projectsea.websocket.WebsocketController;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class ProjectSeaMain extends ApplicationAdapter implements InputProcessor {

    /* CONSTANTS */
    public static final float GAME_TICK = 1 / 60f;

    public static final float EPSILON = 0.00001f;

    private Actor tmpShipActor;

    private float accumulator = 0f;
    private SeaMap seaMap;
    private GameInstance gameInstance;

    private RenderAdapter renderAdapter;

    private float deltaTime;
    private WebsocketController websocketController;

    @Override
    public void create() {
        this.renderAdapter = new RenderAdapter();
        String myShipName = GUID.get();
        this.websocketController = new WebsocketController(myShipName);
        this.gameInstance = new GameInstance(myShipName, renderAdapter, websocketController, new Cooldown(), new Cooldown());
        seaMap = new SeaMap(25, 25);
        this.gameInstance.initView(seaMap);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(renderAdapter.getGameStage());
        multiplexer.addProcessor(renderAdapter.getUiStage());
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        deltaTime = Gdx.graphics.getDeltaTime();
        GameTime.updateCurrentTime(deltaTime);
        accumulator += deltaTime;
        while (accumulator >= GAME_TICK) {
            accumulator -= GAME_TICK;
            gameInstance.update(seaMap);
        }
        gameInstance.updateView(deltaTime);
    }

    @Override
    public void dispose() {
        renderAdapter.dispose();
        websocketController.closeConnection();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            gameInstance.removeTarget();
        }
        if (keycode == Input.Keys.Q) {
            gameInstance.triggerPortShoot();
        }
        if (keycode == Input.Keys.E) {
            gameInstance.triggerStarboardShoot();
        }
        if (keycode == Input.Keys.LEFT) {
            gameInstance.updateDirection(Direction.LEFT);
        }
        if (keycode == Input.Keys.RIGHT) {
            gameInstance.updateDirection(Direction.RIGHT);
        }
        if (keycode == Input.Keys.UP) {
            gameInstance.updateDirection(Direction.TOP);
        }
        if (keycode == Input.Keys.DOWN) {
            gameInstance.updateDirection(Direction.BOT);
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
            gameInstance.stop();
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 vector2 = renderAdapter.getGameStage().screenToStageCoordinates(new Vector2(screenX, screenY));
        tmpShipActor = renderAdapter.getGameStage().hit(vector2.x, vector2.y, true);
        if (tmpShipActor != null) {
            gameInstance.setTarget(((ShipActor) tmpShipActor).getShip());
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
