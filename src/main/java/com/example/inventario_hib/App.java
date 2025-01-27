package com.example.inventario_hib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static int directory = 0;
    /*
        - 0: Directorio principal
        - 1: Directorio de aulas
        - 2: Directorio de categorias
        - 3: Directorio de marcajes
        - 4: Directorio de productos
        - 5: Directorio de productos categorias
        - 6: Consultas
     */

    public static int getDirectory() {
        return directory;
    }

    public static void setDirectory(int directory) {
        App.directory = directory;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("principal"), 1300, 800);
        stage.setTitle("Sistema de gestión de productos mediante RFID - Daniel Brito Negrín.");
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/styles/table.css").toExternalForm());
        stage.show();
    }

    public static void setRoot(String fxml, String title) throws IOException {
        scene.setRoot(loadFXML(fxml));

        Stage stage = (Stage) scene.getWindow();
        stage.sizeToScene();
        stage.setTitle(title);
    }

    public static Object getUserData() {
        Parent root = scene.getRoot();
        Stage stage = (Stage) root.getScene().getWindow();
        return stage.getUserData();
    }

    public static void setUserData(Object objeto) {
        Parent root = scene.getRoot();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setUserData(objeto);
    }

    private static Parent loadFXML(String file) throws IOException {
        String directory = checkDirectory();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(directory + file + ".fxml"));
        return fxmlLoader.load();
    }

    private static String checkDirectory() {
        switch (directory) {
            case 0:
                return "";
            case 1:
                return "aula/";
            case 2:
                return "categoria/";
            case 3:
                return "marcaje/";
            case 4:
                return "producto/";
            case 5:
                return "producto_categoria/";
            case 6:
                return "consultas/";
            default:
                return "";
        }
    }

    public static void main(String[] args) {
        launch();
    }
}