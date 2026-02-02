package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.command.ActionCommand;
import com.bmrt.projectsea.domain.command.ShootCommand;
import com.bmrt.projectsea.websocket.Action;

import java.util.HashMap;
import java.util.Objects;

public class GameInstance {

    private final String myShipName;
    private final HashMap<String, Ship> ships;
    private final RenderPort renderPort;
    private final WebSocketPort webSocketPort;
    private final Cooldown portCooldown;
    private final Cooldown starboardCooldown;
    private Ship target;

    public GameInstance(String myShipName, RenderPort renderPort, WebSocketPort websocketPort, Cooldown portCooldown, Cooldown starboardCooldown) {
        this.ships = new HashMap<>();
        this.myShipName = myShipName;
        this.renderPort = renderPort;
        this.webSocketPort = websocketPort;
        this.portCooldown = portCooldown;
        this.starboardCooldown = starboardCooldown;
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
        if (target != null && getMyShip().canShoot(target) && portCooldown.isReady()) {
            portCooldown.trigger();
            webSocketPort.shoot(myShipName, target.getName());
        }
    }

    public void triggerStarboardShoot() {
        if (target != null && getMyShip().canShoot(target) && starboardCooldown.isReady()) {
            starboardCooldown.trigger();
            webSocketPort.shoot(myShipName, target.getName());
        }
    }

    public void renderShoot(ShootCommand command) {
        renderPort.renderPortShoot(command);
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

    public Cooldown getPortCooldown() {
        return portCooldown;
    }

    public Cooldown getStarboardCooldown() {
        return starboardCooldown;
    }
}
