package mams.ui;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.model.module.Module;

/**
 * A UI component that displays the full expanded information of an {@code Module}.
 * Does not inherit from {@code ModuleCard} as it uses a different FXML file -
 * this is to ensure minimal complications in future development, where fx:id's
 * and layouts of various components in the FXML file of {@code ExpandedModuleCard}
 * may diverge from the FXML file used in the {@code ModuleCard}. Furthermore,
 * the {@code id} is also no longer used in the expanded view.
 */
public class ExpandedModuleCard extends UiPart<Region> {

    private static final String FXML = "ExpandedModuleListCard.fxml";
    private static final String EXPANDED_CREDITS_LABEL_TEMPLATE = "%1$s Modular Credit(s)";
    public final Module module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label moduleCode;
    @FXML
    private Label moduleName;
    @FXML
    private Label credits;
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
    @FXML
    private Label moduleDescription;
    @FXML
    private Label studentsEnrolled;

    public ExpandedModuleCard(Module module) {
        super(FXML);
        this.module = module;
        moduleCode.setText(module.getModuleCode());
        moduleName.setText(module.getModuleName());
        credits.setText(String.format(EXPANDED_CREDITS_LABEL_TEMPLATE, module.getModuleWorkload()));
        lecturerName.setText(module.getLecturerName());
        timeSlot.setText(module.getModuleTimeTableToString());
        enrolment.setText(Integer.toString(module.getCurrentEnrolment()));
        quota.setText(module.getQuota());
        setEnrolmentProgressBar();
        moduleDescription.setText(module.getModuleDescription());
        studentsEnrolled.setText(formatStudentEnrolmentToText());
    }

    private void setEnrolmentProgressBar() {
        double percentageFilled = (double) module.getCurrentEnrolment() / module.getQuotaInt();
        quotaBar.setProgress(percentageFilled);
    }

    /**
     * Formats the list of student IDs contained in {@code Module} to a {@code String} object,
     * with one student ID on each line, sorted by alpha-numeric order.
     * @return formatted text of student IDs.
     */
    private String formatStudentEnrolmentToText () {
        return module.getStudents().stream()
                .sorted(Comparator.comparing(student -> student.tagName))
                .map(student -> student.tagName)
                .collect(Collectors.joining("\n"));
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
