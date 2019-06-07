package view;

import application.IOManager;
import engine.Game;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.GameMenuSubScene;
import model.InfoLabel;
import model.MenuButton;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private IOManager ioManager;

    private static final int GAME_SCR_WIDTH = 1920;
    private static final int GAME_SCR_HEIGHT = 1080;

    private static final int OPTIONS_BUTTONS_START_X = 255;
    private static final int OPTIONS_BUTTONS_START_Y = 113;

    private Stage menuStage;
    private Game game;

    private GameMenuSubScene optionsSubScene;
    private GameMenuSubScene sceneToHide;
    private List<MenuButton> optionsButtons;



    private AnimationTimer gameTimer;

    private static Logger log = LogManager.getRootLogger();

    public GameViewManager(){

        optionsButtons = new ArrayList<>();
        initializeStage();
        createKeyListeners();
        createMouseListeners();
        createButtons();

    }

    private void showSubScene(GameMenuSubScene subScene) {

        if (subScene.equals(sceneToHide)) {
            subScene.moveSubScene();
            sceneToHide = null;
            return;
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createOptionsSubScene() {

        optionsSubScene = new GameMenuSubScene();
        gamePane.getChildren().add(optionsSubScene);

        InfoLabel choseModeLabel = new InfoLabel("Options");
        choseModeLabel.setLayoutX(1000);
        choseModeLabel.setLayoutY(300);
        optionsSubScene.getPane().getChildren().add(choseModeLabel);
        optionsSubScene.getPane().getChildren().addAll(optionsButtons);
    }

    private void createButtons() {

        createBackButton();
        createSaveMapButton();
        createLoadMapButton();
        createExitButton();
    }



    private void createBackButton() {

        MenuButton backButton = new MenuButton("Main menu");
        backButton.setLayoutX(OPTIONS_BUTTONS_START_X);
        backButton.setLayoutY(OPTIONS_BUTTONS_START_Y + optionsButtons.size() * 75);
        optionsButtons.add(backButton);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStage.hide();
                menuStage.show();
                gameStage.close();
            }
        });

    }

    private void createSaveMapButton() {

        MenuButton saveMapButton = new MenuButton("Save");
        saveMapButton.setLayoutX(OPTIONS_BUTTONS_START_X);
        saveMapButton.setLayoutY(OPTIONS_BUTTONS_START_Y + optionsButtons.size() * 75);
        optionsButtons.add(saveMapButton);

        saveMapButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                ioManager.saveMap(gameStage, game.getTiles());
            }
        });

    }

    private void createLoadMapButton() {

        MenuButton loadMapButton = new MenuButton("Load");
        loadMapButton.setLayoutX(OPTIONS_BUTTONS_START_X);
        loadMapButton.setLayoutY(OPTIONS_BUTTONS_START_Y + optionsButtons.size() * 75);
        optionsButtons.add(loadMapButton);

        loadMapButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.setTiles(ioManager.loadMap(gameStage));
            }
        });

    }

    private void createExitButton() {

        MenuButton exitButton = new MenuButton("Exit");
        exitButton.setLayoutX(OPTIONS_BUTTONS_START_X);
        exitButton.setLayoutY(OPTIONS_BUTTONS_START_Y + optionsButtons.size() * 75);
        optionsButtons.add(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStage.close();
            }
        });

    }


    private void createKeyListeners() {

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE)
                    gameStage.close();

                // should be changed
                if (keyEvent.getCode() == KeyCode.K)
                    showSubScene(optionsSubScene);
            }
        });
    }



    private void createMouseListeners() {


        gameScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                game.select(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        });



        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                game.click(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        });

    }


    private void initializeStage() {

        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_SCR_WIDTH, GAME_SCR_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);

        gameStage.setFullScreen(true);
    }

    void createNewGame(Stage menuStage){
        this.menuStage = menuStage;
        this.menuStage.hide();

        ImageProvider provider = ImageProvider.getInstance();
        ioManager = new IOManager();

        game = new Game(provider);
        addAllImageViews(game);

        gamePane.getChildren().add(game.getSelectedTile());
        gamePane.getChildren().addAll(game.getCharactersImageViews());
        createOptionsSubScene();

        createGameLoop();
        gameStage.show();
    }

    private void addAllImageViews(Game game){
        for (List<List<ImageView>> ivvv: game.getImageViews())
            for (List<ImageView> ivv: ivvv)
                for (ImageView iv: ivv)
                    gamePane.getChildren().add(iv);
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

