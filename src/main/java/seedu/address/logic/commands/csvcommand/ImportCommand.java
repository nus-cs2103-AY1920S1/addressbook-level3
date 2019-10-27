package seedu.address.logic.commands.csvcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.MissingEntityException;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.csvcommand.csvutil.ErrorTracker;
import seedu.address.logic.commands.csvcommand.csvutil.ErrorTracker.Error;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * Supports bulk registration via a CSV file.
 * This command aims to facilitate registration of entities onto Alfred.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_SUCCESS = "Successfully imported CSV file into Alfred";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Following line(s) were unable to be imported into Alfred\n"
             + "Possible reasons include incorrect formatting or adding of duplicate Entity:";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found at %s"; // %s -> this.csvFileName
    public static final String MESSAGE_IO_EXCEPTION =
            "Something went wrong while accessing your file! Please try again...";
    public static final String MESSAGE_INVALID_DATA = "CSV file contains invalid data";
    public static final String MESSAGE_INVALID_FORMAT = "CSV file must contain Entity data in the following format:\n"
            + "\tMentors: " + CsvUtil.HEADER_MENTOR + "\n"
            + "\tParticipants: " + CsvUtil.HEADER_PARTICIPANT + "\n"
            + "\tTeams: " + CsvUtil.HEADER_TEAM;
    public static final String MESSAGE_ERROR_FILE_CREATED =
            "CSV file containing the errors was created at %s."; // %s -> file path
    public static final String MESSAGE_ERROR_FILE_NOT_CREATED =
            "CSV file containing the errors was not able to be created.";
    public static final String CAUSE_INVALID_DATA = "Invalid data format";
    public static final String CAUSE_DUPLICATE_ENTITY = "This entity already exists in Alfred";
    public static final String ASSERTION_FAILED_NOT_CSV = "File given is not a CSV file.";
    public static final String POSTFIX_ERROR_FILE = "_Error";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads data in CSV file into Alfred"
            + " Parameters: "
            + PREFIX_FILE_PATH + "CSV_FILE_NAME\n"
            + "\tExample (Windows): " + COMMAND_WORD
            + " " + PREFIX_FILE_PATH + "C:/Users/USER/AlfredData/Alfred.csv\n";

    private Path csvFilePath;
    private Path errorFilePath;
    private Queue<String> teamBuffers;
    private ErrorTracker errors;

    public ImportCommand(String csvFilePath) {
        assert csvFilePath.toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;

        this.csvFilePath = Paths.get(csvFilePath);
        this.errorFilePath = Paths.get(this.insertErrorPostfix(csvFilePath));
        this.teamBuffers = new LinkedList<>();
        this.errors = new ErrorTracker();
    }

    public ImportCommand(String csvFilePath, String errorFilePath) {
        assert csvFilePath.toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;
        assert errorFilePath.toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;

        this.csvFilePath = Paths.get(csvFilePath);
        this.errorFilePath = Paths.get(errorFilePath);
        this.teamBuffers = new LinkedList<>();
        this.errors = new ErrorTracker();
    }

    private String insertErrorPostfix(String csvFilePath) {
        // 4 -> length of ".csv"
        return new StringBuilder(csvFilePath).insert(csvFilePath.length() - 4, POSTFIX_ERROR_FILE).toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Details must not be empty (except for ID)
        if (!FileUtil.isFileExists(this.csvFilePath)) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, this.csvFilePath));
        }
        File csvFile = this.csvFilePath.toFile();
        try {
            this.parseFile(csvFile, model);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_IO_EXCEPTION);
        }
        if (!errors.isEmpty()) {
            // Create csv file containing all lines unable to be loaded
            File errorFile = this.errorFilePath.toFile();
            String errorFileMessage;
            try {
                FileUtil.createFile(errorFilePath);
                CsvUtil.writeToCsv(errorFile, false, errors.toCsvString());
                errorFileMessage = String.format(MESSAGE_ERROR_FILE_CREATED, this.errorFilePath.toString());
            } catch (IOException ioe) {
                errorFileMessage = MESSAGE_ERROR_FILE_NOT_CREATED;
            }
            // Return result message
            String message = String.join(
                    "\n",
                    MESSAGE_PARTIAL_SUCCESS,
                    errors.toString(),
                    errorFileMessage,
                    MESSAGE_INVALID_FORMAT
            );
            throw new CommandException(message);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Parses a CSV file located at given {@link #csvFilePath} to {@code Entity} objects.
     *
     * @param csvFile The CSV file to parse.
     * @param model {@code Model} to add the {@code Entity} objects.
     */
    private void parseFile(File csvFile, Model model) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        int lineNumber = 1;
        String line;
        while ((line = csvReader.readLine()) != null) {
            Entity entityToAdd = this.parseLineToEntity(model, lineNumber, line, true);
            try {
                this.addEntity(entityToAdd, model);
            } catch (AlfredException e) {
                this.errors.add(new Error(lineNumber, line, CAUSE_DUPLICATE_ENTITY));
            }
            lineNumber++;
        }
        csvReader.close();
        this.addBufferedTeams(model);
    }

    /**
     * Parses and adds teams in {@link #teamBuffers} into {@code Alfred}.
     */
    private void addBufferedTeams(Model model) {
        while (!this.teamBuffers.isEmpty()) {
            String[] data = this.teamBuffers.poll().split(CsvUtil.CSV_SEPARATOR_REGEX, 2);
            int lineNumber = Integer.parseInt(data[0]);
            Entity entityToAdd = this.parseLineToEntity(model, lineNumber, data[1], false);
            try {
                this.addEntity(entityToAdd, model);
            } catch (AlfredException e) {
                this.errors.add(new Error(lineNumber, data[1], CAUSE_DUPLICATE_ENTITY));
            }
        }
    }

    /**
     * Parses given line into the corresponding {@code Entity}.
     *
     * @param lineNumber Line number of given line in the CSV file.
     * @param line Line in the CSV file.
     * @return Corresponding {@code Entity}.
     */
    private Entity parseLineToEntity(Model model, int lineNumber, String line, boolean shouldBufferTeam) {
        if (CsvUtil.isCsvHeader(line)) {
            return null;
        }
        String[] data = line.split(CsvUtil.CSV_SEPARATOR_REGEX);
        data[0] = data[0].toUpperCase();
        try {
            switch (data[0]) {
            case CliSyntax.PREFIX_ENTITY_MENTOR:
                return CsvUtil.parseToMentor(data);
            case CliSyntax.PREFIX_ENTITY_PARTICIPANT:
                return CsvUtil.parseToParticipant(data);
            case CliSyntax.PREFIX_ENTITY_TEAM:
                // Buffer teams to add them after Mentors and Participants
                if (shouldBufferTeam) {
                    this.teamBuffers.offer(lineNumber + CsvUtil.CSV_SEPARATOR + line);
                    return null;
                }
                return CsvUtil.parseToTeam(data, model);
            default:
                // If Entity CommandType is incorrect
                this.errors.add(new Error(lineNumber, line, CAUSE_INVALID_DATA));
            }
        } catch (IllegalArgumentException iae) {
            this.errors.add(new Error(lineNumber, line, CAUSE_INVALID_DATA));
        } catch (MissingEntityException mee) {
            this.errors.add(new Error(lineNumber, line, mee.getMessage()));
        }
        return null;
    }

    /**
     * Adds an {@code Entity} corresponding to given {@code data}.
     *
     * @param entityToAdd Entity to add into {@code model}.
     * @param model {@code Model} to add {@code Entity} to.
     * @throws AlfredException If the parsed Entity is already contained in {@code Model} (i.e. duplicate Entity).
     */
    private void addEntity(Entity entityToAdd, Model model) throws AlfredException {
        if (entityToAdd == null) {
            return;
        }
        if (entityToAdd instanceof Mentor) {
            model.addMentor((Mentor) entityToAdd);
        } else if (entityToAdd instanceof Participant) {
            model.addParticipant((Participant) entityToAdd);
        } else if (entityToAdd instanceof Team) {
            model.addTeam((Team) entityToAdd);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ImportCommand)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        ImportCommand command = (ImportCommand) other;
        return this.csvFilePath.equals(command.csvFilePath);
    }

}
