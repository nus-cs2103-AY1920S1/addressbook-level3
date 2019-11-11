package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventScheduleViewMode;
import seedu.address.model.note.Note;
import seedu.address.model.question.Question;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.student.Student;
import seedu.address.storage.printable.NjoyPrintable;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, IOException;

    //region Questions
    /**
     * Returns the list of slideshow questions
     *
     * @see seedu.address.model.Model#getSlideshowQuestions()
     */
    ObservableList<Question> getSlideshowQuestions();

    /**
     * Returns the list of all questions
     *
     * @see seedu.address.model.Model#getAllQuestions()
     */
    ObservableList<Question> getAllQuestions();

    /**
     * Returns the list of all searched questions.
     *
     * @see seedu.address.model.Model#getSearchQuestions()
     */
    ObservableList<Question> getSearchQuestions();
    //endregion

    //region Statistics
    /**
     * Returns an unmodifiable view of the processed statistics of students
     */
    ObservableList<Statistics> getProcessedStatistics();
    //endregion

    //region Notes
    /**
     * Returns an unmodifiable view of the filtered list of notes
     */
    ObservableList<Note> getFilteredNotesList();

    /**
     * Returns the user prefs' notes record file path.
     */
    Path getNotesRecordFilePath();
    //endregion

    /**
     * Returns an unmodifiable view of the filtered list of students
     */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Returns an unmodifiable view of the students in queried group.
     */
    ObservableList<Student> getStudentsInGroup();

    /**
     * Returns an unmodifiable view of the questions in queried quiz.
     */
    ObservableList<Question> getQuestionsInQuiz();

    /**
     * Returns an unmodifiable view of the answers in queried quiz.
     */
    ObservableList<Question> getAnswersInQuiz();

    /**
     * Returns an unmodifiable view of the questions and answers in queried quiz.
     */
    ObservableList<Question> getQuestionsAndAnswersInQuiz();

    //region VEvents
    ObservableList<VEvent> getVEventList();
    //endregion

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    void savePrintable(NjoyPrintable printable) throws IOException;

    LocalDateTime getEventScheduleTargetDateTime();

    EventScheduleViewMode getScheduleViewMode();
}
