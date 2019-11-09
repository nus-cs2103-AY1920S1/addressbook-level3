package seedu.scheduler.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.model.person.Interviewer;



/**
 * Panel containing the list of interviewer.
 */
public class InterviewerListPanel extends UiPart<Region> {
    private static final String FXML = "InterviewerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InterviewerListPanel.class);

    private ObservableList<Interviewer> interviewerList;

    @FXML
    private TableView interviewerTableView;

    InterviewerListPanel(ObservableList<Interviewer> interviewerList) {
        super(FXML);
        this.interviewerList = interviewerList;
        initialise();
    }

    /**
     * Set the columns and the data from each column
     */
    private void initialise() {
        setTableColumn();
        this.interviewerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.interviewerTableView.setItems(this.interviewerList);
    }

    /**
     * Set the table columns.
     */
    private void setTableColumn() {
        createNewTableColumn("Name");
        createNewTableColumn("Department");
        createNewTableColumn("Availabilities");
    }

    /**
     * Create a new TableColumn object.
     * @param titles The titles for each columns.
     */
    private void createNewTableColumn(String titles) {
        TableColumn<ObservableList<String>, String> columnTitle =
                new TableColumn<ObservableList<String>, String>(
                        titles
                );
        columnTitle.setMinWidth(80);
        columnTitle.setReorderable(false);
        columnTitle.setSortable(false);
        columnTitle.setCellValueFactory(new PropertyValueFactory<ObservableList<String>, String>(titles));

        interviewerTableView.getColumns().add(columnTitle);

    }
}

