package com.bmrt.projectsea.domain;

import java.util.HashMap;

public class GameInstance {

    private final String myShipName;
    private final HashMap<String, Ship> ships;
    private final RenderPort renderPort;

    private final WebSocketPort webSocketPort;

    public GameInstance(String myShipName, RenderPort renderPort, WebSocketPort websocketPort) {
        this.ships = new HashMap<>();
        this.myShipName = myShipName;
        this.renderPort = renderPort;
        this.webSocketPort = websocketPort;
        this.ships.put(myShipName, new Ship(new Vector(10, 5), new Vector(0, 0), Direction.TOP, myShipName,
            Ship.MAX_HP, Ship.MAX_HP));
        webSocketPort.addListener(this);
        websocketPort.startConnection();
    }

    public Ship getMyShip() {
        return ships.get(myShipName);
    }

    public void add(Ship ship) {
        ships.put(ship.getName(), ship);
        renderPort.add(ship);
    }

    public Ship get(String name) {
        return ships.get(name);
    }

    public boolean contains(String name) {
        return ships.containsKey(name);
    }

    public void initView(SeaMap seaMap) {
        renderPort.initRendering(seaMap, getMyShip());
    }

    public void update(SeaMap seaMap) {
        for (Ship ship : ships.values()) {
            ship.update(seaMap);
        }
    }

    public void updateView(float deltaTime) {
        renderPort.updateView(getMyShip(), deltaTime);
    }

    public void triggerPortShoot() {
        renderPort.triggerPortShoot();
    }

    public void triggerStarboardShoot() {
        renderPort.triggerStarboardShoot();
    }

    public void removeTarget() {
        renderPort.removeTarget();
    }

    public void setTarget(Ship ship) {
        renderPort.setTarget(ship);
    }

    public void updateDirection(Direction direction) {
        webSocketPort.updateDirection(direction);
    }

    public void stop() {
        webSocketPort.stop();
    }
}
