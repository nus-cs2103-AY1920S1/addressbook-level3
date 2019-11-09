package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_MODE;

import seedu.revision.commons.core.Messages;
import seedu.revision.logic.commands.Command;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.quiz.Mode;

/**
 * Command to start the quiz session.
 */

public class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String COMMAND_AUTOCOMPLETE = "start mode/";
    public static final String COMMAND_AUTOCOMPLETE_NORMAL = "start mode/normal";
    public static final String COMMAND_AUTOCOMPLETE_ARCADE = "start mode/arcade";
    public static final String COMMAND_AUTOCOMPLETE_CUSTOM = "start mode/custom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts quiz based on Mode (Normal / Arcade / Custom)\n"
            + "For Custom, you can add the optional prefixes timer/ cat/ diff/ "
            + "If no prefixes are provided, quiz will default to normal mode.\n"
            + "Parameters: "
            + PREFIX_MODE + "MODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODE + "normal";

    private static final String MESSAGE_SUCCESS = "Starting Quiz!";

    private Mode mode;

    /** Instantiates a StartCommand to start a quiz session. **/
    public StartCommand(Mode mode) {
        this.mode = mode;
    }

    @Override
    public CommandResult execute(Model model) throws ParseException {
        requireNonNull(model);
        model.updateFilteredAnswerableList(this.mode.getCombinedPredicate());

        return new CommandResultBuilder().withFeedBack(String.format(Messages.MESSAGE_ANSWERABLES_LISTED_OVERVIEW,
                model.getFilteredAnswerableList().size())).withStart(true).withMode(mode).build();
    }
}
