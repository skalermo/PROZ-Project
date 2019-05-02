package engine;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import view.ImageProvider;

public class Game {

    private AnchorPane gamePane;
    private Map map;
    private ImageProvider provider;
    private Layout layout;
    private Tile lastSelectedTile;

    public Game(AnchorPane gamePane, ImageProvider provider){

        this.gamePane = gamePane;
        this.provider = provider;
        layout = Map.getLayout(true, 37, 32, 0, 0);
        map = new Map(MapShape.PARALLELOGRAM, 1, 4, 2, 7);
        lastSelectedTile = null;
        drawTiles();

    }

    private void drawTiles() {

        for (Tile[] tt: map.getMap())
            for (Tile t: tt)
            {
                if (t == null)
                    continue;
                ImageView iv = new ImageView();
                iv.setImage(provider.getImage(t.getType()));
                //iv.setCache(true);
                iv.setX(layout.hexToPixel(t).x);
                iv.setY(layout.hexToPixel(t).y);
                gamePane.getChildren().add(iv);
                if (t.isSelected()) {
                    iv = new ImageView();
                    iv.setImage(provider.getImage("selectedTile"));
                    iv.setX(layout.hexToPixel(t).x);
                    iv.setY(layout.hexToPixel(t).y);
                    gamePane.getChildren().add(iv);
                }
            }

//        HashMap<Point, Hex> map = Map.getMap((MapShape.PARALLELOGRAM));
//        for (HashMap.Entry<Point, Hex> entry : map.entrySet()) {
////            System.out.println(entry.getKey() + "/" + entry.getValue());
//            ImageView iv = new ImageView();
//            iv.setImage(provider.getImage("tileGrass"));
//            //iv.setCache(true);
//            iv.setX(layout.hexToPixel(entry.getValue()).x);
//            iv.setY(layout.hexToPixel(entry.getValue()).y);
//            gamePane.getChildren().add(iv);



    }

    public void drawSelectedTile(double x, double y){
        if (lastSelectedTile != null)
            lastSelectedTile.changeSelection();
        Tile selectedTile = new Tile(layout.pixelToHex(new Point(x, y)).hexRound());
        selectedTile.changeSelection();
        lastSelectedTile = selectedTile;
    }

}
