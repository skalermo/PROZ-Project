package view;

import application.IOManager;
import engine.MapEditor;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class MapEditorViewManager {

    private AnchorPane editorPane;
    private Scene editorScene;
    private Stage editorStage;
    private IOManager ioManager;

    private static final int GAME_SCR_WIDTH = 1920;
    private static final int GAME_SCR_HEIGHT = 1080;

    private static final int OPTIONS_BUTTONS_START_X = 155;
    private static final int OPTIONS_BUTTONS_START_Y = 40;

    private Stage menuStage;
    private MapEditor editor;

    private GameMenuSubScene optionsSubScene;
    private GameMenuSubScene sceneToHide;
    private List<MenuButton> optionsButtons;
    private InstrumentPanel instrumentPanel;

    private AnimationTimer editorTimer;

    private boolean isMouseOnTopRightEdge = false;

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
        optionsSubScene.getPane().getChildren().addAll(optionsButtons);
    }

    private void createButtons() {

        createBackButton();
        createSaveMapButton();
        createLoadMapButton();
        createExitButton();
    }

    private void addButton(MenuButton button) {

        button.setLayoutX(OPTIONS_BUTTONS_START_X);
        button.setLayoutY(OPTIONS_BUTTONS_START_Y + optionsButtons.size() * 75);
        optionsButtons.add(button);

    }


    private void createBackButton() {

        MenuButton backButton = new MenuButton("Main menu");
        addButton(backButton);

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
        addButton(saveMapButton);

        saveMapButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                ioManager.saveMap(editorStage, editor.getTiles());
            }
        });

    }

    private void createLoadMapButton() {

        MenuButton loadMapButton = new MenuButton("Load");
        addButton(loadMapButton);

        loadMapButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                editor.setTiles(ioManager.loadMap(editorStage));
            }
        });

    }

    private void createExitButton() {

        MenuButton exitButton = new MenuButton("Exit");
        addButton(exitButton);

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
                    showSubScene(optionsSubScene);

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
                if (mouseEvent.getSceneX() + 5 > editorScene.getWidth() && mouseEvent.getSceneY() < editorScene.getHeight()/3)
                    isMouseOnTopRightEdge = true;
                if (mouseEvent.getSceneX() + 5 < editorScene.getWidth())
                    isMouseOnTopRightEdge = false;
                if (isMouseOnTopRightEdge && mouseEvent.getSceneY() > editorScene.getHeight()*2/3) {
                    showSubScene(optionsSubScene);
                    isMouseOnTopRightEdge = false;
                }

                editor.moved(instrumentPanel.getCurrentInstrumetStyle(), mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        });



        editorScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                    editor.leftClicked(instrumentPanel.getCurrentInstrumetStyle(), mouseEvent.getSceneX(), mouseEvent.getSceneY());
                if (mouseEvent.getButton() == MouseButton.SECONDARY)
                    editor.rightClicked(instrumentPanel.getCurrentInstrumetStyle(), mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        });

        editorScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                    editor.leftClicked(instrumentPanel.getCurrentInstrumetStyle(), mouseEvent.getSceneX(), mouseEvent.getSceneY());
                else if (mouseEvent.getButton() == MouseButton.SECONDARY)
                    editor.rightClicked(instrumentPanel.getCurrentInstrumetStyle(), mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        });

    }


    private void initializeStage() {

        editorPane = new AnchorPane();
        editorScene = new Scene(editorPane, GAME_SCR_WIDTH, GAME_SCR_HEIGHT);
        editorStage = new Stage();
        editorStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
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
        editor.getSelectedTile().managedProperty().bind(editor.getSelectedTile().visibleProperty());
        createInstrumentPanel();
        createOptionsSubScene();

        //createSessionLoop();
        editorStage.show();
    }

    private void createInstrumentPanel() {
        instrumentPanel = new InstrumentPanel();
        instrumentPanel.setVisible(true);
        instrumentPanel.setLayoutX(10);
        instrumentPanel.setLayoutY(100);
        instrumentPanel.setPrefSize(46, 500);
        createTools();
        editorPane.getChildren().add(instrumentPanel);
    }

    private void createTools() {
        createSelectTool();
    }

    private void createSelectTool() {
        ExpandableInstrumentButton select = new ExpandableInstrumentButton(INSTRUMENT.SELECT);
        instrumentPanel.addInstrument(select);

        select.getCurrentInstrument().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                instrumentPanel.setSelectedInstrument(select);
            }
        });
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
