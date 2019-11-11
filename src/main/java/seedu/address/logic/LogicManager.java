package seedu.address.logic;

import static seedu.address.logic.commands.CommandResultType.EXPORT_CALENDAR;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.NjoyParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.event.EventScheduleViewMode;
import seedu.address.model.note.Note;
import seedu.address.model.question.Question;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.student.Student;
import seedu.address.storage.Storage;
import seedu.address.storage.printable.NjoyPrintable;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final NjoyParser njoyParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        njoyParser = new NjoyParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, IOException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = njoyParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveStudentRecord(model.getStudentRecord());
            storage.saveQuestions(model.getSavedQuestions());
            storage.saveEvents(model.getEventRecord());
            storage.saveQuizzes(model.getSavedQuizzes());
            storage.saveNotesRecord(model.getNotesRecord());
            if (commandResult.getCommandResultType().equals(EXPORT_CALENDAR)) {
                storage.exportEvent(model.getVEventRecord());
            }
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public void savePrintable(NjoyPrintable printable) throws IOException {
        storage.savePrintable(printable);
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }


    @Override
    public ObservableList<Question> getSlideshowQuestions() {
        return model.getSlideshowQuestions();
    }

    @Override
    public ObservableList<Question> getAllQuestions() {
        return model.getAllQuestions();
    }

    @Override
    public ObservableList<Question> getSearchQuestions() {
        return model.getSearchQuestions();
    }

    @Override
    public ObservableList<Statistics> getProcessedStatistics() {
        return model.getProcessedStatistics();
    }

    @Override
    public ObservableList<Student> getStudentsInGroup() {
        return model.getObservableListStudentsFromGroup();
    }

    @Override
    public ObservableList<Question> getQuestionsInQuiz() {
        return model.getObservableListQuestionsFromQuiz();
    }

    @Override
    public ObservableList<Question> getAnswersInQuiz() {
        return model.getObservableListQuestionsFromQuiz();
    }

    @Override
    public ObservableList<Question> getQuestionsAndAnswersInQuiz() {
        return model.getObservableListQuestionsFromQuiz();
    }

    @Override
    public ObservableList<Note> getFilteredNotesList() {
        return model.getFilteredNotesList();
    }

    @Override
    public Path getNotesRecordFilePath() {
        return model.getNotesRecordFilePath();
    }

    @Override
    public ObservableList<VEvent> getVEventList() {
        return model.getVEventList();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public LocalDateTime getEventScheduleTargetDateTime() {
        return model.getEventScheduleTargetDateTime();
    }

    @Override
    public EventScheduleViewMode getScheduleViewMode() {
        return model.getEventScheduleViewMode();
    }
}
