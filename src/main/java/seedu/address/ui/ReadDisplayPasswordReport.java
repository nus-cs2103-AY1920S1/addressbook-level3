package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import seedu.address.model.password.analyser.Analyser;
import seedu.address.model.password.analyser.report.AnalysisReport;
import seedu.address.model.password.analyser.report.StrongAnalysisReport;
import seedu.address.model.password.analyser.result.Result;

/**
* A Ui for the displaying password analysis report that is displayed when read command is called.
*/
public class ReadDisplayPasswordReport extends UiPart<Region> {
    private static final String FXML = "ReadDisplayPasswordReport.fxml";

    @FXML
    private TextArea resultDisplay;

    @FXML
    private ScrollPane tableHolder;

    @FXML
    private VBox tables;

    public ReadDisplayPasswordReport() {
        super(FXML);
    }

    public void setFeedbackToUser(Object analysisReport) {
        tableHolder.setVisible(false);
        tableHolder.setManaged(false);
        Font f = Font.loadFont(getClass().getResource("/fonts/COURIER.TTF").toExternalForm(), 10);
        resultDisplay.setFont(f);
        requireNonNull(analysisReport);
        if (analysisReport instanceof StrongAnalysisReport) {
            resultDisplay.setText(analysisReport.toString());
        } else {
            tableHolder.setVisible(true);
            tableHolder.setManaged(true);
            AnalysisReport castedAnalysisReport = (AnalysisReport) analysisReport;
            HashMap<Analyser, List<Result>> reports = castedAnalysisReport.getReports();
            for (Map.Entry<Analyser, List<Result>> report : reports.entrySet()) {
                Analyser a = report.getKey();
                List<Result> results = report.getValue();
                Label label = new Label(a.getHeader());
                TableView table = new TableView<AnalysisReport>();
                table.setFixedCellSize(25);

                TableColumn<AnalysisReport, Number> indexColumn = new TableColumn<AnalysisReport, Number>("Index");
                indexColumn.setSortable(false);
                indexColumn.setCellValueFactory(column->
                        new ReadOnlyObjectWrapper<Number>(table.getItems().indexOf(column.getValue()) + 1));

                TableColumn descColumn = new TableColumn("Description");
                descColumn.setCellValueFactory(new PropertyValueFactory("passwordDesc"));
                descColumn.setSortable(false);

                TableColumn userColumn = new TableColumn("Username");
                userColumn.setCellValueFactory(new PropertyValueFactory("passwordUser"));
                userColumn.setSortable(false);

                TableColumn passwordColumn = new TableColumn("Password");
                passwordColumn.setCellValueFactory(new PropertyValueFactory("passwordValue"));
                passwordColumn.setSortable(false);

                TableColumn resultColumn = new TableColumn("Result");
                resultColumn.setCellValueFactory(new PropertyValueFactory("description"));
                resultColumn.setSortable(false);

                table.getColumns().setAll(indexColumn, descColumn, userColumn, passwordColumn, resultColumn);
                table.setItems(castedAnalysisReport.getObservableResults(results));
                table.prefHeightProperty().bind(Bindings.max(2, Bindings.size(table.getItems()))
                        .multiply(25)
                        .add(45)); //room for table header
                table.minHeightProperty().bind(table.prefHeightProperty());
                table.maxHeightProperty().bind(table.prefHeightProperty());
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                tables.getChildren().addAll(label, table);
            }

        }
    }
}
