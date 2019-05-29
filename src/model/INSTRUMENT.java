package model;

public enum INSTRUMENT {
    NONE(""),

    SELECT("/model/resources/Path-selection-tool.png"),

    TILEGRASS_TILE("/view/resources/Tiles/tileGrass_tile.png"),

    TILEGRASS("/view/resources/Tiles/tileGrass.png"),

    ERASER("model/resources/Editing-Eraser-icon-small.png");




    private String urlInstrument;

    INSTRUMENT(String urlInstrument) {
        this.urlInstrument = urlInstrument;
    }

    public String getUrlInstrument() {
        return this.urlInstrument;
    }
}
