package model;

import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class InstrumentPanel extends VBox {
    private List<ExpandableInstrumentButton> instrumentList;
    private INSTRUMENT currentInstrumentStyle;
    private FlowPane currentRelPane;
    private static final String BACKGROUND_STYLE = "-fx-background-color: rgba(215, 215, 215, 0.85)";

    public InstrumentPanel() {
        instrumentList = new ArrayList<>();
        currentInstrumentStyle = INSTRUMENT.SELECT;
        this.setVisible(true);
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setStyle(BACKGROUND_STYLE);
        this.setSpacing(10);
    }

    public FlowPane getCurrentRelPane() {
        return currentRelPane;
    }

    public void setCurrentRelPane(FlowPane currentRelPane) {
        this.currentRelPane = currentRelPane;
    }

    public void setSelectedInstrument(INSTRUMENT instrument) {
        currentInstrumentStyle = instrument;
    }

    public INSTRUMENT getCurrentInstrumentStyle() {
        return currentInstrumentStyle;
    }

    public void addInstrument(ExpandableInstrumentButton expandableInstrumentButton) {
        this.getChildren().add(expandableInstrumentButton.getCurrentInstrument());
        instrumentList.add(expandableInstrumentButton);
        expandableInstrumentButton.setFlowPanePosition(instrumentList.size());
    }

}
