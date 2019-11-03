package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.core.Messages.MESSAGE_SUGGESTED_CORRECTIONS;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.ArrayList;
import java.util.List;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.Storage;

/**
 * Displays the selected tutorial attendance
 */
public class DisplayAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "displayAttendance";

    public static final String MESSAGE_SUCCESS = "Attendance is displayed!";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "displayatt", "showattendance",
        "showatt"};

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the tutorial attendance identified by the tutorial name and module code of the tutorial.\n"
            + "Parameters:\n"
            + PREFIX_TUTORIAL_NAME + "TUTORIAL_NAME "
            + PREFIX_MODULE + "MODULE_CODE \n"
            + PREFIX_INDEX + "TUTORIAL_INDEX\n"
            + "Example:\n"
            + COMMAND_WORD + " " + PREFIX_INDEX + "1\n"
            + COMMAND_WORD + " " + PREFIX_TUTORIAL_NAME + "Lab 02 " + PREFIX_MODULE + "CS2040 \n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private static final ModCode DEFAULT_MOD_CODE = new ModCode("MC1010");
    private static final TutName DEFAULT_TUT_NAME = new TutName("notARealTutorial");
    private static final Index DEFAULT_INDEX = Index.fromOneBased(1);

    private ModCode modCode;
    private TutName tutName;
    private Index index;

    /**
     * Default Constructor with module code and tutorial name provided
     */
    public DisplayAttendanceCommand(ModCode modCode, TutName tutName) {
        this.modCode = modCode;
        this.tutName = tutName;
        this.index = DEFAULT_INDEX;
    }

    /**
     * Constructor based on shortcut index format
     */
    public DisplayAttendanceCommand(Index index) {
        this.index = index;
        this.modCode = DEFAULT_MOD_CODE;
        this.tutName = DEFAULT_TUT_NAME;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Tutorial> lastShownTutorialList = model.getFilteredTutorialList();

        if (tutName.equals(DEFAULT_TUT_NAME)) {
            if (index.getZeroBased() >= lastShownTutorialList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
            }
            Tutorial tutorial = lastShownTutorialList.get(index.getZeroBased());
            return new CommandResult(String.format(MESSAGE_SUCCESS, tutorial), tutorial);
        }

        Tutorial tutorialToDisplay = getTutorial(lastShownTutorialList);

        if (tutorialToDisplay != null) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, tutorialToDisplay), tutorialToDisplay);
        }

        return handleSuggestedCommands(model);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }


    /**
     * Retrieves tutorial based on module code and tutorial name provided by user
     * @throws CommandException if no such module code or tutorial name is found
     */
    private Tutorial getTutorial(List<Tutorial> tutorialList) {
        for (Tutorial tutorial : tutorialList) {
            if ((tutorial.getTutName().equals(tutName)) && (tutorial.getModCode().equals(modCode))) {
                return tutorial;
            }
        }

        return null;
    }

    /**
     * Handles the creating and processing of suggested {@code DisplayAttendanceCommand}s, if the user's input does not
     * match any combination of modules, tutorials and students.
     */
    private CommandResult handleSuggestedCommands(Model model)
        throws CommandException {
        List<TutName> similarTutNames = getSimilarTutNamesWithModule(modCode, tutName, model);
        List<ModCode> similarModCodes = getSimilarModCodesWithTutorial(modCode, tutName, model);
        if (similarTutNames.size() == 0 && similarModCodes.size() == 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
        }

        String suggestedCorrections = createSuggestedCommands(similarModCodes, similarTutNames, model);
        return new CommandResult(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                modCode.toString() + " " + tutName.toString()) + suggestedCorrections);
    }

    /**
     * Generates and stores {@code DisplayAttendanceCommand}s from a list of {@code ModCode}s and {@code TutName}s.
     *
     * @param similarModCodes List of {@code ModCode}s similar to the user's input.
     * @param similarTutNames List of {@code TutName}s similar to the user's input.
     * @param model The {@code Model} in which to store the generated commands.
     * @return string representing the suggested {@code ModCode}s and their corresponding indexes for user selection.
     */
    private String createSuggestedCommands(List<ModCode> similarModCodes, List<TutName> similarTutNames, Model model) {
        List<Command> suggestedCommands = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int index = 1;
        for (ModCode similarModCode : similarModCodes) {
            suggestedCommands.add(new DisplayAttendanceCommand(similarModCode, tutName));
            s.append(index).append(". ").append(similarModCode).append(", ").append(tutName).append("\n");
            index++;
        }
        for (TutName similarTutName: similarTutNames) {
            DisplayAttendanceCommand newCommand = new DisplayAttendanceCommand(modCode, similarTutName);
            if (suggestedCommands.stream()
                    .anyMatch(existingCommand -> existingCommand.equals(newCommand))) {
                continue;
            }
            suggestedCommands.add(newCommand);
            s.append(index).append(". ").append(modCode).append(", ").append(similarTutName).append("\n");
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
                || ((other instanceof DisplayAttendanceCommand // instanceof handles nulls
                && (modCode.equals(((DisplayAttendanceCommand) other).modCode))
                && (tutName.equals(((DisplayAttendanceCommand) other).tutName))
                && (index.equals(((DisplayAttendanceCommand) other).index)))); // state check
    }
}
