package model;

public enum INSTRUMENT {
    NONE(""),

    SELECT("/model/resources/Path-selection-tool.png"),

    TILE("/view/resources/modeChoser/editorMod.png"),

    ERASER("");




    private String urlInstrument;

    INSTRUMENT(String urlInstrument) {
        this.urlInstrument = urlInstrument;
    }

    public String getUrlInstrument() {
        return this.urlInstrument;
    }
}
