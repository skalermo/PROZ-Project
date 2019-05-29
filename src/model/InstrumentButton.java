package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class InstrumentButton extends Button {

    private INSTRUMENT instrumentStyle;
    private InstrumentPanel instrumentPanel;
    private ExpandableInstrumentButton eib;

    public InstrumentButton(INSTRUMENT instrument, InstrumentPanel panel, ExpandableInstrumentButton eib) {
        this.eib = eib;
        instrumentStyle = instrument;
        instrumentPanel = panel;
        setMinSize(32, 32);
        setPrefSize(32, 32);
        createBackgroundStyle(instrument);
        initializeButtonListeners();

    }

    public INSTRUMENT getInstrumentStyle() {
        return instrumentStyle;
    }

    public void setInstrumentStyle(INSTRUMENT instrumentStyle) {
        this.instrumentStyle = instrumentStyle;
        createBackgroundStyle(instrumentStyle);
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

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                instrumentPanel.setSelectedInstrument(instrumentStyle);
                eib.setCurrentInstrument(instrumentStyle);
                if (instrumentPanel.getCurrentRelPane() != null) {
                    instrumentPanel.getCurrentRelPane().setVisible(false);
                    instrumentPanel.setCurrentRelPane(null);
                }
            }
        });
    }


    private void createBackgroundStyle(INSTRUMENT instrument) {
        setStyle("-fx-background-image: url('" + instrument.getUrlInstrument() + "');" +
                "-fx-background-size: 32 32");
    }
}
