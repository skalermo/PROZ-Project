package engine;

import javafx.scene.image.ImageView;
import view.ImageProvider;


public class HexTileImage extends Tile{
    private ImageView imageView;

    public HexTileImage(int q, int r) {
        super(q, r);
    }

    public HexTileImage(Hex h){
        super(h);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void createImageView(Layout layout, ImageProvider provider){
        imageView = new ImageView();
        imageView.setImage(provider.getImage(getType()));
        Point p = layout.hexToPixel(this);
        imageView.setLayoutX(p.x);
        imageView.setLayoutY(p.y);
    }



}
