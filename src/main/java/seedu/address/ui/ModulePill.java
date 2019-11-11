package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.module.Module;

/**
 * An UI component that displays information of a collapsed {@code Module}.
 */
public class ModulePill extends UiPart<Region> {

    private static final String PREREQS_NOT_MET_STYLE_CLASS = "prereqsNotMet";

    private static final String FXML = "ModulePill.fxml";


    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;

    @FXML
    private HBox modulePill;
    @FXML
    private Label name;
    @FXML
    private Label prereqs;

    public ModulePill(Module module) {
        super(FXML);
        requireNonNull(module);
        this.module = module;
        name.setText(module.getModuleCode().value);
        if (!module.getPrereqsSatisfied()) {
            modulePill.getStyleClass().add(PREREQS_NOT_MET_STYLE_CLASS);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModulePill)) {
            return false;
        }

        // state check
        return module.equals(((ModulePill) other).module);
    }
}
