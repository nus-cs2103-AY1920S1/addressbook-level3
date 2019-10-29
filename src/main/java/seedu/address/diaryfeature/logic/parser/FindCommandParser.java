package seedu.address.diaryfeature.logic.parser;


import seedu.address.address.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.logic.commands.ErrorCommand;
import seedu.address.diaryfeature.logic.commands.FindCommand;
import seedu.address.diaryfeature.logic.predicates.FindPredicate;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;


public class FindCommandParser implements Parser<Command> {
    private final String EMPTY_STRING = "";
    public static final String COMMAND_NAME = "FIND COMMAND";



    public Command parse(String args) {

        String trimmed = args.trim();

        if(args.equalsIgnoreCase(EMPTY_STRING)) {
            return new ErrorCommand(new EmptyArgumentException(COMMAND_NAME));
        }

        return new FindCommand(new FindPredicate(trimmed));
    }


}
