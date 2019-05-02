package engine;

import javafx.scene.image.ImageView;


public class HexTileImage {
    private ImageView imageView;
    private Tile tile;

    public HexTileImage(Tile tile) {
        this.tile = tile;
        imageView = new ImageView();
    }
}
