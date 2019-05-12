package model;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class GameMenuSubScene extends MenuSubScene{
    public GameMenuSubScene() {
        super();
        prefWidth(700);
        prefHeight(500);

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
