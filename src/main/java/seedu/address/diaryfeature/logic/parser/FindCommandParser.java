package seedu.address.diaryfeature.logic.parser;


import seedu.address.diaryfeature.logic.commands.FindCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.logic.predicates.FindPredicate;
import seedu.address.logic.commands.Command;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {
    private static final String FIND_USAGE = "In particular, input your find command like this: \n" +
            "find TARGET | EG: find birthday. \n Note that the input cant be empty, and has to be at least 1 character\n" +
            "Remember to type the command list to go back to your original list";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws EmptyArgumentException if the user input does not conform the expected format
     */
    public Command parse(String args) throws EmptyArgumentException {
        try {
            String trimmed = ParserUtil.parseStringArgs(args, FindCommand.COMMAND_WORD);
            return new FindCommand(new FindPredicate(trimmed));
        } catch (EmptyArgumentException error) {
            throw new EmptyArgumentException(FindCommand.COMMAND_WORD, FIND_USAGE);
        }
    }
}
