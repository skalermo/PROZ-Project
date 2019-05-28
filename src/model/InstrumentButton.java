package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class InstrumentButton extends Button {

    private INSTRUMENT instrumentStyle;

    public InstrumentButton(INSTRUMENT instrument) {
        instrumentStyle = instrument;
        setMinSize(32, 32);
        setPrefSize(32, 32);
        createBackgroundStyle(instrument);
        initializeButtonListeners();

    }

    public INSTRUMENT getInstrumentStyle() {
        return instrumentStyle;
    }

    private void initializeButtonListeners() {


        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
            }
        });


        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }


    private void createBackgroundStyle(INSTRUMENT instrument) {
        setStyle("-fx-background-image: url('" + instrument.getUrlInstrument() + "')");
    }
}
