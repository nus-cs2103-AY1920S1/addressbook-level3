package dream.fcard.gui.controllers.displays.test;

import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.model.State;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * Alert window that opens upon finishing a test.
 */
public class EndOfTestAlert {

    /**
     * Imported Consumer: Used by TestDisplay to trigger MainWindow to re-render DeckDisplay
     */
    @SuppressWarnings("unchecked")
    private static Consumer<Boolean> displayDecks = State.getState().getConsumer(ConsumerSchema.DISPLAY_DECKS);
    /**
     * Imported Consumer: Used by TestDisplay to trigger MainWindow to clear the message bar.
     */
    @SuppressWarnings("unchecked")
    private static Consumer<Boolean> clearMessage = State.getState().getConsumer(ConsumerSchema.CLEAR_MESSAGE);

    /**
     * method that can be called by controller to display the alertbox.
     * @param title Title of alert box.
     * @param message Contents of alert box.
     */
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        //
        Scene scene = new Scene (layout);
        window.setScene(scene);
        window.setOnHidden( e -> onClose());
        window.show();
    }

    private static void onClose() {
        displayDecks.accept(true);
        clearMessage.accept(true);
    }
}
