package engine;

import javafx.scene.image.ImageView;
import view.ImageProvider;

public class Character {
    private ImageView imageView;
    private int hp;
    private int movement;

    public void setH(Hex h) {
        this.h = h;
    }

    public Hex getH() {
        return h;
    }

    private Hex h;

    public Character() {
        imageView = new ImageView();
        hp = 1;
        movement = 1;
        h = new Hex(5, 5);
    }

    public void createImageView(Layout layout, ImageProvider provider){
        imageView = new ImageView();
        imageView.setImage(provider.getImage("alienBeige"));
        Point p = layout.hexToPixel(h);
        imageView.setLayoutX(p.x+15);
        imageView.setLayoutY(p.y-10);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
