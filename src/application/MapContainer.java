package application;

import engine.Game;
import engine.Map;

import java.io.Serializable;

public class MapContainer implements Serializable {

    private Map map;

    public MapContainer(Game game){
        this.map = game.getMap();
    }

    public Map getMap() {
        return map;
    }
}
