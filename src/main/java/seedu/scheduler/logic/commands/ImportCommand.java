package seedu.scheduler.logic.commands;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.FilePath;
import seedu.scheduler.model.IntervieweeList;
import seedu.scheduler.model.InterviewerList;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Role;
import seedu.scheduler.model.person.RoleType;
import seedu.scheduler.model.person.exceptions.DuplicatePersonException;
import seedu.scheduler.model.util.CsvReader;


/**
 * Import csv file containing interviewer's/ interviewers's information.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import .csv file containing "
            + "interviewer or interviewee's information.\n"
            + "Parameters: "
            + "Type of Data (Can be only interviewer or interviewee) "
            + PREFIX_FILE_PATH + "FILE_PATH \n"
            + "Example: " + COMMAND_WORD + " interviewer " + PREFIX_FILE_PATH + "C:\\Users\\john\\Desktop\\test.csv";
    public static final String SUCCESS_MESSAGE = "Data imported successfully.";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Command not implemented yet";
    public static final String INCORRECT_FORMAT = "Data is in incorrect format. Please refer to the "
            + "User Guide for the supported format";
    public static final String FILE_DOES_NOT_EXIST = "Target file does not exist. Please ensure that "
            + "the file path is correct.";
    public static final String DUPLICATE_PERSON_ERROR = "Data contains entries that are duplicated/already exists "
            + "in storage. Please type 'clear'(without the quote) to remove those entries before running the import "
            + "command.";
    public static final String DATE_FORMAT_ERROR_MESSAGE = "Error in data formatting: ";
    private static final Logger logger = LogsCenter.getLogger(ImportCommand.class);

    private FilePath filePath;
    private Role type;

    public ImportCommand(Role type, FilePath filePath) {
        this.type = type;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            if (type.getRole().equals(RoleType.INTERVIEWER)) {
                CsvReader csvReader = new CsvReader(filePath.getValue());
                logger.log(Level.INFO, "Starts reading .csv file");
                ArrayList<Interviewer> interviewers = csvReader.readInterviewers();
                logger.log(Level.INFO, "Finished reading .csv file");
                logger.log(Level.INFO, stringifyInterviewers(interviewers));
                checkForDuplicateInterviewerEntries(interviewers, model.getUnfilteredInterviewerList());
                for (Interviewer interviewer: interviewers) {
                    model.addInterviewer(interviewer);
                }
                model.setScheduled(false);
                return new CommandResult(SUCCESS_MESSAGE, false, false);
            } else if (type.getRole().equals(RoleType.INTERVIEWEE)) {
                CsvReader csvReader = new CsvReader(filePath.getValue());
                logger.log(Level.INFO, "Starts reading .csv file");
                ArrayList<Interviewee> interviewees = csvReader.readInterviewees();
                logger.log(Level.INFO, "Finished reading .csv file");
                logger.log(Level.INFO, stringifyInterviewees(interviewees));
                checkForDuplicateIntervieweeEntries(interviewees, model.getUnfilteredIntervieweeList());
                for (Interviewee interviewee: interviewees) {
                    model.addInterviewee(interviewee);
                }
                model.setScheduled(false);
                return new CommandResult(SUCCESS_MESSAGE, false, false);
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        } catch (FileNotFoundException fileE) {
            throw new CommandException(FILE_DOES_NOT_EXIST, fileE);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new CommandException(INCORRECT_FORMAT, e);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(DUPLICATE_PERSON_ERROR, dpe);
        } catch (IllegalArgumentException iae) {
            throw new CommandException(DATE_FORMAT_ERROR_MESSAGE + iae.getMessage(), iae);
        }
    }

    /**
     * Generates a string of interviewers and their data for logging purposes.
     * @param interviewers list of {@code interviewers}.
     * @return String of all interviewers in the given list/
     */
    private static String stringifyInterviewers(ArrayList<Interviewer> interviewers) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("List of interviewers imported: \n");
        for (Interviewer interviewer: interviewers) {
            resultBuilder.append(interviewer.toString());
            resultBuilder.append("\n");
        }
        return resultBuilder.toString();
    }

    /**
     * Generates a string of interviewees and their data for logging purposes.
     * @param interviewees list of {@code interviewees}.
     * @return String of all interviewees in the given list/
     */
    private static String stringifyInterviewees(ArrayList<Interviewee> interviewees) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("List of interviewees imported: \n");
        for (Interviewee interviewee: interviewees) {
            resultBuilder.append(interviewee.toString());
            resultBuilder.append("\n");
        }
        return resultBuilder.toString();
    }

    /**
     * Checks imported list of interviewers for duplicate entries by comparing them with the pre-existing list.
     * @param interviewers Imported list of interviewers
     * @param modelList Current list of interviewers in the model
     * @throws DuplicatePersonException when there is a duplicate entry
     */
    public static void checkForDuplicateInterviewerEntries(ArrayList<Interviewer> interviewers,
            ObservableList<Interviewer> modelList)
            throws DuplicatePersonException {
        InterviewerList interviewerList = new InterviewerList();
        for (Interviewer interviewer: modelList) {
            interviewerList.addEntity(interviewer);
        }
        for (Interviewer interviewer: interviewers) {
            interviewerList.addEntity(interviewer);
        }
    }

    /**
     * Checks imported list of interviewees for duplicate entries by comparing them with the pre-existing list.
     * @param interviewees Imported list of interviewers
     * @param modelList Current list of interviewees in the model
     * @throws DuplicatePersonException when there is a duplicate entry
     */
    public static void checkForDuplicateIntervieweeEntries(ArrayList<Interviewee> interviewees,
            ObservableList<Interviewee> modelList)
            throws DuplicatePersonException {
        IntervieweeList intervieweeList = new IntervieweeList();
        for (Interviewee interviewee: modelList) {
            intervieweeList.addEntity(interviewee);
        }
        for (Interviewee interviewee: interviewees) {
            intervieweeList.addEntity(interviewee);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && type.equals(((ImportCommand) other).type)
                && filePath.equals(((ImportCommand) other).filePath));
    }
}
