package mams.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.model.module.Module;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleListCard.fxml";
    public final Module module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label moduleCode;
    @FXML
    private Label moduleName;
    @FXML
    private Label lecturerName;
    @FXML
    private Label id;
    @FXML
    private Label timeSlot;
    @FXML
    private Label enrolment;
    @FXML
    private Label quota;
    @FXML
    private ProgressBar quotaBar;

    // TODO add more fields for module display card

    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        id.setText(displayedIndex + ". ");
        moduleCode.setText(module.getModuleCode());
        moduleName.setText(module.getModuleName());
        lecturerName.setText(module.getLecturerName());
        timeSlot.setText(module.timeSlotsToString());
        enrolment.setText(Integer.toString(module.getCurrentEnrolment()));
        quota.setText(Integer.toString(module.getQuota()));
        double percentageFilled = (double) module.getCurrentEnrolment() / module.getQuota();
        quotaBar.setProgress(percentageFilled);
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
        return id.getText().equals(card.id.getText())
                && module.equals(card.module);
    }
}
