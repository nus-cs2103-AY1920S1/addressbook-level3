package seedu.module.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.module.commons.core.LogsCenter;
import seedu.module.model.module.Module;
import seedu.module.model.module.SemesterDetail;
import seedu.module.model.module.Trackable;

/**
 * Panel containing the active displayed module.
 */
public class ModuleViewPanel extends UiPart<Region> {
    private static final String FXML = "ModuleViewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private Label moduleCode;
    @FXML
    private Label title;
    @FXML
    private Accordion moduleDetails;
    @FXML
    private TitledPane moduleDetailsPane;
    @FXML
    private Text description;
    @FXML
    private Text prerequisite;
    @FXML
    private Text preclusion;
    @FXML
    private ListView<SemesterDetail> semesterData;
    @FXML
    private FlowPane links;
    @FXML
    private Label deadline;

    public ModuleViewPanel(Module module) {
        super(FXML);
        moduleCode.setText(module.getModuleCode());
        title.setText(module.getTitle());
        description.setText(module.getDescription());
        prerequisite.setText(module.getPrerequisite()
            .orElse("There are no prerequisites for this module."));
        preclusion.setText(module.getPreclusion()
            .orElse("There are no preclusions for this module."));
        semesterData.setItems(module.getSemesterDetails().getAsObservableList());
        semesterData.setCellFactory(listView -> new ModuleSemesterDetailCell());

        // Expands the accordion if the module is not yet tracked
        moduleDetails.setExpandedPane(moduleDetailsPane);

        if (module instanceof Trackable) {
            moduleDetails.setExpandedPane(null);
            Trackable trackedModule = ((Trackable) module);
            deadline.setText(trackedModule.getDeadline());
            trackedModule.getLink().stream().map(link -> new LinkButton(link))
                    .forEach(button -> links.getChildren().add(button));
            links.setHgap(10);
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ModuleSemesterDetailCell extends ListCell<SemesterDetail> {
        @Override
        protected void updateItem(SemesterDetail semesterDetail, boolean empty) {
            super.updateItem(semesterDetail, empty);

            if (empty || semesterDetail == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleSemesterDetailCard(semesterDetail).getRoot());
            }
        }
    }
}
