package seedu.address.ui.panel.calendar;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import javafx.scene.layout.RowConstraints;
import seedu.address.model.events.EventSource;
import seedu.address.ui.EventCardHolder;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

import java.util.ArrayList;
import java.util.List;

public abstract class TimelineView extends UiPart<Region> {

    private static final Integer SPACING = 62;
    private static final Integer TIMING = 10;

    ArrayList<EventCardHolder> eventCardHolders;
    UiParser uiParser;
    Integer totalRows;

    @FXML
    Label timelineTitle;

    @FXML
    GridPane timelineGrid;

    TimelineView(UiParser uiParser, String FXML) {
        super(FXML);
        this.uiParser = uiParser;
    }

    void addEventCardHolders() {
        this.eventCardHolders = new ArrayList<>();
        for(int row = 0; row < totalRows; row++) {
            EventCardHolder eventCardHolder = new EventCardHolder();
            eventCardHolders.add(eventCardHolder);
            timelineGrid.add(eventCardHolder.getRoot(), 1, row);
        }
    }

    void resetTimeline() {
        for (int row = 0; row < totalRows; row++) {
            // Reset RowConstraints
            RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(row);
            rowConstraints.setPrefHeight(0);

            // Reset EventCards for each hour
            eventCardHolders.get(row).removeEventCards();
        }
    }

    void resizeTimelineView() {
        for(int row = 0; row < totalRows; row++) {
            EventCardHolder eventCardHolder = eventCardHolders.get(row);
            RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(row);
            rowConstraints.setPrefHeight(eventCardHolder.getHeight() + SPACING);
        }
    }

    void updateSizeDelay(RowConstraints rowConstraints, EventCardHolder eventCardHolder) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(TIMING);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                // rowConstraints.setPrefHeight(eventCardHolder.getHeight() + SPACING);
                resizeTimelineView();
            }
        });
        new Thread(sleeper).start();
    }

    void addLabels(String ...labels) {
        int row = 0;
        for(String label : labels) {
            TimelineLabel timelineLabel = new TimelineLabel(label);
            timelineGrid.add(timelineLabel.getRoot(), 0, row);
            row++;
        }
    }

    void addGrids() {
        for(int row = 0; row < totalRows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.TOP);
            rowConstraints.setPrefHeight(100);
            rowConstraints.setMinHeight(100);
            timelineGrid.getRowConstraints().add(rowConstraints);
        }
    }


    abstract void eventChange(List<EventSource> eventList);
}
