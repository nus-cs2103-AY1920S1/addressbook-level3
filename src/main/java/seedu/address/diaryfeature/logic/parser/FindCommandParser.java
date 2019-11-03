package seedu.address.diaryfeature.logic.parser;


import seedu.address.diaryfeature.logic.commands.FindCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.logic.predicates.FindPredicate;
import seedu.address.logic.commands.Command;


public class FindCommandParser {


    public Command parse(String args) throws EmptyArgumentException {
        String trimmed = ParserUtil.parseArgs(args,FindCommand.COMMAND_WORD);
        return new FindCommand(new FindPredicate(trimmed));
    }


}
