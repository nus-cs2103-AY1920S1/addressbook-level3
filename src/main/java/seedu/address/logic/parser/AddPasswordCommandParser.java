package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_TAG_FIELDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORDVALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommandPassword object
 */
public class AddPasswordCommandParser implements Parser<AddPasswordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPasswordCommand
     * and returns an AddPasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPasswordCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DESCRIPTION,
                        PREFIX_USERNAME, PREFIX_PASSWORDVALUE, PREFIX_WEBSITE, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_USERNAME, PREFIX_PASSWORDVALUE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPasswordCommand.MESSAGE_USAGE));
        }

        PasswordDescription passwordDescription =
                ParserUtil.parsePasswordDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
        PasswordValue passwordValue = ParserUtil.parsePasswordValue(argMultimap.getValue(PREFIX_PASSWORDVALUE).get());
        PasswordModifiedAt passwordModifiedAt = new PasswordModifiedAt(new Date());
        Website website = ParserUtil.parseWebsite(argMultimap.getValue(PREFIX_WEBSITE).orElse("NIL"));

        if (argMultimap.getAllValues(PREFIX_TAG).size() > 5) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_TAG_FIELDS, AddPasswordCommand.MESSAGE_USAGE));
        }
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Password password =
                new Password(passwordDescription, username, passwordValue, passwordModifiedAt, website, tagList);
        return new AddPasswordCommand(password);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
