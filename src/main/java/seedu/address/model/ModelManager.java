package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Slot;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the schedule table data.
 */
public class ModelManager implements Model {
    public static final Schedule EMPTY_SCHEDULE = new Schedule("", new LinkedList<>());
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final List<Schedule> schedulesList;

    private final IntervieweeList intervieweeList; // functionality not stable, refrain from using
    private final InterviewerList interviewerList;
    private final FilteredList<Interviewee> filteredInterviewees; // if we want to display all interviewees on UI
    private final FilteredList<Interviewer> filteredInterviewers; // if we want to display all inteviewers on UI

    /**
     * Initializes a ModelManager with the given intervieweeList, interviewerList, userPrefs and schedulesList.
     */
    public ModelManager(ReadOnlyList<Interviewee> intervieweeList, ReadOnlyList<Interviewer> interviewerList,
                        ReadOnlyUserPrefs userPrefs, List<Schedule> schedulesList) {
        super();
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

        this.schedulesList = cloneSchedulesList(schedulesList);
        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager() {
        this(new IntervieweeList(), new InterviewerList(), new UserPrefs(), new LinkedList<>());
    }

    // ==================================IntervieweeList and InterviewerList ======================================

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
    public boolean hasInterviewer(Interviewer interviewer) {
        return interviewerList.hasEntity(interviewer);
    }

    @Override
    public void addInterviewer(Interviewer interviewer) {
        interviewerList.addEntity(interviewer);
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
    }

    @Override
    public void deleteInterviewer(Interviewer target) throws PersonNotFoundException {
        interviewerList.removeEntity(target);
    }

    @Override
    public void setInterviewee(Interviewee target, Interviewee editedTarget) throws PersonNotFoundException {
        intervieweeList.setEntity(target, editedTarget);
    }

    @Override
    public void setInterviewer(Interviewer target, Interviewer editedTarget) throws PersonNotFoundException {
        interviewerList.setEntity(target, editedTarget);
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

        String sb = "mailto:"
                + URLEncoder.encode(intervieweeEmails,
                java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20")
                + "?cc=" + "copied@example.com" + "&subject="
                + URLEncoder.encode("This is a test subject",
                java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20")
                + "&body="
                + URLEncoder.encode(intervieweeEmails,
                java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20");
        URI uri = URI.create(sb);
        desktop.mail(uri);
    }

    // ============================================ Schedule ===================================================

    /**
     * Adds the given interviewer to schedule(s) in which the interviewer's availability fall. If the interviewer's
     * availability does not fall within any of the schedule, then the interviewer will not be added into any of
     * the schedule.
     */
    @Override
    public void addInterviewerToSchedule(Interviewer interviewer) {
        interviewerList.addEntity(interviewer);
        for (Schedule schedule : schedulesList) {
            schedule.addInterviewer(interviewer);
        }
    }

    /**
     * Returns the date of the first schedule in which the interviewer exists in, otherwise return empty string.
     */
    @Override
    public String scheduleHasInterviewer(Interviewer interviewer) {
        String date = "";
        for (Schedule schedule : schedulesList) {
            if (schedule.hasInterviewer(interviewer)) {
                date = schedule.getDate();
                break;
            }
        }
        return date;
    }

    /**
     * Replaces schedule data with the data in {@code schedule}.
     */
    @Override
    public void setSchedulesList(List<Schedule> list) {
        schedulesList.clear();
        schedulesList.addAll(cloneSchedulesList(list));
        logger.fine("Schedules list is reset");
    }

    /**
     * Returns a list of interview slots assigned to the interviewee with the {@code intervieweeName}.
     */
    @Override
    public List<Slot> getInterviewSlots(String intervieweeName) {
        List<Slot> slots = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            slots.addAll(schedule.getInterviewSlots(intervieweeName));
        }
        return slots;
    }

    /**
     * Returns a list of observable list of the schedules.
     */
    @Override
    public List<ObservableList<ObservableList<String>>> getObservableLists() {
        List<ObservableList<ObservableList<String>>> observableLists = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            observableLists.add(schedule.getObservableList());
        }
        return observableLists;
    }

    /** Returns the schedulesList **/
    @Override
    public List<Schedule> getSchedulesList() {
        return schedulesList;
    }

    /** Returns a list of lists of column titles, each list of column titles belong to a Schedule table*/
    @Override
    public List<List<String>> getTitlesLists() {
        List<List<String>> titlesLists = new LinkedList<>();
        for (Schedule schedule : schedulesList) {
            titlesLists.add(schedule.getTitles());
        }
        return titlesLists;
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

    /**
     * Returns the deep copy of the interviewee's list given.
     *
     * @param list the list of interviewees to be copied.
     * @return a deep copy of interviewee's list.
     */
    private static List<Interviewee> cloneIntervieweesList(List<Interviewee> list) {
        List<Interviewee> listClone = new LinkedList<>();
        for (Interviewee interviewee : list) {
            listClone.add(interviewee);
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
