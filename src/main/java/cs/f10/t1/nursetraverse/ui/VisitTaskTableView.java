package cs.f10.t1.nursetraverse.ui;

import java.util.List;

import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.model.visittask.VisitTask;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * An UI component that displays information of a table of {@code VisitTasks}.
 */
public class VisitTaskTableView extends UiPart<Region> {
    private static final String FXML = "VisitTaskTableView.fxml";

    private final List<VisitTask> visitTaskList;

    @FXML
    private TableView<VisitTask> visitTasks;
    @FXML
    private TableColumn<VisitTask, String> indexColumn;
    @FXML
    private TableColumn<VisitTask, String> descriptionColumn;
    @FXML
    private TableColumn<VisitTask, String> detailColumn;
    @FXML
    private TableColumn<VisitTask, String> finishedColumn;

    /**
     * Display UI for a patient, taking into account their displayed index.
     */
    public VisitTaskTableView(List<VisitTask> visitTaskList) {
        super(FXML);
        this.visitTaskList = visitTaskList;

        //Setup visit task display columns and rows
        //Setup the auto-incrementing index
        indexColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index + 1));
                }
            }
        });

        //Setup the other columns
        setupWordWrappedTableColumnCellFactory(descriptionColumn);
        descriptionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVisitTodo().getDescription()));

        setupWordWrappedTableColumnCellFactory(detailColumn);
        detailColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDetail().toString()));

        finishedColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIsDoneAsString()));

        //Setup placeholder & data
        visitTasks.setPlaceholder((new Label("This visit has no assigned tasks.")));
        visitTaskList.forEach(visitTask -> visitTasks.getItems().add(visitTask));
    }

    /**
     * Helper class to setup a word wrapped table column cell factory.
     */
    private void setupWordWrappedTableColumnCellFactory(final TableColumn<VisitTask, String> column) {
        column.setCellFactory(tableCell -> {
            TableCell<VisitTask, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(column.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VisitTaskTableView)) {
            return false;
        }

        // state check
        VisitTaskTableView card = (VisitTaskTableView) other;
        return CollectionUtil.checkEqual(visitTaskList, card.visitTaskList);
    }
}
