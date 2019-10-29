package seedu.module.logic.parser.deadlinecommandparsers;

import static seedu.module.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TASK_LIST_NUMBER;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.module.logic.commands.deadlinecommands.DeadlineCommand;
import seedu.module.logic.parser.ArgumentMultimap;
import seedu.module.logic.parser.ArgumentTokenizer;
import seedu.module.logic.parser.Parser;
import seedu.module.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeadlineCommand object.
 */
public class DeadlineCommandParser implements Parser<DeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeadlineCommand
     * @param args
     * @return a DeadlineCommand object for execution.
     * @throws ParseException if user input does not conform the expected format.
     */
    public DeadlineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ACTION, PREFIX_TASK_LIST_NUMBER,
                PREFIX_DESCRIPTION, PREFIX_TIME, PREFIX_TAG);
        try {
            if (!argMultimap.getValue(PREFIX_ACTION).isPresent()) {
                throw new ParseException("Input format error. a/ACTION not found");
            }
            if (argMultimap.getValue(PREFIX_ACTION).get().equals("add")) {
                ArgumentMultimap newArgMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ACTION, PREFIX_DESCRIPTION,
                        PREFIX_TIME, PREFIX_TAG);
                return new AddDeadlineCommandParser().parse(newArgMultimap);
            } else if (argMultimap.getValue(PREFIX_ACTION).get().equals("edit")) {
                ArgumentMultimap newArgMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ACTION,
                        PREFIX_TASK_LIST_NUMBER, PREFIX_DESCRIPTION, PREFIX_TIME);
                return new EditDeadlineCommandParser().parse(newArgMultimap);
            } else if (argMultimap.getValue(PREFIX_ACTION).get().equals("delete")) {
                ArgumentMultimap newArgMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ACTION,
                        PREFIX_TASK_LIST_NUMBER);
                return new DeleteDeadlineCommandParser().parse(newArgMultimap);
            } else if (argMultimap.getValue(PREFIX_ACTION).get().equals("done")) {
                ArgumentMultimap newArgMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ACTION,
                        PREFIX_TASK_LIST_NUMBER);
                return new DoneDeadlineCommandParser().parse(newArgMultimap);
            } else if (argMultimap.getValue(PREFIX_ACTION).get().equals("inProgress")) {
                ArgumentMultimap newArgMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ACTION,
                        PREFIX_TASK_LIST_NUMBER);
                return new InProgressDeadlineCommandParser().parse(newArgMultimap);
            } else {
                throw new ParseException("Command not recognised");
            }
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, e));
        }
    }
}
