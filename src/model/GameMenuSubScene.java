package model;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class GameMenuSubScene extends MenuSubScene{
    public GameMenuSubScene() {
        super();
        prefWidth(700);
        prefHeight(500);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 700, 500, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        isHidden = true;

        setLayoutX(2000);
        setLayoutY(290);
    }

    public void moveSubScene() {

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(-1390);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }
}
