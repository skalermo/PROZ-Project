package engine;

import javafx.scene.image.ImageView;
import view.ImageProvider;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Character chara;
    private ImageProvider provider;
    private Layout offsetLayout;
    private Layout hexLayout;
    private Tile lastSelectedTile;
    private ImageView selection;
    private List<List<List<ImageView>>> imageViews;
    private List<List<Tile>> tiles;

    public Game(ImageProvider provider){

        this.provider = provider;
        chara = new Character();
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
        for (int i = 0; i < Map.SCR_TILEHEIGHT; i++) {
            tiles.add(new ArrayList<>(Map.SCR_TILEWIDTH));
            imageViews.add(new ArrayList<>(Map.SCR_TILEWIDTH));
            for (int j = 0; j < Map.SCR_TILEWIDTH; j++){
                Tile t = Map.arrIndicesToTile(i, j);
                tiles.get(i).add(j, t);
                imageViews.get(i).add(j, createImageViewColumn(t));
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

    public List<List<Tile>> getTiles() {
        return tiles;
    }

    public List<List<List<ImageView>>> getImageViews() {

        return imageViews;
    }

    public void setTiles(List<List<Tile>> tiles) {
        this.tiles = tiles;
        refreshImageViews(tiles);
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
            }
        }

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

    public ArrayList<ImageView> getCharactersImageViews(){
        ArrayList<ImageView> imageViews = new ArrayList<>();
        chara.createImageView(hexLayout, provider);
        imageViews.add(chara.getImageView());
        return imageViews;
    }

    public void select(double x, double y) {
        if (selection == null)
            return;
        Tile selectedTile = new Tile(hexLayout.pixelToHex(new Point(x, y)).hexRound());
        Point p = offsetLayout.hexToPixel(selectedTile);

        selection.setLayoutX(p.x);
        selection.setLayoutY(p.y);
    }

    public void click(double x, double y) {
        Hex selectedHex = hexLayout.pixelToHex(new Point(x, y)).hexRound();
        if (chara.getH().distance(selectedHex) < 3)
        {
            chara.setH(selectedHex);
            Point p = offsetLayout.hexToPixel(selectedHex);
            chara.getImageView().setLayoutX(p.x+15);
            chara.getImageView().setLayoutY(p.y-10);
        }

    }

    private boolean isAccessible(Hex h){

        return notOutOfBounds(h) &&
            tiles.get(h.r).get(h.q).isAccessible();
    }

    private boolean notOutOfBounds(Hex h){

        return 0 <= h.r && h.r < Map.SCR_TILEWIDTH && 0 <= h.q && h.q < Map.SCR_TILEHEIGHT;
    }


}
