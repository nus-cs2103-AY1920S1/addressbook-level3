package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.finance.Budget;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An UI component that displays information of a {@code Project}.
 */
public class ProjectOverview extends UiPart<Region> {

    private static final String FXML = "ProjectOverview.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Project project;
    public final ObservableList<Project> projects;

    @FXML
    private ScrollPane cardPane;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label memberTitle;
    @FXML
    private FlowPane members;
    @FXML
    private Label taskTitle;
    @FXML
    private FlowPane tasks;
    @FXML
    private Label meetingTitle;
    @FXML
    private FlowPane meetings;
    @FXML
    private VBox wrapper;

    public ProjectOverview(ObservableList<Project> projects, Project project) {
        super(FXML);
        this.projects = projects;
        this.project = projects.filtered(x -> x.getTitle() == project.getTitle()).get(0);
        int count = 0;

        cardPane.setFitToWidth(true);
        title.setText(project.getTitle().title);
        description.setText(project.getDescription().description);

        memberTitle.setText("Members:");
        members.setOrientation(Orientation.VERTICAL);
        members.setPrefWrapLength(100);
        project.getMemberNames().forEach(member -> members.getChildren().add(new Label(member)));

        for (Task task : project.getTasks()) {
            tasks.getChildren().add(new Label("    " + ++count + ". " + task.toString()));
        }
        taskTitle.setText("Tasks: ");
        tasks.setOrientation(Orientation.VERTICAL);
        tasks.setPrefWrapLength(100);

        meetingTitle.setText("Meetings: ");
        displayMeeting(meetings, this.project);

        //Defining the x axis
        CategoryAxis xAxis = new CategoryAxis();

        xAxis.setCategories(FXCollections.observableArrayList(project.getFinance().getBudgetObservableList().stream().map(x -> x.getName()).collect(Collectors.toList())));
        xAxis.setLabel("budgets");

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");

        //Creating the Bar chart
        StackedBarChart<String, Number> stackedBarChart =
                new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle("Budget Summary");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> amountSpent = new XYChart.Series<>();
        amountSpent.setName("Amount spent");
        XYChart.Series<String, Number> remainingAmount = new XYChart.Series<>();
        remainingAmount.setName("Amount remaining");
        for (Budget budget : project.getFinance().getBudgetObservableList()) {
            amountSpent.getData().add(new XYChart.Data<>(budget.getName(), budget.getAmount().subtract(budget.getRemainingAmount())));
            remainingAmount.getData().add(new XYChart.Data<>(budget.getName(), budget.getRemainingAmount()));
        }

        //Setting the data to bar chart
        stackedBarChart.getData().addAll(amountSpent, remainingAmount);

        //Add the chart to the HBox
        wrapper.getChildren().add(stackedBarChart);
    }

    public void displayMeeting(FlowPane meetings, Project project) {
        meetings.setOrientation(Orientation.VERTICAL);
        meetings.setPrefWrapLength(100);
        List<Meeting> listOfMeetings = new ArrayList<Meeting>(project.getListOfMeeting());
        int meetingCount = 1;
        listOfMeetings.sort(Comparator.comparing(m -> m.getTime().getDate()));
        for (Meeting meeting: listOfMeetings) {
            meetings.getChildren().add(new Label("    " + meetingCount++ + ". " + meeting.getDescription().description + " " + meeting.getTime().time));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectOverview)) {
            return false;
        }

        // state check
        ProjectOverview card = (ProjectOverview) other;
        return project.equals(card.project);
    }
}
