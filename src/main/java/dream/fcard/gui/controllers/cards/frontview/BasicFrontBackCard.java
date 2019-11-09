package dream.fcard.gui.controllers.cards.frontview;

import java.io.IOException;

import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.cards.FlashCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The front view of a front-back card.
 */
public class BasicFrontBackCard extends VBox {
    @FXML
    private Label questionLabel;
    @FXML
    private Button seeBackButton;

    public BasicFrontBackCard(FlashCard card) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Cards/Front"
                    + "/BasicFrontBackCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();

            String front = card.getFront();
            questionLabel.setText(front);
            seeBackButton.setOnAction(e -> {
                Consumers.doTask("SEE_BACK", true);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
