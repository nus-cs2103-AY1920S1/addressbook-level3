package dream.fcard.gui.controllers.windows;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 * Window to display user's statistics.
 */
public class StatisticsWindow extends VBox {

    /** Creates a new instance of StatisticsWindow. */
    public StatisticsWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                .getResource("/view/Windows/StatisticsWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            //TODO: replace with logger
            e.printStackTrace();
        }
    }
}
