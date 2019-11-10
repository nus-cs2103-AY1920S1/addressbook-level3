package com.typee.ui.calendar;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.model.engagement.Engagement;
import com.typee.ui.EngagementCard;
import com.typee.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Controller for a single day engagements display window.
 */
public class SingleDayEngagementsDisplayWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(com.typee.ui.HelpWindow.class);
    private static final String FXML = "SingleDayEngagementsDisplayWindow.fxml";

    private Stage root;
    private LocalDate date;

    @FXML
    private ListView<Engagement> engagementListView;

    /**
     * Creates a new {@code SingleDayEngagementsDisplayWindow}.
     *
     * @param root Stage to use as the root of the {@code SingleDayEngagementsDisplayWindow}.
     */
    public SingleDayEngagementsDisplayWindow(Stage root) {
        super(FXML, root);
        this.root = root;
    }

    /**
     * Creates a new {@code SingleDayEngagementsDisplayWindow}.
     */
    public SingleDayEngagementsDisplayWindow(ObservableList<Engagement> engagements) {
        this(new Stage());
        engagementListView.setItems(engagements);
        engagementListView.setCellFactory(listView -> new EngagementListViewCell());
    }

    /**
     * Shows the single day engagements display window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing single day engagements display window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the single day engagements display window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help single day engagements display window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the single day engagements display window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    public void setDate(LocalDate date) {
        this.date = date;
        root.setTitle(getDateString());
    }

    private String getDateString() {
        String dateString = String.format("Engagements for %02d/%02d/%04d",
                date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        return dateString;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Engagement} using an {@code EngagementCard}.
     */
    class EngagementListViewCell extends ListCell<Engagement> {
        @Override
        protected void updateItem(Engagement engagement, boolean empty) {
            super.updateItem(engagement, empty);

            if (empty || engagement == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EngagementCard(engagement, getIndex() + 1).getRoot());
            }
        }
    }

}
