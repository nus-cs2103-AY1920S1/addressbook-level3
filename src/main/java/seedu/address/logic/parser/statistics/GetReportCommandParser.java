package seedu.address.logic.parser.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.statistics.GetReportCommand.COMMAND_WORD;

import seedu.address.logic.commands.statistics.GetReportCommand;
import seedu.address.logic.parser.Parser;

public class GetReportCommandParser implements Parser<GetReportCommand> {

    public GetReportCommand parse(String args) {
        requireNonNull(args);
        int index = Integer.parseInt(args.replace(COMMAND_WORD, "").trim());
        return new GetReportCommand(index);
    }
}
