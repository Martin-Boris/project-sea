package com.bmrt.projectsea.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.bmrt.projectsea.domain.SeaMap;

public class TiledMap {

    private final com.badlogic.gdx.maps.tiled.TiledMap tiledMap;
    private final Texture seaTiles;

    public TiledMap(SeaMap seaMap) {
        seaTiles = new Texture(Gdx.files.internal("sprite/water.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(seaTiles, 32, 32);
        tiledMap = new com.badlogic.gdx.maps.tiled.TiledMap();
        MapLayers layers = tiledMap.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(seaMap.getWidth(), seaMap.getHeight(), 32, 32);
        layer.setName("ocean");
        layer.setParallaxX(1);
        layer.setParallaxY(1);
        for (int x = 0; x < seaMap.getWidth(); x++) {
            for (int y = 0; y < seaMap.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                StaticTiledMapTile frame1 = new StaticTiledMapTile(splitTiles[0][0]);
                StaticTiledMapTile frame2 = new StaticTiledMapTile(splitTiles[0][1]);
                cell.setTile(new AnimatedTiledMapTile(0.5f, new Array<>(new StaticTiledMapTile[]{frame1, frame2})));
                layer.setCell(x, y, cell);
            }
        }
        layers.add(layer);
    }

    public void dispose() {
        tiledMap.dispose();
        seaTiles.dispose();
    }

    public com.badlogic.gdx.maps.tiled.TiledMap get() {
        return tiledMap;
    }
}
