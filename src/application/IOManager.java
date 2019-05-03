package application;

import engine.Game;

import java.io.*;

public class IOManager {

    private Game game;

    public IOManager(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void saveMap(Game game) {
        MapContainer container = new MapContainer(game);
        try {
            new File("src/application/savedMaps").mkdir();

            FileOutputStream fos = new FileOutputStream("src/application/savedMaps/map.jpm");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(container);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(Game game) {
        try {

            FileInputStream fis = new FileInputStream("src/application/savedMaps/map.jpm");
            ObjectInputStream ois = new ObjectInputStream(fis);
            MapContainer container = (MapContainer) ois.readObject();
            ois.close();
            game.setTiles(container.getTiles());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void saveState(Game game) {
    }
}
