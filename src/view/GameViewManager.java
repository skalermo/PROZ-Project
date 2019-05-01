package view;

import engine.Hex;
import engine.Layout;
import engine.Map;
import engine.MapShape;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.MODE;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int GAME_WIDTH = 1440;
    private static final int GAME_HEIGHT = 960;
    private Stage menuStage;
    private ImageView mode;

    private AnimationTimer gameTimer;

    private static Logger log = LogManager.getRootLogger();

    public GameViewManager(){

        initializeStage();
        createKeyListeners();
        createMouseListeners();
    }

    private void createKeyListeners() {

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE)
                    gameStage.close();
            }
        });
    }

    private void createMouseListeners() {


        gameScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                String msg =
//                        "(x: "       + mouseEvent.getX()      + ", y: "       + mouseEvent.getY()       + ") -- " +
//                                "(sceneX: "  + mouseEvent.getSceneX() + ", sceneY: "  + mouseEvent.getSceneY()  + ") -- " +
//                                "(screenX: " + mouseEvent.getScreenX()+ ", screenY: " + mouseEvent.getScreenY() + ")";
//                log.info(msg);
            }
        });



        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                log.debug("Click!");
            }
        });

    }

    private void initializeStage() {

        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage menuStage, MODE chosenMode){
        this.menuStage = menuStage;
        this.menuStage.hide();
        drawTiles();
        gameStage.show();
    }

    private void drawTiles() {

        Layout layout = Map.getLayout(true, 37, 32, 0, 0);
        ImageProvider provider = ImageProvider.getInstance();

        Hex[][] map = Map.getMap(MapShape.PARALLELOGRAM);
        for (Hex[] hh: map)
            for (Hex h: hh)
            {
                if (h == null)
                    continue;
                ImageView iv = new ImageView();
                iv.setImage(provider.getImage("treeGreen_high"));
                //iv.setCache(true);
                iv.setX(layout.hexToPixel(h).x);
                iv.setY(layout.hexToPixel(h).y);
                gamePane.getChildren().add(iv);
            }



    }


}

