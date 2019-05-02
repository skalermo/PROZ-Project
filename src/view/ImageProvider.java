package view;

import javafx.scene.image.Image;

import java.util.HashMap;


public class ImageProvider {

    private static HashMap<String, Image> images;
    private static ImageProvider provider = null;

    private ImageProvider(){

        images = new HashMap<>();
        loadImages();
    }

    private void loadImages(){

        images.put("alienBeige", new Image("/view/resources/Tiles/alienBeige.png", 40, 66, false, true));
        images.put("alienBlue", new Image("/view/resources/Tiles/alienBlue.png", 40, 66, false, true));
        images.put("alienGreen", new Image("/view/resources/Tiles/alienGreen.png", 40, 6, false, true));
        images.put("alienPink", new Image("/view/resources/Tiles/alienPink.png", 40, 66, false, true));
        images.put("alienYellow", new Image("/view/resources/Tiles/alienYellow.png", 40, 60, false, true));
        images.put("bushAutumn", new Image("/view/resources/Tiles/bushAutumn.png", 15, 20, false, true));
        images.put("bushDirt", new Image("/view/resources/Tiles/bushDirt.png", 15, 20, false, true));
        images.put("bushGrass", new Image("/view/resources/Tiles/bushGrass.png", 15, 20, false, true));
        images.put("bushMagic", new Image("/view/resources/Tiles/bushMagic.png", 15, 20, false, true));
        images.put("bushSand", new Image("/view/resources/Tiles/bushSand.png", 15, 20, false, true));
        images.put("bushSnow", new Image("/view/resources/Tiles/bushSnow.png", 15, 20, false, true));
        images.put("flowerBlue", new Image("/view/resources/Tiles/flowerBlue.png", 12, 11, false, true));
        images.put("flowerGreen", new Image("/view/resources/Tiles/flowerGreen.png", 12, 11, false, true));
        images.put("flowerRed", new Image("/view/resources/Tiles/flowerRed.png", 12, 11, false, true));
        images.put("flowerWhite", new Image("/view/resources/Tiles/flowerWhite.png", 12, 11, false, true));
        images.put("flowerYellow", new Image("/view/resources/Tiles/flowerYellow.png", 12, 11, false, true));
        images.put("hillAutumn", new Image("/view/resources/Tiles/hillAutumn.png", 33, 12, false, true));
        images.put("hillDirt", new Image("/view/resources/Tiles/hillDirt.png", 33, 12, false, true));
        images.put("hillGrass", new Image("/view/resources/Tiles/hillGrass.png", 33, 12, false, true));
        images.put("hillMagic", new Image("/view/resources/Tiles/hillMagic.png", 33, 12, false, true));
        images.put("hillSand", new Image("/view/resources/Tiles/hillSand.png", 33, 12, false, true));
        images.put("hillSnow", new Image("/view/resources/Tiles/hillSnow.png", 33, 12, false, true));
        images.put("pineAutumn_high", new Image("/view/resources/Tiles/pineAutumn_high.png", 30, 99, false, true));
        images.put("pineAutumn_low", new Image("/view/resources/Tiles/pineAutumn_low.png", 30, 79, false, true));
        images.put("pineAutumn_mid", new Image("/view/resources/Tiles/pineAutumn_mid.png", 30, 101, false, true));
        images.put("pineBlue_high", new Image("/view/resources/Tiles/pineBlue_high.png", 30, 99, false, true));
        images.put("pineBlue_low", new Image("/view/resources/Tiles/pineBlue_low.png", 30, 79, false, true));
        images.put("pineBlue_mid", new Image("/view/resources/Tiles/pineBlue_mid.png", 30, 101, false, true));
        images.put("pineGreen_high", new Image("/view/resources/Tiles/pineGreen_high.png", 30, 99, false, true));
        images.put("pineGreen_low", new Image("/view/resources/Tiles/tileGrass.png", 30, 79, false, true));
        images.put("pineGreen_mid", new Image("/view/resources/Tiles/pineGreen_mid.png", 30, 101, false, true));
        images.put("rockDirt_moss1", new Image("/view/resources/Tiles/rockDirt_moss1.png", 65, 91, false, true));
        images.put("rockDirt_moss2", new Image("/view/resources/Tiles/rockDirt_moss2.png", 65, 91, false, true));
        images.put("rockDirt_moss3", new Image("/view/resources/Tiles/rockDirt_moss3.png", 65, 91, false, true));
        images.put("rockDirt", new Image("/view/resources/Tiles/rockDirt.png", 65, 91, false, true));
        images.put("rockSnow_1", new Image("/view/resources/Tiles/rockSnow_1.png", 65, 91, false, true));
        images.put("rockSnow_2", new Image("/view/resources/Tiles/rockSnow_2.png", 65, 91, false, true));
        images.put("rockSnow_3", new Image("/view/resources/Tiles/rockSnow_3.png", 65, 91, false, true));
        images.put("rockStone_moss1", new Image("/view/resources/Tiles/rockStone_moss1.png", 91, 89, false, true));
        images.put("rockStone_moss2", new Image("/view/resources/Tiles/rockStone_moss2.png", 91, 89, false, true));
        images.put("rockStone_moss3", new Image("/view/resources/Tiles/rockStone_moss3.png", 91, 89, false, true));
        images.put("rockStone", new Image("/view/resources/Tiles/rockStone.png", 65, 91, false, true));
        images.put("smallRockDirt", new Image("/view/resources/Tiles/smallRockDirt.png", 29, 33, false, true));
        images.put("smallRockGrass", new Image("/view/resources/Tiles/smallRockGrass.png", 29, 33, false, true));
        images.put("smallRockSnow", new Image("/view/resources/Tiles/smallRockSnow.png", 29, 33, false, true));
        images.put("smallRockStone", new Image("/view/resources/Tiles/smallRockStone.png", 29, 33, false, true));
        images.put("tileAutumn_full", new Image("/view/resources/Tiles/tileAutumn_full.png", 65, 89, false, true));
        images.put("tileAutumn_tile", new Image("/view/resources/Tiles/tileAutumn_tile.png", 65, 89, false, true));
        images.put("tileAutumn", new Image("/view/resources/Tiles/tileAutumn.png", 65, 89, false, true));
        images.put("tileDirt_full", new Image("/view/resources/Tiles/tileDirt_full.png", 65, 89, false, true));
        images.put("tileDirt_tile", new Image("/view/resources/Tiles/tileDirt_tile.png", 65, 89, false, true));
        images.put("tileDirt", new Image("/view/resources/Tiles/tileDirt.png", 65, 89, false, true));
        images.put("tileGrass_full", new Image("/view/resources/Tiles/tileGrass_full.png", 65, 89, false, true));
        images.put("tileGrass_tile", new Image("/view/resources/Tiles/tileGrass_tile.png", 65, 89, false, true));
        images.put("tileGrass", new Image("/view/resources/Tiles/tileGrass.png", 65, 89, false, true));
        images.put("tileLava_full", new Image("/view/resources/Tiles/tileLava_full.png", 65, 89, false, true));
        images.put("tileLava_tile", new Image("/view/resources/Tiles/tileLava_tile.png", 65, 89, false, true));
        images.put("tileLava", new Image("/view/resources/Tiles/tileLava.png", 65, 89, false, true));
        images.put("tileMagic_full", new Image("/view/resources/Tiles/tileMagic_full.png", 65, 89, false, true));
        images.put("tileMagic_tile", new Image("/view/resources/Tiles/tileMagic_tile.png", 65, 89, false, true));
        images.put("tileMagic", new Image("/view/resources/Tiles/tileMagic.png", 65, 89, false, true));
        images.put("tileRock_full", new Image("/view/resources/Tiles/tileRock_full.png", 65, 89, false, true));
        images.put("tileRock_tile", new Image("/view/resources/Tiles/tileRock_tile.png", 65, 89, false, true));
        images.put("tileRock", new Image("/view/resources/Tiles/tileRock.png", 65, 89, false, true));
        images.put("tileSand_full", new Image("/view/resources/Tiles/tileSand_full.png", 65, 89, false, true));
        images.put("tileSand_tile", new Image("/view/resources/Tiles/tileSand_tile.png", 65, 89, false, true));
        images.put("tileSand", new Image("/view/resources/Tiles/tileSand.png", 65, 89, false, true));
        images.put("tileSelected", new Image("/view/resources/Tiles/tileSelected.png", 65, 89, false, true));
        images.put("tileSnow_full", new Image("/view/resources/Tiles/tileSnow_full.png", 65, 89, false, true));
        images.put("tileSnow_tile", new Image("/view/resources/Tiles/tileSnow_tile.png", 65, 89, false, true));
        images.put("tileSnow", new Image("/view/resources/Tiles/tileSnow.png", 65, 89, false, true));
        images.put("tileStone_bridge", new Image("/view/resources/Tiles/tileStone_bridge.png", 65, 89, false, true));
        images.put("tileStone_full", new Image("/view/resources/Tiles/tileStone_full.png", 65, 89, false, true));
        images.put("tileStone_tile", new Image("/view/resources/Tiles/tileStone_tile.png", 65, 89, false, true));
        images.put("tileStone", new Image("/view/resources/Tiles/tileStone.png", 65, 89, false, true));
        images.put("tileWater_full", new Image("/view/resources/Tiles/tileWater_full.png", 65, 89, false, true));
        images.put("tileWater_shadow", new Image("/view/resources/Tiles/tileWater_shadow.png", 65, 89, false, true));
        images.put("tileWater_tile", new Image("/view/resources/Tiles/tileWater_tile.png", 65, 89, false, true));
        images.put("tileWater", new Image("/view/resources/Tiles/tileWater.png", 65, 89, false, true));
        images.put("tileWood_bridge", new Image("/view/resources/Tiles/tileWood_bridge.png", 65, 89, false, true));
        images.put("treeAutumn_high", new Image("/view/resources/Tiles/treeAutumn_high.png", 27, 107, false, true));
        images.put("treeAutumn_low", new Image("/view/resources/Tiles/treeAutumn_low.png", 27, 87, false, true));
        images.put("treeAutumn_mid", new Image("/view/resources/Tiles/treeAutumn_mid.png", 27, 107, false, true));
        images.put("treeBlue_high", new Image("/view/resources/Tiles/treeBlue_high.png", 27, 107, false, true));
        images.put("treeBlue_low", new Image("/view/resources/Tiles/treeBlue_low.png", 27, 87, false, true));
        images.put("treeBlue_mid", new Image("/view/resources/Tiles/treeBlue_mid.png", 27, 107, false, true));
        images.put("treeCactus_1", new Image("/view/resources/Tiles/treeCactus_1.png", 32, 66, false, true));
        images.put("treeCactus_2", new Image("/view/resources/Tiles/treeCactus_2.png", 29, 66, false, true));
        images.put("treeCactus_3", new Image("/view/resources/Tiles/treeCactus_3.png", 32, 66, false, true));
        images.put("treeGreen_high", new Image("/view/resources/Tiles/treeGreen_high.png", 27, 107, false, true));
        images.put("treeGreen_low", new Image("/view/resources/Tiles/treeGreen_low.png", 27, 87, false, true));
        images.put("treeGreen_mid", new Image("/view/resources/Tiles/treeGreen_mid.png", 27, 107, false, true));
        images.put("waveLava", new Image("/view/resources/Tiles/waveLava.png", 33, 10, false, true));
        images.put("waveWater", new Image("/view/resources/Tiles/waveWater.png", 33, 10, false, true));

    }


    public static ImageProvider getInstance() {
        if (provider == null)
            provider = new ImageProvider();
        return provider;
    }

    public Image getImage(String name){
        return images.get(name);
    }

}
