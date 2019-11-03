package seedu.address.diaryfeature.logic.parser;


import seedu.address.diaryfeature.logic.commands.FindCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.logic.predicates.FindPredicate;
import seedu.address.logic.commands.Command;


public class FindCommandParser {
    public static final String FIND_USAGE = "In particular, input your find command like this: \n" +
            "find target Eg: find birthday. \n Note that the input cant be empty, and has to be at least 1 character";


    public Command parse(String args) throws EmptyArgumentException {
        try {
            String trimmed = ParserUtil.parseStringArgs(args, FindCommand.COMMAND_WORD);
            return new FindCommand(new FindPredicate(trimmed));
        } catch (EmptyArgumentException error) {
            throw new EmptyArgumentException(FindCommand.COMMAND_WORD, FIND_USAGE);
        }


    }
}
