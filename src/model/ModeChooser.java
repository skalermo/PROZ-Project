package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;



public class ModeChooser extends VBox {

    private ImageView circleImage;
    private ImageView modeImage;

    private String circleNotChosen = "view/resources/modeChoser/grey_circle.png";
    private String circleChosen = "view/resources/modeChoser/chosen_circle.png";

    private MODE mode;

    private boolean isCircleChosen;

    public ModeChooser(MODE mode) {

        circleImage = new ImageView(circleNotChosen);
        modeImage = new ImageView(mode.getUrlMode());
        this.mode = mode;
        isCircleChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(50);

        this.getChildren().add(circleImage);
        this.getChildren().add(modeImage);

    }

    public MODE getMode() {
        return mode;
    }

    public boolean getIsCircleChosen() {
        return isCircleChosen;
    }

    public void setIsCircleChosen(boolean isCircleChosen) {
        this.isCircleChosen = isCircleChosen;
        String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
        circleImage.setImage(new Image(imageToSet));
    }

}
