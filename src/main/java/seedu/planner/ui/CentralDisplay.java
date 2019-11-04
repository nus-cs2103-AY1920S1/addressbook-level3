package seedu.planner.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javafx.beans.property.SimpleObjectProperty;
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

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.AddAccommodationCommand;
import seedu.planner.logic.commands.AddActivityCommand;
import seedu.planner.logic.commands.AddContactCommand;
import seedu.planner.logic.commands.AddDayCommand;
import seedu.planner.logic.commands.AutoScheduleCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.DeleteAccommodationCommand;
import seedu.planner.logic.commands.DeleteActivityCommand;
import seedu.planner.logic.commands.DeleteContactCommand;
import seedu.planner.logic.commands.DeleteDayCommand;
import seedu.planner.logic.commands.EditAccommodationCommand;
import seedu.planner.logic.commands.EditActivityCommand;
import seedu.planner.logic.commands.EditContactCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.commands.OptimiseCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.ScheduleCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.logic.commands.UnscheduleCommand;
import seedu.planner.logic.commands.ViewAccommodationCommand;
import seedu.planner.logic.commands.ViewActivityCommand;
import seedu.planner.logic.commands.ViewCommand;
import seedu.planner.logic.commands.ViewContactCommand;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.Duration;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;

/**
 * A ui for the split window that is displayed at the center of the application.
 */
public class CentralDisplay extends UiPart<Region> {

    private static final String FXML = "CentralDisplay.fxml";

    private final Agenda agenda;

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
    private SimpleObjectProperty<Name> nameProperty;
    private ObservableList<Day> dayList;
    private HashMap<Integer, Agenda.AppointmentGroup> appointmentGroupHashMap;

    public CentralDisplay(ObservableList<Day> dayList, ObservableList<Accommodation> accommodationList,
                          ObservableList<Activity> activityList, ObservableList<Contact> contactList,
                          SimpleObjectProperty<LocalDate> startDateProperty,
                          SimpleObjectProperty<Name> nameProperty) {
        super(FXML);
        this.startDateProperty = startDateProperty;
        this.dayList = dayList;
        this.nameProperty = nameProperty;

        // initialising agenda
        this.agenda = new Agenda() {
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
        setupDesign();
        setupContent(dayList, accommodationList, activityList, contactList);
        setupListeners();
    }

    /**
     * Setup the colour, height, width of parts in the central display.
     */
    private void setupDesign() {
        // allows the tabDisplay to expand according to the width of user display
        tabDisplay.prefWidthProperty().bind(this.getRoot().prefWidthProperty());
    }

    /**
     * Setups the content contained within the central display.
     */
    private void setupContent(ObservableList<Day> dayList, ObservableList<Accommodation> accommodationList,
                              ObservableList<Activity> activityList, ObservableList<Contact> contactList) {
        accommodationPane.setContent((new AccommodationListPanel(accommodationList)).getRoot());
        activityPane.setContent((new ActivityListPanel(activityList)).getRoot());
        contactPane.setContent((new ContactListPanel(contactList)).getRoot());
        // expands the activity pane
        sideDisplay.setExpandedPane(activityPane);
        setupAgendaAppointmentGroups();
        agendaTab.setText(nameProperty.getValue().toString() + " Itinerary");
        agenda.setDisplayedLocalDateTime(startDateProperty.getValue().atStartOfDay());
        updateAgenda(agenda, dayList);
        // disables dragging
        agenda.setAllowDragging(false);
        // disables modify start time and end time by dragging
        agenda.setAllowResize(false);
        // disables right click editing
        agenda.setEditAppointmentCallback((appointment) -> null);
        agendaTab.setContent(agenda);
    }

    private void setupAgendaAppointmentGroups() {
        if (appointmentGroupHashMap == null) {
            this.appointmentGroupHashMap = new HashMap<>();
            appointmentGroupHashMap.put(1, new Agenda.AppointmentGroupImpl().withStyleClass("brown"));
            appointmentGroupHashMap.put(2, new Agenda.AppointmentGroupImpl().withStyleClass("orange"));
            appointmentGroupHashMap.put(3, new Agenda.AppointmentGroupImpl().withStyleClass("softorange"));
            appointmentGroupHashMap.put(4, new Agenda.AppointmentGroupImpl().withStyleClass("softgreen"));
            appointmentGroupHashMap.put(5, new Agenda.AppointmentGroupImpl().withStyleClass("softcyan"));
            appointmentGroupHashMap.put(6, new Agenda.AppointmentGroupImpl().withStyleClass("softblue"));
            appointmentGroupHashMap.put(7, new Agenda.AppointmentGroupImpl().withStyleClass("yellowgreen"));
            appointmentGroupHashMap.put(8, new Agenda.AppointmentGroupImpl().withStyleClass("grayred"));
            appointmentGroupHashMap.put(9, new Agenda.AppointmentGroupImpl().withStyleClass("gray"));
            appointmentGroupHashMap.put(10, new Agenda.AppointmentGroupImpl().withStyleClass("softpurple"));
        }
    }

    /**
     * Setup listeners that will update the central display.
     */
    private void setupListeners() {
        // set up listeners that will update the agenda
        //dayList.addListener((ListChangeListener<? super Day>) c -> {
        //    updateSkin(agenda);
        //    updateAgenda(agenda, dayList);
        //});
        startDateProperty.addListener((observable, oldValue, newValue) -> {
            updateAgenda(agenda, dayList);
            agenda.setDisplayedLocalDateTime(newValue.atStartOfDay());
        });
        nameProperty.addListener((observable, oldValue, newValue) -> {
            agendaTab.setText(newValue.toString() + " Itinerary");
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
                updateSkin(agenda);
                updateAgenda(agenda, dayList);
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
        helpList.getItems().clear();
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
                new HelpCard(ViewAccommodationCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ViewActivityCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ViewContactCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ViewCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ListCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(ScheduleCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(AutoScheduleCommand.MESSAGE_USAGE).getRoot(),
                new HelpCard(OptimiseCommand.MESSAGE_USAGE).getRoot(),
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
        for (Day day : dayList) {
            addAppointmentsWithDay(agenda, day);
        }
    }

    /**
     * Adds all activities in a day to the agenda.
     * @param agenda the agenda to add to
     * @param day the day to search for activities
     */
    private void addAppointmentsWithDay(Agenda agenda, Day day) {
        Random random = new Random();
        for (ActivityWithTime activityWithTime : day.getListOfActivityWithTime()) {
            Optional<Agenda.AppointmentGroup> optionalGroup = activityWithTime.getAppointmentGroup();
            Agenda.AppointmentGroup currGroup;
            if (optionalGroup.isPresent()) {
                currGroup = optionalGroup.get();
            } else {
                currGroup = generateRandomGroup(random);
                activityWithTime.setAppointmentGroup(currGroup);
            }
            String textToDisplay = createSummaryOfAppointment(activityWithTime.getActivity(),
                    activityWithTime.getActivity().getDuration());
            agenda.appointments().add(
                new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(activityWithTime.getStartDateTime())
                    .withEndLocalDateTime(activityWithTime.getEndDateTime())
                    .withSummary(textToDisplay)
                    .withAppointmentGroup(currGroup)
            );
        }
    }


    /**
     * Generates a random appointment group from the appointmentGroupHashMap.
     */
    private Agenda.AppointmentGroup generateRandomGroup(Random random) {
        return appointmentGroupHashMap.get(random.nextInt(appointmentGroupHashMap.size()) + 1);
    }

    /**
     * Returns a string with the name and tags of the {@code activity}.
     */
    private String createSummaryOfAppointment(Activity activity, Duration duration) {
        StringBuilder textToDisplay = new StringBuilder("\n" + activity.getName().toString());

        if (duration.value >= 60) {
            boolean isFirst = true;
            for (Tag tag : activity.getTags()) {
                if (isFirst) {
                    isFirst = false;
                    textToDisplay.append("\nTags: ")
                            .append(tag.toString());
                } else {
                    textToDisplay.append(", ")
                            .append(tag.toString());
                }
            }
        }
        return textToDisplay.toString();
    }
}
