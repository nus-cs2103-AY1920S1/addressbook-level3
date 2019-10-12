package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateStudyPlanCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CreateStudyPlanCommand object
 */
public class CreateStudyPlanCommandParser implements Parser<CreateStudyPlanCommand> {

    // TODO: implement this entire class

    /**
     * Parses the given {@code String} of arguments in the context of the CreateStudyPlanCommand
     * and returns an CreateStudyPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateStudyPlanCommand parse(String args) throws ParseException {
        /*
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CreateStudyPlanCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<UserTag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        StudyPlan studyPlan = new StudyPlan(name, phone, email, address, tagList);

        return new CreateStudyPlanCommand(studyPlan);
         */
        // return new CreateStudyPlanCommand(new StudyPlan(new Title("this is just a temporary holder")));
        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
