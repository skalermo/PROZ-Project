package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        ViewManager manager = new ViewManager();
        primaryStage = manager.getMainStage();

        primaryStage.show();
        //primaryStage.setResizable(false);
//        primaryStage.setWidth(1024);
//        primaryStage.setHeight(768);
    }

    public static void main(String[] args) {

        launch(args);
    }
}
