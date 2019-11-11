package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.StartCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author keiteo
/**
 * This class parses input arguments and creates a new StartCommand object.
 */
public class StartCommandParser implements Parser<StartCommand> {

    public static final String BAD_ARGUMENTS = "Please make sure your tags are alphanumeric!";

    private final KeyboardFlashCardsParser keyboardFlashCardsParser;

    StartCommandParser(KeyboardFlashCardsParser keyboardFlashCardsParser) {
        requireNonNull(keyboardFlashCardsParser);
        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the StartCommand
     * and returns a StartCommand object for execution.
     */
    public StartCommand parse(String args) throws ParseException {
        String alphaNumericWithSpacesRegex = "^[a-zA-Z0-9\\s+]+$";
        if (!args.matches(alphaNumericWithSpacesRegex) && !args.isEmpty()) {
            throw new ParseException(BAD_ARGUMENTS);
        }

        String trimmedArgs = args.trim();
        return new StartCommand(keyboardFlashCardsParser, trimmedArgs);
    }
}
