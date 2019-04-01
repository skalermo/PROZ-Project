package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuButton extends Button {

    private final String FONT_PATH = "src/model/resources/some_font.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: rgba(200, 200, 200, 1)";
    private final String BUTTON_RELEASED_STYLE = "-fx-background-color: rgba(200, 200, 200, 1)";
    private final String BUTTON_ENTERED_STYLE = "-fx-background-color: rgba(200, 200, 200, 1)";
    private final String BUTTON_EXITED_STYLE = "-fx-background-color: rgba(200, 200, 200, 0.75)";

    public MenuButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_EXITED_STYLE);
        initializeButtonListeners();
    }

    private void setButtonFont() {

        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }

    }

    private void setButtonPressedStyle() {

        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonRealesedStyle() {

        setStyle(BUTTON_RELEASED_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void setButtonEnteredStyle() {

        setStyle(BUTTON_ENTERED_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY());
    }

    private void setButtonExitedStyle() {

        setStyle(BUTTON_EXITED_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY());
    }

    private void initializeButtonListeners() {

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonRealesedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setButtonEnteredStyle();
                setEffect(new DropShadow());
            }
        });


        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setButtonExitedStyle();
                setEffect(null);
            }
        });
    }

}
