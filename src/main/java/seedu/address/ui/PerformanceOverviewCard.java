package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import seedu.address.model.performanceoverview.PerformanceOverview;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A UI component that displays the information of a {@code PerformanceOverview}.
 */
public class PerformanceOverviewCard extends UiPart<Region> {

    public static final String FXML = "PerformanceOverviewCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final PerformanceOverview performanceOverview;
    public final List<Person> memberList;

    @FXML
    private ScrollPane cardPane;
    @FXML
    private Label title;
    @FXML
    private VBox chartWrapper;
    @FXML
    private VBox performanceCardsWrapper;

    public PerformanceOverviewCard(PerformanceOverview performanceOverview) {
        super(FXML);
        this.performanceOverview = performanceOverview;
        this.memberList = performanceOverview.getSortedMemberList();
        title.setText(performanceOverview.getProject().getTitle().title + "\nMember Performance Overview");

        cardPane.setFitToWidth(true);
        cardPane.setPrefHeight(850);
        //Defining the x axis
        CategoryAxis xAxis1 = new CategoryAxis();

        xAxis1.setCategories(FXCollections.observableArrayList(performanceOverview.getSortedMemberList().stream()
                .map(person -> person.getName().fullName).collect(Collectors.toList())));
        xAxis1.setLabel("Members");
        xAxis1.setTickLabelFont(Font.font(15));

        CategoryAxis xAxis2 = new CategoryAxis();

        xAxis2.setCategories(FXCollections.observableArrayList(performanceOverview.getSortedMemberList().stream()
                .map(person -> person.getName().fullName).collect(Collectors.toList())));
        xAxis2.setLabel("Members");
        xAxis2.setTickLabelFont(Font.font(15));

        CategoryAxis xAxis3 = new CategoryAxis();

        xAxis3.setCategories(FXCollections.observableArrayList(performanceOverview.getSortedMemberList().stream()
                .map(person -> person.getName().fullName).collect(Collectors.toList())));
        xAxis3.setLabel("Members");
        xAxis3.setTickLabelFont(Font.font(15));

        CategoryAxis xAxis4 = new CategoryAxis();

        xAxis4.setCategories(FXCollections.observableArrayList(performanceOverview.getSortedMemberList().stream()
                .map(person -> person.getName().fullName).collect(Collectors.toList())));
        xAxis4.setLabel("Members");
        xAxis4.setTickLabelFont(Font.font(15));

        //Defining y axis for number of tasks done
        NumberAxis yAxisTask = new NumberAxis();
        yAxisTask.setLabel("No. of tasks");
        yAxisTask.setId("yAxis-title");
        yAxisTask.setTickUnit(1);

        //Defining y axis for percentage of tasks done
        NumberAxis yAxisTaskPercentage = new NumberAxis(0, 100, 10);
        yAxisTaskPercentage.setLabel("% of assigned task completed");
        yAxisTaskPercentage.setId("yAxis-title");

        //Defining y axis for number of meetings attended
        NumberAxis yAxisMeeting = new NumberAxis(0, performanceOverview.getProject().getListOfMeeting().size(), 1);
        yAxisMeeting.setLabel("No. of meetings attended");
        yAxisMeeting.setId("yAxis-title");

        //Defining y axis for rate of attendance
        NumberAxis yAxisMeetingRate = new NumberAxis(0, 100, 10);
        yAxisMeetingRate.setLabel("Rate of attendance");
        yAxisMeetingRate.setId("yAxis-title");

        //Creating bar chart for number of tasks done
        BarChart<String, Number> taskDoneChart = new BarChart<>(xAxis1, yAxisTask);
        taskDoneChart.setTitle("Number of tasks completed");

        //Creating bar chart for percentage of tasks done
        BarChart<String, Number> percentageOfTasksDoneChart = new BarChart<>(xAxis2, yAxisTaskPercentage);
        percentageOfTasksDoneChart.setTitle("Percentage of tasks completed");

        //Creating bar chart for number of meetings attended
        BarChart<String, Number> meetingsAttendedChart = new BarChart<>(xAxis3, yAxisMeeting);
        meetingsAttendedChart.setTitle("Number of meetings attended");

        //Creating bar chart for percentage of meetings attended
        BarChart<String, Number> attendanceRateChart = new BarChart<>(xAxis4, yAxisMeetingRate);
        attendanceRateChart.setTitle("Rate of attendance");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> tasksDone = new XYChart.Series<>();
        tasksDone.setName("No. of tasks");

        XYChart.Series<String, Number> percentageTasksDone = new XYChart.Series<>();
        percentageTasksDone.setName("% of tasks completed");

        XYChart.Series<String, Number> meetingsAttended = new XYChart.Series<>();
        meetingsAttended.setName("No. of meetings attended");

        XYChart.Series<String, Number> rateOfAttendance = new XYChart.Series<>();
        rateOfAttendance.setName("% of meetings attended");

        int index = 1;
        for (Person person : performanceOverview.getSortedMemberList()) {
            String name = person.getName().fullName;

            tasksDone.getData().add(new XYChart.Data<>(name, performanceOverview.getNumOfTaskDoneOf(person)));
            percentageTasksDone.getData().add(new XYChart.Data<>(name, performanceOverview.getTaskCompletionRateOf(person).getRate()));
            meetingsAttended.getData().add(new XYChart.Data<>(name, performanceOverview.getAttendanceOf(person)));
            rateOfAttendance.getData().add(new XYChart.Data<>(name, performanceOverview.getRateOfAttendanceOf(person).getRate()));

            performanceCardsWrapper.getChildren().add(new IndividualPerformanceCard(person, performanceOverview, index).getRoot());
            index++;
        }

        taskDoneChart.getData().addAll(tasksDone);
        taskDoneChart.setId("charts");
        taskDoneChart.setPadding(new Insets(0, 0, 50, 0));
        percentageOfTasksDoneChart.getData().addAll(percentageTasksDone);
        percentageOfTasksDoneChart.setId("charts");
        percentageOfTasksDoneChart.setPadding(new Insets(0, 0, 50, 0));
        meetingsAttendedChart.getData().addAll(meetingsAttended);
        meetingsAttendedChart.setId("charts");
        meetingsAttendedChart.setPadding(new Insets(0, 0, 50, 0));
        attendanceRateChart.getData().addAll(rateOfAttendance);
        attendanceRateChart.setId("charts");
        attendanceRateChart.setPadding(new Insets(0, 0, 50, 0));

        chartWrapper.getChildren().addAll(taskDoneChart, percentageOfTasksDoneChart, meetingsAttendedChart, attendanceRateChart);
    }

}
