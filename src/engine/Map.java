package engine;

import javafx.scene.image.ImageView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

import static java.lang.StrictMath.*;


public class Map {

    private static Logger log = LogManager.getRootLogger();

    static final int SCR_TILEWIDTH = 30; //30
    static final int SCR_TILEHEIGHT = 21; //21


    private static int q1 = 0;
    private static int q2 = 19; //19
    private static int r1 = 0;
    private static int r2 = 19; //19
    private static int map_size = 10; //10
    private static int map_radius = 10; //10
    private static int map_width = 20; //20
    private static int map_height = 21; //21

    static private int initialStrength;
    static private int[] seeds;


//    static HashMap<Point, Hex> map = new HashMap<>();

    public static void createMap(List<List<Tile>> tiles, List<List<ImageView>> imageViews, MapShape mapShape){
        int biom = (int)(random()*3);
        switch (mapShape){
            case HEXAGON:
                for (int q = -map_radius; q <= map_radius; q++) {
                    int t1 = max(-map_radius, -q - map_radius);
                    int t2 = min(map_radius, -q + map_radius);
                    for (int r = t1; r <= t2; r++) {
                        int a = r + map_radius;// + ((map_radius<10)?(5-map_radius/2):0);
                        int b = q + map_radius;// + (5-map_radius/2);
//                        Tile tile = new Tile(b, a);
                        Tile tile = new Tile(b, a);
                        changeTile(tile, biom);
                        tiles.get(a).set(b, tile);

                    }
                }
                break;

            case PARALLELOGRAM:
                for (int q = q1; q <= q2; q++) {
                    for (int r = r1; r <= r2; r++) {
                        tiles.get(r).set(q, new Tile(q, r));
                    }
                }
                break;

            case RECTANGLE:
                for (int r = 0; r < map_height; r++) {
                    int r_offset = r>>1; // or r>>1
                    for (int q = -r_offset; q < map_width - r_offset; q++) {

                        int a = r;// + map_width;///2;
                        int b = q + r_offset;// + map_height/2;
                        tiles.get(a).set(b, new Tile(b, a));

                    }
                }
                break;

            case TRIANGLE:
                for (int q = 0; q <= map_size; q++) {
                    for (int r = 0; r <= map_size - q; r++) {
                        tiles.get(r).set(q, new Tile(q, r));
                    }
                }
                break;
            default:
                createMap(tiles, imageViews, MapShape.HEXAGON);

        }
    }

    static private void changeTile(Tile tile, int biom){
        switch (biom){
            case 0:
                tile.setType("tileGrass");
                break;

            case 1:
                tile.setType("tileWater");
                break;

            case 2:
                tile.setType("tileSnow");
                break;
        }
    }

    static private void changeTiles(List<List<Tile>> tiles, List<List<Tile>> imageViews){
    }

//
//    public Tile[][] getMap(){
//        return this.map;
//    }
//
//    public void randomizeMap(){
//
//        seeds = new int[SCR_TILEHEIGHT*SCR_TILEWIDTH];
//        //int biom = (int)(random()*BIOMES.values().length);
//        int biom = (int)(random()*3);
//        for (Tile[] tt: this.map)
//            for (Tile t: tt){
//                if (t == null)
//                    continue;
//                switch (biom){
//                    case 0:
//                        t.setType("tileGrass");
//                        t.setAccess(true);
//                        break;
//                    case 1:
//                        t.setType("tileSnow");
//                        t.setAccess(true);
//                        break;
//                    case 2:
//                        t.setType("tileWater_full");
//                        t.setAccess(false);
//                        break;
//                }
//            }
//
//        int seed = (int)(random()*SCR_TILEWIDTH*SCR_TILEHEIGHT);
//        biom = ((int)(random()*2) == 1)?biom+1:biom-1;
//        biom %= 3;
//        int strength = 20;
//        initialStrength = strength;
//        plant(seed, biom, strength);
//        plant(seed+SCR_TILEWIDTH*2, biom, strength-2);
////        for (int s: seeds)
////            System.out.print(s);
////        seed = (int)(random()*SCR_TILEWIDTH*SCR_TILEHEIGHT);
////        plant(seed, biom, strength);
//    }
//
//
//    void plant(int seed, int biom, int strength) {
//        if (seed < 0 || seed >= SCR_TILEWIDTH*SCR_TILEHEIGHT)
//            return;
//        if (strength < 0)
//            return;
//        int a = seed/SCR_TILEWIDTH;
//        int b = seed%SCR_TILEHEIGHT;
//        if (map[a][b] != null){
//            seeds[seed] = 1;
//            change(a, b, biom);
//            if (seed+1<SCR_TILEWIDTH * SCR_TILEHEIGHT && seeds[seed+1] == 0)
//                plant(seed+1, biom, strength-2);
//            else
//                strength--;
//            if (seed-1>=0 && seeds[seed-1] == 0)
//                plant(seed-1, biom, strength-2);
//            else
//                strength--;
//            if (seed + SCR_TILEHEIGHT < SCR_TILEWIDTH * SCR_TILEHEIGHT && seeds[seed+SCR_TILEHEIGHT] == 0)
//                plant(seed+SCR_TILEHEIGHT, biom, strength-1);
//            else
//                strength--;
//            if (seed-SCR_TILEHEIGHT >= 0 && seeds[seed-SCR_TILEHEIGHT] == 0)
//                plant(seed-SCR_TILEHEIGHT, biom, strength-1);
//            else
//                strength--;
//            if (seed+SCR_TILEHEIGHT+1 < SCR_TILEHEIGHT * SCR_TILEWIDTH && seeds[seed+SCR_TILEHEIGHT+1] == 0)
//                plant(seed+SCR_TILEHEIGHT+1, biom, strength-1);
//            else
//                strength--;
//            if (seed-SCR_TILEHEIGHT-1 >=0 && seeds[seed-SCR_TILEHEIGHT-1] == 0)
//                plant(seed-SCR_TILEHEIGHT-1, biom, strength-1);
//
//        }else if(initialStrength == strength)
//        {
//            map[seed/SCR_TILEWIDTH][seed%SCR_TILEHEIGHT] = new HexTileImage(seed%SCR_TILEHEIGHT, seed/SCR_TILEWIDTH);
//
//            log.info(getNotNullSeed(seed));
//            plant(getNotNullSeed(seed), biom, strength-1);
//            map[seed/SCR_TILEWIDTH][seed%SCR_TILEHEIGHT].setType("tileSelected");
//
//        }
//    }
//
//    void change(int a, int b, int biom) {
//        switch (biom) {
//            case 0:
//                this.map[a][b].setType("tileGrass");
//                this.map[a][b].setAccess(true);
//                break;
//            case 1:
//                this.map[a][b].setType("tileSnow");
//                this.map[a][b].setAccess(true);
//                break;
//            case 2:
//                this.map[a][b].setType("tileWater_full");
//                this.map[a][b].setAccess(false);
//                break;
//        }
//    }
//
//    int getNotNullSeed(int nullSeed){
//        int back = nullSeed;
//        int forward = nullSeed;
//
//        do {
//
//            if (back > 0)
//                back--;
//            if (map[back / SCR_TILEWIDTH][back % SCR_TILEHEIGHT] != null)
//                return back;
//            if (forward < SCR_TILEWIDTH * SCR_TILEHEIGHT - 1)
//                forward++;
//            if (map[forward / SCR_TILEWIDTH][forward % SCR_TILEHEIGHT] != null)
//                return forward;
//
//        } while (back > 0 || forward < SCR_TILEWIDTH * SCR_TILEHEIGHT - 1);
//
//        return -1;
//    }
//
//    private Tile convertSeedToTile(int seed) throws IndexOutOfBoundsException, NullPointerException{
//        return map[seed/SCR_TILEWIDTH][seed%SCR_TILEHEIGHT];
//    }


}
