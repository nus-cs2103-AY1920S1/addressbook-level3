package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Attendance;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;
import seedu.tarence.storage.Storage;

/**
 * Marks attendance of student in a specified tutorial.
 * Keyword matching is case insensitive.
 */
public class ExportAttendanceCommand extends Command {
    public static final String MESSAGE_EXPORT_ATTENDANCE_SUCCESS = "Attendance of %1$s exported successfully to /data";
    public static final String COMMAND_WORD = "exportAttendance";

    private static final String EXPORT_PATH = "./data/%s.csv";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "export",
        "exporta", "exportatt"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the attendance of a tutorial in a csv file.\n"
            + "Parameters:\n"
            + PREFIX_TUTORIAL_NAME + "TUTORIAL NAME "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_FILE + "FILE NAME (OPTIONAL)\n"
            + PREFIX_INDEX + "TUTORIAL INDEX "
            + PREFIX_FILE + "FILE NAME (OPTIONAL)\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "CS1010 "
            + PREFIX_FILE + "exportedAttendance\n"
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_FILE + "exportedAttendance\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private final Optional<ModCode> targetModCode;
    private final Optional<TutName> targetTutName;
    private final Optional<Index> targetIndex;
    private Optional<String> fileName;

    public ExportAttendanceCommand(ModCode modCode, TutName tutName, Index index, String fileName) {
        this.targetModCode = Optional.ofNullable(modCode);
        this.targetTutName = Optional.ofNullable(tutName);
        this.targetIndex = Optional.ofNullable(index);
        this.fileName = Optional.ofNullable(fileName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        // TODO: Consider cases with multiple matching tutorials, students
        Tutorial targetTutorial = null;
        if (targetModCode.isPresent() && targetTutName.isPresent()) {
            // format with modcode and tut name
            targetTutorial = lastShownList.stream()
                    .filter(tut -> tut.getTutName().equals(targetTutName.get())
                    && tut.getModCode().equals(targetModCode.get()))
                    .findFirst()
                    .orElse(null);

            if (targetTutorial == null) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
            }
        } else if (targetIndex.isPresent()) {
            // format with tutorial index
            try {
                targetTutorial = lastShownList.get(targetIndex.get().getZeroBased());
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX));
            }
        }

        if (fileName.isEmpty()) {
            fileName = Optional.of(
                    String.format("Attendance_%1$s_%2$s",
                    targetTutorial.getTutName(),
                    targetTutorial.getModCode()));
        }

        List<Student> students = targetTutorial.getStudents();
        Set<Week> weeks = targetTutorial.getTimeTable().getWeeks();
        Attendance attendance = targetTutorial.getAttendance();

        // try-with-resources
        try (
            Writer writer = Files.newBufferedWriter(Paths.get(String.format(EXPORT_PATH, fileName.get())));

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

        return new CommandResult(String.format(MESSAGE_EXPORT_ATTENDANCE_SUCCESS, targetTutorial.getTutName()));
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
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
                && targetTutName.equals(((ExportAttendanceCommand) other).targetTutName)
                && targetIndex.equals(((ExportAttendanceCommand) other).targetIndex)
                && fileName.equals(((ExportAttendanceCommand) other).fileName)); // state check
    }
}
