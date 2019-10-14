package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKS;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Attendance;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

/**
 * Marks attendance of student in a specified tutorial.
 * Keyword matching is case insensitive.
 */
public class ExportAttendanceCommand extends Command {

    private static final String EXPORT_PATH = "./data/%s.csv";

    public static final String EXPORT_ATTENDANCE_SUCCESS = "Attendance of %1$s exported successfully to /data";

    public static final String COMMAND_WORD = "export";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase()};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the attendance of a tutorial in a csv file.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_NAME + "TUTORIAL_NAME "
            + PREFIX_MODULE + "MODULE_CODE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "CS1010 ";

    private final ModCode targetModCode;
    private final TutName targetTutName;
    private final String fileName;

    public ExportAttendanceCommand(ModCode modCode, TutName tutName) {
        requireAllNonNull(modCode, tutName);
        this.targetModCode = modCode;
        this.targetTutName = tutName;
        this.fileName = String.format("Attendance_%s_%s", modCode, tutName);
    }

    public ExportAttendanceCommand(ModCode modCode, TutName tutName, String fileName) {
        requireAllNonNull(modCode, tutName);
        this.targetModCode = modCode;
        this.targetTutName = tutName;
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        // TODO: Multiple tutorials, students error?
        Tutorial targetTutorial = lastShownList.stream()
                .filter(tut -> tut.getTutName().equals(targetTutName) && tut.getModCode().equals(targetModCode))
                .findFirst()
                .orElse(null);
        if (targetTutorial == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
        }

        List<Student> students = targetTutorial.getStudents();
        Set<Week> weeks = targetTutorial.getTimeTable().getWeeks();
        Attendance attendance = targetTutorial.getAttendance();

        // try-with-resources
        try (
            Writer writer = Files.newBufferedWriter(Paths.get(String.format(EXPORT_PATH, fileName)));

            CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
        ) {
            List<String> headerList = weeks.stream()
                    .map(week -> "Week " + week.toString())
                    .collect(Collectors.toList());
            headerList.add(0, "Name");
            String[] header = new String[weeks.size() + 1];
            header = headerList.toArray(header);
            csvWriter.writeNext(header);

            for (Student student : students) {
                List<String> rowList = new ArrayList<>();
                rowList.add(student.getName().toString());
                for (Week week : weeks) {
                    rowList.add(attendance.isPresent(week, student) + "");
                }
                String[] row = new String[weeks.size() + 1];
                row = rowList.toArray(row);
                csvWriter.writeNext(row);
            }
        } catch (IOException | InvalidPathException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_FILE);
        }

        return new CommandResult(String.format(EXPORT_ATTENDANCE_SUCCESS, targetTutorial.getTutName()));
    }

    @Override
    public boolean needsInput() {
        return false;
    }

    @Override
    public boolean needsCommand(Command command) {
        return false;
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportAttendanceCommand // instanceof handles nulls
                && targetModCode.equals(((ExportAttendanceCommand) other).targetModCode)
                && targetTutName.equals(((ExportAttendanceCommand) other).targetTutName)); // state check
    }
}
