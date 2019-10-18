package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_MERGE_COMMAND;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.AddPolicyTagCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePolicyCommand;
import seedu.address.logic.commands.DeletePolicyTagCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExpandPersonCommand;
import seedu.address.logic.commands.ExpandPolicyCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPolicyCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListPeopleCommand;
import seedu.address.logic.commands.ListPolicyCommand;
import seedu.address.logic.commands.UnassignPolicyCommand;
import seedu.address.logic.commands.merge.DoNotMergePersonCommand;
import seedu.address.logic.commands.merge.DoNotMergePolicyCommand;
import seedu.address.logic.commands.merge.MergeCommand;
import seedu.address.logic.commands.merge.MergeConfirmedCommand;
import seedu.address.logic.commands.merge.MergePersonCommand;
import seedu.address.logic.commands.merge.MergePersonConfirmedCommand;
import seedu.address.logic.commands.merge.MergePersonRejectedCommand;
import seedu.address.logic.commands.merge.MergePolicyCommand;
import seedu.address.logic.commands.merge.MergePolicyConfirmedCommand;
import seedu.address.logic.commands.merge.MergePolicyRejectedCommand;
import seedu.address.logic.commands.merge.MergeRejectedCommand;
import seedu.address.logic.commands.merge.MergeStopCommand;
import seedu.address.logic.parser.exceptions.MergeParseException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    public static final String MERGE_POLICY = "policy";
    public static final String MERGE_PERSON = "person";
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private boolean isMerging = false;
    private MergeCommand currentMergeCommand;
    private String mergeType;

    /**
     * Parses user input and obtains the command word which determines the command being executed.
     *
     * @param userInput full user input string
     * @return the command work based on the user input
     * @throws ParseException if the user input does not conform to the expected format
     */
    public Optional<String> getCommandWord(String userInput) throws ParseException {
        if (isMerging) {
            return Optional.empty();
        }

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        return Optional.of(commandWord);
    }

    /**
     * Parses user input into command for execution. Calls the parseCommand(String, boolean) where the boolean's
     * default is false.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        return parseCommand(userInput, false);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param isSystemInput whether the command was invoked by the user or the system
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, boolean isSystemInput) throws ParseException {
        if (isMerging) {
            return parseMerge(userInput);
        } else {
            final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }

            final String commandWord = matcher.group("commandWord");
            final String arguments = matcher.group("arguments");
            switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case AddPolicyCommand.COMMAND_WORD:
                return new AddPolicyCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case EditPolicyCommand.COMMAND_WORD:
                return new EditPolicyCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case DeletePolicyCommand.COMMAND_WORD:
                return new DeletePolicyCommandParser().parse(arguments);

            case AssignPolicyCommand.COMMAND_WORD:
                return new AssignPolicyCommandParser().parse(arguments);

            case UnassignPolicyCommand.COMMAND_WORD:
                return new UnassignPolicyCommandParser().parse(arguments);

            case AddTagCommand.COMMAND_WORD:
                return new AddTagCommandParser().parse(arguments);

            case AddPolicyTagCommand.COMMAND_WORD:
                return new AddPolicyTagCommandParser().parse(arguments);

            case DeleteTagCommand.COMMAND_WORD:
                return new DeleteTagCommandParser().parse(arguments);

            case DeletePolicyTagCommand.COMMAND_WORD:
                return new DeletePolicyTagCommandParser().parse(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case FindPolicyCommand.COMMAND_WORD:
                return new FindPolicyCommandParser().parse(arguments);

            case ListPeopleCommand.COMMAND_WORD:
                return new ListPeopleCommand();

            case ListPolicyCommand.COMMAND_WORD:
                return new ListPolicyCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case MergePersonCommand.COMMAND_WORD:
                if (isSystemInput) {
                    isMerging = true;
                    MergePersonCommand command = new MergePersonCommandParser().parse(arguments);
                    currentMergeCommand = command;
                    mergeType = MERGE_PERSON;
                    return command;
                } else {
                    throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
                }

            case DoNotMergePersonCommand.COMMAND_WORD:
                return new DoNotMergePersonCommandParser().parse(arguments);

            case MergePolicyCommand.COMMAND_WORD:
                if (isSystemInput) {
                    isMerging = true;
                    MergePolicyCommand command = new MergePolicyCommandParser().parse(arguments);
                    currentMergeCommand = command;
                    mergeType = MERGE_POLICY;
                    return command;
                } else {
                    throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
                }

            case DoNotMergePolicyCommand.COMMAND_WORD:
                return new DoNotMergePolicyCommandParser().parse(arguments);

            case ExpandPersonCommand.COMMAND_WORD:
                return new ExpandPersonCommandParser().parse(arguments);

            case ExpandPolicyCommand.COMMAND_WORD:
                return new ExpandPolicyCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }

    /**
     * Parses the merge commands into commands for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseMerge(String userInput) throws ParseException {
        String commandWord = userInput.trim();
        switch (commandWord) {

        case (MergeConfirmedCommand.COMMAND_WORD):
        case (MergeConfirmedCommand.DEFAULT_COMMAND_WORD):
            if (mergeType.equals(MERGE_PERSON)) {
                MergePersonConfirmedCommand confirmCommand = new MergePersonConfirmedCommand(
                        (MergePersonCommand) currentMergeCommand);
                if (confirmCommand.isLastMerge()) {
                    isMerging = false;
                }
                return confirmCommand;
            } else {
                MergePolicyConfirmedCommand confirmCommand = new MergePolicyConfirmedCommand(
                        (MergePolicyCommand) currentMergeCommand);
                if (confirmCommand.isLastMerge()) {
                    isMerging = false;
                }
                return confirmCommand;
            }

        case MergeRejectedCommand.COMMAND_WORD:
            if (mergeType.equals(MERGE_PERSON)) {
                MergePersonRejectedCommand rejectCommand = new MergePersonRejectedCommand(
                        (MergePersonCommand) currentMergeCommand);
                if (rejectCommand.isLastMerge()) {
                    isMerging = false;
                }
                return rejectCommand;
            } else {
                MergePolicyRejectedCommand rejectCommand = new MergePolicyRejectedCommand(
                        (MergePolicyCommand) currentMergeCommand);
                if (rejectCommand.isLastMerge()) {
                    isMerging = false;
                }
                return rejectCommand;
            }

        case MergeStopCommand.COMMAND_WORD:
            isMerging = false;
            return new MergeStopCommand(currentMergeCommand, mergeType);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            if (mergeType.equals(MERGE_PERSON)) {
                throw new MergeParseException(String.format(MESSAGE_UNKNOWN_MERGE_COMMAND, (
                        (MergePersonCommand) currentMergeCommand).getNextMergePrompt()));
            } else {
                throw new MergeParseException(String.format(MESSAGE_UNKNOWN_MERGE_COMMAND, (
                        (MergePolicyCommand) currentMergeCommand).getNextMergePrompt()));
            }
        }
    }

}
