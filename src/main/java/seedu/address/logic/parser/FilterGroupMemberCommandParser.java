package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FilterGroupMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parser to parse filtering of group members in a group.
 */
public class FilterGroupMemberCommandParser implements Parser<FilterGroupMemberCommand> {
    @Override
    public FilterGroupMemberCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        List<String> namesInString = argMultimap.getAllValues(PREFIX_NAME);
        List<Name> names = namesInString.stream()
                .map(name -> new Name(name)).collect(Collectors.toCollection(ArrayList::new));

        return new FilterGroupMemberCommand(names);
    }
}
