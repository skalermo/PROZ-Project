package engine;

public class Hexgrid {

    private static final int ARRAY_DIMENSION_SIZE = 13;

    private Tile[][] tiles;

    public Hexgrid() {
        tiles  = new Tile[ARRAY_DIMENSION_SIZE][ARRAY_DIMENSION_SIZE];
    }
}
