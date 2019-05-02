package view;

import engine.*;
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

    private static final int GAME_WIDTH = 1920;
    private static final int GAME_HEIGHT = 1080;
    private Stage menuStage;
    private Game game;
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
                game.drawSelectedTile(mouseEvent.getX(), mouseEvent.getY());
            }
        });



        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                log.info("Click!");
            }
        });

    }


    private void initializeStage() {

        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setFullScreen(true);
    }

    public void createNewGame(Stage menuStage, MODE chosenMode){
        this.menuStage = menuStage;
        this.menuStage.hide();
//        drawTiles();
        ImageProvider provider = ImageProvider.getInstance();
        game = new Game(gamePane, provider);
        gameStage.show();
    }




}

