package model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class ExpandableInstrumentButton {
    private List<InstrumentButton> relatedInstruments;
    private InstrumentButton currentInstrument;
    private FlowPane paneWithInstruments;
    private InstrumentPanel instrumentPanel;

    private boolean isHidden;

    public ExpandableInstrumentButton(INSTRUMENT instrument, FlowPane flowPane, InstrumentPanel panel) {
        currentInstrument = new InstrumentButton(instrument, panel, this);
        paneWithInstruments = flowPane;
        instrumentPanel = panel;
        createListeners();
    }

    private void createListeners() {
        if (paneWithInstruments == null)
            return;
        currentInstrument.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (instrumentPanel.getCurrentRelPane() != null)
                    instrumentPanel.getCurrentRelPane().setVisible(false);
                instrumentPanel.setCurrentRelPane(paneWithInstruments);
                paneWithInstruments.setVisible(true);
            }
        });
    }

    public boolean isPaneWithInstrumentsHidden() {
        return isHidden;
    }

    public void setCurrentInstrument(INSTRUMENT instrument) {
        currentInstrument.setInstrumentStyle(instrument);
    }

    public void setFlowPanePosition(int i){
        if (paneWithInstruments == null)
            return;

        paneWithInstruments.setPrefSize((10+currentInstrument.getMinWidth()*(i%3)), (5+currentInstrument.getMinHeight())*(double)(i/3));
        paneWithInstruments.setTranslateX(2*currentInstrument.getMinWidth());
        paneWithInstruments.setTranslateY(i*currentInstrument.getMinHeight());
    }


    public void addRelated(InstrumentButton instrumentButton) {
        if (paneWithInstruments == null)
            return;
        if (relatedInstruments == null) {
            relatedInstruments = new ArrayList<>();
        }
        paneWithInstruments.getChildren().add(instrumentButton);
        relatedInstruments.add(instrumentButton);
    }

    public void addAllRelated(List<InstrumentButton> instrumentButtons) {
        if (paneWithInstruments == null)
            return;
        if (relatedInstruments == null) {
            relatedInstruments = new ArrayList<>();
        }

        paneWithInstruments.getChildren().addAll(instrumentButtons);
        relatedInstruments.addAll(instrumentButtons);

    }

    public InstrumentButton getCurrentInstrument() {
        return currentInstrument;
    }

    public INSTRUMENT getCurrentInstrumentStyle() {
        return currentInstrument.getInstrumentStyle();
    }
}

