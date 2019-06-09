package view;

import application.IOManager;
import engine.Map;
import engine.MapEditor;
import engine.Tile;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
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
    private boolean isDrawingBlocked = false;

    public MapEditorViewManager(){

        optionsButtons = new ArrayList<>();
        initializeStage();
        createKeyListeners();
        createMouseListeners();
        createButtons();

    }

    public void blockDrawing() {
        isDrawingBlocked = true;
    }

    public void allowDrawing() {
        isDrawingBlocked = false;
    }


    private void showSubScene(GameMenuSubScene subScene) {

        if (subScene.equals(sceneToHide)) {
            subScene.moveSubScene();
            sceneToHide = null;
            isDrawingBlocked = false;
            return;
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
        isDrawingBlocked = true;
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

                editor.moved(instrumentPanel.getCurrentInstrumentStyle(), mouseEvent.getSceneX(), mouseEvent.getSceneY(), isDrawingBlocked);
            }
        });



        editorScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                    editor.leftClicked(instrumentPanel.getCurrentInstrumentStyle(), mouseEvent.getSceneX(), mouseEvent.getSceneY(), isDrawingBlocked);
                if (mouseEvent.getButton() == MouseButton.SECONDARY)
                    editor.rightClicked(instrumentPanel.getCurrentInstrumentStyle(), mouseEvent.getSceneX(), mouseEvent.getSceneY(), isDrawingBlocked);
            }
        });

        editorScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                    editor.leftClicked(instrumentPanel.getCurrentInstrumentStyle(), mouseEvent.getSceneX(), mouseEvent.getSceneY(), isDrawingBlocked);
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
//        editor.getSelectedTile().managedProperty().bind(editor.getSelectedTile().visibleProperty());
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
        createEraserTool();
        createTileTool();
        createTreesTool();
    }

    private void createSelectTool() {
        ExpandableInstrumentButton select = new ExpandableInstrumentButton(INSTRUMENT.SELECT, null, instrumentPanel);
        instrumentPanel.addInstrument(select);

//        select.getCurrentInstrument().setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                instrumentPanel.setSelectedInstrument(select.getCurrentInstrumentStyle());
//            }
//        });
    }

    private void createEraserTool() {
        ExpandableInstrumentButton eraser = new ExpandableInstrumentButton(INSTRUMENT.ERASER, null, instrumentPanel);
        instrumentPanel.addInstrument(eraser);

//        eraser.getCurrentInstrument().setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                instrumentPanel.setSelectedInstrument(eraser.getCurrentInstrumentStyle());
//            }
//        });
    }

    private void createTileTool() {

        FlowPane relatedInstrumentsPane = createRelatedInstruments();
        ExpandableInstrumentButton tile = new ExpandableInstrumentButton(INSTRUMENT.TILEGRASS, relatedInstrumentsPane, instrumentPanel);
        instrumentPanel.addInstrument(tile);
        relatedInstrumentsPane.setLayoutX(instrumentPanel.getLayoutX());
        relatedInstrumentsPane.setLayoutY(instrumentPanel.getLayoutY());
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEGRASS, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEGRASS_FULL, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEMAGIC, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEMAGIC_FULL, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEDIRT, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEDIRT_FULL, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEWATER, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEWATER_FULL, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILESTONE, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILESTONE_FULL, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEAUTUMN, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILEAUTUMN_FULL, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILELAVA, instrumentPanel, tile));
        tile.addRelated(new InstrumentButton(INSTRUMENT.TILELAVA_FULL, instrumentPanel, tile));


//        tile.getCurrentInstrument().setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                instrumentPanel.setSelectedInstrument(tile.getCurrentInstrumentStyle());
//            }
//        });
    }

    private void createTreesTool() {
        FlowPane treesPane = createRelatedInstruments();
        ExpandableInstrumentButton tree = new ExpandableInstrumentButton(INSTRUMENT.TREEGREEN_MID, treesPane, instrumentPanel);
        instrumentPanel.addInstrument(tree);
        treesPane.setLayoutX(instrumentPanel.getLayoutX());
        treesPane.setLayoutY(instrumentPanel.getLayoutY());

    }

    private FlowPane createRelatedInstruments() {
        FlowPane relatedInstrumentsPane = new FlowPane();
        final String BACKGROUND_STYLE = "-fx-background-color: rgba(215, 215, 215, 0.85)";
        relatedInstrumentsPane.setStyle(BACKGROUND_STYLE);
        relatedInstrumentsPane.setHgap(5);
        relatedInstrumentsPane.setVgap(5);
        relatedInstrumentsPane.setAlignment(Pos.CENTER);
        relatedInstrumentsPane.setMinWidth(80);
        relatedInstrumentsPane.setVisible(false);
        editorPane.getChildren().add(relatedInstrumentsPane);
        return relatedInstrumentsPane;
    }



    private void addAllImageViews(MapEditor editor){
        for (int i = 0; i < Map.SCR_TILEHEIGHT; i++)
            for (int j = 0; j < Map.SCR_TILEWIDTH; j++) {
                for (int k = 0; k < Tile.MAX_TILE_HEIGHT; k++)
                    editorPane.getChildren().add(editor.getImageViews().get(i).get(j).get(k));
                for (int k = 0; k < Tile.MAX_ELEMENTS_AMOUNT; k++)
                    editorPane.getChildren().add(editor.getElements().get(i).get(j).get(k));
            }


//        for (List<List<ImageView>> ivvv: editor.getImageViews())
//            for (List<ImageView> ivv: ivvv)
//                for (ImageView iv: ivv)
//                    editorPane.getChildren().add(iv);
//
//        for (List<List<ImageView>> ivvv: editor.getElements())
//            for (List<ImageView> ivv: ivvv)
//                for (ImageView iv: ivv)
//                    editorPane.getChildren().add(iv);
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
