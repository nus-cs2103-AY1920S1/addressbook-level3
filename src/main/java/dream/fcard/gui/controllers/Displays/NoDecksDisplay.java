package dream.fcard.gui.controllers.Displays;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.Windows.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NoDecksDisplay extends VBox {

    @FXML
    private Label message;
    @FXML
    private Button createNewDeck;

    private Consumer<Boolean> consumer;

    public NoDecksDisplay(Consumer<Boolean> consumer) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/" +
                    "NoDecksDisplay.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            createNewDeck.setOnAction(e -> onCreateNewDeck());
            this.consumer = consumer;
        } catch (IOException e) {
            //TODO: again, replace with a logger later on
            e.printStackTrace();
        }
    }

    /**
     * Will feedback to parent container which is the main window that the user
     * wants to switch to creating a new form. Main window will then handle the re-rendering.
     */
    @FXML
    void onCreateNewDeck() {
        consumer.accept(true);
    }
}
