package dream.fcard.gui.controllers.Cards;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.Windows.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SimpleCardBack extends AnchorPane {
    @FXML
    private Button seeFrontButton;
    @FXML
    private Label answerLabel;

    public SimpleCardBack(String backOfCard, Consumer<Boolean> wantToSeeFront) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Cards/Back/SimpleCardBack.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            answerLabel.setText(backOfCard);
            seeFrontButton.setOnAction(e -> wantToSeeFront.accept(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
