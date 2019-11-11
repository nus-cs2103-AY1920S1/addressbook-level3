package seedu.address.model;

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
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<Note> PREDICATE_SHOW_ALL_NOTES = unused -> true;

    //region PREFERENCES & SETTINGS

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //endregion


    //region SavedQuestions

    /**
     * Returns the user prefs' questions file path.
     */
    Path getSavedQuestionsFilePath();

    /**
     * Sets the user prefs' questions file path.
     */
    void setSavedQuestionsFilePath(Path savedQuestionsFilePath);

    /**
     * Replaces questions data with the data in {@code savedQuestions}.
     */
    void setSavedQuestions(ReadOnlyQuestions savedQuestions);

    /**
     * Returns the list of slideshow questions
     */
    ObservableList<Question> getSlideshowQuestions();

    /**
     * Returns the saved questions
     */
    ReadOnlyQuestions getSavedQuestions();

    //endregion

    boolean getIsMarked(Student student);
    //region Students

    /**
     * Gets the record of students in read only format.
     */
    ReadOnlyStudentRecord getStudentRecord();

    /**
     * Sets the student record with the given student record.
     */
    void setStudentRecord(ReadOnlyStudentRecord studentRecord);

    /**
     * Checks if the list already contains specified student.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes specified student from the list of students.
     */
    void deleteStudent(Student target);

    /**
     * Adds a specified student to the list of students.
     */
    void addStudent(Student student);

    /**
     * Gets the index of a specified student.
     */
    Optional<Index> getIndexFromStudentList(Student student);

    /**
     * Adds a student to the list with a specific index.
     */
    void setStudentWithIndex(Index index, Student student);

    /**
     * Edits a student in the student list to become a new student that was specified.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Returns student with specific index number.
     */
    Student getStudent(int index);

    /**
     * Gets the filtered student list.
     */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filtered student list.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Gets the list of students in string format.
     */
    String getStudentSummary();
    //endregion

    //region Group

    /**
     * Creates a group manually.
     */
    void createGroupManually(String groupId, ArrayList<Integer> studentNumbers);

    /**
     * Gets observable list of students from queried group
     */
    ObservableList<Student> getObservableListStudentsFromGroup();

    /**
     * Adds a student to a group. {@code groupId} Must already exist in the list of groups. {@code
     * studentNumber} Must already exist in the list of students. {@code groupIndexNumber} Must
     * already exist in the quiz.
     */
    boolean addStudentToGroup(String groupId, int studentNumber, int groupIndexNumber);

    /**
     * Removes a student from a group.
     */
    void removeStudentFromGroup(String groupId, int groupIndexNumber);

    /**
     * Check if group already exists.
     */
    boolean checkGroupExists(String groupId);

    /**
     * Check if student exists in group.
     *
     * @param groupId Id of group.
     * @param student Student to check.
     * @return True if student exists in group.
     */
    boolean checkStudentExistInGroup(String groupId, Student student);

    /**
     * Exports group to word document
     */
    void exportGroup(String groupId);

    /**
     * Gets group size
     */
    int getGroupSize(String groupId);
    //endregion

    //region Questions

    /**
     * Adds the given question. {@code question} must not exist in the question list.
     */
    void addQuestion(Question question);

    /**
     * Returns the question that has been deleted based on the index.
     */
    Question deleteQuestion(Index index);

    /**
     * Returns the list of questions stored.
     */
    ObservableList<Question> getAllQuestions();

    /**
     * Returns the list of search questions.
     */
    ObservableList<Question> getSearchQuestions();

    /**
     * Returns the question based on its Index.
     */
    Question getQuestion(Index index);

    /**
     * Returns true if a note with the same identity as {@code question} exists in the Questions
     * record.
     */
    boolean hasQuestion(Question question);

    /**
     * Returns the summary of questions searched.
     */
    String searchQuestions(String textToFind);

    /**
     * Replaces the question at the specified index.
     */
    void setQuestion(Index index, Question question);

    /**
     * Sets slideshow questions based on the list of question indexes passed in.
     *
     * @param questionsIndexes list of question indexes.
     */
    void setSlideshowQuestions(List<Index> questionsIndexes);

    /**
     * Returns the questions summary.
     *
     * @return Summary of questions list.
     */
    String getQuestionsSummary();

    //endregion

    //region Quizzes

    /**
     * Creates a quiz manually.
     */
    boolean createQuizManually(String quizId, ArrayList<Integer> questionNumbers);

    /**
     * Creates a quiz automatically.
     */
    boolean createQuizAutomatically(String quizId, int numQuestions, String type);

    /**
     * Adds a question to a quiz. {@code quizId} Must already exist in the quiz bank. {@code
     * questionNumber} Must already exist in the question bank. {@code quizQuestionNumber} Must
     * already exist in the quiz.
     */
    boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber);

    /**
     * Removes a question from a quiz.
     */
    boolean deleteQuizQuestion(String quizId, int questionNumber);

    /**
     * Returns a quiz's questions and answers, for testing purposes.
     */
    String getQuestionsAndAnswers(String quizId);

    /**
     * Check if quiz already exists.
     */
    boolean checkQuizExists(String quizId);

    /**
     * Gets observable list of questions from queried quiz.
     */
    ObservableList<Question> getObservableListQuestionsFromQuiz();

    /**
     * Exports a quiz to a html file.
     */
    boolean exportQuiz(String quizId) throws IOException;

    //endregion

    //region SavedQuizzes

    /**
     * Returns the user prefs' quizzes file path.
     */
    Path getSavedQuizzesFilePath();

    /**
     * Sets the user prefs' quizzes file path.
     */
    void setSavedQuizzesFilePath(Path savedQuizzesFilePath);

    /**
     * Replaces quizzes data with the data in {@code savedQuizzes}.
     */
    void setSavedQuizzes(ReadOnlyQuizzes savedQuizzes);

    /**
     * Returns the saved questions
     */
    ReadOnlyQuizzes getSavedQuizzes();

    //endregion

    //region NotesRecord

    /**
     * Returns the user prefs' notes record file path.
     */
    Path getNotesRecordFilePath();

    /**
     * Replaces notes record data with the data in {@code notesRecord}.
     */
    void setNotesRecord(ReadOnlyNotesRecord notesRecord);

    /**
     * Returns the NotesRecord
     */
    ReadOnlyNotesRecord getNotesRecord();

    /**
     * Sorts the notes list using the {@code noteComparator} provided.
     */
    void sortNotesRecord(Comparator<Note> noteComparator);
    //endregion

    //region Notes

    /**
     * Returns true if a note with the same identity as {@code note} exists in the notes record.
     */
    boolean hasNote(Note note);

    /**
     * Deletes the given note. The note must exist in the notes record.
     */
    void deleteNote(Note target);

    /**
     * Adds the given note. {@code note} must not already exist in the notes record.
     */
    void addNote(Note note);

    /**
     * Replaces the given note {@code target} with {@code editedNote}. {@code target} must exist in
     * the notes record. The note title of {@code editedNote} must not be the same as another
     * existing note in the notes record.
     */
    void setNote(Note target, Note editedNote);

    /**
     * Returns an unmodifiable view of the filtered notes list.
     */
    ObservableList<Note> getFilteredNotesList();

    /**
     * Updates the filter of the filtered note list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredNotesList(Predicate<Note> predicate);
    //endregion

    //region EventRecord
    void setEventRecord(Path eventRecordFilePath);

    void setEventRecord(ReadOnlyEvents events);

    Path getEventRecordFilePath();

    ReadOnlyEvents getEventRecord();

    ReadOnlyVEvents getVEventRecord();

    //endregion

    //region EventSchedulePrefs

    LocalDateTime getEventScheduleTargetDateTime();

    void setEventScheduleTargetDateTime(LocalDateTime targetDateTime);

    EventScheduleViewMode getEventScheduleViewMode();

    void setEventScheduleViewMode(EventScheduleViewMode viewMode);

    String getEventSchedulePrefString();

    //endregion

    //region VEvents
    boolean hasVEvent(VEvent vEvent);

    void deleteVEvent(Index index);

    void addVEvent(VEvent vEvent);

    void setVEvent(Index index, VEvent editedVEvent);

    VEvent getVEvent(Index index);

    List<Pair<Index, VEvent>> findVEvents(String desiredEventName);

    ObservableList<VEvent> getVEventList();

    Pair<Index, VEvent> findMostSimilarVEvent(String desiredEventName);

    //endregion

    //region Statistics

    /**
     * Returns the ReadOnlyStatisticsRecord in the Model.
     */
    ReadOnlyStatisticsRecord getStatisticsRecord();

    /**
     * Returns the one-item list of Statistics list in StatisticsRecord.
     */
    ObservableList<Statistics> getProcessedStatistics();

    /**
     * Sets the Statistics item in StatisticsRecord to be {@code statistic}
     *
     * @param statistics Statistic object to place in StatisticsRecord.
     */
    void addStatistics(Statistics statistics);

    //endregion
}
