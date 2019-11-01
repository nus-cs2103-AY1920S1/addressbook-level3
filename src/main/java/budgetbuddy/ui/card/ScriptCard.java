package budgetbuddy.ui.card;

import java.util.Objects;

import budgetbuddy.model.script.Script;
import budgetbuddy.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A UI component that displays information of a {@link Script}.
 */
public class ScriptCard extends UiPart<Region> {
    private static final String FXML = "ScriptCard.fxml";
    private static final String MESSAGE_NO_DESCRIPTION = "No description provided";

    private final Script script;

    @FXML
    private Label name;
    @FXML
    private Label description;

    public ScriptCard(Script script) {
        super(FXML);
        this.script = script;
        name.setText(script.getName().toString());
        String desc = script.getDescription().toString();
        if (desc.isBlank()) {
            description.setText(MESSAGE_NO_DESCRIPTION);
            description.setStyle("-fx-font-style: italic");
        } else {
            description.setText(desc);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScriptCard that = (ScriptCard) o;
        return Objects.equals(script, that.script);
    }

    @Override
    public int hashCode() {
        return script.hashCode();
    }
}
