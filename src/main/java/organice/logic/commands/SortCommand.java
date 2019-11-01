package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.SortCommandParser;
import organice.model.Model;

/**
 * Sorts a list of matches in ORGANice.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a sorted list of matches.\n"
            + "Use this command only after a match command is run. Valid commands:\n"
            + "'match ic/[patientNRIC]' then 'sort rate/expiry'.\n"
            + "'match ic/all' then 'sort priority'.\n";

    public static final String LIST_OF_SORTED_DONORS = "List of matched donors found.\n";
    public static final String LIST_OF_SORTED_PATIENTS = "List of matched patients found.\n";
    public static final String MESSAGE_SUCCESS = "Successfully sorted.\n";
    public static final String MESSAGE_FAILURE = "No match list detected.\n"
            + "Before sorting, run a match command first with 'match ic/[patientNRIC]' or 'match ic/all'.\n";

    private String input;

    public SortCommand(String input) {
        requireNonNull(input);
        this.input = input;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String resultMessage = "";
        requireNonNull(model);
        if (input.equalsIgnoreCase(SortCommandParser.ORGAN_EXPIRY_DATE)) {
            model.sortByOrganExpiryDate();
            resultMessage = LIST_OF_SORTED_DONORS;
        } else if (input.equalsIgnoreCase(SortCommandParser.PRIORITY)) {
            model.sortByPriority();
            resultMessage = LIST_OF_SORTED_PATIENTS;
        } else if (input.equalsIgnoreCase(SortCommandParser.SUCCESS_RATE)) {
            model.sortBySuccessRate();
            resultMessage = LIST_OF_SORTED_DONORS;
        }
        CommandResult commandResult = new CommandResult(resultMessage + MESSAGE_SUCCESS);
        commandResult.setSort(true);
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && input.equals(((SortCommand) other).input)); // state check
    }
}
