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

    TILESAND("/view/resources/Tiles/tileSand.png"),

    TILESAND_FULL("/view/resources/Tiles/tileSand_full.png"),

    TILEWATER("/view/resources/Tiles/tileWater.png"),

    TILEWATER_FULL("/view/resources/Tiles/tileWater_full.png"),

    TILESTONE("/view/resources/Tiles/tileStone.png"),

    TILESTONE_FULL("/view/resources/Tiles/tileStone_full.png"),

    TILEAUTUMN("/view/resources/Tiles/tileAutumn.png"),

    TILEAUTUMN_FULL("/view/resources/Tiles/tileAutumn_full.png"),

    TILELAVA("/view/resources/Tiles/tileLava.png"),

    TILELAVA_FULL("/view/resources/Tiles/tileLava_full.png"),

    TILESNOW("/view/resources/Tiles/tileSnow.png"),

    TILESNOW_FULL("/view/resources/Tiles/tileSnow_full.png"),



    TREEGREEN_MID("/view/resources/Tiles/treeGreen_mid.png"),

    TREEGREEN_LOW("/view/resources/Tiles/treeGreen_low.png"),

    TREEGREEN_HIGH("/view/resources/Tiles/treeGreen_high.png"),

    TREEBLUE_LOW("/view/resources/Tiles/treeBlue_low.png"),

    TREEBLUE_MID("/view/resources/Tiles/treeBlue_mid.png"),

    TREEBLUE_HIGH("/view/resources/Tiles/treeBlue_high.png"),

    PINEGREEN_MID("/view/resources/Tiles/pineGreen_mid.png"),

    PINEGREEN_LOW("/view/resources/Tiles/pineGreen_low.png"),

    PINEGREEN_HIGH("/view/resources/Tiles/pineGreen_high.png"),

    PINEBLUE_LOW("/view/resources/Tiles/pineBlue_low.png"),

    PINEBLUE_MID("/view/resources/Tiles/pineBlue_mid.png"),

    PINEBLUE_HIGH("/view/resources/Tiles/pineBlue_high.png"),


    FLOWERELLOW("/view/resources/Tiles/flowerYellow.png"),

    FLOWERWHITE("/view/resources/Tiles/flowerWhite.png"),

    FLOWERRED("/view/resources/Tiles/flowerRed.png"),

    FLOWERGREEN("/view/resources/Tiles/flowerGreen.png"),

    FLOWERBLUE("/view/resources/Tiles/flowerBlue.png"),

    BUSHSNOW("/view/resources/Tiles/bushSnow.png"),

    BUSHSAND("/view/resources/Tiles/bushSand.png"),

    BUSHMAGIC("/view/resources/Tiles/bushMagic.png"),

    BUSHGRASS("/view/resources/Tiles/bushGrass.png"),

    BUSHDIRT("/view/resources/Tiles/bushDirt.png"),

    BUSHAUTUMN("/view/resources/Tiles/bushAutumn.png"),



    ERASER("model/resources/Editing-Eraser-icon-small.png");




    private String urlInstrument;

    INSTRUMENT(String urlInstrument) {
        this.urlInstrument = urlInstrument;
    }

    public String getUrlInstrument() {
        return this.urlInstrument;
    }
}
