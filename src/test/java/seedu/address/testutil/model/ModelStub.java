package seedu.address.testutil.model;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.math3.util.Pair;

import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.EventScheduleViewMode;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.model.note.Note;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.model.question.Question;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.quiz.ReadOnlyQuizzes;
import seedu.address.model.statistics.ReadOnlyStatisticsRecord;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.model.student.Student;


/**
 * A default model stub that have all of the methods failing. All model stubs should extend this stub and
 * override methods that concern the test in question.
 */
public class ModelStub implements Model {
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
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addQuestion(Question question) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Question deleteQuestion(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getAllQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getSearchQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Question getQuestion(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasQuestion(Question question) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String searchQuestions(String textToFind) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setQuestion(Index index, Question question) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSlideshowQuestions(List<Index> questionsIndexes) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getQuestionsSummary() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean createQuizManually(String quizId, ArrayList<Integer> questionNumbers) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean createQuizAutomatically(String quizId, int numQuestions, String type) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean deleteQuizQuestion(String quizId, int questionNumber) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getQuestionsAndAnswers(String quizId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean checkQuizExists(String quizId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getObservableListQuestionsFromQuiz() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean exportQuiz(String quizId) throws IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getSavedQuizzesFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSavedQuizzesFilePath(Path savedQuizzesFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSavedQuizzes(ReadOnlyQuizzes savedQuizzes) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyQuizzes getSavedQuizzes() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getNotesRecordFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setNotesRecord(ReadOnlyNotesRecord notesRecord) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyNotesRecord getNotesRecord() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortNotesRecord(Comparator<Note> noteComparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasNote(Note note) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteNote(Note target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addNote(Note note) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Note> getFilteredNotesList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredNotesList(Predicate<Note> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEventRecord(Path eventRecordFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEventRecord(ReadOnlyEvents events) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getEventRecordFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyEvents getEventRecord() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyVEvents getVEventRecord() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public LocalDateTime getEventScheduleTargetDateTime() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEventScheduleTargetDateTime(LocalDateTime targetDateTime) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public EventScheduleViewMode getEventScheduleViewMode() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEventScheduleViewMode(EventScheduleViewMode viewMode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getEventSchedulePrefString() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasVEvent(VEvent vEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteVEvent(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addVEvent(VEvent vEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setVEvent(Index index, VEvent editedVEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public VEvent getVEvent(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Pair<Index, VEvent>> findVEvents(String desiredEventName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<VEvent> getVEventList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Pair<Index, VEvent> findMostSimilarVEvent(String desiredEventName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyStatisticsRecord getStatisticsRecord() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Statistics> getProcessedStatistics() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStatistics(Statistics statistics) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getSavedQuestionsFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSavedQuestionsFilePath(Path savedQuestionsFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSavedQuestions(ReadOnlyQuestions savedQuestions) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getSlideshowQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyQuestions getSavedQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean getIsMarked(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyStudentRecord getStudentRecord() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudentRecord(ReadOnlyStudentRecord studentRecord) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudent(Student target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Index> getIndexFromStudentList(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Student getStudent(int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudentWithIndex(Index index, Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getStudentSummary() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void createGroupManually(String groupId, ArrayList<Integer> studentNumbers) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Student> getObservableListStudentsFromGroup() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean addStudentToGroup(String groupId, int studentNumber, int groupIndexNumber) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeStudentFromGroup(String groupId, int groupIndexNumber) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean checkGroupExists(String groupId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean checkStudentExistInGroup(String groupId, Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportGroup(String groupId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getGroupSize(String groupId) {
        throw new AssertionError("This method should not be called.");
    }
}
