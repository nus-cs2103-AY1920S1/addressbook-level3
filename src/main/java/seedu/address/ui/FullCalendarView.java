package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.task.Task;

/**
 * Constructor for the entire calendar view
 */
public class FullCalendarView extends UiPart<Region> {

    private static final String FXML = "fullCalendar.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;

    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public FullCalendarView(YearMonth yearMonth) {
        super(FXML);
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        calendar.setGridLinesVisible(true);
        calendar.autosize();
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{ new
                Text("Sunday"), new
                Text("Monday"), new
                Text("Tuesday"), new
                Text("Wednesday"), new
                Text("Thursday"), new
                Text("Friday"), new
                Text("Saturday")
        };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        //titleBar.setStyle("-fx-background-color: #F0591E;");
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        view.setStyle("-fx-background-color: #FFDF2F;");
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {

            if (ap.getChildren().size() != 0) {
                /*for (Node node : markedNumbers) {
                    ap.getChildren().remove(node);
                    System.out.println("node deleted");
                }
                markedNumbers.clear();*/
                //ap.getChildren().remove(0 );
                ap.getChildren().clear();
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);

            ObservableList<Task> tasks = UiManager.returnTaskByDate(ap.getDate());

            /*VBox vb = new VBox();
            Text test = new Text("test");
            vb.setPadding(new Insets(0, 10, 0, 7));
            vb.setSpacing(0);
            vb.getChildren().addAll(test);
            //ap.getChildren().add(vb);

            ap.setTopAnchor(vb, 20.0);
            ap.setLeftAnchor(vb, 5.0);


            ListView<Task> taskListView = new ListView<>();
            taskListView.setItems(t);
            taskListView.setCellFactory(listView -> new TaskListViewCell());
            //should look at list and add task to date till no more tasks
            ap.getChildren().add(taskListView);
            ap.setTopAnchor(taskListView, 20.0);
            ap.setLeftAnchor(taskListView, 5.0);
            */

            VBox newvb = new VBox();
            int count = 0;
            for (Task t : tasks) {
                Text names = new Text(t.toWindowString());
                names.setFont(Font.font ("Serif", 10));
                if (count<2) {
                    newvb.getChildren().add(names);
                    count = count +1 ;
                } else if (count==2) {
                    Text dots = new Text("...");
                    dots.setFont(Font.font ("Serif", 10));
                    newvb.getChildren().add(dots);
                    count = count + 1;
                } else {
                    count = count + 1;
                }
            }
            Text num = new Text("Tasks: " + count);
            num.setFont(Font.font ("Cambria", 10));
            ap.getChildren().add(num);
            ap.getChildren().add(newvb);
            ap.setTopAnchor(num, 7.0);
            ap.setLeftAnchor(num, 25.0);
            ap.setTopAnchor(newvb, 20.0);
            ap.setLeftAnchor(newvb, 5.0);


        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class TaskListViewCell extends ListCell<Task> {

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task== null) {
                setText(null);
            } else {
                setText(task.toWindowString());

            }
        }
    }
}
