//@@author stevenwjy
package tagline.logic.parser.contact;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tagline.logic.commands.Command;
import tagline.logic.commands.HelpCommand;
import tagline.logic.commands.contact.ClearContactCommand;
import tagline.logic.commands.contact.CreateContactCommand;
import tagline.logic.commands.contact.DeleteContactCommand;
import tagline.logic.commands.contact.EditContactCommand;
import tagline.logic.commands.contact.FindContactCommand;
import tagline.logic.commands.contact.ListContactCommand;
import tagline.logic.commands.contact.ShowContactCommand;
import tagline.logic.parser.ParserUtil;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;

/**
 * Parses user input for contact commands.
 */
public class ContactCommandParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    //@@author tanlk99
    /**
     * Parses user input into a contact command for execution, using a list of filled prompts.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, List<Prompt> promptList) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.stripLeading());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        final String filledArguments = ParserUtil.getArgsWithFilledPrompts(arguments, promptList);

        switch (commandWord) {

        case CreateContactCommand.COMMAND_WORD:
            return new CreateContactParser().parse(filledArguments);

        case EditContactCommand.COMMAND_WORD:
            return new EditContactParser().parse(filledArguments);

        case DeleteContactCommand.COMMAND_WORD:
            return new DeleteContactParser().parse(filledArguments);

        case FindContactCommand.COMMAND_WORD:
            return new FindContactParser().parse(filledArguments);

        case ShowContactCommand.COMMAND_WORD:
            return new ShowContactParser().parse(filledArguments);

        case ListContactCommand.COMMAND_WORD:
            return new ListContactCommand();

        case ClearContactCommand.COMMAND_WORD:
            return new ClearContactParser().parse(arguments, promptList);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into a contact command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        return parseCommand(userInput, Collections.emptyList());
    }
}
