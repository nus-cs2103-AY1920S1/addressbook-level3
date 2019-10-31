package tagline.logic.parser.contact;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
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
import tagline.logic.parser.ParserPromptHandlerUtil;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;

/**
 * Parses user input.
 */
public class ContactCommandParser {
    public static final String CONTACT_CLEAR_COMMAND_CONFIRM_STRING = "Are you sure you want to clear your contact"
            + " list? Enter 'Y' to continue.";
    public static final String CONTACT_CLEAR_COMMAND_ABORTED_STRING = "The current command has been aborted.";
    public static final String CONTACT_CLEAR_CONFIRM_CHARACTER = "Y";

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into a contact command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.stripLeading());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case CreateContactCommand.COMMAND_WORD:
            return new AddContactParser().parse(arguments);

        case ShowContactCommand.COMMAND_WORD:
            return new ShowContactParser().parse(arguments);

        case EditContactCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteContactCommand.COMMAND_WORD:
            return new DeleteContactParser().parse(arguments);

        case ClearContactCommand.COMMAND_WORD:
            throw new PromptRequestException(Arrays.asList(
                    new Prompt("", CONTACT_CLEAR_COMMAND_CONFIRM_STRING)));

        case FindContactCommand.COMMAND_WORD:
            return new FindContactParser().parse(arguments);

        case ListContactCommand.COMMAND_WORD:
            return new ListContactCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    //@author tanlk99
    /**
     * Parses user input into a contact command for execution, using a list of filled prompts.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, List<Prompt> promptList) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        final String filledArguments = ParserPromptHandlerUtil.getArgsWithFilledPrompts(arguments, promptList);

        switch (commandWord) {

        case CreateContactCommand.COMMAND_WORD:
            return new AddContactParser().parse(filledArguments);

        case EditContactCommand.COMMAND_WORD:
            return new EditCommandParser().parse(filledArguments);

        case DeleteContactCommand.COMMAND_WORD:
            return new DeleteContactParser().parse(filledArguments);

        case FindContactCommand.COMMAND_WORD:
            return new FindContactParser().parse(filledArguments);

        case ListContactCommand.COMMAND_WORD:
            return new ListContactCommand();

        case ClearContactCommand.COMMAND_WORD:
            if (ParserPromptHandlerUtil.getPromptResponseFromPrefix("", promptList)
                    .equals(CONTACT_CLEAR_CONFIRM_CHARACTER)) {
                return new ClearContactCommand();
            } else {
                throw new ParseException(CONTACT_CLEAR_COMMAND_ABORTED_STRING);
            }

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
