package engine;

enum TileType {
    GROUND,
    VOID,
    NONEXIST
}

class Tile {
    private TileType type;
//    private Creature creature;


    public Tile(TileType type) {
        this.type = type;
    }
}
