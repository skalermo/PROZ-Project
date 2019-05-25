package engine;

import application.Logger;
import javafx.scene.image.ImageView;
import view.ImageProvider;

import java.util.ArrayList;
import java.util.List;

public class MapEditor {

    private static double HEX_WIDTH_SIZE = 65/Math.sqrt(3);
    private static double HEX_HEIGHT_SIZE = 31.5;

    private ImageProvider provider;
    private Layout offsetLayout;
    private Layout hexLayout;
    private Tile lastSelectedTile;
    private ImageView selection;
    private List<List<ImageView>> imageViews;
    private List<List<Tile>> tiles;

    static private int initialStrength;
    static private int[] seeds;

    public MapEditor(ImageProvider provider) {

        this.provider = provider;
        offsetLayout = new Layout(Layout.pointy, new Point(HEX_WIDTH_SIZE, HEX_HEIGHT_SIZE), new Point(-HEX_HEIGHT_SIZE, -HEX_HEIGHT_SIZE));
        hexLayout = new Layout(Layout.pointy, new Point(HEX_WIDTH_SIZE, HEX_HEIGHT_SIZE), new Point(0, 0));
        init2dArrays();
        Map.createMap(tiles, imageViews, MapShape.HEXAGON);
        refreshImageViews(tiles);
        lastSelectedTile = null;
        selection = null;
    }


    private void init2dArrays() {
        imageViews = new ArrayList<>(Map.SCR_TILEHEIGHT);
        tiles = new ArrayList<>(Map.SCR_TILEHEIGHT);
        for (int i = 0; i < Map.SCR_TILEHEIGHT; i++) {
            tiles.add(new ArrayList<>(Map.SCR_TILEWIDTH));
            imageViews.add(new ArrayList<>(Map.SCR_TILEWIDTH));
            for (int j = 0; j < Map.SCR_TILEWIDTH; j++){
                Tile t = Map.arrIndeciesToTile(i, j);
                tiles.get(i).add(j, t);
                imageViews.get(i).add(j, createImageView(t));
            }

        }
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }

    public List<List<ImageView>> getImageViews() {

        return imageViews;
    }

    public void setTiles(List<List<Tile>> tiles) {
        if (tiles != null) {
            this.tiles = tiles;
            refreshImageViews(tiles);
        }
    }

    private void refreshImageViews(List<List<Tile>> tiles) {
        for (int i = 0; i < tiles.size(); i++){
            for (int j = 0; j < tiles.get(i).size(); j++){
                if (imageViews.get(i).get(j) != null && tiles.get(i).get(j) != null)
                    imageViews.get(i).get(j).setImage(provider.getImage(tiles.get(i).get(j).getType()));
            }
        }

    }

    private ImageView createImageView(Tile t) {
        ImageView imageView = new ImageView();
        imageView.setImage(provider.getImage(t.getType()));
        Point p = offsetLayout.hexToPixel(t);
        imageView.setLayoutX(p.x);
        imageView.setLayoutY(p.y);
        return imageView;
    }

    public ImageView getSelectedTile(){
        if (selection == null)
        {
            selection = new ImageView();
            selection.setImage(provider.getImage("tileSelected"));
            selection.setLayoutX(-100);
            selection.setLayoutY(-100);

        }
        return selection;
    }

    public void moved(double x, double y) {
        if (selection == null)
            return;

        Tile selectedTile = new Tile(hexLayout.pixelToHex(new Point(x, y)).hexRound());
        if(outOfBounds(selectedTile))
            return;
        Point p = offsetLayout.hexToPixel(selectedTile);
//        LogManager.getRootLogger().info(selectedTile.q + " " + selectedTile.r);
        selection.setLayoutX(p.x);
        selection.setLayoutY(p.y);
    }

    public void leftClicked(double x, double y) {
        if (selection == null)
            return;
        Tile selectedTile = new Tile(hexLayout.pixelToHex(new Point(x, y)).hexRound());
        if (outOfBounds(selectedTile))
            return;
        Point p = offsetLayout.hexToPixel(selectedTile);

        int q = selectedTile.q;
        int r = selectedTile.r;

        Map.getTile(tiles, q, r).setType("tileMagic");
        Map.getTile(tiles, q, r).setAccess(true);
        Map.getImageView(imageViews, q, r).setImage(provider.getImage("tileMagic"));


        selection.setLayoutX(p.x);
        selection.setLayoutY(p.y);

    }

    public void rightClicked(double x, double y) {
        Hex selectedTile = hexLayout.pixelToHex(new Point(x, y)).hexRound();
        if (outOfBounds(selectedTile))
            return;
        int q = selectedTile.q;
        int r = selectedTile.r;

        Map.getTile(tiles, q, r).setType("empty");
        Map.getTile(tiles, q, r).setAccess(false);
        Map.getImageView(imageViews, q, r).setImage(provider.getImage("empty"));
    }

//    public void dragged(double x, double y) {
//        leftClicked(x, y);
//    }

    private boolean outOfBounds(Hex h){
        Point p = Map.hexCoordsToArrIndices(h.q, h.r);

        return 1 > p.x || p.x >= Map.SCR_TILEHEIGHT-2 || 2 > p.y || p.y >= Map.SCR_TILEWIDTH-2;
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