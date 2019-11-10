package seedu.moolah.ui.statistics;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seedu.moolah.model.statistics.FiveElementTableEntry;

/**
 * A factory class to produce the TableView for the Statistics Panel
 */
public class TableViewRegionFactory implements StatisticsRegionFactory {

    private List<FiveElementTableEntry> rows;

    private String title;

    public TableViewRegionFactory(List<FiveElementTableEntry> rows, String title) {
        this.rows = rows;
        this.title = title;
    }

    @Override
    public TableView createRegion() {
        TableColumn<FiveElementTableEntry, String> nameColumn = new TableColumn<>("Category");
        nameColumn.setMinWidth(250);
        nameColumn.setCellValueFactory(new PropertyValueFactory<FiveElementTableEntry, String>("name"));

        TableColumn<FiveElementTableEntry, String> overlapAmountColumn = new TableColumn<>("Overlap Amount");
        overlapAmountColumn.setMinWidth(250);
        overlapAmountColumn.setCellValueFactory(
                new PropertyValueFactory<FiveElementTableEntry, String>("overlapAmount"));

        TableColumn<FiveElementTableEntry, String> overlapFrequencyColumn = new TableColumn<>("Overlap Frequency");
        overlapFrequencyColumn.setMinWidth(250);
        overlapFrequencyColumn.setCellValueFactory(
                new PropertyValueFactory<FiveElementTableEntry, String>("overlapNumEntries"));

        TableColumn<FiveElementTableEntry, String> differenceAmountColumn =
                new TableColumn<>("Difference Amount");
        differenceAmountColumn.setMinWidth(250);
        differenceAmountColumn.setCellValueFactory(
                new PropertyValueFactory<FiveElementTableEntry, String>("differenceAmount"));

        TableColumn<FiveElementTableEntry, String> differenceFrequencyColumn =
                new TableColumn<>("Difference Frequency");
        differenceFrequencyColumn.setMinWidth(250);
        differenceFrequencyColumn.setCellValueFactory(
                new PropertyValueFactory<FiveElementTableEntry, String>("differenceNumEntries"));


        TableView<FiveElementTableEntry> table = new TableView<>();
        table.setItems(getTableEntries(rows));

        table.getColumns().addAll(nameColumn, overlapAmountColumn, overlapFrequencyColumn,
                differenceAmountColumn, differenceFrequencyColumn);

        return table;
    }

    @Override
    public String getTitle() {
        return title;
    }

    private ObservableList<FiveElementTableEntry> getTableEntries(List<FiveElementTableEntry> rows) {
        ObservableList<FiveElementTableEntry> result = FXCollections.observableArrayList();
        result.addAll(rows);
        return result;
    }
}
