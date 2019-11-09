package seedu.address.ui;

import static java.util.Objects.requireNonNull;

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

import seedu.address.model.password.analyser.report.AnalysisReport;
import seedu.address.model.password.analyser.report.StrongAnalysisReport;

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
            AnalysisReport a = (AnalysisReport) analysisReport;
            for (int i = 0; i < a.getResults().size(); i++) {
                Label label = new Label(a.getTargetHeader(i));
                TableView table = new TableView<AnalysisReport>();
                table.setFixedCellSize(25);
                TableColumn<AnalysisReport, Number> indexColumn = new TableColumn<AnalysisReport, Number>("Index");
                indexColumn.setSortable(false);
                indexColumn.setCellValueFactory(column->
                        new ReadOnlyObjectWrapper<Number>(table.getItems().indexOf(column.getValue()) + 1));
                TableColumn one = new TableColumn("Description");
                one.setCellValueFactory(new PropertyValueFactory("passwordDesc"));
                one.setSortable(false);
                TableColumn two = new TableColumn("Username");
                two.setCellValueFactory(new PropertyValueFactory("passwordUser"));
                two.setSortable(false);
                TableColumn three = new TableColumn("Password");
                three.setCellValueFactory(new PropertyValueFactory("passwordValue"));
                three.setSortable(false);
                TableColumn four = new TableColumn("Result");
                four.setCellValueFactory(new PropertyValueFactory("description"));
                four.setSortable(false);

                table.getColumns().setAll(indexColumn, one, two, three, four);
                table.setItems(a.getTargetResults(i));
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
