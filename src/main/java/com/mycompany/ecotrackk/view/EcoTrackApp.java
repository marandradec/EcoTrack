package ecotrack.view;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Es la clase principal de la aplicación, su función es iniciar el programa
 * y mostrar la interfaz.
 */
public class EcoTrackApp extends Application {
    
    @Override
    public void start(Stage stage) {
        EcoTrackUI ui = new EcoTrackUI();
        stage.setTitle("EcoTrack - Sistema de Gestión de Residuos Urbanos");
        stage.setScene(new javafx.scene.Scene(ui.getRoot(), 1000, 700));
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    /**
     * Método principal para ejecutar la aplicación
     */
    public static void main(String[] args) {
        launch(args);
    }
}
