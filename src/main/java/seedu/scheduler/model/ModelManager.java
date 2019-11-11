package seedu.scheduler.model;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.scheduler.logic.commands.EmailCommand.EMAIL_MESSAGE_BODY;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.scheduler.commons.core.GuiSettings;
import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.exceptions.ScheduleException;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;
import seedu.scheduler.model.util.DateComparator;
import seedu.scheduler.ui.RefreshListener;
import seedu.scheduler.ui.TabListener;


/**
 * Represents the in-memory model of the schedule table data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static List<Schedule> emptyScheduleList = new ArrayList<>();

    private final UserPrefs userPrefs;
    private final AppStatus appStatus;
    private final List<Schedule> schedulesList;

    private final IntervieweeList intervieweeList; // functionality not stable, refrain from using
    private final InterviewerList interviewerList;
    private final FilteredList<Interviewee> filteredInterviewees; // if we want to display all interviewees on UI
    private final FilteredList<Interviewer> filteredInterviewers; // if we want to display all interviewers on UI

    private RefreshListener refreshListener;
    private TabListener tabListener;

    /**
     * Initializes a ModelManager with the given intervieweeList, interviewerList, userPrefs and schedulesList.
     */
    public ModelManager(ReadOnlyList<Interviewee> intervieweeList, ReadOnlyList<Interviewer> interviewerList,
            ReadOnlyUserPrefs userPrefs, List<Schedule> schedulesList) {
        super();
        this.schedulesList = cloneSchedulesList(schedulesList);
        requireAllNonNull(intervieweeList, interviewerList, userPrefs, schedulesList);

        logger.fine("Initialising with list of interviewees: " + intervieweeList
                + ", list of interviewers: " + interviewerList
                + ", user prefs: " + userPrefs
                + " and schedules list: " + schedulesList
        );

        this.intervieweeList = new IntervieweeList(intervieweeList);
        this.interviewerList = new InterviewerList(interviewerList);
        filteredInterviewees = new FilteredList<>(this.intervieweeList.getEntityList());
        filteredInterviewers = new FilteredList<>(this.interviewerList.getEntityList());

        this.userPrefs = new UserPrefs(userPrefs);
        this.appStatus = AppStatus.getInstance();
        this.updateScheduleList();
    }

    public ModelManager() {
        this(new IntervieweeList(), new InterviewerList(), new UserPrefs(), new LinkedList<>());
    }

    // ================================== AppStatus ======================================
    @Override
    public void setScheduled(boolean scheduled) {
        this.appStatus.setScheduled(scheduled);
    }

    @Override
    public boolean isScheduled() {
        return this.appStatus.isScheduled();
    }


    // ================================== IntervieweeList and InterviewerList ======================================

    @Override
    public void setIntervieweeList(List<Interviewee> interviewees) {
        logger.fine("Updating list of interviewees: " + interviewees);
        this.intervieweeList.setIntervieweeList(interviewees);
    }

    @Override
    public void setInterviewerList(List<Interviewer> interviewers) {
        logger.fine("Updating list of interviewers: " + interviewers);
        this.interviewerList.setInterviewerList(interviewers);
    }

    @Override
    public void setIntervieweeListFilePath(Path intervieweeListFilePath) {
        requireNonNull(intervieweeListFilePath);
        userPrefs.setIntervieweeListFilePath(intervieweeListFilePath);
    }

    @Override
    public void setInterviewerListFilePath(Path interviewerListFilePath) {
        requireNonNull(interviewerListFilePath);
        userPrefs.setInterviewerListFilePath(interviewerListFilePath);
    }

    @Override
    public Path getIntervieweeListFilePath() {
        return userPrefs.getIntervieweeListFilePath();
    }

    @Override
    public Path getInterviewerListFilePath() {
        return userPrefs.getInterviewerListFilePath();
    }

    @Override
    public void addInterviewee(Interviewee interviewee) {
        intervieweeList.addEntity(interviewee);
    }

    @Override
    public boolean hasInterviewee(Interviewee interviewee) {
        return intervieweeList.hasEntity(interviewee);
    }

    @Override
    public boolean hasInterviewee(Name name) {
        try {
            intervieweeList.getEntity(name);
        } catch (PersonNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasInterviewer(Interviewer interviewer) {
        return interviewerList.hasEntity(interviewer);
    }

    @Override
    public boolean hasInterviewer(Name name) {
        try {
            interviewerList.getEntity(name);
        } catch (PersonNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public void addInterviewer(Interviewer interviewer) {
        interviewerList.addEntity(interviewer);
        this.updateScheduleList();
    }

    /**
     * Gets the model's underlying IntervieweeList. For testing purposes only.
     */
    public IntervieweeList getIntervieweeList() {
        return this.intervieweeList;
    }

    /**
     * Gets the model's underlying InterviewerList. For testing purposes only.
     */
    public InterviewerList getInterviewerList() {
        return this.interviewerList;
    }

    @Override
    public ReadAndWriteList<Interviewee> getMutableIntervieweeList() {
        return intervieweeList;
    }

    @Override
    public ReadAndWriteList<Interviewer> getMutableInterviewerList() {
        return interviewerList;
    }

    @Override
    public ObservableList<Interviewee> getFilteredIntervieweeList() {
        return filteredInterviewees;
    }

    @Override
    public ObservableList<Interviewer> getFilteredInterviewerList() {
        return filteredInterviewers;
    }

    @Override
    public ObservableList<Interviewee> getUnfilteredIntervieweeList() {
        updateFilteredIntervieweeList(PREDICATE_SHOW_ALL_INTERVIEWEES);
        return getFilteredIntervieweeList();
    }

    @Override
    public ObservableList<Interviewer> getUnfilteredInterviewerList() {
        updateFilteredInterviewerList(PREDICATE_SHOW_ALL_INTERVIEWERS);
        return getFilteredInterviewerList();
    }

    @Override
    public void updateFilteredIntervieweeList(Predicate<Interviewee> predicate) {
        requireNonNull(predicate);
        filteredInterviewees.setPredicate(predicate);
    }

    @Override
    public void updateFilteredInterviewerList(Predicate<Interviewer> predicate) {
        requireNonNull(predicate);
        filteredInterviewers.setPredicate(predicate);
    }

    @Override
    public Interviewee getInterviewee(String name) throws NoSuchElementException {
        return intervieweeList.getEntity(new Name(name));
    }

    @Override
    public Interviewer getInterviewer(String name) throws NoSuchElementException {
        return interviewerList.getEntity(new Name(name));
    }

    @Override
    public void deleteInterviewee(Interviewee target) throws PersonNotFoundException {
        intervieweeList.removeEntity(target);
        this.updateScheduleList();
    }

    @Override
    public void deleteInterviewer(Interviewer target) throws PersonNotFoundException {
        interviewerList.removeEntity(target);
        this.updateScheduleList();
    }

    @Override
    public void setInterviewee(Interviewee target, Interviewee editedTarget) throws PersonNotFoundException {
        intervieweeList.setEntity(target, editedTarget);
    }

    @Override
    public void setInterviewer(Interviewer target, Interviewer editedTarget) throws PersonNotFoundException {
        interviewerList.setEntity(target, editedTarget);
        this.updateScheduleList();
    }


    // =========================================== Mass Email =================================================

    @Override
    public void emailInterviewee(Interviewee interviewee) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        String intervieweeEmails = interviewee.getEmails().getAllEmails().values().stream()
                .map((x) -> {
                    StringBuilder output = new StringBuilder();
                    for (int i = 0; i < x.size(); i++) {
                        output.append(x.get(i));
                        output.append("; ");
                    }

                    if (output.length() != 0) {
                        output.delete(output.length() - 2, output.length());
                    }

                    return output.toString();
                })
                .reduce((x, y) -> x + "; " + y).get();

        String ccEmails = this.userPrefs.getCcEmails().stream()
                .reduce((x, y) -> x + "; " + y)
                .get();

        String sb = "mailto:"
                + encodeForEmail(intervieweeEmails)
                + "?cc=" + encodeForEmail(ccEmails)
                + "&subject="
                + encodeForEmail(this.userPrefs.getEmailSubject())
                + "&body="
                + encodeForEmail(getEmailBody(interviewee));
        URI uri = URI.create(sb);
        desktop.mail(uri);
    }

    /**
     * This method encodes the given String into a format that can be used to generate the email message
     * contents.
     * @param input The String to encode
     * @return The encoded message
     */
    public String encodeForEmail(String input) {
        try {
            return URLEncoder.encode(input,
                    java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public String getEmailBody(Interviewee interviewee) {
        // TODO: Uncomment after "schedule" command is implemented!
        // String slots = this.getInterviewSlots(interviewee.getName().toString()).stream()
        //         .map(Slot::toString)
        //         .reduce((x, y) -> x + ", " + y)
        //         .get();
        Slot allocatedSlot = this.getAllocatedSlot(interviewee.getName().toString());
        String slot = "";

        if (!allocatedSlot.equals(Slot.EMPTY_SLOT)) {
            slot = allocatedSlot.toString();
        }

        return String.format(EMAIL_MESSAGE_BODY, interviewee.getName(), this.userPrefs.getOrganisation(),
                slot, this.userPrefs.getInterviewLocation(), this.userPrefs.getEmailAdditionalInformation());
    }

    // ================================== Refresh Listener ======================================
    public void addRefreshListener(RefreshListener listener) {
        this.refreshListener = listener;
    }
    public void addTabListener(TabListener tabListener) {
        this.tabListener = tabListener;
    }

    //=========== Schedule ================================================================================
    public void setEmptyScheduleList() throws ParseException {
        emptyScheduleList = this.generateEmptyScheduleList();
    }

    public List<Schedule> getEmptyScheduleList() {
        return emptyScheduleList;
    }

    /**
     * Generates an empty schedule list from the current interviewer list. Used to generate GUI after user modifies data
     * in InterviewerList, before the user runs a schedule command.
     *
     * @return ArrayList of {@Code Schedule}
     * @throws ParseException when timings are not of HH:mm format
     */
    public ArrayList<Schedule> generateEmptyScheduleList() throws ParseException {
        ArrayList<Schedule> emptyScheduleList = new ArrayList<>();
        HashSet<String> dates = new HashSet<>();
        String startTime = userPrefs.getStartTime();
        String endTime = userPrefs.getEndTime();
        int duration = userPrefs.getDurationPerSlot();
        ObservableList<Interviewer> listOfInterviewers = interviewerList.getEntityList();
        for (Interviewer interviewer: listOfInterviewers) {
            List<Slot> availabilities = interviewer.getAvailabilities();
            for (Slot slot: availabilities) {
                String date = slot.date;
                dates.add(date);
            }
        }
        ArrayList<String> headers = interviewerList.getTitlesForSchedule();
        ArrayList<String> datesList = new ArrayList<>(dates);
        datesList.sort(new DateComparator()); //sorts the dates in ascending order
        for (String date: datesList) {
            LinkedList<LinkedList<String>> table = new LinkedList<>();
            LinkedList<String> fullHeader = new LinkedList<>();
            fullHeader.add(date);
            for (String header: headers) {
                fullHeader.add(header);
            }
            table.add(fullHeader);
            String currentTime = startTime;
            while (!isGreaterThanOrEqual(currentTime, endTime)) {
                LinkedList<String> row = new LinkedList<>();
                String nextTimeSlot = addTime(duration, currentTime);
                row.add(currentTime + "-" + nextTimeSlot);
                Slot currentSlot = new Slot(date, currentTime, nextTimeSlot);
                for (int i = 0; i < listOfInterviewers.size(); i++) {
                    Interviewer currentInterviewer = listOfInterviewers.get(i);
                    if (currentInterviewer.isAvailable(currentSlot)) {
                        row.add("1");
                    } else {
                        row.add("0");
                    }
                }
                currentTime = nextTimeSlot;
                table.add(row);
            }
            emptyScheduleList.add(new Schedule(date, table));
        }
        return emptyScheduleList;
    }

    /**
     * Adds the given duration to the given time.
     * @param duration amount of time to add
     * @param currentTime original time
     * @return String result after addition
     * @throws ParseException when String currentTime is not of HH:mm format
     */
    public static String addTime(int duration, String currentTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = dateFormat.parse(currentTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, duration);
        String newTime = dateFormat.format(cal.getTime());
        return newTime;
    }

    /**
     * Compares 2 given times as String in format HH:mm, and returns true if the first is greater than the second.
     * @param currentTime
     * @param endTime
     * @return True if currentTime is greater or equals to endTime
     * @throws ParseException
     */
    public static boolean isGreaterThanOrEqual(String currentTime, String endTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date currentTimeAsDate = dateFormat.parse(currentTime);
        Date endTimeAsDate = dateFormat.parse(endTime);
        return currentTimeAsDate.after(endTimeAsDate) || currentTimeAsDate.equals(endTimeAsDate);
    }

    /**
     * Replaces schedule data with the data in {@code schedule}.
     * @param list
     */
    @Override
    public void setSchedulesList(List<Schedule> list) {
        schedulesList.clear();
        schedulesList.addAll(cloneSchedulesList(list));
        if (refreshListener != null) {
            refreshListener.scheduleDataUpdated();
        }
        logger.fine("Schedules list is reset");
    }

    @Override
    public void intervieweeTabChange() {
        if (tabListener != null) {
            tabListener.changeTabInterviewee();
        }
    }

    @Override
    public void interviewerTabChange() {
        if (tabListener != null) {
            tabListener.changeTabInterviewer();
        }
    }

    @Override
    public void scheduleTabChange() {
        if (tabListener != null) {
            tabListener.changeTabSchedule();
        }
    }

    /**
     * Updates schedule list based on interviewer's data.
     */
    public void updateScheduleList() {
        try {
            this.setEmptyScheduleList();
            List<Schedule> schedules = this.getEmptyScheduleList();
            this.setSchedulesList(schedules);
        } catch (ParseException e) {
            logger.log(Level.WARNING, "Should not have exceptions");
        }
    }

    /**
     * Returns the interview slot allocated to the interviewee with the {@code intervieweeName}.
     * @return
     */
    @Override
    public Slot getAllocatedSlot(String intervieweeName) {
        return intervieweeList.getEntity(new Name(intervieweeName)).getAllocatedSlot();
    }

    /**
     * Returns a list of observable list of the schedules.
     */
    @Override
    public List<ObservableList<ObservableList<String>>> getObservableLists() {
        Collections.sort(schedulesList);
        List<ObservableList<ObservableList<String>>> observableLists = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            observableLists.add(schedule.getObservableList());
        }
        return observableLists;
    }

    /**
     * Adds the given interviewer to schedule(s) in which the interviewer's availability fall.
     * If the interviewer's availability does not fall within any of the schedule, then the interviewer will not
     * be added into any of the schedule.
     */
    @Override
    public List<Schedule> getSchedulesList() {
        Collections.sort(schedulesList);
        return schedulesList;
    }

    @Override
    public List<List<String>> getTitlesLists() {
        Collections.sort(schedulesList);
        List<List<String>> titlesLists = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            titlesLists.add(schedule.getTitles());
        }
        return titlesLists;
    }

    @Override
    public void updateSchedulesAfterScheduling() throws ScheduleException {
        List<Interviewer> interviewers = interviewerList.getEntityList();
        for (Interviewer interviewer : interviewers) {
            logger.info(interviewers.toString());
            logger.info(schedulesList.toString());
            for (Schedule schedule : schedulesList) {
                schedule.addAllocatedInterviewees(interviewer, interviewer.getAllocatedSlots());
            }
        }

        if (refreshListener != null) {
            refreshListener.scheduleDataUpdated();
        }
    }

    /**
     * Returns the deep copy of the schedules list given.
     *
     * @param list the list of schedules to be copied.
     * @return the deep copy of the schedules list given.
     */
    private static List<Schedule> cloneSchedulesList(List<Schedule> list) {
        List<Schedule> listClone = new LinkedList<>();
        for (Schedule schedule : list) {
            listClone.add(Schedule.cloneSchedule(schedule));
        }
        return listClone;
    }

    // ============================================ User Prefs ===================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    // ===========================================================================================================

    @Override
    public void resetDataBeforeScheduling() {
        // Clear all allocated slots
        this.intervieweeList.clearAllAllocatedSlots();
        this.interviewerList.clearAllAllocatedSlots();

        // Clear the interviewees from the schedule
        for (Schedule schedule : schedulesList) {
            schedule.clearAllocatedInterviewees();
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && intervieweeList.equals(other.intervieweeList)
                && interviewerList.equals(other.interviewerList)
                && filteredInterviewees.equals(other.filteredInterviewees)
                && filteredInterviewers.equals(other.filteredInterviewers);
    }
}
