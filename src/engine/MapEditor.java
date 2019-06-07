package engine;

import javafx.scene.image.ImageView;
import model.INSTRUMENT;
import view.ImageProvider;

import java.util.ArrayList;
import java.util.List;



public class MapEditor {



    private ImageProvider provider;
    private Layout offsetLayout;
    private Layout hexLayout;
    private Tile lastSelectedTile;
    private ImageView selection;
    private List<List<List<ImageView>>> elements;
    private List<List<List<ImageView>>> imageViews;
    private List<List<Tile>> tiles;

    static private int initialStrength;
    static private int[] seeds;

    public MapEditor(ImageProvider provider) {

        this.provider = provider;
        offsetLayout = new Layout(Layout.pointy, new Point(Tile.HEX_WIDTH_SIZE, Tile.HEX_HEIGHT_SIZE), new Point(-Tile.HEX_HEIGHT_SIZE, -Tile.HEX_HEIGHT_SIZE));
        hexLayout = new Layout(Layout.pointy, new Point(Tile.HEX_WIDTH_SIZE, Tile.HEX_HEIGHT_SIZE), new Point(0, 0));
        initTilesAndViews();
        Map.createMap(tiles, MapShape.HEXAGON);
        refreshImageViews(tiles);
        lastSelectedTile = null;
        selection = null;
    }


    private void initTilesAndViews() {
        imageViews = new ArrayList<>(Map.SCR_TILEHEIGHT);
        tiles = new ArrayList<>(Map.SCR_TILEHEIGHT);
        elements = new ArrayList<>(Map.SCR_TILEHEIGHT);
        for (int i = 0; i < Map.SCR_TILEHEIGHT; i++) {
            tiles.add(new ArrayList<>(Map.SCR_TILEWIDTH));
            imageViews.add(new ArrayList<>(Map.SCR_TILEWIDTH));
            elements.add(new ArrayList<>(Map.SCR_TILEWIDTH));
            for (int j = 0; j < Map.SCR_TILEWIDTH; j++){
                Tile t = Map.arrIndicesToTile(i, j);
                tiles.get(i).add(j, t);
                imageViews.get(i).add(j, createImageViewColumn(t));
                elements.get(i).add(j, createTileElements());
            }


        }
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }

    public List<List<List<ImageView>>> getImageViews() {

        return imageViews;
    }
    public List<List<List<ImageView>>> getElements() {
        return elements;
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

//                if (imageViews.get(i).get(j) != null && tiles.get(i).get(j) != null)
                int height = tiles.get(i).get(j).getHeightOfTile();
                if (height < 2)
                    imageViews.get(i).get(j).get(0).setImage(provider.getImage(tiles.get(i).get(j).getType()));
                else {
                    for (int h = 0; h < height - 1; h++) {
                        imageViews.get(i).get(j).get(h).setImage(provider.getImage(tiles.get(i).get(j).getTypeFromTile(h)));
                    }
                    imageViews.get(i).get(j).get(height-1).setImage(provider.getImage(tiles.get(i).get(j).getType()));
                }

                for (int k = 0; k < tiles.get(i).get(j).getElementsAmount(); k++) {
                    elements.get(i).get(j).get(k).setImage(provider.getImage(tiles.get(i).get(j).getElementsTypes().get(k)));
                    elements.get(i).get(j).get(k).setLayoutX(tiles.get(i).get(j).getElementsCoords().get(k).x);
                    elements.get(i).get(j).get(k).setLayoutY(tiles.get(i).get(j).getElementsCoords().get(k).y);

                }

            }
        }

    }

    private List<ImageView> createImageViewColumn(Tile t) {
        Point p = offsetLayout.hexToPixel(t);

        List<ImageView> imageViews = new ArrayList<>(Tile.MAX_TILE_HEIGHT);
        for (int i = 0; i < Tile.MAX_TILE_HEIGHT; i++) {
            ImageView iv = new ImageView();
            iv.setLayoutX(p.x);
            iv.setLayoutY(p.y - i * Tile.HEX_VERTICAL_OFFSET);
            iv.setImage(provider.getImage(t.getType()));
            imageViews.add(iv);
        }

        return imageViews;
    }

    private List<ImageView> createTileElements() {
        List<ImageView> imageViews = new ArrayList<>(Tile.MAX_ELEMENTS_AMOUNT);
        for (int i = 0; i < Tile.MAX_ELEMENTS_AMOUNT; i++) {
            ImageView iv = new ImageView();
            imageViews.add(iv);
        }

        return imageViews;
    }

    public ImageView getSelectedTile(){
        if (selection == null)
        {
            selection = new ImageView();
            selection.setImage(provider.getImage("tileSelected"));
            selection.setLayoutX(-100);
            selection.setLayoutY(-100);
            selection.setVisible(false);

        }
        return selection;
    }

    public void moved(INSTRUMENT instrument, double x, double y, boolean blocked) {
        if (blocked)
            return;
        Tile selectedTile = new Tile(hexLayout.pixelToHex(new Point(x, y)).hexRound());
        Point p = offsetLayout.hexToPixel(selectedTile);
        switch (instrument) {
            case NONE:
                return;

            case SELECT:
            case ERASER:
            case TILEGRASS:
            case TILEGRASS_FULL:
            case TILEMAGIC:
            case TILEMAGIC_FULL:
            case TILEDIRT:
            case TILEDIRT_FULL:
            case TILEWATER:
            case TILEWATER_FULL:
            case TILESTONE:
            case TILESTONE_FULL:
            case TILEAUTUMN:
            case TILEAUTUMN_FULL:
            case TILELAVA:
            case TILELAVA_FULL:
            case TREEGREEN_MID:
                selection.setVisible(true);
                if(outOfBounds(selectedTile))
                    return;

                int h = Map.getTile(tiles, selectedTile.q, selectedTile.r).getHeightOfTile() - 1;
                selection.setLayoutX(p.x);
                selection.setLayoutY(p.y - (h > 0 ? Tile.HEX_VERTICAL_OFFSET * h : 0));
                break;




        }

//        Tile selectedTile = new Tile(hexLayout.pixelToHex(new Point(x, y)).hexRound());
//        if(outOfBounds(selectedTile))
//            return;
//        Point p = offsetLayout.hexToPixel(selectedTile);
////        LogManager.getRootLogger().info(selectedTile.q + " " + selectedTile.r);
//        selection.setLayoutX(p.x);
//        selection.setLayoutY(p.y);
    }

    public void leftClicked(INSTRUMENT instrument, double x, double y, boolean blocked) {
        if (blocked)
            return;
        Point pixel = new Point(x, y);
        Tile selectedTile = new Tile(hexLayout.pixelToHex(pixel).hexRound());
        Point p = offsetLayout.hexToPixel(selectedTile);
        int q, r;


        switch (instrument) {
            case NONE:
                return;

            case ERASER:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).popTile();
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile()).setImage(provider.getImage("empty"));

                break;

            case TILEGRASS:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileGrass");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileGrass"));

                break;

            case TILEGRASS_FULL:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileGrass_full");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileGrass_full"));selection.setLayoutX(p.x);

                break;
//
            case TILEMAGIC:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileMagic");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileMagic"));selection.setLayoutX(p.x);

                break;
//
            case TILEMAGIC_FULL:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileMagic_full");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileMagic_full"));selection.setLayoutX(p.x);


                break;

            case TILEDIRT:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileDirt");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileDirt"));selection.setLayoutX(p.x);

                break;

            case TILEDIRT_FULL:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileDirt_full");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileDirt_full"));selection.setLayoutX(p.x);

                break;

            case TILEWATER:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileWater");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileWater"));selection.setLayoutX(p.x);

                break;

            case TILEWATER_FULL:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileWater_full");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileWater_full"));selection.setLayoutX(p.x);

                break;

            case TILESTONE:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileStone");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileStone"));selection.setLayoutX(p.x);

                break;

            case TILESTONE_FULL:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileStone_full");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileStone_full"));selection.setLayoutX(p.x);

                break;

            case TILEAUTUMN:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileAutumn");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileAutumn"));selection.setLayoutX(p.x);

                break;

            case TILEAUTUMN_FULL:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileAutumn_full");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileAutumn_full"));selection.setLayoutX(p.x);

                break;

            case TILELAVA:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileLava");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileLava"));selection.setLayoutX(p.x);

                break;

            case TILELAVA_FULL:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                Map.getTile(tiles, q, r).pushTile("tileLava_full");
                Map.getImageView(imageViews, q, r, Map.getTile(tiles, q, r).getHeightOfTile() - 1).setImage(provider.getImage("tileLava_full"));
                selection.setLayoutX(p.x);

                break;

            case TREEGREEN_MID:
                selection.setVisible(true);
                if (outOfBounds(selectedTile))
                    return;

                q = selectedTile.q;
                r = selectedTile.r;
                ImageView iv = new ImageView(provider.getImage("treeGreen_mid"));
                int height = Map.getTile(tiles, selectedTile.q, selectedTile.r).getHeightOfTile() - 1;
                Point treesOrigin = new Point( x - iv.getImage().getWidth()/2, y - iv.getImage().getHeight() * 0.9 - (height > 0 ? height * Tile.HEX_VERTICAL_OFFSET : 0));

                Map.getTile(tiles, q, r).pushElement("treeGreen_mid", treesOrigin);
                Map.pushElementView(elements, iv, q, r, treesOrigin);
                break;

        }
        int h = Map.getTile(tiles, selectedTile.q, selectedTile.r).getHeightOfTile() - 1;
        selection.setLayoutX(p.x);
        selection.setLayoutY(p.y - (h > 0 ? Tile.HEX_VERTICAL_OFFSET * h : 0));
//        if (selection == null)
//            return;
//        Tile selectedTile = new Tile(hexLayout.pixelToHex(new Point(x, y)).hexRound());
//        if (outOfBounds(selectedTile))
//            return;
//        Point p = offsetLayout.hexToPixel(selectedTile);
//
//        int q = selectedTile.q;
//        int r = selectedTile.r;
//
//        Map.getTile(tiles, q, r).setType("tileMagic");
//        Map.getTile(tiles, q, r).setAccess(true);
//        Map.getImageView(imageViews, q, r).setImage(provider.getImage("tileMagic"));
//
//
//        selection.setLayoutX(p.x);
//        selection.setLayoutY(p.y);

    }

    public void rightClicked(INSTRUMENT instrument, double x, double y, boolean blocked) {
//        if (blocked)
//            return;
//        Hex selectedTile = hexLayout.pixelToHex(new Point(x, y)).hexRound();
//        if (outOfBounds(selectedTile))
//            return;
//        int q = selectedTile.q;
//        int r = selectedTile.r;
//
//        Map.getTile(tiles, q, r).setType("empty");
//        Map.getTile(tiles, q, r).setAccess(false);
//        Map.getImageView(imageViews, q, r).setImage(provider.getImage("empty"));
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