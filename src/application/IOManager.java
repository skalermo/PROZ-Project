package application;

import engine.Game;
import engine.Tile;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class IOManager {

    private FileChooser fileChooser;

    public IOManager(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Java Project Maps (*.jpm)", "*.jpm");
        fileChooser.getExtensionFilters().add(extFilter);
        File mapsDir = new File("src/application/savedMaps");
        if (mapsDir.exists())
            fileChooser.setInitialDirectory(mapsDir);
    }

//    //public Game getGame() {
//        return game;
//    }

    public void saveMap(Stage stage, List<List<Tile>> tiles) {

        MapContainer container = new MapContainer(tiles);
        fileChooser.setTitle("Save in");
        //todo
        File file = fileChooser.showSaveDialog(stage);

        try {
            //new File("src/application/savedMaps").mkdir();

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(container);
            oos.close();

        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<List<Tile>> loadMap(Stage stage) {
        fileChooser.setTitle("Load from");
        File file = fileChooser.showOpenDialog(stage);
        try {

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            MapContainer container = (MapContainer) ois.readObject();
            ois.close();
            return container.getTiles();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            return null;
        }

    }

    public void saveState(Game game) {
        // todo
    }
}
