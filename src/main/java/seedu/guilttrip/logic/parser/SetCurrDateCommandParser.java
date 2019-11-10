package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;

import seedu.guilttrip.logic.commands.SetCurrDateCommand;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Date;

public class SetCurrDateCommandParser implements Parser<SetCurrDateCommand> {
    @Override
    public SetCurrDateCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args);
        Date date;
        if (args.isEmpty()) {
            date = new Date(LocalDate.now());
        } else {
            date = ParserUtil.parseDate(args.trim());
        }
        return new SetCurrDateCommand(date);
    }
}
