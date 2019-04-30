package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.MODE;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.text.html.ImageView;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;

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
                String msg =
                        "(x: "       + mouseEvent.getX()      + ", y: "       + mouseEvent.getY()       + ") -- " +
                                "(sceneX: "  + mouseEvent.getSceneX() + ", sceneY: "  + mouseEvent.getSceneY()  + ") -- " +
                                "(screenX: " + mouseEvent.getScreenX()+ ", screenY: " + mouseEvent.getScreenY() + ")";
                log.info(msg);
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
        createGameLoop();
        gameStage.show();
    }

    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {

            }
        };

        gameTimer.start();
    }




}

