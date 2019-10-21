package seedu.address.ui.calendar;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendar.person.Task;


/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "CalendarListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

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

//        for (Task task : taskList) {
//            switch (task.getTaskDay().toString()) {
//            case "monday":
//                mondayListView.getItems().add(task);
//                break;
//            case "tuesday":
//                tuesdayListView.getItems().add(task);
//                break;
//            case "wednesday":
//                wednesdayListView.getItems().add(task);
//                break;
//            case "thursday":
//                thursdayListView.getItems().add(task);
//                break;
//            case "friday":
//                fridayListView.getItems().add(task);
//                break;
//            case "saturday":
//                saturdayListView.getItems().add(task);
//                break;
//            case "sunday":
//            default:
//                sundayListView.getItems().add(task);
//                break;
//
//            }
//        }



        mondayListView.setItems(taskList);
        tuesdayListView.setItems(taskList);
        wednesdayListView.setItems(taskList);
        thursdayListView.setItems(taskList);
        fridayListView.setItems(taskList);
        saturdayListView.setItems(taskList);
        sundayListView.setItems(taskList);


        mondayListView.setCellFactory(listView -> new MondayListViewCell());
        tuesdayListView.setCellFactory(listView -> new TuesdayListViewCell());
        wednesdayListView.setCellFactory(listView -> new WednesdayListViewCell());
        thursdayListView.setCellFactory(listView -> new ThursdayListViewCell());
        fridayListView.setCellFactory(listView -> new FridayListViewCell());
        saturdayListView.setCellFactory(listView -> new SaturdayListViewCell());
        sundayListView.setCellFactory(listView -> new SundayListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null ) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(task, getIndex() + 1).getRoot());
            }
        }
    }

    class MondayListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null || !task.getTaskDay().toString().equals("monday")) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(task, getIndex() + 1).getRoot());
            }
        }
    }

    class TuesdayListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null || !task.getTaskDay().toString().equals("tuesday")) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(task, getIndex() + 1).getRoot());
            }
        }
    }

    class WednesdayListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null || !task.getTaskDay().toString().equals("wednesday")) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(task, getIndex() + 1).getRoot());
            }
        }
    }

    class ThursdayListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null || !task.getTaskDay().toString().equals("thursday")) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(task, getIndex() + 1).getRoot());
            }
        }
    }

    class FridayListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null || !task.getTaskDay().toString().equals("friday")) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(task, getIndex() + 1).getRoot());
            }
        }
    }

    class SaturdayListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null || !task.getTaskDay().toString().equals("saturday")) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(task, getIndex() + 1).getRoot());
            }
        }
    }

    class SundayListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null || !task.getTaskDay().toString().equals("sunday")) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}
