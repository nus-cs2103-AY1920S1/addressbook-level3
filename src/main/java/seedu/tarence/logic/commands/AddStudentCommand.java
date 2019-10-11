package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
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
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_TUTORIAL_INDEX + "1";

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
            throw new CommandException(MESSAGE_INVALID_CLASS);
        }

        model.addStudent(toAdd);
        model.addStudentToTutorial(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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
