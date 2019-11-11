package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.NotebookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
//import seedu.address.model.Notebook;
import seedu.address.model.ReadOnlyNotebook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
//import seedu.address.model.lesson.UniqueLessonWeekList;
import seedu.address.model.student.Student;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final NotebookParser notebookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        notebookParser = new NotebookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("--------------st--[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = notebookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveNotebook(model.getNotebook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyClassroom getClassroom() {
        return model.getCurrentClassroom();
    }

    @Override
    public ReadOnlyNotebook getNotebook() {
        return this.model.getNotebook();
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return model.getFilteredLessonList();
    }

    @Override
    public ObservableList<UniqueLessonList> getFilteredLessonWeekList() {
        return model.getFilteredLessonWeekList();
    }

    @Override
    public ObservableList<Assignment> getFilteredAssignmentList() {
        return model.getFilteredAssignmentList();
    }

    @Override
    public ObservableList<Classroom> getClassroomList() {
        return model.getClassroomList();
    }

    @Override
    public Path getNotebookFilePath() {
        return model.getNotebookFilePath();
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
    public boolean isDisplayStudents() {
        return model.isDisplayStudents();
    }


}
