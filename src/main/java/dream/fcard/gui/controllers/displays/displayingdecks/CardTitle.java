package dream.fcard.gui.controllers.displays.displayingdecks;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.model.cards.FlashCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * The tile for viewing a question when looking at a deck. Used inside DeckDisplay.
 */
public class CardTitle extends HBox {

    @FXML
    private Label questionLabel;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private FlashCard card;
    private int indexInDeck;

    CardTitle(FlashCard card, int index, Consumer<Integer> deleteCard, Consumer<Integer> editCard) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/CardTitle.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            questionLabel.setText(card.getFront());
            this.card = card;
            indexInDeck = index;
            editButton.setOnAction(e -> editCard.accept(index));
            deleteButton.setOnAction(e -> deleteCard.accept(index));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disableDelete() {
        deleteButton.setDisable(true);
    }
}
