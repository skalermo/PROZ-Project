package application;

import engine.Tile;

import java.io.Serializable;
import java.util.List;

public class MapContainer implements Serializable {

    private List<List<Tile>> tiles;

    public MapContainer(List<List<Tile>> tiles){
        this.tiles = tiles;
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }
}
