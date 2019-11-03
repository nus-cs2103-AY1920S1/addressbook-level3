package dream.fcard.gui.controllers.cards.backview;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * The rear view of a front-back card.
 */
public class SimpleCardBack extends AnchorPane {
    @FXML
    private Button seeFrontButton;
    @FXML
    private Button correctButton;
    @FXML
    private Button wrongButton;
    @FXML
    private Label answerLabel;

    private Consumer<Boolean> onNext;
    private Consumer<Boolean> updateScore;

    public SimpleCardBack(String backOfCard, Consumer<Boolean> wantToSeeFront,
                          Consumer<Boolean> updateScore, Consumer<Boolean> onNext) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Cards/"
                    + "Back/SimpleCardBack.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.onNext = onNext;
            this.updateScore = updateScore;
            answerLabel.setText(backOfCard);
            seeFrontButton.setOnAction(e -> wantToSeeFront.accept(true));
            correctButton.setOnAction(e -> onCorrect());
            wrongButton.setOnAction((e -> onWrong()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onCorrect() {
        updateScore.accept(true);
        onNext.accept(true);
    }

    private void onWrong() {
        updateScore.accept(false);
        onNext.accept(false);
    }
}
