package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.semester.Semester;

/**
 * An UI component that displays information of a {@code Semester}.
 */
public class SemesterCard extends UiPart<Region> {
    private static final String FXML = "SemesterListCard.fxml";
    private static final String CURRENT_SEM_CLASS = "currentSemester";
    private static final String CURRENT_SEM_TEXT = "(Current Sem)";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Semester semester;

    @FXML
    private HBox semesterCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label totalMcCount;
    @FXML
    private Label currentSemester;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox moduleListPanelPlaceholder;
    @FXML
    private VBox moduleList;
    @FXML
    private FlowPane modulesCollapsedPlaceholder;

    private final Logger logger = LogsCenter.getLogger(getClass());

    public SemesterCard(Semester semester, boolean isCurrentSemester) {
        super(FXML);
        requireNonNull(semester);
        this.semester = semester;
        name.setText(semester.getSemesterName().name());
        totalMcCount.setText("(" + semester.getMcCount() + ")");
        currentSemester.setVisible(false);

        if (isCurrentSemester) {
            semesterCardPane.getStyleClass().add(CURRENT_SEM_CLASS);
            currentSemester.setVisible(true);
            currentSemester.setText(CURRENT_SEM_TEXT);
        }

        if (semester.isExpanded()) {
            modulesCollapsedPlaceholder.setVisible(false);
            modulesCollapsedPlaceholder.setManaged(false);
            semester.getModules().asUnmodifiableObservableList().stream()
                    .sorted(Comparator.comparing(module -> module.getModuleCode().toString()))
                    .forEach(module -> {
                        ModuleCard moduleCard = new ModuleCard(module);
                        moduleListPanelPlaceholder.getChildren().add(moduleCard.getRoot());
                    });
        } else {
            moduleList.setVisible(false);
            moduleList.setManaged(false);
            semester.getModules().asUnmodifiableObservableList().stream()
                    .sorted(Comparator.comparing(module -> module.getModuleCode().toString()))
                    .forEach(module -> {
                        ModulePill modulePill = new ModulePill(module);
                        modulesCollapsedPlaceholder.getChildren().add(modulePill.getRoot());
                    });
        }
    }
}
