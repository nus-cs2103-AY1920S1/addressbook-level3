package mams.ui.student;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.model.student.Student;
import mams.ui.UiPart;

/**
 * A UI component that displays the full expanded information of an {@code Student}.
 * Does not inherit from {@code StudentCard} as it uses a different FXML file -
 * this is to ensure minimal complications in future development, where fx:id's
 * and layouts of various components in the FXML file of {@code ExpandedStudentCard}
 * may diverge from the FXML file used in the {@code StudentCard}. Furthermore,
 * the {@code id} is also no longer used in the expanded view.
 */
public class ExpandedStudentCard extends UiPart<Region> {

    private static final String FXML = "ExpandedStudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label credits;
    @FXML
    private Label matricId;
    @FXML
    private Label currentMods;
    @FXML
    private Label currentAppeals;
    @FXML
    private FlowPane appealTags;
    @FXML
    private Label numberOfMods;
    @FXML
    private Label previousModules;

    public ExpandedStudentCard(Student student) {
        super(FXML);
        this.student = student;
        name.setText(student.getName().fullName);
        credits.setText(student.getCredits().value);
        matricId.setText(student.getMatricId().value);
        currentMods.setText(formatCurrentModuleListToText());
        currentAppeals.setText(formatCurrentAppealListToText());
        numberOfMods.setText(Integer.toString(student.getNumberOfMods()));
        previousModules.setText(student.getPrevMods().toString());
        student.getCurrentAppeals().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> appealTags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Formats the list of {@code Module} codes contained in {@code Student} to a {@code String} object,
     * with one module code on each line, sorted by alpha-numeric order.
     * @return formatted text of module codes.
     */
    private String formatCurrentModuleListToText () {
        return student.getCurrentModules().stream()
                .sorted(Comparator.comparing(module -> module.tagName))
                .map(module -> module.tagName)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Formats the list of {@code Appeal} IDs contained in {@code Student} to a {@code String} object,
     * with one appeal ID on each line, sorted by alpha-numeric order.
     * @return formatted text of appeal IDs.
     */
    private String formatCurrentAppealListToText () {
        return student.getCurrentAppeals().stream()
                .sorted(Comparator.comparing(appeal -> appeal.tagName))
                .map(appeal -> appeal.tagName)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCard)) {
            return false;
        }

        // state check
        StudentCard card = (StudentCard) other;
        return student.equals(card.student);
    }
}
