package engine;

import application.Logger;
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
    }

    public void leftClicked(INSTRUMENT instrument, double x, double y, boolean blocked) {
        if (blocked)
            return;
        Point pixel = new Point(x, y);
        Tile selectedScreenTile = new Tile(hexLayout.pixelToHex(pixel).hexRound());
        if (outOfBounds(selectedScreenTile))
            return;
        int q = selectedScreenTile.q;
        int r = selectedScreenTile.r;
        Point p = offsetLayout.hexToPixel(selectedScreenTile);
        Tile selectedTile = Map.getTile(tiles, q, r);


        switch (instrument) {
            case NONE:
                return;

            case ERASER:
                selection.setVisible(true);



                Logger.info(""+selectedTile.getHeightOfTile());
                selectedTile.popTile();
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile()).setImage(provider.getImage("empty"));
                if (selectedTile.getType().equals("empty")) {
                    selectedTile.removeElements();
                    for (int i = 0; i < Tile.MAX_ELEMENTS_AMOUNT; i++)
                        Map.getImageView(elements, q, r, i).setImage(null);
                }
                else
                    for (int i = 0; i < selectedTile.getElementsAmount(); i++)
                        Map.getImageView(elements, q, r, i).setLayoutY(selectedTile.getElementsCoords().get(i).y - (selectedTile.getHeightOfTile()-1) * Tile.HEX_VERTICAL_OFFSET);
                Logger.info(""+selectedTile.getHeightOfTile());

                break;

            case TILEGRASS:
                selection.setVisible(true);



                selectedTile.pushTile("tileGrass");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileGrass"));
                for (int i = 0; i < selectedTile.getElementsAmount(); i++)
                    Map.getImageView(elements, q, r, i).setLayoutY(selectedTile.getElementsCoords().get(i).y - (selectedTile.getHeightOfTile()-1) * Tile.HEX_VERTICAL_OFFSET);

                break;

            case TILEGRASS_FULL:
                selection.setVisible(true);



                selectedTile.pushTile("tileGrass_full");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileGrass_full"));selection.setLayoutX(p.x);

                break;
//
            case TILEMAGIC:
                selection.setVisible(true);



                selectedTile.pushTile("tileMagic");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileMagic"));selection.setLayoutX(p.x);

                break;
//
            case TILEMAGIC_FULL:
                selection.setVisible(true);



                selectedTile.pushTile("tileMagic_full");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileMagic_full"));selection.setLayoutX(p.x);


                break;

            case TILEDIRT:
                selection.setVisible(true);


                selectedTile.pushTile("tileDirt");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileDirt"));selection.setLayoutX(p.x);

                break;

            case TILEDIRT_FULL:
                selection.setVisible(true);



                selectedTile.pushTile("tileDirt_full");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileDirt_full"));selection.setLayoutX(p.x);

                break;

            case TILEWATER:
                selection.setVisible(true);



                selectedTile.pushTile("tileWater");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileWater"));selection.setLayoutX(p.x);

                break;

            case TILEWATER_FULL:
                selection.setVisible(true);



                selectedTile.pushTile("tileWater_full");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileWater_full"));selection.setLayoutX(p.x);

                break;

            case TILESTONE:
                selection.setVisible(true);



                selectedTile.pushTile("tileStone");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileStone"));selection.setLayoutX(p.x);

                break;

            case TILESTONE_FULL:
                selection.setVisible(true);



                selectedTile.pushTile("tileStone_full");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileStone_full"));selection.setLayoutX(p.x);

                break;

            case TILEAUTUMN:
                selection.setVisible(true);



                selectedTile.pushTile("tileAutumn");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileAutumn"));selection.setLayoutX(p.x);

                break;

            case TILEAUTUMN_FULL:
                selection.setVisible(true);



                selectedTile.pushTile("tileAutumn_full");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileAutumn_full"));selection.setLayoutX(p.x);

                break;

            case TILELAVA:
                selection.setVisible(true);



                selectedTile.pushTile("tileLava");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileLava"));selection.setLayoutX(p.x);

                break;

            case TILELAVA_FULL:
                selection.setVisible(true);


                selectedTile.pushTile("tileLava_full");
                Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile() - 1).setImage(provider.getImage("tileLava_full"));
                selection.setLayoutX(p.x);

                break;

            case TREEGREEN_MID:
                selection.setVisible(true);



                if (selectedTile.getType() == "empty")
                    break;
                ImageView iv = new ImageView(provider.getImage("treeGreen_mid"));
                int height = Map.getTile(tiles, selectedScreenTile.q, selectedScreenTile.r).getHeightOfTile() - 1;
                Point treesOrigin = new Point( x - iv.getImage().getWidth()/2, y - iv.getImage().getHeight() * 0.9);

                Point treesIVOrigin = new Point( x - iv.getImage().getWidth()/2, y - iv.getImage().getHeight() * 0.9 - height * Tile.HEX_VERTICAL_OFFSET);

                selectedTile.pushElement("treeGreen_mid", treesOrigin);
                Map.pushElementView(elements, iv, q, r, treesIVOrigin);
                break;

        }
        int h = Map.getTile(tiles, selectedScreenTile.q, selectedScreenTile.r).getHeightOfTile() - 1;
        selection.setLayoutX(p.x);
        selection.setLayoutY(p.y - (h > 0 ? Tile.HEX_VERTICAL_OFFSET * h : 0));

    }

    public void rightClicked(INSTRUMENT instrument, double x, double y, boolean blocked) {
        selection.setVisible(true);
        if (blocked)
            return;
        Hex selectedScreenTile = hexLayout.pixelToHex(new Point(x, y)).hexRound();
        if (outOfBounds(selectedScreenTile))
            return;
        int q = selectedScreenTile.q;
        int r = selectedScreenTile.r;
        Tile selectedTile = Map.getTile(tiles, q, r);
        Logger.info("rc. "+selectedTile.getHeightOfTile());

        if (selectedTile.getElementsAmount() == 0) {
            selectedTile.popTile();
            Map.getImageView(imageViews, q, r, selectedTile.getHeightOfTile()).setImage(provider.getImage("empty"));
        }else {
            selectedTile.removeElements();
            for (int i = 0; i < Tile.MAX_ELEMENTS_AMOUNT; i++)
                Map.getImageView(elements, q, r, i).setImage(null);
        }
        Point p = offsetLayout.hexToPixel(selectedScreenTile);
        int h = Map.getTile(tiles, selectedTile.q, selectedTile.r).getHeightOfTile() - 1;
        selection.setLayoutX(p.x);
        selection.setLayoutY(p.y - (h > 0 ? Tile.HEX_VERTICAL_OFFSET * h : 0));
        Logger.info("rc.. "+selectedTile.getHeightOfTile());

    }

    private boolean outOfBounds(Hex h){
        Point p = Map.hexCoordsToArrIndices(h.q, h.r);
        return 1 > p.x || p.x >= Map.SCR_TILEHEIGHT-2 || 2 > p.y || p.y >= Map.SCR_TILEWIDTH-2;
    }

}