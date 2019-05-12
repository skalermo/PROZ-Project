package model;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class GameMenuSubScene extends MenuSubScene{
    public GameMenuSubScene() {
        super();
        setWidth(500);
        setHeight(350);

        isHidden = true;

        setLayoutX(1920);
        setLayoutY(365);
    }

    public void moveSubScene() {

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(-1210);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }
}
