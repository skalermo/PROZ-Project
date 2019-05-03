package engine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.Serializable;

import static java.lang.StrictMath.*;


public class Map implements Serializable {

    private static Logger log = LogManager.getRootLogger();
    static final int SCR_TILEWIDTH = 30; //30
    static final int SCR_TILEHEIGHT = 21; //21


    private int q1 = 0;
    private int q2 = 19; //19
    private int r1 = 0;
    private int r2 = 19; //19
    private int map_size = 10; //10
    private int map_radius = 10; //10
    private int map_width = 20; //20
    private int map_height = 21; //21

    private Tile[][] map;

    static private int initialStrength;
    static private int[] seeds;


//    static HashMap<Point, Hex> map = new HashMap<>();

    public Map(MapShape mapShape, int q1, int q2, int r1, int r2){
        if (mapShape != MapShape.PARALLELOGRAM) {
            throw new IllegalArgumentException("this constructor is for parallelogram");
        }
        map = new HexTileImage[SCR_TILEWIDTH][SCR_TILEHEIGHT];
            this.q1 = q1;
            this.q2 = q2;
            this.r1 = r1;
            this.r2 = r2;
        for (int q = q1; q <= q2; q++) {
            for (int r = r1; r <= r2; r++) {
                map[r][q] = new Tile(q, r);
            }
        }

    }



    public Map(MapShape mapShape, int map_width, int map_height){
        if (mapShape != MapShape.RECTANGLE)
            throw new IllegalArgumentException("this constructor is for rectangle");
        map = new Tile[SCR_TILEWIDTH][SCR_TILEHEIGHT];
        this.map_width = map_width;
        this.map_height = map_height;
        for (int r = 0; r < map_height; r++) {
            int r_offset = r>>1; // or r>>1
            for (int q = -r_offset; q < map_width - r_offset; q++) {

                int a = r;// + map_width;///2;
                int b = q + r_offset;// + map_height/2;
                map[a][b] = new Tile(b, a);
            }
        }
    }

    public Map(MapShape mapShape, int size){
        if (mapShape != MapShape.HEXAGON && mapShape != MapShape.TRIANGLE)
            throw new IllegalArgumentException("this constructor is for hexagon or triangle");
        map = new Tile[SCR_TILEWIDTH][SCR_TILEHEIGHT];
        switch (mapShape){
            case HEXAGON:
                this.map_radius = size;
                for (int q = -map_radius; q <= map_radius; q++) {
                    int t1 = max(-map_radius, -q - map_radius);
                    int t2 = min(map_radius, -q + map_radius);
                    for (int r = t1; r <= t2; r++) {
                        int a = r + map_radius;// + ((map_radius<10)?(5-map_radius/2):0);
                        int b = q + map_radius;// + (5-map_radius/2);
                        map[a][b] = new Tile(b, a);
                    }
                }
                break;
            case TRIANGLE:
                this.map_size = size;
                for (int q = 0; q <= map_size; q++) {
                    for (int r = 0; r <= map_size - q; r++) {
                        map[r][q] = new Tile(q, r);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("this constructor is for hexagon or triangle");
        }
    }


    public Tile[][] getMap(){
        return this.map;
    }

    public void randomizeMap(){

        seeds = new int[SCR_TILEHEIGHT*SCR_TILEWIDTH];
        //int biom = (int)(random()*BIOMES.values().length);
        int biom = (int)(random()*3);
        for (Tile[] tt: this.map)
            for (Tile t: tt){
                if (t == null)
                    continue;
                switch (biom){
                    case 0:
                        t.setType("tileGrass");
                        t.setAccess(true);
                        break;
                    case 1:
                        t.setType("tileSnow");
                        t.setAccess(true);
                        break;
                    case 2:
                        t.setType("tileWater_full");
                        t.setAccess(false);
                        break;
                }
            }

        int seed = (int)(random()*SCR_TILEWIDTH*SCR_TILEHEIGHT);
        biom = ((int)(random()*2) == 1)?biom+1:biom-1;
        biom %= 3;
        int strength = 20;
        initialStrength = strength;
        plant(seed, biom, strength);
        plant(seed+SCR_TILEWIDTH*2, biom, strength-2);
//        for (int s: seeds)
//            System.out.print(s);
//        seed = (int)(random()*SCR_TILEWIDTH*SCR_TILEHEIGHT);
//        plant(seed, biom, strength);
    }


    void plant(int seed, int biom, int strength) {
        if (seed < 0 || seed >= SCR_TILEWIDTH*SCR_TILEHEIGHT)
            return;
        if (strength < 0)
            return;
        int a = seed/SCR_TILEWIDTH;
        int b = seed%SCR_TILEHEIGHT;
        if (map[a][b] != null){
            seeds[seed] = 1;
            change(a, b, biom);
            if (seed+1<SCR_TILEWIDTH * SCR_TILEHEIGHT && seeds[seed+1] == 0)
                plant(seed+1, biom, strength-2);
            else
                strength--;
            if (seed-1>=0 && seeds[seed-1] == 0)
                plant(seed-1, biom, strength-2);
            else
                strength--;
            if (seed + SCR_TILEHEIGHT < SCR_TILEWIDTH * SCR_TILEHEIGHT && seeds[seed+SCR_TILEHEIGHT] == 0)
                plant(seed+SCR_TILEHEIGHT, biom, strength-1);
            else
                strength--;
            if (seed-SCR_TILEHEIGHT >= 0 && seeds[seed-SCR_TILEHEIGHT] == 0)
                plant(seed-SCR_TILEHEIGHT, biom, strength-1);
            else
                strength--;
            if (seed+SCR_TILEHEIGHT+1 < SCR_TILEHEIGHT * SCR_TILEWIDTH && seeds[seed+SCR_TILEHEIGHT+1] == 0)
                plant(seed+SCR_TILEHEIGHT+1, biom, strength-1);
            else
                strength--;
            if (seed-SCR_TILEHEIGHT-1 >=0 && seeds[seed-SCR_TILEHEIGHT-1] == 0)
                plant(seed-SCR_TILEHEIGHT-1, biom, strength-1);

        }else if(initialStrength == strength)
        {
            map[seed/SCR_TILEWIDTH][seed%SCR_TILEHEIGHT] = new HexTileImage(seed%SCR_TILEHEIGHT, seed/SCR_TILEWIDTH);

            log.info(getNotNullSeed(seed));
            plant(getNotNullSeed(seed), biom, strength-1);
            map[seed/SCR_TILEWIDTH][seed%SCR_TILEHEIGHT].setType("tileSelected");

        }
    }

    void change(int a, int b, int biom) {
        switch (biom) {
            case 0:
                this.map[a][b].setType("tileGrass");
                this.map[a][b].setAccess(true);
                break;
            case 1:
                this.map[a][b].setType("tileSnow");
                this.map[a][b].setAccess(true);
                break;
            case 2:
                this.map[a][b].setType("tileWater_full");
                this.map[a][b].setAccess(false);
                break;
        }
    }

    int getNotNullSeed(int nullSeed){
        int back = nullSeed;
        int forward = nullSeed;

        do {

            if (back > 0)
                back--;
            if (map[back / SCR_TILEWIDTH][back % SCR_TILEHEIGHT] != null)
                return back;
            if (forward < SCR_TILEWIDTH * SCR_TILEHEIGHT - 1)
                forward++;
            if (map[forward / SCR_TILEWIDTH][forward % SCR_TILEHEIGHT] != null)
                return forward;

        } while (back > 0 || forward < SCR_TILEWIDTH * SCR_TILEHEIGHT - 1);

        return -1;
    }

    private Tile convertSeedToTile(int seed) throws IndexOutOfBoundsException, NullPointerException{
        return map[seed/SCR_TILEWIDTH][seed%SCR_TILEHEIGHT];
    }


}
