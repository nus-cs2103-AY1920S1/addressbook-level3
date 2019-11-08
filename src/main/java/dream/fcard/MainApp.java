package dream.fcard;

import java.io.IOException;

import dream.fcard.core.Main;
import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.logic.stats.UserStatsHolder;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.StateHolder;

import java.util.logging.Logger;
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

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    @Override
    public void start(Stage stage) {
        logger.info("=============================[ Starting FlashCard Pro ]===========================");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/Windows/MainWindow.fxml"));
            VBox vbox = fxmlLoader.load();
            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.setTitle("FlashCard Pro");
            stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/address_book_32.png")));
            stage.show();

            StorageManager.loadUserStats();
            // start a session. if UserStats has not been retrieved, it will do so at this point.
            UserStatsHolder.getUserStats().startCurrentSession();

            // when the 'X' button is clicked.
            stage.setOnCloseRequest(e -> {
                logger.info("============================ [ Stopping FlashCard Pro ] =============================");
                UserStatsHolder.getUserStats().endCurrentSession();
                StorageManager.saveAll(StateHolder.getState().getDecks());
                StorageManager.saveStats(UserStatsHolder.getUserStats());
            });
        } catch (IOException e) {
            logger.severe("Failed to load app");
            e.printStackTrace();
        }
    }
}
