package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.websocket.Action;

import java.util.HashMap;
import java.util.Objects;

public class GameInstance {

    private static final float RELOAD_DURATION = 2f;
    private final String myShipName;
    private final HashMap<String, Ship> ships;
    private final RenderPort renderPort;
    private final WebSocketPort webSocketPort;
    private Ship target;

    public GameInstance(String myShipName, RenderPort renderPort, WebSocketPort websocketPort) {
        this.ships = new HashMap<>();
        this.myShipName = myShipName;
        this.renderPort = renderPort;
        this.webSocketPort = websocketPort;
        webSocketPort.addListener(this);
        websocketPort.startConnection();
    }

    public Ship getMyShip() {
        return ships.get(myShipName);
    }

    public void addShip(Ship ship) {
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
        renderPort.initRendering(seaMap, this);
    }

    public void update(SeaMap seaMap) {
        for (Ship ship : ships.values()) {
            ship.update(seaMap);
        }
    }

    public void updateView(float deltaTime) {
        if (!ships.containsKey(myShipName)) {
            return;
        }
        boolean canShoot = target != null && getMyShip().canShoot(target);
        renderPort.updateView(getMyShip().getPosition(), deltaTime, canShoot);
    }

    public void triggerPortShoot() {
        if (target != null && getMyShip().canShoot(target)) {
            renderPort.triggerPortShoot(myShipName);
            webSocketPort.shoot(myShipName, target.getName());
        }
    }

    public void triggerStarboardShoot() {
        if (target != null && getMyShip().canShoot(target)) {
            renderPort.triggerStarboardShoot(myShipName);
            webSocketPort.shoot(myShipName, target.getName());
        }
    }

    public void removeTarget() {
        target = null;
        renderPort.removeTarget();
    }

    public void setTarget(Ship ship) {
        if (!Objects.equals(ship.getName(), myShipName)) {
            target = ship;
            renderPort.setTarget(ship);
        }
    }

    public void updateDirection(Direction direction) {
        webSocketPort.updateDirection(direction);
    }

    public void stop() {
        webSocketPort.stop();
    }

    public String getMyShipName() {
        return myShipName;
    }

    public void handleAction(ActionCommand command) {
        if (command.getAction().equals(Action.JOIN)) {
            addShip(command.getNewShip());
        } else if (command.getAction().equals(Action.LEAVE)) {
            ships.remove(command.getName());
            renderPort.remove(command.getName());
        } else if (ships.containsKey(command.getName())) {
            command.updateShip(ships.get(command.getName()));
        }
    }
}
