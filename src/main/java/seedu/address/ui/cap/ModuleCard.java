package seedu.address.ui.cap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.common.Module;

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
    private HBox cardPane;
    @FXML
    private Label moduleCode;
    @FXML
    private Label semester;
    @FXML
    private Label title;
    @FXML
    private Label grade;

    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        moduleCode.setText(module.getModuleCode().toString());
        title.setText(module.getTitle().toString());
        semester.setText(module.getSemester().toString());
        grade.setText(module.getGrade().toString());
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
        return moduleCode.getText().equals(card.moduleCode.getText())
                && module.equals(card.module);
    }
}
