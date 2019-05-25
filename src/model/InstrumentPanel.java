package model;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class InstrumentPanel extends VBox {
    private List<Node> instrumentList;
    private Node selectedInstrument;
    private static final String BACKGROUND_STYLE = "-fx-background-color: rgba(215, 215, 215, 0.85)";

    public InstrumentPanel() {
        instrumentList = new ArrayList<>();
        selectedInstrument = null;
        this.setVisible(true);
        this.setStyle(BACKGROUND_STYLE);
    }


}
