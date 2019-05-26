package model;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class InstrumentPanel extends VBox {
    private List<ExpandableInstrumentButton> instrumentList;
    private ExpandableInstrumentButton selectedInstrument;
    private INSTRUMENT currentInstrumentStyle;
    private static final String BACKGROUND_STYLE = "-fx-background-color: rgba(215, 215, 215, 0.85)";

    public InstrumentPanel() {
        instrumentList = new ArrayList<>();
        selectedInstrument = null;
        currentInstrumentStyle = INSTRUMENT.NONE;
        this.setVisible(true);
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setStyle(BACKGROUND_STYLE);
    }

    public ExpandableInstrumentButton getSelectedInstrument() {
        return selectedInstrument;
    }

    public void setSelectedInstrument(ExpandableInstrumentButton selectedInstrument) {
        this.selectedInstrument = selectedInstrument;
        currentInstrumentStyle = selectedInstrument.getCurrentInstrumentStyle();
    }

    public INSTRUMENT getCurrentInstrumetStyle() {
        return currentInstrumentStyle;
    }

    public void addInstrument(ExpandableInstrumentButton expandableInstrumentButton) {
        this.getChildren().add(expandableInstrumentButton.getCurrentInstrument());
        instrumentList.add(expandableInstrumentButton);
    }

}
