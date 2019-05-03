package engine;

import javafx.scene.image.ImageView;
import model.MODE;
import view.ImageProvider;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Character chara;
    private ImageProvider provider;
    private Layout layout;
    private Tile lastSelectedTile;
    private ImageView selection;
    private List<List<ImageView>> imageViews;
    private List<List<Tile>> tiles;

    public Game(ImageProvider provider, MODE chosenMode){

        this.provider = provider;
        init2dArrays();
        layout = new Layout(Layout.pointy, new Point(37.53, 31.5), new Point(-32, -32)); //37, 32
        Map.createMap(tiles, imageViews, MapShape.HEXAGON);

//        map.randomizeMap();
        createImageViews();
        chara = new Character();
        lastSelectedTile = null;
        selection = null;
    }

    private void init2dArrays(){
        imageViews = new ArrayList<>(Map.SCR_TILEWIDTH);
        for (int i = 0; i < Map.SCR_TILEWIDTH; i++){
            imageViews.add(new ArrayList<>(Map.SCR_TILEHEIGHT));
            for (int j = 0; j < Map.SCR_TILEHEIGHT; j++)
                imageViews.get(i).add(j, null);
        }
        tiles = new ArrayList<>(Map.SCR_TILEWIDTH);
        for (int i = 0; i < Map.SCR_TILEWIDTH; i++){
            tiles.add(new ArrayList<>(Map.SCR_TILEHEIGHT));
            for (int j = 0; j < Map.SCR_TILEHEIGHT; j++)
                tiles.get(i).add(j, new Tile(j, i));
        }
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }

    public List<List<ImageView>> getImageViews() {

        return imageViews;
    }

    public void setTiles(List<List<Tile>> tiles) {
        this.tiles = tiles;
        refreshImageViews(tiles);
    }

    private void refreshImageViews(List<List<Tile>> tiles) {
        for (int i = 0; i < tiles.size(); i++){
            for (int j = 0; j < tiles.get(i).size(); j++){
                if (imageViews.get(i).get(j) != null && tiles.get(i).get(j) != null)
                imageViews.get(i).get(j).setImage(provider.getImage(tiles.get(i).get(j).getType()));
            }
        }

    }

    private void createImageViews(){

        for (int i = 0; i < tiles.size(); i++){
            for (int j = 0; j < tiles.get(i).size(); j++){
                if (tiles.get(i).get(j) != null)
                    imageViews.get(i).set(j, createImageView(tiles.get(i).get(j)));
            }
        }
    }

    private ImageView createImageView(Tile t) {
        ImageView imageView = new ImageView();
        imageView.setImage(provider.getImage(t.getType()));
        Point p = layout.hexToPixel(t);
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

    public ArrayList<ImageView> getCharactersImageViews(){
        ArrayList<ImageView> imageViews = new ArrayList<>();
        chara.createImageView(layout, provider);
        imageViews.add(chara.getImageView());
        return imageViews;
    }

    public void select(double x, double y) {
        if (selection == null)
            return;
        Layout layout1 = new Layout(Layout.pointy, new Point(37.53, 31.5), new Point(-0, -0));
        Tile selectedTile = new Tile(layout1.pixelToHex(new Point(x, y)).hexRound());
        Point p = layout.hexToPixel(selectedTile);

//        if (selectedTile.r >= 0 && selectedTile.r < 30 && selectedTile.q >= 0 && selectedTile.q < 21 && map.getMap()[selectedTile.r][selectedTile.q] != null){
//            map.getMap()[selectedTile.r][selectedTile.q].setType("empty");
//            imageViews[selectedTile.r][selectedTile.q].setImage(provider.getImage("emtpy"));
//        }

        selection.setLayoutX(p.x);
        selection.setLayoutY(p.y);
    }

    public void click(double x, double y) {
        Layout layout1 = new Layout(Layout.pointy, new Point(37.53, 31.5), new Point(-0, -0));
        Hex selectedTile = layout1.pixelToHex(new Point(x, y)).hexRound();
        if (isAccessible(selectedTile) && chara.getH().distance(selectedTile) < 3)
        {
            chara.setH(selectedTile);
            Point p = layout.hexToPixel(selectedTile);
            chara.getImageView().setLayoutX(p.x+15);
            chara.getImageView().setLayoutY(p.y-10);
        }

    }

    private boolean isAccessible(Hex h){

        return between(h.r, 0, Map.SCR_TILEWIDTH) &&
                between(h.q, 0, Map.SCR_TILEHEIGHT) &&
            tiles.get(h.r).get(h.q).isAccessible();
    }

    private boolean between(int v, int lb, int hb){
        return lb <= v && v < hb;
    }


}
