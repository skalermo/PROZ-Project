package application;

import engine.Game;
import engine.Tile;

import java.io.Serializable;
import java.util.List;

public class MapContainer implements Serializable {

    private List<List<Tile>> tiles;

    public MapContainer(Game game){
        this.tiles = game.getTiles();
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }
}
