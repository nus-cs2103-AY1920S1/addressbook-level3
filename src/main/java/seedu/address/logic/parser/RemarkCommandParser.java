package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemarkCommandParser implements Parser<RemarkCommand>{
    @Override
    public RemarkCommand parse(String userInput) throws ParseException {
        Index index = ParserUtil.parseIndex(userInput);
        return new RemarkCommand();
    }
}
