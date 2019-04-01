package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class ViewManager {

    private static Logger log = LogManager.getRootLogger();

    private AnchorPane mainPane;
    private Scene startMenuScene;
    private Stage mainStage;
    private static final int SCR_WIDTH = 1024;
    private static final int SCR_HEIGHT = 768;

    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 150;


    private MenuSubScene modeChoserSubScene;
    private MenuSubScene helpSubScene;
    private MenuSubScene scoreSubScene;
    private MenuSubScene creditsSubScene;
    private MenuSubScene sceneToHide;


    List<MenuButton> startMenuButtons;

    List<ModeChoser> modesList;
    private MODE chosenMode;



    public ViewManager() {
        startMenuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        startMenuScene = new Scene(mainPane, SCR_WIDTH, SCR_HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(startMenuScene);

        createSubScenes();
        createButtons();
        createBackground();
    }

    private void showSubScene(MenuSubScene subScene) {

        if (subScene.equals(sceneToHide)) {
            subScene.moveSubScene();
            sceneToHide = null;
            return;
        }

        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScenes() {

        scoreSubScene = new MenuSubScene();
        helpSubScene = new MenuSubScene();
        creditsSubScene = new MenuSubScene();

        mainPane.getChildren().addAll(scoreSubScene, helpSubScene, creditsSubScene);

        createModeChoserSubScene();
    }

    private void createModeChoserSubScene() {

        modeChoserSubScene = new MenuSubScene();
        mainPane.getChildren().add(modeChoserSubScene);

        InfoLabel choseModeLabel = new InfoLabel("Chose the mode.");
        choseModeLabel.setLayoutX(170);
        choseModeLabel.setLayoutY(80);
        modeChoserSubScene.getPane().getChildren().add(choseModeLabel);
        modeChoserSubScene.getPane().getChildren().add(createModsToChose());
        modeChoserSubScene.getPane().getChildren().add(createButtonToStart());
    }

    private HBox createModsToChose() {

        HBox box = new HBox();
        box.setSpacing(30);
        modesList = new ArrayList<>();
        for (MODE mode : MODE.values()) {
            ModeChoser modeToChose = new ModeChoser(mode);
            modesList.add(modeToChose);
            box.getChildren().add(modeToChose);
            modeToChose.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (ModeChoser mode : modesList) {
                        mode.setIsCircleChosen(false);
                    }
                    modeToChose.setIsCircleChosen(true);
                    chosenMode = modeToChose.getMode();
                }
            });
        }
        box.setLayoutX(300 - (75 * 2));
        box.setLayoutY(150);
        return box;
    }

    private MenuButton createButtonToStart() {

        MenuButton startButton = new MenuButton("Start.");
        startButton.setLayoutX(350);
        startButton.setLayoutY(380);
        return startButton;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(MenuButton button) {

        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + startMenuButtons.size() * 100);
        startMenuButtons.add(button);
        mainPane.getChildren().add(button);

    }

    private void createButtons() {

        createNewGameButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createNewGameButton() {

        MenuButton newGameButton = new MenuButton("New game");
        addMenuButton(newGameButton);

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSubScene(modeChoserSubScene);
            }
        });
    }

    private void createScoresButton() {

        MenuButton scoresButton = new MenuButton("Scores");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSubScene(scoreSubScene);
            }
        });
    }

    private void createHelpButton() {

        MenuButton helpButton = new MenuButton("Help");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton() {

        MenuButton creditsButton = new MenuButton("Credits");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton() {

        MenuButton exitButton = new MenuButton("Exit");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainStage.close();
            }
        });


    }

    private void createBackground() {
        Image backgroundImage = new Image("/view/resources/shot.png", 1024, 768, false, true);
        BackgroundImage background  = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }
}
