package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Each help instruction is represented as a box.
 */
public class HelpCommandBox extends UiPart<Region> {
    private static final String FXML = "HelpCommandBox.fxml";

    private final String title;
    private final String description;

    @FXML
    private VBox card;

    @FXML
    private Label commandTitle;

    @FXML
    private Text commandDescription;

    /**
     * Constructor.
     * @param title which is the name of the command.
     * @param description on how to use the command.
     */
    public HelpCommandBox(String title, String description) {
        super(FXML);
        this.title = title;
        this.description = description;

        commandTitle.setText(this.title);
        commandDescription.setText(this.description);
        commandTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
