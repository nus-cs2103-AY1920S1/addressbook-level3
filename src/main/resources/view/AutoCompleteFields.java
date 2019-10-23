package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

public class AutoCompleteFields implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField input;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextFields.bindAutoCompletion(input,possibleSuggestions);
    }
}