package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class StatisticsWindow extends VBox {
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
