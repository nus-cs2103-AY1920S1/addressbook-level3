package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Panel to hold multiple schedule tables together.
 */
public class ScheduleViewPanel extends UiPart<Region> implements RefreshListener {

    private static final String FXML = "ScheduleViewPanel.fxml";

    private List<ObservableList<ObservableList<String>>> scheduleList;

    private List<ScheduleView> scheduleViewList;

    private List<List<String>> titles;

    @FXML
    private StackPane container;

    ScheduleViewPanel(
            List<List<String>> titles, List<ObservableList<ObservableList<String>>> scheduleList) {
        super(FXML);
        this.scheduleList = scheduleList;
        this.scheduleViewList = new ArrayList<>();
        this.titles = titles;
        fillPanel();
    }
    /**
     * Fill the panel with the tables that is retrieved from scheduleView class.
     */
    private void fillPanel() {
        for (int i = 0; i < this.scheduleList.size(); i++) {
            scheduleViewList.add(new ScheduleView(this.titles.get(i), this.scheduleList.get(i)));
        }
        for (ScheduleView schedule : scheduleViewList) {
            container.getChildren().add(schedule.getRoot());
        }
    }

    /**
     * Fill the panel when user import data.
     */
    public void dataImported() {
        clearData();
        for (int i = 0; i < scheduleList.size(); i++) {
            scheduleViewList.add(new ScheduleView(titles.get(i), scheduleList.get(i)));
        }
        for (ScheduleView schedule : scheduleViewList) {
            container.getChildren().add(schedule.getRoot());
        }
    }

    protected void clearData() {
        this.scheduleViewList.removeAll(this.scheduleViewList);
    }
}



