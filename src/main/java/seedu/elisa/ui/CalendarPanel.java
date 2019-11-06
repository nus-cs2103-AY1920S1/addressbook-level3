package seedu.elisa.ui;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seedu.elisa.commons.core.LogsCenter;
import seedu.elisa.commons.core.item.Item;

/**
 * The calendar panel for Elisa
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private int year;
    private int month;

    @FXML
    private Label monthAndYear;

    @FXML
    private GridPane calendarGrid;

    /**
     * Creates a calendar panel base on the current date.
     * @param visualList
     */
    public CalendarPanel(ObservableList<Item> visualList) {
        super(FXML);
        LocalDateTime currentTime = LocalDateTime.now();
        this.month = currentTime.getMonthValue();
        this.year = currentTime.getYear();
        monthAndYear.setText(String.format("%s, %s", Month.of(month), String.valueOf(year)));
        initializeCalendarGrid();
        loadData(visualList);
        visualList.addListener(new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> c) {
                System.out.println("Change");
                clearCells();
                generateDate();
                loadData(visualList);
            }
        });
    }

    private void initializeCalendarGrid() {
        generateHeader();
        generateDate();
    }

    /**
     * Load the events from the visualization list to the calendar.
     * @param visualList the list containing all the data to be loaded from.
     */
    private void loadData(ObservableList<Item> visualList) {
        HashMap<Integer, ArrayList<Item>> monthEvents = new HashMap<>();
        for (Item item : visualList) {
            if (!isMonth(item)) {
                continue;
            } else {
                int day = getDate(item);
                if (!monthEvents.containsKey(day)) {
                    monthEvents.put(day, new ArrayList<Item>());
                }
                monthEvents.get(day).add(item);
            }
        }

        for (Integer i : monthEvents.keySet()) {
            ArrayList<Item> temp = monthEvents.get(i);
            int size = temp.size() - 1;
            Label lbl = new Label();
            lbl.setPadding(new Insets(0, 5, 0, 5));
            Item item = temp.get(0);
            if (size > 0) {
                lbl.setText(String.format("%s + %d event(s)", item.getItemDescription().getDescription(), size));
            } else {
                lbl.setText(String.format("%s", item.getItemDescription().getDescription()));
            }
            String priority = item.getPriority().toString();
            switch (priority) {
            case "HIGH":
                lbl.setStyle("-fx-background-color: red; -fx-background-radius: 15, 15, 15, 15");
                lbl.setTextFill(Color.WHITE);
                break;
            case "MEDIUM":
                lbl.setStyle("-fx-background-color: orange; -fx-background-radius: 15, 15, 15, 15");
                break;
            case "LOW":
                lbl.setStyle("-fx-background-color: green; -fx-background-radius: 15, 15, 15, 15");
                break;
            default:
            }
            Node node = calendarGrid.lookup("#" + Integer.toString(i));
            VBox pane = (VBox) node;
            pane.getChildren().add(lbl);
        }


        /*
        ObservableList<Item> eventList = visualList.filtered(x -> x.hasEvent());
        System.out.println("check here?");
        ObservableList<Item> monthEvent = eventList.filtered(x -> x.getEvent()
                .get().getStartDateTime().getMonthValue() == month);

        HashMap<Integer, Integer> checker = new HashMap<>();
        if (!monthEvent.isEmpty()) {
            for (Item item: monthEvent) {
                int date = item.getEvent().get().getStartDateTime().getDayOfMonth();
                if (checker.containsKey(date)) {
                    checker.put(date, checker.get(date) + 1);
                    if (checker.get(date) > 2) {
                        continue;
                    }
                } else {
                    checker.put(date, 1);
                }
                Node node = calendarGrid.lookup("#" + Integer.toString(date));
                VBox pane = (VBox) node;

                Label lbl = new Label();
                lbl.setText(item.getItemDescription().toString());
                lbl.setPadding(new Insets(0, 5, 0, 5));

                String priority = item.getPriority().toString();
                switch (priority) {
                case "HIGH":
                    lbl.setStyle("-fx-background-color: red; -fx-background-radius: 15, 15, 15, 15");
                    break;
                case "MEDIUM":
                    lbl.setStyle("-fx-background-color: orange; -fx-background-radius: 15, 15, 15, 15");
                    break;
                case "LOW":
                    lbl.setStyle("-fx-background-color: green; -fx-background-radius: 15, 15, 15, 15");
                    break;
                default:
                }

                if (node instanceof VBox) {
                    pane.getChildren().add(lbl);
                }
            }
        }
        */
    }

    /**
     * Check if an item belongs to the current month.
     * @param item the item to be checked for.
     * @return a boolean value if the item is happening within the month.
     */
    private boolean isMonth(Item item) {
        if (!item.hasEvent()) {
            return false;
        } else {
            return item.getEvent().get().getStartDateTime().getMonthValue() == month;
        }
    }

    private int getDate(Item item) {
        return item.getEvent().get().getStartDateTime().getDayOfMonth();
    }

    /**
     * Generates the header of the calendar
     */
    private void generateHeader() {
        for (int i = 0; i < 7; i++) {
            VBox vPane = new VBox();
            vPane.getStyleClass().add("calendar_pane");
            GridPane.setVgrow(vPane, Priority.NEVER);
            Label day = new Label();
            day.setAlignment(Pos.CENTER);
            day.setText(DayOfWeek.of(i + 1).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            vPane.getChildren().add(day);
            calendarGrid.add(vPane, i, 0);
        }
    }

    /**
     * Generates the date on the calendar base on the current month.
     */
    private void generateDate() {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        int firstDay = startOfMonth.getDayOfWeek().getValue();
        int daysInMonth = startOfMonth.getMonth().maxLength();
        if (year % 4 != 0 && (startOfMonth.getMonth() == Month.FEBRUARY)) {
            daysInMonth = 28;
        }

        double height = calendarGrid.getHeight();
        double maxHeight = height / 7;

        int offset = firstDay - 1;
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
                if (lblCount <= daysInMonth) {
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

    /**
     * Helper method to clear all the cells so that it can be updated.
     */
    private void clearCells() {
        ObservableList<Node> allCells = calendarGrid.getChildren();
        for (Node cell: allCells) {
            if (GridPane.getRowIndex(cell) != 0) {
                VBox pane = (VBox) cell;
                pane.getChildren().clear();
            }
        }
    }
}
