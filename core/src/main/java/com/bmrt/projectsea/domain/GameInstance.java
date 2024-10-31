package com.bmrt.projectsea.domain;

import java.util.HashMap;

public class GameInstance {

    private final String myShipName;
    private final HashMap<String, Ship> ships;

    public GameInstance(String myShipName) {
        this.ships = new HashMap<>();
        this.myShipName = myShipName;
        this.ships.put(myShipName, new Ship(new Vector(10, 5), new Vector(0, 0), Direction.TOP, myShipName,
            Ship.MAX_HP, Ship.MAX_HP));
    }

    public Ship getMyShip() {
        return ships.get(myShipName);
    }

    public void add(Ship ship) {
        ships.put(ship.getName(), ship);
    }

    public Ship get(String name) {
        return ships.get(name);
    }
}
