package seedu.module.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.module.commons.core.LogsCenter;
import seedu.module.model.module.Deadline;
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
    private VBox moduleViewVBox;
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
    private Accordion linksAccordion;
    @FXML
    private TitledPane linksAccordionPane;
    @FXML
    private FlowPane links;
    @FXML
    private Accordion deadlinesAccordion;
    @FXML
    private TitledPane deadlinesAccordionPane;
    @FXML
    private TextFlow deadline;

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

        // GUi to show to the user if tracked
        if (module instanceof Trackable) {
            // Sets the accordions accordingly
            linksAccordion.setExpandedPane(linksAccordionPane);
            deadlinesAccordion.setExpandedPane(deadlinesAccordionPane);

            // Sets the data accordingly
            Trackable trackedModule = ((Trackable) module);
            List<Deadline> deadlineList = trackedModule.getDeadlineList();
            for (int i = 0; i < deadlineList.size(); i++) {
                Text text = new Text(trackedModule.getDeadlineTask(i));
                if (deadlineList.get(i).getTag().equals("HIGH")) {
                    text.setFill(Color.ORANGERED);
                } else if (deadlineList.get(i).getTag().equals("MEDIUM")) {
                    text.setFill(Color.YELLOW);
                } else if (deadlineList.get(i).getTag().equals("LOW")) {
                    text.setFill(Color.CHARTREUSE);
                } else {
                    text.setFill(Color.WHITE);
                }
                text.setFont(Font.font ("Verdana", 15));
                deadline.getChildren().add(text);
            }
            trackedModule.getLink().stream().map(link -> new LinkButton(link))
                    .forEach(button -> links.getChildren().add(button));
            links.setPrefWrapLength(500);
            links.setHgap(10);
            links.setVgap(5);

            return;
        }

        // Gui to show to the user if archived
        moduleDetails.setExpandedPane(moduleDetailsPane);
        moduleViewVBox.getChildren().remove(linksAccordion);
        moduleViewVBox.getChildren().remove(deadlinesAccordion);
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
