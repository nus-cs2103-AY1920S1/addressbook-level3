package seedu.planner.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Skin;
import javafx.scene.layout.VBox;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.Duration;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;
import seedu.planner.model.tag.Tag;

//@@author 1nefootstep
/**
 * The manager of Agenda.
 */
public class AgendaManager {
    private static final Logger logger = LogsCenter.getLogger(AgendaManager.class);
    private static final int CHAR_LIMIT_BEFORE_TRUNCATING_SUMMARY = 220;
    private static final int MAX_MULTIPLE_OF_DAYLIST_SIZE_BEFORE_CLEARING_HASHMAP = 3;
    private static final String FXML = "InfoListPanel.fxml";

    private final Agenda agenda;
    private HashMap<Integer, Agenda.AppointmentGroup> appointmentGroupHashMap;
    /* To ensure that continuous editing of activity do not make activityToAppointmentGroupHashMap use too much memory*/
    private HashMap<Activity, Agenda.AppointmentGroup> activityToAppointmentGroupHashMap;

    @FXML
    private VBox agendaContainer;

    public AgendaManager(LocalDate startDate, List<Day> dayList) {
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
                        if (dayList.size() == 0) {
                            lLocalDates.add(startDate);
                        } else {
                            for (int i = 0; i < dayList.size(); i++) {
                                lLocalDates.add(startDate.plusDays(i));
                            }
                        }
                        // done
                        return lLocalDates;
                    }
                };
            }
        };
        setupAgenda(startDate, dayList);
    }

    /**
     * Refreshes the skin and redetermine the number of dates to display.
     */
    public void updateSkin(List<Day> dayList, LocalDate startDate) {
        agenda.setSkin(new AgendaWeekSkin(agenda) {
            @Override
            protected List<LocalDate> determineDisplayedLocalDates() {
                // the result
                List<LocalDate> lLocalDates = new ArrayList<>();

                if (dayList.size() == 0) {
                    lLocalDates.add(startDate);
                } else {
                    for (int i = 0; i < dayList.size(); i++) {
                        lLocalDates.add(startDate.plusDays(i));
                    }
                }
                // done
                return lLocalDates;
            }
        });
    }

    public void setDisplayedLocalDateTime(LocalDateTime dateTime) {
        agenda.setDisplayedLocalDateTime(dateTime);
    }

    /**
     * Updates the agenda with activities of every day from {@code dayList}.
     *
     * @param dayList the latest dayList from model
     */
    public void updateAgenda(List<Day> dayList) {
        agenda.appointments().clear();
        for (Day day : dayList) {
            addAppointmentsWithDay(day, dayList.size());
        }
        int totalNumberOfActivityWithTime = dayList.stream()
                .mapToInt(day -> day.getListOfActivityWithTime().size()).sum();

        if (activityToAppointmentGroupHashMap.size()
                > totalNumberOfActivityWithTime * MAX_MULTIPLE_OF_DAYLIST_SIZE_BEFORE_CLEARING_HASHMAP) {
            logger.fine("activityToAppointmentGroupHashMap has been cleared because there are too many "
                    + "entries not in used.");
            activityToAppointmentGroupHashMap = new HashMap<>();
        }
    }

    public Agenda getAgenda() {
        return agenda;
    }

    private void setupAgenda(LocalDate startDate, List<Day> dayList) {
        setupAgendaAppointmentGroups();
        this.activityToAppointmentGroupHashMap = new HashMap<>();
        agenda.setDisplayedLocalDateTime(startDate.atStartOfDay());
        updateAgenda(dayList);
        // disables dragging
        agenda.setAllowDragging(false);
        // disables modify start time and end time by dragging
        agenda.setAllowResize(false);
        // disables right click editing
        agenda.setEditAppointmentCallback((appointment) -> null);
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
     * Adds all activities in a day to the agenda.
     *
     * @param day    the day to search for activities
     */
    private void addAppointmentsWithDay(Day day, int numOfDays) {
        Random random = new Random();
        for (ActivityWithTime activityWithTime : day.getListOfActivityWithTime()) {
            Agenda.AppointmentGroup currGroup = getAppointmentGroupOfActivity(activityWithTime.getActivity(), random);
            String textToDisplay = createSummaryOfAppointment(activityWithTime.getActivity(),
                    activityWithTime.getActivity().getDuration(), numOfDays);
            agenda.appointments().add(
                    new Agenda.AppointmentImplLocal()
                            .withStartLocalDateTime(activityWithTime.getStartDateTime())
                            .withEndLocalDateTime(activityWithTime.getEndDateTime())
                            .withSummary(textToDisplay)
                            .withAppointmentGroup(currGroup)
            );
        }
    }


    private Agenda.AppointmentGroup getAppointmentGroupOfActivity(Activity activity, Random random) {
        if (activityToAppointmentGroupHashMap.get(activity) == null) {
            Agenda.AppointmentGroup randomAppointmentGroup = generateRandomGroup(random);
            activityToAppointmentGroupHashMap.put(activity, randomAppointmentGroup);
            return randomAppointmentGroup;
        } else {
            return activityToAppointmentGroupHashMap.get(activity);
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
    private String createSummaryOfAppointment(Activity activity, Duration duration, int numOfDays) {
        StringBuilder textToDisplay = new StringBuilder("\n");
        int charLimit = CHAR_LIMIT_BEFORE_TRUNCATING_SUMMARY / numOfDays;
        String nameToDisplay = activity.getName().toString();
        if (nameToDisplay.length() > charLimit) {
            logger.fine("Length of name of " + activity.getName() + " is too long and has been truncated.");
            textToDisplay.append(nameToDisplay, 0, charLimit + 1);
            textToDisplay.append("...");
        } else {
            textToDisplay.append(nameToDisplay);
        }
        textToDisplay.append('\n');

        StringBuilder tagsToDisplay = new StringBuilder("Tags: ");
        if (duration.value >= 120) {
            boolean isFirst = true;
            for (Tag tag : activity.getTags()) {
                if (isFirst) {
                    isFirst = false;
                    tagsToDisplay.append(tag.toString());
                } else {
                    tagsToDisplay.append(", ")
                            .append(tag.toString());
                }
            }
            if (tagsToDisplay.length() > charLimit) {
                logger.fine("Length of " + activity.getName() + " tags is too long and has been truncated.");
                textToDisplay.append(tagsToDisplay.substring(0, charLimit + 1));
                textToDisplay.append("...");
            } else {
                textToDisplay.append(tagsToDisplay);
            }
        }
        return textToDisplay.toString();
    }
}
