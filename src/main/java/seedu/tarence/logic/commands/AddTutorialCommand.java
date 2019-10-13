package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.core.Messages.MESSAGE_SUGGESTED_CORRECTIONS;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_DAY;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_DURATION_IN_MINUTES;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_START_TIME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKS;

import java.util.ArrayList;
import java.util.List;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.finder.Finder;
import seedu.tarence.model.Model;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Adds a tutorial into T.A.rence.
 */
public class AddTutorialCommand extends Command {

    public static final String COMMAND_WORD = "addTutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Tutorial to the Application.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_NAME + "NAME "
            + PREFIX_TUTORIAL_DAY + "DAY "
            + PREFIX_TUTORIAL_START_TIME + "START_TIME "
            + PREFIX_TUTORIAL_WEEKS + "WEEKS "
            + PREFIX_TUTORIAL_DURATION_IN_MINUTES + "DURATION_IN_MINUTES\n"
            + "Examples: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_NAME + "Lab 01 "
            + PREFIX_MODULE + "PC1431 "
            + PREFIX_TUTORIAL_DAY + "MONDAY "
            + PREFIX_TUTORIAL_START_TIME + "1200 "
            + PREFIX_TUTORIAL_WEEKS + "7,10,12 "
            + PREFIX_TUTORIAL_DURATION_IN_MINUTES + "120\n"
            + "Omit w/WEEKS field for default range (weeks 3-13), or specify in the form of a list (e.g. 1,2,3), a "
            + "range (4-6), or 'odd' or 'even' for those weeks only.";

    public static final String MESSAGE_DUPLICATE_TUTORIAL = "Wow, this tutorial already exists!";
    public static final String MESSAGE_INVALID_MODULE = "Error: No such module exists.";

    public static final String MESSAGE_SUCCESS = "New tutorial added: %1$s. Day: %2$s. Weeks: %3$s. "
                                                + "Start Time: %4$s. Duration: %5$s MINS.";

    public static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "addtut"};

    private Tutorial tutorial;

    public AddTutorialCommand(Tutorial newTutorial) {
        requireNonNull(newTutorial);
        tutorial = newTutorial;
    }

    @Override
    public CommandResult execute (Model model) throws CommandException {
        requireNonNull(model);

        // reject input if requested tutorial already exists in application
        if (model.hasTutorial(tutorial)) {
            throw new CommandException(MESSAGE_DUPLICATE_TUTORIAL);
        }

        ModCode modCode = tutorial.getModCode();
        if (!model.hasModuleOfCode(modCode)) {
            List<ModCode> similarModCodes = new Finder(model).findSimilarModCodes(modCode);
            if (similarModCodes.size() == 0) {
                throw new CommandException(MESSAGE_INVALID_MODULE);
            }

            String suggestedCorrections = createSuggestedCommands(similarModCodes, model);
            model.storePendingCommand(new SelectSuggestionCommand());
            return new CommandResult(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Module",
                    modCode) + suggestedCorrections);
        }

        model.addTutorial(tutorial);
        model.addTutorialToModule(tutorial);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorial, tutorial.getTimeTable().getDay(),
                tutorial.getTimeTable().getWeeks(), tutorial.getTimeTable().getStartTime(),
                tutorial.getTimeTable().getDuration().toMinutes()));
    }

    /**
     * Generates and stores {@code AddTutorialCommand}s from a list of {@code ModCode}s.
     *
     * @param similarModCodes List of {@code ModCode}s similar to the user's input.
     * @param model The {@code Model} in which to store the generated commands.
     * @return string representing the suggested {@code ModCode}s and their corresponding indexes for user selection.
     */
    private String createSuggestedCommands(List<ModCode> similarModCodes, Model model) {
        List<Command> suggestedCommands = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int index = 1;
        for (ModCode similarModCode : similarModCodes) {
            Tutorial newTutorial = new TutorialBuilder(tutorial).withModCode(similarModCode).build();
            suggestedCommands.add(new AddTutorialCommand(newTutorial));
            s.append(index).append(". ").append(similarModCode).append("\n");
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
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTutorialCommand)) {
            return false;
        }

        return tutorial.equals(((AddTutorialCommand) other).tutorial);
    }
}

