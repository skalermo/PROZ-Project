package model;

public enum INSTRUMENT {
    NONE(""),

    SELECT("/model/resources/Path-selection-tool.png"),

    TILEGRASS("/view/resources/Tiles/tileGrass.png"),

    TILEGRASS_FULL("/view/resources/Tiles/tileGrass_full.png"),

    TILEMAGIC("/view/resources/Tiles/tileMagic.png"),

    TILEMAGIC_FULL("/view/resources/Tiles/tileMagic_full.png"),

    TILEDIRT("/view/resources/Tiles/tileDirt.png"),

    TILEDIRT_FULL("/view/resources/Tiles/tileDirt_full.png"),

    TILEWATER("/view/resources/Tiles/tileWater.png"),

    TILEWATER_FULL("/view/resources/Tiles/tileWater_full.png"),

    TILESTONE("/view/resources/Tiles/tileStone.png"),

    TILESTONE_FULL("/view/resources/Tiles/tileStone_full.png"),

    TILEAUTUMN("/view/resources/Tiles/tileAutumn.png"),

    TILEAUTUMN_FULL("/view/resources/Tiles/tileAutumn_full.png"),

    TILELAVA("/view/resources/Tiles/tileLava.png"),

    TILELAVA_FULL("/view/resources/Tiles/tileLava_full.png"),


    ERASER("model/resources/Editing-Eraser-icon-small.png");




    private String urlInstrument;

    INSTRUMENT(String urlInstrument) {
        this.urlInstrument = urlInstrument;
    }

    public String getUrlInstrument() {
        return this.urlInstrument;
    }
}
