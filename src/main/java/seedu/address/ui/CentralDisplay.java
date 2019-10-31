package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;

import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAccommodationCommand;
import seedu.address.logic.commands.AddActivityCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddDayCommand;
import seedu.address.logic.commands.AutoScheduleCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteAccommodationCommand;
import seedu.address.logic.commands.DeleteActivityCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteDayCommand;
import seedu.address.logic.commands.EditAccommodationCommand;
import seedu.address.logic.commands.EditActivityCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.OptimiseBudgetCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnscheduleCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.contact.Contact;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;
import seedu.address.model.tag.Tag;

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
    private ListView<Node> infoList;
    @FXML
    private Tab helpTab;
    @FXML
    private ListView<Node> helpList;

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
            public String getUserAgentStylesheet() {
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

    /**
     * Refreshes the skin and redetermine the number of dates to display.
     */
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

    /**
     * Displays the relevant information in infoList from command executed.
     */
    public void changeInfo(ResultInformation[] resultInformation) {
        infoList.getItems().clear();
        for (ResultInformation i : resultInformation) {
            i.getAccommodation().ifPresent(accommodation ->
                    addAccommodationInfo(accommodation, i.getIndex(), i.getDescription().orElse(""))
            );
            i.getActivity().ifPresent(activity ->
                    addActivityInfo(activity, i.getIndex(), i.getDescription().orElse(""))
            );
            i.getContact().ifPresent(contact ->
                    addContactInfo(contact, i.getIndex(), i.getDescription().orElse(""))
            );
        }
    }

    private void addAccommodationInfo(Accommodation accommodation, Index displayedIndex, String description) {
        infoList.getItems().add(
                new AccommodationCardFull(accommodation, displayedIndex.getOneBased(), description).getRoot());
    }

    private void addContactInfo(Contact contact, Index displayedIndex, String description) {
        infoList.getItems().add(
                new ContactCardFull(contact, displayedIndex.getOneBased(), description).getRoot());
    }

    private void addActivityInfo(Activity activity, Index displayedIndex, String description) {
        infoList.getItems().add(
                new ActivityCardFull(activity, displayedIndex.getOneBased(), description).getRoot());
    }

    /**
     * Expands tabs according to the provided {@code uiFocus}.
     */
    public void changeFocus(UiFocus[] uiFocus) {
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

    /**
     * Generates a complete summary of all commands available in plan2travel.
     */
    public void generateCommandHelpSummary() {
        helpList.getItems().addAll(
                new HelpCard(AddAccommodationCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(AddActivityCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(AddContactCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(AddDayCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(DeleteAccommodationCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(DeleteActivityCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(DeleteContactCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(DeleteDayCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(EditAccommodationCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(EditActivityCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(EditContactCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ViewCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ListCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ScheduleCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(AutoScheduleCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(OptimiseBudgetCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(UnscheduleCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(UndoCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(RedoCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ClearCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ExitCommand.MESSAGE_USAGE).getRoot()
        );
    }

    /**
     * Updates the agenda with activities in every day of {@code dayList}.
     * @param agenda the agenda that is updated
     * @param dayList the latest dayList from model
     */
    private void updateAgenda(Agenda agenda, ObservableList<Day> dayList) {
        agenda.appointments().clear();
        int currDayNum = 1;
        for (Day day : dayList) {
            addAppointmentsWithDay(agenda, day, Index.fromOneBased(currDayNum));
            currDayNum++;
        }
    }

    /**
     * Adds all activities in a day to the agenda.
     * @param agenda the agenda to add to
     * @param day the day to search for activities
     * @param dayIndex the nth day of the itinerary
     */
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

    /**
     * Returns a string with the name and tags of the {@code activity}.
     */
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
