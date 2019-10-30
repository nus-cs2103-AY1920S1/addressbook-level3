package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.module.Module;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;

    @FXML
    private HBox moduleCardPane;
    @FXML
    private Label name;
    @FXML
    private Label mcCount;
    @FXML
    private Label prereqs;
    @FXML
    private FlowPane tags;

    public ModuleCard(Module module) {
        super(FXML);
        requireNonNull(module);
        this.module = module;
        name.setText(module.getModuleCode().value + " " + module.getName().fullName);
        mcCount.setText(Integer.toString(module.getMcCount()));
        prereqs.setText("NEEDS: " + module.getPrereqString());
        prereqs.setVisible(!module.getPrereqsSatisfied());
        module.getTags().asUnmodifiableObservableList().stream()
                .sorted(Comparator.comparing(tag -> tag.getTagName()))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.getTagName());
                    if (tag.isDefault()) {
                        tagLabel.setId("defaultTag");
                    } else {
                        tagLabel.setId("userTag");
                    }
                    tags.getChildren().add(tagLabel);
                });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCard)) {
            return false;
        }

        // state check
        ModuleCard card = (ModuleCard) other;
        return module.equals(card.module);
    }
}
