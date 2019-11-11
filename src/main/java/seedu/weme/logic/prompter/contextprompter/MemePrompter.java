package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.MEME_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.MEME_COMMANDS_DESCRIPTION_MAP;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptCommandWord;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.commands.memecommand.MemeArchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeArchivesCommand;
import seedu.weme.logic.commands.memecommand.MemeClearCommand;
import seedu.weme.logic.commands.memecommand.MemeDeleteCommand;
import seedu.weme.logic.commands.memecommand.MemeDislikeCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.logic.commands.memecommand.MemeFindCommand;
import seedu.weme.logic.commands.memecommand.MemeLikeCommand;
import seedu.weme.logic.commands.memecommand.MemeListCommand;
import seedu.weme.logic.commands.memecommand.MemeStageCommand;
import seedu.weme.logic.commands.memecommand.MemeUnarchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeViewCommand;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeAddCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeArchiveCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeDeleteCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeDislikeCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeEditCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeFindCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeLikeCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeStageCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeUnarchiveCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeViewCommandPrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt command arguments in the meme context.
 */
public class MemePrompter extends WemePrompter {

    @Override
    public CommandPrompt promptCommand(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new CommandPrompt(MEME_COMMANDS
                    .stream()
                    .sorted()
                    .map(command -> MEME_COMMANDS_DESCRIPTION_MAP.get(command))
                    .reduce((x, y) -> x + '\n' + y)
                    .orElse(""),
                    MEME_COMMANDS.stream().sorted().findFirst().orElse("") + COMMAND_DELIMITER);
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
        if (GENERAL_COMMANDS.contains(commandWord)) {
            return super.promptCommand(model, userInput);
        }

        switch (commandWord) {
        case MemeAddCommand.COMMAND_WORD:
            return new MemeAddCommandPrompter().prompt(model, userInput);

        case MemeArchiveCommand.COMMAND_WORD:
            return new MemeArchiveCommandPrompter().prompt(model, userInput);

        case MemeArchivesCommand.COMMAND_WORD:
            return new CommandPrompt(MemeArchivesCommand.MESSAGE_USAGE, userInput);

        case MemeEditCommand.COMMAND_WORD:
            return new MemeEditCommandPrompter().prompt(model, userInput);

        case MemeDeleteCommand.COMMAND_WORD:
            return new MemeDeleteCommandPrompter().prompt(model, userInput);

        case MemeDislikeCommand.COMMAND_WORD:
            return new MemeDislikeCommandPrompter().prompt(model, userInput);

        case MemeFindCommand.COMMAND_WORD:
            return new MemeFindCommandPrompter().prompt(model, userInput);

        case MemeLikeCommand.COMMAND_WORD:
            return new MemeLikeCommandPrompter().prompt(model, userInput);

        case MemeStageCommand.COMMAND_WORD:
            return new MemeStageCommandPrompter().prompt(model, userInput);

        case MemeClearCommand.COMMAND_WORD:
            return new CommandPrompt(MemeClearCommand.MESSAGE_USAGE, userInput);

        case MemeUnarchiveCommand.COMMAND_WORD:
            return new MemeUnarchiveCommandPrompter().prompt(model, userInput);

        case MemeListCommand.COMMAND_WORD:
            return new CommandPrompt(MemeListCommand.MESSAGE_USAGE, userInput);

        case MemeViewCommand.COMMAND_WORD:
            return new MemeViewCommandPrompter().prompt(model, userInput);

        default:
            if (arguments.isBlank()) {
                return promptCommandWord(MEME_COMMANDS, commandWord, MEME_COMMANDS_DESCRIPTION_MAP);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
