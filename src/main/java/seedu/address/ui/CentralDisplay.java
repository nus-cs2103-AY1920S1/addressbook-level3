package seedu.address.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.model.contact.Contact;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.activity.Activity;
import seedu.address.model.tag.Tag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A ui for the split window that is displayed at the center of the application.
 */
public class CentralDisplay extends UiPart<Region> {

    private static final String FXML = "CentralDisplay.fxml";

    @FXML
    private Accordion sideDisplay;

    @FXML
    private TitledPane activityPane;

    @FXML
    private TitledPane contactPane;

    @FXML
    private TitledPane accommodationPane;

    @FXML
    private TabPane tabDisplay;

    @FXML
    private Tab agendaTab;

    @FXML
    private Tab infoTab;

    @FXML
    private Tab helpTab;

    private SimpleObjectProperty<LocalDate> startDateProperty;
    private ObservableList<Day> dayList;

    public CentralDisplay(ObservableList<Day> dayList, ObservableList<Accommodation> accommodationList,
                          ObservableList<Activity> activityList, ObservableList<Contact> contactList,
                          SimpleObjectProperty<LocalDate> startDateProperty) {
        super(FXML);
        this.startDateProperty = startDateProperty;
        this.dayList = dayList;

        // initialising agenda
        Agenda agenda = new Agenda() {
            @Override
            public String getUserAgentStylesheet()
            {
                return Agenda.class.getResource("/view/" + Agenda.class.getSimpleName() + ".css")
                        .toExternalForm();
            }
            @Override
            public Skin<?> createDefaultSkin() {
                return new AgendaWeekSkin(this) {
                    @Override
                    protected List<LocalDate> determineDisplayedLocalDates() {
                        // the result
                        List<LocalDate> lLocalDates = new ArrayList<>();
                        LocalDate lStartLocalDate = startDateProperty.getValue();
                        if (dayList.size() == 0) {
                            lLocalDates.add(lStartLocalDate);
                        } else {
                            for (int i = 0; i < dayList.size(); i++) {
                                lLocalDates.add(lStartLocalDate.plusDays(i));
                            }
                        }
                        // done
                        return lLocalDates;
                    }
                };
            }
        };

        agenda.setDisplayedLocalDateTime(startDateProperty.getValue().atStartOfDay());
        updateAgenda(agenda, dayList);
        sideDisplay.setExpandedPane(activityPane);
        // disables dragging
        agenda.setAllowDragging(false);
        // disables modify start time and end time by dragging
        agenda.setAllowResize(false);
        // disables right click editing
        agenda.setEditAppointmentCallback((appointment) -> null);

        accommodationPane.setContent((new AccommodationListPanel(accommodationList)).getRoot());
        activityPane.setContent((new ActivityListPanel(activityList)).getRoot());
        contactPane.setContent((new ContactListPanel(contactList)).getRoot());
        agendaTab.setContent(agenda);

        tabDisplay.prefWidthProperty().bind(this.getRoot().prefWidthProperty());

        // set up listeners that will update the agenda
        dayList.addListener((ListChangeListener<? super Day>) c -> {
            updateSkin(agenda);
            updateAgenda(agenda, dayList);
        });
        startDateProperty.addListener((observable, oldValue, newValue) -> {
            updateAgenda(agenda, dayList);
            agenda.setDisplayedLocalDateTime(newValue.atStartOfDay());
        });
    }

    private void updateSkin(Agenda agenda) {
        agenda.setSkin(new AgendaWeekSkin(agenda) {
            @Override
            protected List<LocalDate> determineDisplayedLocalDates() {
                // the result
                List<LocalDate> lLocalDates = new ArrayList<>();

                LocalDate lStartLocalDate = startDateProperty.getValue();
                if (dayList.size() == 0) {
                    lLocalDates.add(lStartLocalDate);
                } else {
                    for (int i = 0; i < dayList.size(); i++) {
                        lLocalDates.add(lStartLocalDate.plusDays(i));
                    }
                }
                // done
                return lLocalDates;
            }
        });
    }

    public void changeInfo(ResultInformation resultInformation) {
//        resultInformation.getAccommodation().ifPresent(accommodation -> changeAccommodationInfo(accommodation, resultInformation.getIndex()));
        resultInformation.getActivity().ifPresent(activity -> changeActivityInfo(activity, resultInformation.getIndex()));
        resultInformation.getContact().ifPresent(contact -> changeContactInfo(contact, resultInformation.getIndex()));
    }

//    private void changeAccommodationInfo(Accommodation accommodation, Index displayedIndex) {
//        infoTab.setContent(new );
//    }

    private void changeContactInfo(Contact contact, Index displayedIndex) {
        infoTab.setContent(new ContactCardFull(contact, displayedIndex.getOneBased()).getRoot());
    }

    private void changeActivityInfo(Activity activity, Index displayedIndex) {
        infoTab.setContent(new ActivityCardFull(activity, displayedIndex.getOneBased()).getRoot());
    }

//    private void changeAccommodationInfo(Accommodation accommodation, Index displayedIndex) {
//        infoTab.setContent(new AccommodationCardFull(accommodation, displayedIndex.getOneBased()).getRoot());
//    }

    public void changeFocus(UiFocus ...uiFocus) {
        for (UiFocus u : uiFocus) {
            switch (u) {
            case AGENDA:
                tabDisplay.getSelectionModel().select(agendaTab);
                break;
            case INFO:
                tabDisplay.getSelectionModel().select(infoTab);
                break;
            case HELP:
                tabDisplay.getSelectionModel().select(helpTab);
                break;
            case ACCOMMODATION:
                sideDisplay.setExpandedPane(accommodationPane);
                break;
            case ACTIVITY:
                sideDisplay.setExpandedPane(activityPane);
                break;
            case CONTACT:
                sideDisplay.setExpandedPane(contactPane);
                break;
            default:
                throw new AssertionError(u.toString() + " is not handled in changeFocus.");
            }
        }
    }

    private void updateAgenda(Agenda agenda, ObservableList<Day> dayList) {
        agenda.appointments().clear();
        int currDayNum = 1;
        for (Day day : dayList) {
            addAppointmentsWithDay(agenda, day, Index.fromOneBased(currDayNum));
            currDayNum++;
        }
    }

    private void addAppointmentsWithDay(Agenda agenda, Day day, Index dayIndex) {
        LocalDate currDate = this.startDateProperty.getValue().plusDays(dayIndex.getZeroBased());
        for (ActivityWithTime activityWithTime : day.getListOfActivityWithTime()) {
            String textToDisplay = createSummaryOfAppointment(activityWithTime.getActivity());
            agenda.appointments().add(
                new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(LocalDateTime.of(currDate, activityWithTime.getStartTime()))
                    .withEndLocalDateTime(LocalDateTime.of(currDate, activityWithTime.getEndTime()))
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
