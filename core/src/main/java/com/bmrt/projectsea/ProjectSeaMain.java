package com.bmrt.projectsea;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.SeaMap;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.Vector;
import com.bmrt.projectsea.render.GameCamera;
import com.bmrt.projectsea.render.ShipRender;
import com.bmrt.projectsea.render.TiledMap;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class ProjectSeaMain extends ApplicationAdapter implements InputProcessor {

    public static final float UNIT = 1 / 32f;

    public static final float GAME_TICK = 1 / 60f;
    private float accumulator = 0f;
    private SeaMap seaMap;
    private Ship myShip;
    private GameCamera camera;
    private TiledMap tiledMap;
    private ShipRender shipRender;
    private OrthogonalTiledMapRenderer renderer;
    private float stateTime;

    @Override
    public void create() {
        seaMap = new SeaMap(25, 25);
        myShip = new Ship(Vector.ZERO, Vector.ZERO, Direction.BOT);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        tiledMap = new TiledMap(seaMap);
        shipRender = new ShipRender();

        camera = new GameCamera((w / h) * 10, 10, seaMap.getWidth(), seaMap.getHeight());
        camera.update(myShip.getPosition().getX(), myShip.getPosition().getY());
        renderer = new OrthogonalTiledMapRenderer(tiledMap.get(), UNIT);
        stateTime = 0f;
        Gdx.input.setInputProcessor(this);

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
        Batch orthoBatch = renderer.getBatch();
        orthoBatch.begin();
        shipRender.draw(stateTime, orthoBatch, myShip.getPosition(), myShip.getDirection());
        orthoBatch.end();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        renderer.dispose();
        shipRender.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
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
