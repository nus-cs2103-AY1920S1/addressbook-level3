package dream.fcard;

import java.io.IOException;

import dream.fcard.core.Main;

import dream.fcard.logic.stats.UserStats;
import dream.fcard.logic.stats.UserStatsHolder;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.StateHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/Windows/MainWindow.fxml"));
            VBox vbox = fxmlLoader.load();
            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.setTitle("FlashCard Pro");
            stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/address_book_32.png")));
            stage.show();

            // start a session. if UserStats has not been retrieved, it will do so at this point.
            UserStatsHolder.getUserStats().startCurrentSession();

            // when the 'X' button is clicked.
            stage.setOnCloseRequest(e -> {
                System.out.println("Terminating the application...");
                UserStatsHolder.getUserStats().endCurrentSession();
                StorageManager.saveAll(StateHolder.getState().getDecks());
                StorageManager.saveStats(UserStatsHolder.getUserStats());
            });
        } catch (IOException e) {
            // TODO: Our yet-to-be-reinstated-logger will replace this rudimentary error printing
            System.err.println("Failed to load app");
            e.printStackTrace();
        }
    }
}
