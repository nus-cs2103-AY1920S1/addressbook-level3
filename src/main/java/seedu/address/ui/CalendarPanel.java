package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.item.Item;
import seedu.address.model.item.VisualizeList;

public class CalendarPanel extends UiPart<Region> {
    private static String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private int year;
    private int month;

    @FXML
    private Label monthAndYear;

    @FXML
    private GridPane calendarGrid;


    public CalendarPanel(ObservableList<Item> visualList) {
        super(FXML);
        monthAndYear.setAlignment(Pos.CENTER);
        LocalDateTime currentTime = LocalDateTime.now();
        this.month = currentTime.getMonthValue();
        this.year = currentTime.getYear();
        monthAndYear.setText(String.format("%s, %s", Month.of(month), String.valueOf(year)));
        initializeCalendarGrid();
        loadData(visualList);
    }

    private void initializeCalendarGrid() {
        generateHeader();
        generateDate();
    }

    private void loadData(ObservableList<Item> visualList) {
        ObservableList<Item> eventList = visualList.filtered(x -> x.hasEvent());
        ObservableList<Item> monthEvent = eventList.filtered(x -> x.getEvent()
                .get().getStartDateTime().getMonthValue() == month);
        for (Item item : monthEvent) {
            Label lbl = new Label();
            lbl.setText(item.getItemDescription().toString());
            lbl.setStyle("-fx-background-color: #eff516");
            int date = item.getEvent().get().getStartDateTime().getDayOfMonth();
            Node node = calendarGrid.lookup("#" + Integer.toString(date));
            if (node instanceof VBox) {
                VBox vPane = (VBox) node;
                vPane.getChildren().add(lbl);
            }
        }
    }

    private void generateHeader() {
        for (int i = 0; i < 7; i++) {
            VBox vPane = new VBox();
            vPane.getStyleClass().add("calendar_pane");
            GridPane.setVgrow(vPane, Priority.NEVER);
            Label day = new Label();
            day.setAlignment(Pos.CENTER);
            day.setText(DayOfWeek.of(i + 1).toString());
            vPane.getChildren().add(day);
            calendarGrid.add(vPane, i, 0);
        }
    }

    private void generateDate() {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        int firstDay = startOfMonth.getDayOfWeek().getValue();
        int daysInMonth = startOfMonth.getMonth().maxLength();
        if (year % 4 != 0 && (startOfMonth.getMonth() == Month.FEBRUARY)) {
            daysInMonth = 28;
        }

        double height = calendarGrid.getHeight();
        double maxHeight = height/7;

        int offset = firstDay;
        int lblCount = 1;

        for (int i = 0; i < 7; i++) {
            VBox vPane = new VBox();
            vPane.getStyleClass().add("calendar_pane");
            GridPane.setVgrow(vPane, Priority.ALWAYS);
            if (i < offset) {
                vPane.setStyle("-fx-background-color: #E9F2F5");
            } else {
                vPane.setId(Integer.toString(lblCount));
                Label lbl = new Label(Integer.toString(lblCount));
                lbl.setPadding(new Insets(5));
                lbl.setStyle("-fx-text-fill:darkslategray");
                vPane.getChildren().add(lbl);
                lblCount++;
            }
            calendarGrid.add(vPane, i, 1);
        }

        for (int i = 2; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                VBox vPane = new VBox();
                vPane.getStyleClass().add("calendar_pane");
                GridPane.setVgrow(vPane, Priority.ALWAYS);
                if (lblCount < daysInMonth) {
                    vPane.setId(Integer.toString(lblCount));
                    Label lbl = new Label(Integer.toString(lblCount));
                    lbl.setPadding(new Insets(5));
                    lbl.setStyle("-fx-text-fill:darkslategray");
                    vPane.getChildren().add(lbl);
                    lblCount++;
                } else {
                    vPane.setStyle("-fx-background-color: #E9F2F5");
                }
                calendarGrid.add(vPane, j, i);
            }
        }
    }
}
