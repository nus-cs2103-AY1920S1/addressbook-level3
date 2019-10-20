package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.core.Messages.MESSAGE_SUGGESTED_CORRECTIONS;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.builder.StudentBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Adds a student into T.A.rence.
 */
public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "addStudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person into T.A.rence. \n"
            + "Full format: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_TUTORIAL_NAME + "TUTORIAL_NAME"
            + PREFIX_MODULE + "MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "CS1010\n"
            + "Shortcut Format: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_INDEX + "TUTORIAL_INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_INDEX + "1";

    public static final String MESSAGE_DUPLICATE_STUDENT = "This person already exists!";
    public static final String MESSAGE_INVALID_CLASS = "No such module and/or tutorial class exists.";
    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_TUTORIAL_IDX_OUT_OF_BOUNDS = "The given tutorial index %d is out of bounds.";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
        "addstu", "addstud"};

    private Student toAdd;
    private Index tutIdx;


    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddStudentCommand(Student student) {
        requireNonNull(student);
        this.toAdd = student;
        this.tutIdx = null;
    }


    public AddStudentCommand(Student student, Index tutorialIndex) {
        requireNonNull(student);
        requireNonNull(tutorialIndex);
        this.toAdd = student;
        this.tutIdx = tutorialIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (tutIdx != null) {
            ObservableList<Tutorial> tutList = model.getFilteredTutorialList();
            Tutorial tutorial = getTutorial(tutList, tutIdx.getZeroBased());
            TutName tutName = tutorial.getTutName();
            ModCode modCode = tutorial.getModCode();
            this.toAdd = new Student(toAdd.getName(), toAdd.getEmail(), toAdd.getMatricNum(),
                    toAdd.getNusnetId(), modCode, tutName);
        }

        if (model.hasStudent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        if (!model.hasTutorialInModule(toAdd.getModCode(), toAdd.getTutName())) {
            ModCode modCode = toAdd.getModCode();
            TutName tutName = toAdd.getTutName();
            // find tutorials with same name and similar modcodes, and similar names and same modcode
            List<ModCode> similarModCodes = getSimilarModCodesWithTutorial(modCode, tutName, model);
            List<TutName> similarTutNames = getSimilarTutNamesWithModule(modCode, tutName, model);
            if (similarModCodes.size() == 0 && similarTutNames.size() == 0) {
                throw new CommandException(MESSAGE_INVALID_CLASS);
            }

            String suggestedCorrections = createSuggestedCommands(similarModCodes, modCode,
                    similarTutNames, tutName, toAdd, model);
            model.storePendingCommand(new SelectSuggestionCommand());
            return new CommandResult(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                    modCode.toString() + " " + tutName.toString()) + suggestedCorrections);

        }

        model.addStudent(toAdd);
        model.addStudentToTutorial(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Generates and stores {@code AddStudentCommand}s from a list of {@code ModCode}s.
     *
     * @param similarModCodes List of {@code ModCode}s similar to the user's input.
     * @param model The {@code Model} in which to store the generated commands.
     * @return string representing the suggested {@code ModCode}s and their corresponding indexes for user selection.
     */
    private String createSuggestedCommands(List<ModCode> similarModCodes, ModCode originalModCode,
                                           List<TutName> similarTutNames, TutName originalTutName,
                                           Student student, Model model) {
        List<Command> suggestedCommands = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int index = 1;
        for (ModCode similarModCode : similarModCodes) {
            Student newStudent = new StudentBuilder(student).withModCode(similarModCode.toString()).build();
            suggestedCommands.add(new AddStudentCommand(newStudent));
            s.append(index).append(". ").append(similarModCode).append(", ").append(originalTutName).append("\n");
            index++;
        }
        for (TutName similarTutName: similarTutNames) {
            Student newStudent = new StudentBuilder(student).withTutName(similarTutName.toString()).build();
            AddStudentCommand newCommand = new AddStudentCommand(newStudent);
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
                || (other instanceof AddStudentCommand // instanceof handles nulls
                && toAdd.equals(((AddStudentCommand) other).toAdd));
    }

    /**
     * Returns the tutorial based on the given index
     * @param tutList - existing list of tutorials
     * @param tutIdx - index provided by the user
     * @throws CommandException when the given tutorial is out of bounds
     */
    private Tutorial getTutorial(ObservableList<Tutorial> tutList, Integer tutIdx) throws CommandException {
        try {
            return tutList.get(tutIdx);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(String.format(MESSAGE_TUTORIAL_IDX_OUT_OF_BOUNDS, tutIdx + 1));
        }
    }
}
