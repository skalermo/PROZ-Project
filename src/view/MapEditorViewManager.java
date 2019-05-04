package view;

import application.IOManager;
import engine.MapEditor;
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

public class MapEditorViewManager {

    private AnchorPane editorPane;
    private Scene editorScene;
    private Stage editorStage;
    private IOManager ioManager;

    private static final int GAME_SCR_WIDTH = 1920;
    private static final int GAME_SCR_HEIGHT = 1080;

    private static final int OPTIONS_BUTTONS_START_X = 255;
    private static final int OPTIONS_BUTTONS_START_Y = 113;

    private Stage menuStage;
    private MapEditor editor;

    private GameMenuSubScene optionsSubScene;
    private GameMenuSubScene sceneToHide;
    private List<MenuButton> optionsButtons;

    private AnimationTimer editorTimer;

    private static Logger log = LogManager.getRootLogger();

    public MapEditorViewManager(){

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
        editorPane.getChildren().add(optionsSubScene);

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
                editorStage.hide();
                menuStage.show();
                editorStage.close();
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

                ioManager.saveMap(editorStage, editor.getTiles());
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
                editor.setTiles(ioManager.loadMap(editorStage));
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
                editorStage.close();
            }
        });

    }


    private void createKeyListeners() {

        editorScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE)
                    editorStage.close();

                // should be changed
                if (keyEvent.getCode() == KeyCode.K)
                    showSubScene(optionsSubScene);
            }
        });
    }



    private void createMouseListeners() {


        editorScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editor.moved(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        });



        editorScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editor.clicked(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        });

        editorScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editor.dragged(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        });

    }


    private void initializeStage() {

        editorPane = new AnchorPane();
        editorScene = new Scene(editorPane, GAME_SCR_WIDTH, GAME_SCR_HEIGHT);
        editorStage = new Stage();
        editorStage.setScene(editorScene);
        editorStage.setFullScreen(true);
    }

    void createNewSession(Stage menuStage){
        this.menuStage = menuStage;
        this.menuStage.hide();

        ImageProvider provider = ImageProvider.getInstance();
        ioManager = new IOManager();

        editor = new MapEditor(provider);
        addAllImageViews(editor);

        editorPane.getChildren().add(editor.getSelectedTile());
        createOptionsSubScene();

        createSessionLoop();
        editorStage.show();
    }

    private void addAllImageViews(MapEditor editor){
        for (List<ImageView> imageViews: editor.getImageViews())
            for (ImageView imageView: imageViews)

                    editorPane.getChildren().add(imageView);
    }

    private void createSessionLoop(){

        editorTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {

            }
        };

        editorTimer.start();
    }
}
