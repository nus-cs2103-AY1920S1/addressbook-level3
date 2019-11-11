package seedu.scheduler.model;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.scheduler.commons.core.GuiSettings;
import seedu.scheduler.commons.exceptions.ScheduleException;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;
import seedu.scheduler.ui.RefreshListener;
import seedu.scheduler.ui.TabListener;

/**
 * This is a Model stub that have all the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public void setScheduled(boolean scheduled) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isScheduled() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Schedule> getEmptyScheduleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setIntervieweeList(List<Interviewee> interviewees) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateScheduleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setInterviewerList(List<Interviewer> interviewers) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setIntervieweeListFilePath(Path intervieweeListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setInterviewerListFilePath(Path interviewerListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getIntervieweeListFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getInterviewerListFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadAndWriteList<Interviewee> getMutableIntervieweeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadAndWriteList<Interviewer> getMutableInterviewerList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Interviewee> getFilteredIntervieweeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Interviewer> getFilteredInterviewerList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Interviewee> getUnfilteredIntervieweeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Interviewer> getUnfilteredInterviewerList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredIntervieweeList(Predicate<Interviewee> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredInterviewerList(Predicate<Interviewer> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addInterviewer(Interviewer interviewer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addInterviewee(Interviewee interviewee) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasInterviewee(Interviewee interviewee) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasInterviewee(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasInterviewer(Interviewer interviewer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasInterviewer(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Interviewee getInterviewee(String name) throws NoSuchElementException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Interviewer getInterviewer(String name) throws NoSuchElementException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteInterviewee(Interviewee target) throws PersonNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteInterviewer(Interviewer target) throws PersonNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setInterviewee(Interviewee target, Interviewee editedTarget) throws PersonNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setInterviewer(Interviewer target, Interviewer editedTarget) throws PersonNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void emailInterviewee(Interviewee interviewee) throws IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addRefreshListener(RefreshListener listener) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTabListener(TabListener tabListener) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void intervieweeTabChange() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void interviewerTabChange() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void scheduleTabChange() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEmptyScheduleList() throws ParseException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSchedulesList(List<Schedule> schedulesList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Slot getAllocatedSlot(String intervieweeName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<ObservableList<ObservableList<String>>> getObservableLists() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Schedule> getSchedulesList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<List<String>> getTitlesLists() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateSchedulesAfterScheduling() throws ScheduleException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetDataBeforeScheduling() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }
}
