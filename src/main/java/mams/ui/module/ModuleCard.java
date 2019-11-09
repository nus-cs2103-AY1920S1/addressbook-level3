package mams.ui.module;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.model.module.Module;
import mams.ui.UiPart;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    public static final String CREDITS_LABEL_TEMPLATE = "%1$s MC(s)";

    private static final String FXML = "ModuleListCard.fxml";
    public final Module module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label moduleCode;
    @FXML
    private Label credits;
    @FXML
    private Label moduleName;
    @FXML
    private Label lecturerName;
    @FXML
    private Label timeSlot;
    @FXML
    private Label enrolment;
    @FXML
    private Label quota;
    @FXML
    private ProgressBar quotaBar;

    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        requireNonNull(module);
        this.module = module;
        id.setText(displayedIndex + ". ");
        moduleCode.setText(module.getModuleCode());
        credits.setText(String.format(CREDITS_LABEL_TEMPLATE, module.getModuleWorkload()));
        moduleName.setText(module.getModuleName());
        lecturerName.setText(module.getLecturerName());
        timeSlot.setText(module.getModuleTimeTableToString());
        enrolment.setText(Integer.toString(module.getCurrentEnrolment()));
        quota.setText(Integer.toString(module.getQuotaInt()));
        double percentageFilled = (double) module.getCurrentEnrolment() / module.getQuotaInt();
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
