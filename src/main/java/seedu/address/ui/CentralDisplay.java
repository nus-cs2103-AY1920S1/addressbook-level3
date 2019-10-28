package seedu.address.ui;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.activity.Activity;
import seedu.address.model.tag.Tag;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A ui for the split window that is displayed at the center of the application.
 */
public class CentralDisplay extends UiPart<Region> {

    private static final String FXML = "CentralDisplay.fxml";

    @FXML
    private Accordion sideDisplay;

    @FXML
    private TabPane tabDisplay;

    @FXML
    private Tab agendaTab;

    public CentralDisplay(ObservableList<Day> dayList) {
//    public CentralDisplay() {
        super(FXML);
        Agenda agenda = new Agenda() {
            @Override
            public Skin<?> createDefaultSkin() {
                return new AgendaDaysFromDisplayedSkin(this);
            }
        };
//        Agenda agenda = new Agenda();
        for (Day day : dayList) {
            addAppointmentsWithDay(agenda, day);
        }
        // disables dragging
        agenda.setAllowDragging(false);
        // disables modify start time and end time by dragging
        agenda.setAllowResize(false);
        // disables right click editing
        agenda.setEditAppointmentCallback((appointment) -> null);
//        agenda.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));

        agendaTab.setContent(agenda);
    }

    public void setFocusToAgenda() {
        tabDisplay.getSelectionModel().select(agendaTab);
    }

    private void addAppointmentsWithDay(Agenda agenda, Day day) {
        LocalDate dateStub = LocalDate.now();
        for (ActivityWithTime activityWithTime : day.getListOfActivityWithTime()) {
            String textToDisplay = createSummaryOfAppointment(activityWithTime.getActivity());
            agenda.appointments().add(
                new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(LocalDateTime.of(dateStub, activityWithTime.getStartTime()))
                    .withEndLocalDateTime(LocalDateTime.of(dateStub, activityWithTime.getEndTime()))
                    .withSummary(textToDisplay)
            );
        }
    }

    private String createSummaryOfAppointment(Activity activity) {
        StringBuilder textToDisplay = new StringBuilder(activity.getName().toString())
                .append("\n\nTags: ");
        boolean isFirst = true;
        for (Tag tag: activity.getTags()) {
            if (isFirst) {
                isFirst = false;
                textToDisplay.append(tag.toString());
            } else {
                textToDisplay.append(", ")
                    .append(tag.toString());
            }
        }
        return textToDisplay.toString();
    }
}
