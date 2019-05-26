package model;

import java.util.List;

public class ExpandableInstrumentButton {
    private List<InstrumentButton> relatedInstruments;
    private InstrumentButton currentInstrument;

    public ExpandableInstrumentButton(INSTRUMENT instrument) {
        currentInstrument = new InstrumentButton(instrument);
    }

    public InstrumentButton getCurrentInstrument() {
        return currentInstrument;
    }

    public INSTRUMENT getCurrentInstrumentStyle() {
        return currentInstrument.getInstrumentStyle();
    }
}

