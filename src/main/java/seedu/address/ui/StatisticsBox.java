package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.statistics.GenerateStatisticsCommand;
import seedu.address.logic.commands.statistics.GenerateStatisticsDetailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * A UI for the Statistics Tab.
 */
public class StatisticsBox extends Tabs<AnchorPane> {
    private static final String FXML = "Statistics.fxml";

    @FXML
    private ListView<Event> statisticsListView;

    @FXML
    private PieChart employeeTagPieChart;

    @FXML
    private PieChart eventTagPieChart;

    @FXML
    private Label statisticsListLabel;

    @FXML
    private Label statisticsLabel;

    @FXML
    private Label employeeLabel;

    @FXML
    private Label eventLabel;


    public StatisticsBox(ObservableList<Event> eventList, Logic logic, MainWindow mainWindow) {
        super(FXML, mainWindow, logic);

        ObservableList<Event> filterEventNeedingManpower = eventList
                .filtered(event -> !event.isPastEvent())
                .filtered(event -> event.getCurrentManpowerCount() < event.getManpowerNeeded().value)
                .sorted(Comparator.comparing(Event::getStartDate));
        statisticsListView.setItems(filterEventNeedingManpower);
        statisticsListView.setCellFactory(listView -> new EventListViewCell());
        employeeLabel.setText("# employees");
        eventLabel.setText("# events");
        generatePieChart();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);
            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCardForStats(event, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Generate custom Pie Charts to display {@code Event} and {@code Employee} {@code Tag} data.
     */
    public void generatePieChart() {
        ObservableList<PieChart.Data> pieChartEmployeeTagData = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> pieChartEventTagData = FXCollections.observableArrayList();
        List<Employee> employeeList = logic.getFullEmployeeList();
        ObservableList<Event> filteredEventList = logic.getFullEventList();
        ObservableList<Event> eventList = filteredEventList
                .filtered(event -> !event.isPastEvent())
                .filtered(event -> event.getCurrentManpowerCount() < event.getManpowerNeeded().value)
                .sorted(Comparator.comparing(Event::getStartDate));
        Map<String, Integer> employeeTagMap = new TreeMap<>();
        Map<String, Integer> eventTagMap = new TreeMap<>();
        Set<Tag> tempSet;

        for (Employee employee : employeeList) {
            tempSet = employee.getTags();
            for (Tag tag : tempSet) {
                if (employeeTagMap.containsKey(tag.getTagName().toLowerCase())) {
                    int count = employeeTagMap.get(tag.getTagName().toLowerCase());
                    employeeTagMap.put(tag.getTagName().toLowerCase(), count + 1);
                } else {
                    employeeTagMap.put(tag.getTagName().toLowerCase(), 1);
                }
            }
        }

        for (Event event : eventList) {
            tempSet = event.getTags();
            for (Tag tag : tempSet) {
                if (eventTagMap.containsKey(tag.getTagName().toLowerCase())) {
                    int count = eventTagMap.get(tag.getTagName().toLowerCase());
                    eventTagMap.put(tag.getTagName().toLowerCase(), count + 1);
                } else {
                    eventTagMap.put(tag.getTagName().toLowerCase(), 1);
                }
            }
        }

        employeeTagMap.remove("male");
        employeeTagMap.remove("female");

        for (Map.Entry<String, Integer> employeeEntry : employeeTagMap.entrySet()) {
            pieChartEmployeeTagData.add(new PieChart.Data(employeeEntry.getKey(), employeeEntry.getValue()));
        }

        for (Map.Entry<String, Integer> eventEntry : eventTagMap.entrySet()) {
            pieChartEventTagData.add(new PieChart.Data(eventEntry.getKey(), eventEntry.getValue()));
        }

        employeeTagPieChart.setData(pieChartEmployeeTagData);
        eventTagPieChart.setData(pieChartEventTagData);
    }

    /**
     * Generate statistics on the statistics tab, refreshes after each button press.
     *
     * @throws ParseException   thrown when input format is in the wrong order or format and could not be parsed.
     * @throws CommandException thrown when input format is in the wrong format.
     */
    @FXML
    private void generateStatistics() throws ParseException, CommandException {
        mainWindow.executeCommand(GenerateStatisticsCommand.COMMAND_WORD);
        generatePieChart();
        employeeLabel.setText("# employees");
        eventLabel.setText("# events");
    }

    /**
     * Generates a new window which will display statistics in detail.
     *
     * @throws ParseException   thrown when input format is in the wrong order or format and could not be parsed.
     * @throws CommandException thrown when input format is in the wrong format.
     */
    @FXML
    private void generateDetailedStatistics() throws ParseException, CommandException {
        mainWindow.executeCommand(GenerateStatisticsDetailCommand.COMMAND_WORD);
    }

    /**
     * Generate data from pie chart when mouse click on sections of pie chart.
     */
    @FXML
    private void mouseClick(MouseEvent mouseevent) {
        for (final PieChart.Data data : employeeTagPieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    employeeLabel.setText((int) data.getPieValue() + " employees");
                });
        }

        for (final PieChart.Data data : eventTagPieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    eventLabel.setText((int) data.getPieValue() + " events");
                });
        }
    }
}
