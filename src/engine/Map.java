package engine;

import javafx.scene.image.ImageView;

import java.util.List;

import static java.lang.StrictMath.*;


public class Map {


    public static final int SCR_TILEWIDTH = 31; //25
    public static final int SCR_TILEHEIGHT = 24; //44


    private static int q1 = 0;
    private static int q2 = 19; //19
    private static int r1 = 0;
    private static int r2 = 19; //19
    private static int map_size = 10; //10
    private static int map_radius = 10; //10
    private static int map_width = 20; //20
    private static int map_height = 21; //21


//    static void oset(List<List<Tile>> tiles, Tile tile){
//        tiles.get(tile.r).set(tile.q + INDEX_OFFSET, tile);
//    }
//
//    static Tile oget(List<List<Tile>> tiles, int i, int j){
//        return tiles.get(i).get(j + INDEX_OFFSET);
//    }

//    static HashMap<Point, Hex> map = new HashMap<>();

    public static void createMap(List<List<Tile>> tiles, MapShape mapShape){
        int biom = (int)(random()*3);
        switch (mapShape){
            case HEXAGON:
                for (int q = -map_radius; q <= map_radius; q++) {
                    int t1 = max(-map_radius, -q - map_radius);
                    int t2 = min(map_radius, -q + map_radius);
                    for (int r = t1; r <= t2; r++) {
                        int a = r + map_radius;// + ((map_radius<10)?(5-map_radius/2):0);
                        int b = q + map_radius;// + (5-map_radius/2);
                        changeTile(getTile(tiles, b, a), biom);
                    }
                }
                break;

//            case PARALLELOGRAM:
//                for (int q = q1; q <= q2; q++) {
//                    for (int r = r1; r <= r2; r++) {
//                        tiles.get(r).set(q, new Tile(q, r));
//                    }
//                }
//                break;
//
//            case RECTANGLE:
//                for (int r = 0; r < map_height; r++) {
//                    int r_offset = r>>1; // or r>>1
//                    for (int q = -r_offset; q < map_width - r_offset; q++) {
//
//                        int a = r;// map_width;///2;
//                        int b = q + r_offset;// + map_height/2;
//                        Tile tile = new Tile(b, a);
//                        a = floorMod(a, SCR_TILEWIDTH);
//                        b = floorMod(b, SCR_TILEHEIGHT);
//                        changeTile(tile, biom);
//                        tiles.get(a).set(b, tile);
//
//                    }
//                }
//                break;
//
//            case TRIANGLE:
//                for (int q = 0; q <= map_size; q++) {
//                    for (int r = 0; r <= map_size - q; r++) {
//                        Tile tile = new Tile(q, r);
//                        changeTile(tile, biom);
//                        tiles.get(r).set(q, tile);
//                    }
//                }
//                break;
            default:
                createMap(tiles, MapShape.HEXAGON);

        }
    }


    static private void changeTile(Tile tile, int biom){
        switch (biom){
            case 0:
                tile.pushTile("tileGrass");
                break;

            case 1:
                tile.pushTile("tileWater");

                break;

            case 2:
                tile.pushTile("tileSnow");
                break;
        }
    }

    static Tile getTile(List<List<Tile>> tiles, int q, int r){
        return tiles.get(r).get(q + (r+1)/2);
    }

    static void setTile(List<List<Tile>> tiles, int q, int r, Tile tile){
        tiles.get(r).set(q + (r+1)/2, tile);
    }

    static Point hexCoordsToArrIndices(int q, int r) {
        int t = (r+1)/2;
        return new Point(r, q + t);
    }

    static Tile arrIndicesToTile(int i, int j) {
        return new Tile(j - (i+1)/2, i);
    }

    static ImageView getImageView(List<List<List<ImageView>>> imageViews, int q, int r, int h){
        return imageViews.get(r).get(q + (r+1)/2).get(h);
    }

    static void pushElementView(List<List<List<ImageView>>> imageViews, ImageView iv, int q, int r, Point p) {
        int i;
        if ((i = getFirstNull(imageViews, q, r)) == -1)
            return;
        imageViews.get(r).get(q + (r+1)/2).get(i).setImage(iv.getImage());
        imageViews.get(r).get(q + (r+1)/2).get(i).setLayoutX(p.x);
        imageViews.get(r).get(q + (r+1)/2).get(i).setLayoutY(p.y);
    }

    static private int getFirstNull(List<List<List<ImageView>>> imageViews, int q, int r) {
        for (int i = 0; i < Tile.MAX_ELEMENTS_AMOUNT; i++)
            if (imageViews.get(r).get(q + (r+1)/2).get(i).getImage() == null)
                return i;

        return -1;
    }



}
