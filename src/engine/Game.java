package engine;

import javafx.scene.image.ImageView;
import model.MODE;
import view.ImageProvider;

import java.util.ArrayList;

public class Game {

    private Map map;
    private Character chara;
    private ImageProvider provider;
    private Layout layout;
    private Tile lastSelectedTile;
    private ImageView selection;
    private ImageView[][] imageViews;

    public Game(ImageProvider provider, MODE chosenMode){
        this.provider = provider;
        imageViews = new ImageView[Map.SCR_TILEWIDTH][Map.SCR_TILEHEIGHT];
        layout = new Layout(Layout.pointy, new Point(37.53, 31.5), new Point(-32, -32)); //37, 32
//        map = new Map(MapShape.PARALLELOGRAM, 1, 4, 2, 7);
        map = new Map(MapShape.HEXAGON, 10);

//        map = new Map(MapShape.RECTANGLE, 16, 10);
        chara = new Character();
        map.randomizeMap();
        createImageViews();


        lastSelectedTile = null;
        selection = null;
    }

    public Map getMap(){
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
        refreshImageViews(map);
    }

    private void refreshImageViews(Map map) {
        for (Tile[] tt: map.getMap())
            for (Tile t: tt){
                if (t != null)
                    imageViews[t.r][t.q].setImage(provider.getImage(t.getType()));
            }
    }

    public ImageView[][] getTilesImageViews() {

        return imageViews;
    }

    private void createImageViews(){

        for (Tile[] tt: map.getMap())
            for (Tile t: tt)
            {
                if (t == null)
                    continue;
                imageViews[t.r][t.q] = createImageView(t);
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

        if (selectedTile.r >= 0 && selectedTile.r < 30 && selectedTile.q >= 0 && selectedTile.q < 21 && map.getMap()[selectedTile.r][selectedTile.q] != null){
            map.getMap()[selectedTile.r][selectedTile.q].setType("empty");
            imageViews[selectedTile.r][selectedTile.q].setImage(provider.getImage("emtpy"));
        }

        selection.setLayoutX(p.x);
        selection.setLayoutY(p.y);
    }

    public void click(double x, double y) {
        Layout layout1 = new Layout(Layout.pointy, new Point(37.53, 31.5), new Point(-0, -0));
        Hex selectedTile = layout1.pixelToHex(new Point(x, y)).hexRound();
        if (isAccessible(selectedTile) && chara.getH().distance(selectedTile) == 1)
        {
            chara.setH(selectedTile);
            Point p = layout.hexToPixel(selectedTile);
            chara.getImageView().setLayoutX(p.x+15);
            chara.getImageView().setLayoutY(p.y-10);
        }

    }

    private boolean isAccessible(Hex h){

        if (h.r >= 0 && h.r < Map.SCR_TILEWIDTH && h.q >= 0 && h.q < Map.SCR_TILEHEIGHT && map.getMap()[h.r][h.q] != null){
            return map.getMap()[h.r][h.q].isAccessible();
        }
        return false;
    }
}
