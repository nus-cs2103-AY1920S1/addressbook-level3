package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.core.Messages.MESSAGE_SUGGESTED_CORRECTIONS;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;
import seedu.tarence.storage.Storage;

/**
 * Marks attendance of student in a specified tutorial.
 * Keyword matching is case insensitive.
 */
public class MarkAttendanceCommand extends Command {

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Attendance of %1$s marked as %2$s";
    public static final String MESSAGE_CONFIRM_MARK_ATTENDANCE_OF_STUDENT = "Do you want to mark "
            + "%1$s's attendance?\n"
            + "(y/n)";
    public static final String MESSAGE_MARK_ATTENDANCE_TUTORIAL = "Marking attendance of %1$s";

    public static final String COMMAND_WORD = "markAttendance";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "mark", "marka", "markatt"};

    // TODO: Update message to include index format
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles the attendance of a student in a tutorial.\n"
            + "Parameters:\n"
            + PREFIX_NAME + "NAME (OPTIONAL) "
            + PREFIX_TUTORIAL_NAME + "TUTORIAL NAME "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_TUTORIAL_WEEKS + "WEEK\n"
            + PREFIX_INDEX + "TUTORIAL INDEX "
            + PREFIX_TUTORIAL_WEEKS + "WEEK\n"
            + "Note:\n"
            + "If name is not specified, the entire tutorial's attendance will be marked one student at a time.\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "CS1010 "
            + PREFIX_TUTORIAL_WEEKS + "5\n"
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_TUTORIAL_WEEKS + "5\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private final Optional<ModCode> targetModCode;
    private final Optional<TutName> targetTutName;
    private final Optional<Index> targetIndex;
    private final Week week;
    private final Optional<Name> targetStudName;

    public MarkAttendanceCommand(ModCode modCode, TutName tutName, Index index, Week week, Name studName) {
        this.targetModCode = Optional.ofNullable(modCode);
        this.targetTutName = Optional.ofNullable(tutName);
        this.targetIndex = Optional.ofNullable(index);
        this.targetStudName = Optional.ofNullable(studName);
        this.week = week;
    }

    private MarkAttendanceCommand(ModCode modCode, TutName tutName, Index index, Week week, Optional<Name> studName) {
        this.targetModCode = Optional.ofNullable(modCode);
        this.targetTutName = Optional.ofNullable(tutName);
        this.targetIndex = Optional.ofNullable(index);
        this.targetStudName = studName;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        // TODO: Consider cases with multiple matching tutorials, students?
        Tutorial targetTutorial = null;
        if (targetModCode.isPresent() && targetTutName.isPresent()) {
            // format with modcode and tutname
            targetTutorial = lastShownList.stream()
                    .filter(tut -> tut.getTutName().equals(targetTutName.get())
                    && tut.getModCode().equals(targetModCode.get()))
                    .findFirst()
                    .orElse(null);
            if (targetTutorial == null) {
                return handleSuggestedClassCommands(targetModCode.get(), targetTutName.get(), targetStudName, model);
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

        Set<Week> weeks = targetTutorial.getTimeTable().getWeeks();
        if (!weeks.contains(week)) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEEK_IN_TUTORIAL);
        }

        if (targetStudName.isEmpty()) {
            // stores the chain of commands to mark attendance of a class if targetStudName is not specified
            List<Student> students = targetTutorial.getStudents();
            for (int i = students.size() - 1; i >= 0; i--) {
                model.storePendingCommand(
                        new MarkAttendanceVerifiedCommand(targetTutorial, week, students.get(i)));
                model.storePendingCommand(
                        new DisplayCommand(
                        String.format(MESSAGE_CONFIRM_MARK_ATTENDANCE_OF_STUDENT, students.get(i).getName())));
                model.storePendingCommand(
                        new DisplayAttendanceCommand(targetTutorial.getModCode(), targetTutorial.getTutName()));
            }

            return new CommandResult(
                    String.format(MESSAGE_MARK_ATTENDANCE_TUTORIAL, targetTutorial.getTutName()));
        }

        // otherwise marks attendance of individual student
        Student targetStudent = targetTutorial.getStudents().stream()
            .filter(student -> student.getName().equals(targetStudName.get()))
            .findFirst()
            .orElse(null);

        if (targetStudent == null) {
            return handleSuggestedStudentCommands(
                    targetTutorial.getModCode(), targetTutorial.getTutName(), targetStudName.get(), model);
        }

        targetTutorial.setAttendance(week, targetStudent);

        String isPresent = targetTutorial.getAttendance().isPresent(week, targetStudent) ? "present" : "absent";
        model.storePendingCommand(
                        new DisplayAttendanceCommand(targetTutorial.getModCode(), targetTutorial.getTutName()));
        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                targetStudent.getName(), isPresent));
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    /**
     * Handles the creating and processing of suggested {@code MarkAttendanceCommand}s, if the user's input does not
     * match any combination of modules and tutorials.
     *
     * @param modCode The module code entered by the user.
     * @param tutName The tutorial name entered by the user.
     * @param studName The student name entered by the user, if any.
     * @param model The model to search in.
     * @return a string representation of the suggested alternative commands to the user's invalid input.
     * @throws CommandException if no suggested commands can be found.
     */
    private CommandResult handleSuggestedClassCommands(
            ModCode modCode, TutName tutName, Optional<Name> studName, Model model) throws CommandException {
        // find tutorials with same name and similar modcodes, and similar names and same modcode
        List<ModCode> similarModCodes = getSimilarModCodesWithTutorial(modCode, tutName, model);
        List<TutName> similarTutNames = getSimilarTutNamesWithModule(modCode, tutName, model);
        if (similarModCodes.size() == 0 && similarTutNames.size() == 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
        }

        String suggestedCorrections = createSuggestedClassCommands(similarModCodes, modCode,
                similarTutNames, tutName, targetStudName, model);
        model.storePendingCommand(new SelectSuggestionCommand());
        return new CommandResult(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                modCode.toString() + " " + tutName.toString()) + suggestedCorrections);
    }

    /**
     * Handles the creating and processing of suggested {@code MarkAttendanceCommand}s, if the user's input does not
     * match any combination of modules, tutorials and students.
     *
     * @param modCode The module code entered by the user.
     * @param tutName The tutorial name entered by the user.
     * @param studName The student name entered by the user.
     * @param model The model to search in.
     * @return a string representation of the suggested alternative commands to the user's invalid input.
     * @throws CommandException if no suggested commands can be found.
     */
    private CommandResult handleSuggestedStudentCommands(
            ModCode modCode, TutName tutName, Name studName, Model model) throws CommandException {
        // find students with similar names in the given module/tutorial combination.
        List<Name> similarNames = getSimilarStudNamesWithTutorialAndModule(modCode, tutName, studName, model);
        if (similarNames.size() == 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_IN_TUTORIAL);
        }

        String suggestedCorrections = createSuggestedStudentCommands(modCode, tutName, similarNames, model);
        model.storePendingCommand(new SelectSuggestionCommand());
        return new CommandResult(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Student",
                studName.toString()) + suggestedCorrections);
    }

    /**
     * Generates and stores {@code MarkAttendanceCommand}s from a list of {@code ModCode}s and {@code TutName}s.
     *
     * @param similarModCodes List of {@code ModCode}s similar to the user's input.
     * @param originalModCode Original modcode input from user.
     * @param similarTutNames List of {@code TutName}s similar to the user's input.
     * @param originalTutName Original tutorial name input from user.
     * @param studName Name of target student, if any.
     * @param model The {@code Model} in which to store the generated commands.
     * @return string representing the generated suggestions and their corresponding indexes for user selection.
     */
    private String createSuggestedClassCommands(List<ModCode> similarModCodes, ModCode originalModCode,
                                                List<TutName> similarTutNames, TutName originalTutName,
                                                Optional<Name> studName, Model model) {
        List<Command> suggestedCommands = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int index = 1;
        for (ModCode similarModCode : similarModCodes) {
            if (studName.isPresent()
                    && !model.hasStudentInTutorialAndModule(studName.get(), originalTutName, similarModCode)) {
                // if student name is specified, make sure the student exists in the tutorial/module combination.
                // else, skip the check as desired action is to mark attendance for all students.
                continue;
            }
            suggestedCommands.add(new MarkAttendanceCommand(similarModCode, originalTutName, null,
                    week, studName));
            s.append(index).append(". ").append(similarModCode).append(", ").append(originalTutName).append("\n");
            index++;
        }
        for (TutName similarTutName: similarTutNames) {
            if (studName.isPresent()
                    && !model.hasStudentInTutorialAndModule(studName.get(), similarTutName, originalModCode)) {
                continue;
            }
            MarkAttendanceCommand newCommand = new MarkAttendanceCommand(originalModCode, similarTutName, null,
                    week, studName);
            if (suggestedCommands.stream()
                    .anyMatch(existingCommand -> existingCommand.equals(newCommand))) {
                continue;
            }
            suggestedCommands.add(newCommand);
            s.append(index).append(". ").append(originalModCode).append(", ").append(similarTutName).append("\n");
            index++;
        }
        String suggestedCorrections = s.toString();
        model.storeSuggestedCommands(suggestedCommands, suggestedCorrections);
        return suggestedCorrections;
    }

    /**
     * Generates and stores {@code MarkAttendanceCommand}s from a list of {@code Name}s.
     *
     * @param modCode Code of module that suggested student must be in.
     * @param tutName Name of tutorial that suggested student must be in.
     * @param similarNames List of similar student names.
     * @param model The {@code Model} to search in.
     * @return string representing the generated suggestions and their corresponding indexes for user selection.
     */
    private String createSuggestedStudentCommands(
            ModCode modCode, TutName tutName, List<Name> similarNames, Model model) {
        List<Command> suggestedCommands = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int index = 1;
        for (Name similarName : similarNames) {
            suggestedCommands.add(new MarkAttendanceCommand(modCode, tutName, null, week, similarName));
            s.append(index).append(". ").append(similarName).append("\n");
            index++;
        }
        String suggestedCorrections = s.toString();
        model.storeSuggestedCommands(suggestedCommands, suggestedCorrections);
        return suggestedCorrections;
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
                || (other instanceof MarkAttendanceCommand // instanceof handles nulls
                && targetModCode.equals(((MarkAttendanceCommand) other).targetModCode)
                && week.equals(((MarkAttendanceCommand) other).week)
                && targetTutName.equals(((MarkAttendanceCommand) other).targetTutName)
                && targetStudName.equals(((MarkAttendanceCommand) other).targetStudName)); // state check
    }
}
