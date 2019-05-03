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
        File f = null;
        try {
            f = new File("src/application/savedMaps");
            f.mkdir();

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
            game.setMap(container.getMap());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void saveState(Game game) {
    }
}
