package seedu.address.ui.calendar;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.calendar.commands.GoCommand;
import seedu.address.model.calendar.task.Task;


/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "CalendarListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final ObservableList<Task> mainTaskList;
    private ObservableList<Task> weekTaskList;

    @FXML
    private ListView<Task> mondayListView;
    @FXML
    private ListView<Task> tuesdayListView;
    @FXML
    private ListView<Task> wednesdayListView;
    @FXML
    private ListView<Task> thursdayListView;
    @FXML
    private ListView<Task> fridayListView;
    @FXML
    private ListView<Task> saturdayListView;
    @FXML
    private ListView<Task> sundayListView;


    public PersonListPanel(ObservableList<Task> taskList) {
        super(FXML);

        this.mainTaskList = taskList;
        weekTaskList = taskList.filtered(task -> task.getWeek() == GoCommand.getCurrentWeek() || task.isPersistent());

        FilteredList<Task> mondayTaskList =
            weekTaskList.filtered(task -> task.getTaskDay().toString().equals("monday"));
        FilteredList<Task> tuesdayTaskList =
            weekTaskList.filtered(task -> task.getTaskDay().toString().equals("tuesday"));
        FilteredList<Task> wednesdayTaskList =
            weekTaskList.filtered(task -> task.getTaskDay().toString().equals("wednesday"));
        FilteredList<Task> thursdayTaskList =
            weekTaskList.filtered(task -> task.getTaskDay().toString().equals("thursday"));
        FilteredList<Task> fridayTaskList =
            weekTaskList.filtered(task -> task.getTaskDay().toString().equals("friday"));
        FilteredList<Task> saturdayTaskList =
            weekTaskList.filtered(task -> task.getTaskDay().toString().equals("saturday"));
        FilteredList<Task> sundayTaskList =
            weekTaskList.filtered(task -> task.getTaskDay().toString().equals("sunday"));

        mondayListView.setItems(mondayTaskList);
        tuesdayListView.setItems(tuesdayTaskList);
        wednesdayListView.setItems(wednesdayTaskList);
        thursdayListView.setItems(thursdayTaskList);
        fridayListView.setItems(fridayTaskList);
        saturdayListView.setItems(saturdayTaskList);
        sundayListView.setItems(sundayTaskList);

        mondayListView.setCellFactory(listView -> new PersonListViewCell());
        tuesdayListView.setCellFactory(listView -> new PersonListViewCell());
        wednesdayListView.setCellFactory(listView -> new PersonListViewCell());
        thursdayListView.setCellFactory(listView -> new PersonListViewCell());
        fridayListView.setCellFactory(listView -> new PersonListViewCell());
        saturdayListView.setCellFactory(listView -> new PersonListViewCell());
        sundayListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                for (int i = 0; i < weekTaskList.size(); i++) {
                    if (weekTaskList.get(i).equals(task)) {
                        if (task.isPersistent()) {
                            setGraphic(new ModuleTaskCard(task, i + 1).getRoot());
                        } else {
                            setGraphic(new PersonCard(task, i + 1).getRoot());
                        }
                        break;
                    }
                }
            }
        }
    }

}
