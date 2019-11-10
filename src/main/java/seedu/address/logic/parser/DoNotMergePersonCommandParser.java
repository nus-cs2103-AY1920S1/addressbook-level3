package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.merge.DoNotMergePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DoNotMergePersonCommand object
 */
public class DoNotMergePersonCommandParser implements Parser<DoNotMergePersonCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DoNotMergePersonCommand
     * and returns a DoNotMergePersonCommand object for execution.
     * All arguments are expected to be valid at this point and parse exceptions should not be thrown.
     *
     * @throws seedu.address.logic.parser.exceptions.ParseException if the user input does not
     *                                                              conform the expected format
     */
    public DoNotMergePersonCommand parse(String args) throws ParseException {
        String trimmedArgs = removeAddCommandWord(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_DATE_OF_BIRTH, PREFIX_GENDER);

        assert(arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NRIC, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_DATE_OF_BIRTH, PREFIX_GENDER) || !arePrefixesPresent(argMultimap, PREFIX_POLICY, PREFIX_TAG)
            || argMultimap.getPreamble().isEmpty());

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        DateOfBirth dateOfBirth = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DATE_OF_BIRTH).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Set<Policy> policyList = new HashSet<>();
        Set<Tag> tagList = new HashSet<>();

        Person person = new Person(name, nric, phone, email, address, dateOfBirth, gender, policyList, tagList);

        return new DoNotMergePersonCommand(person);
    }

    private String removeAddCommandWord(String args) {
        String withoutAddCommandWord = args.replaceFirst(AddCommand.COMMAND_WORD, "");
        return withoutAddCommandWord;
    }

}
