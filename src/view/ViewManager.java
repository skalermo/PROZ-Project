package view;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.MenuButton;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class ViewManager {

    private static Logger log = LogManager.getRootLogger();

    private AnchorPane mainPane;
    private Scene startMenuScene;

    private AnchorPane gamePane;
    private Scene gameScene;

    private Stage mainStage;
    private static final int SCR_WIDTH = 1200;
    private static final int SCR_HEIGHT = 800;

    private static final int MENU_BUTTONS_START_X = (SCR_WIDTH-190)/2;
    private static final int MENU_BUTTONS_START_Y = 150;

    List<MenuButton> startMenuButtons;



    public ViewManager() {
        startMenuButtons = new ArrayList<>();
        mainPane = new AnchorPane();

        startMenuScene = new Scene(mainPane, SCR_WIDTH, SCR_HEIGHT);

        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, SCR_WIDTH, SCR_HEIGHT);


        mainStage = new Stage();
        mainStage.setScene(startMenuScene);

        createButtons();
//        createBackground();
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

        newGameButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mainStage.setScene(gameScene);
            }
        });
    }

    private void createScoresButton() {

        MenuButton scoresButton = new MenuButton("Scores");
        addMenuButton(scoresButton);
    }

    private void createHelpButton() {

        MenuButton helpButton = new MenuButton("Help");
        addMenuButton(helpButton);
    }

    private void createCreditsButton() {

        MenuButton creditsButton = new MenuButton("Credits");
        addMenuButton(creditsButton);
    }

    private void createExitButton() {

        MenuButton exitButton = new MenuButton("Exit");
        addMenuButton(exitButton);

        exitButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mainStage.close();
            }
        });


    }

    private void createBackground() {
        Image backgroundImage = new Image("/view/resources/menubgfull.png", 1920, 1080, false, true);
        BackgroundImage background  = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }
}
