package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MenuSubScene extends SubScene {

//    private static final String FONT_PATH;
    protected static final String BACKGROUND_IMAGE = "model/resources/subSceneImage.png";
    private static final String BACKGROUND_STYLE = "-fx-background-color: rgba(215, 215, 215, 0.85)";


    protected boolean isHidden;

    public MenuSubScene() {
        super(new AnchorPane(), 700, 500);
        prefWidth(700);
        prefHeight(500);

        AnchorPane root = (AnchorPane) this.getRoot();

        root.setStyle(BACKGROUND_STYLE);

        isHidden = true;

        setLayoutX(1024);
        setLayoutY(130);
    }

    public void moveSubScene() {

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(-744);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}
