package seedu.tarence.logic.commands.event;

import static seedu.tarence.commons.core.Messages.MESSAGE_SUGGESTED_CORRECTIONS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.Command;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.SelectSuggestionCommand;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;

/**
 * Modifies events in a specified tutorial.
 * Keyword matching is case insensitive.
 */
public abstract class EventCommand extends Command {

    protected final Optional<ModCode> targetModCode;
    protected final Optional<TutName> targetTutName;
    protected final Optional<Index> targetTutIndex;
    protected final Optional<Index> targetEventIndex;
    protected final Optional<String> eventName;
    protected final Optional<Date> startTime;
    protected final Optional<Date> endTime;

    public EventCommand(ModCode modCode, TutName tutName, Index tutIndex, Index eventIndex,
            String eventName, Date startTime, Date endTime) {
        this.targetModCode = Optional.ofNullable(modCode);
        this.targetTutName = Optional.ofNullable(tutName);
        this.targetTutIndex = Optional.ofNullable(tutIndex);
        this.targetEventIndex = Optional.ofNullable(eventIndex);
        this.eventName = Optional.ofNullable(eventName);
        this.startTime = Optional.ofNullable(startTime);
        this.endTime = Optional.ofNullable(endTime);
    }

    public EventCommand() {
        this.targetModCode = Optional.empty();
        this.targetTutName = Optional.empty();
        this.targetTutIndex = Optional.empty();
        this.targetEventIndex = Optional.empty();
        this.eventName = Optional.empty();
        this.startTime = Optional.empty();
        this.endTime = Optional.empty();
    }

    public abstract EventCommand build(ModCode modCode, TutName tutName, Index tutIndex, Index eventIndex,
        String eventName, Date startTime, Date endTime);

    /**
     * Handles the creating and processing of suggested {@code EventCommand}s, if the user's input does not
     * match any combination of modules and tutorials.
     *
     * @param model The model to search in.
     * @param eventCommand The command used to build suggested commands.
     * @return a string representation of the suggested alternative commands to the user's invalid input.
     * @throws CommandException if no suggested commands can be found.
     */
    protected CommandResult handleSuggestedCommands(Model model,
            EventCommand eventCommand) throws CommandException {
        ModCode modCode = targetModCode.get();
        TutName tutName = targetTutName.get();
        // find tutorials with same name and similar modcodes, and similar names and same modcode
        List<ModCode> similarModCodes = getSimilarModCodesWithTutorial(modCode,
                tutName, model);
        List<TutName> similarTutNames = getSimilarTutNamesWithModule(modCode,
                tutName, model);
        if (similarModCodes.size() == 0 && similarTutNames.size() == 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
        }

        String suggestedCorrections = createSuggestedCommands(similarModCodes,
                similarTutNames, model, eventCommand);
        model.storePendingCommand(new SelectSuggestionCommand());
        return new CommandResult(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                modCode.toString() + " " + tutName.toString()) + suggestedCorrections);
    }

    /**
     * Generates and stores {@code EventCommand}s from a list of {@code ModCode}s and {@code TutName}s.
     *
     * @param similarModCodes List of {@code ModCode}s similar to the user's input.
     * @param similarTutNames List of {@code TutName}s similar to the user's input.
     * @param model The {@code Model} in which to store the generated commands.
     * @param eventCommand The command used to build suggested comands.
     * @return string representing the generated suggestions and their corresponding indexes for user selection.
     */
    protected String createSuggestedCommands(List<ModCode> similarModCodes, List<TutName> similarTutNames,
                                           Model model, EventCommand eventCommand) {
        ModCode modCode = targetModCode.get();
        TutName tutName = targetTutName.get();
        Index tutIndex = null;
        Index eventIndex = targetEventIndex.orElse(null);
        String eventName = this.eventName.orElse(null);
        Date startTime = this.startTime.orElse(null);
        Date endTime = this.endTime.orElse(null);
        List<Command> suggestedCommands = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int index = 1;
        for (ModCode similarModCode : similarModCodes) {
            suggestedCommands.add(eventCommand.build(
                    similarModCode,
                    tutName,
                    tutIndex,
                    eventIndex,
                    eventName,
                    startTime,
                    endTime));
            s.append(index).append(". ").append(similarModCode).append(", ").append(tutName).append("\n");
            index++;
        }
        for (TutName similarTutName: similarTutNames) {
            EventCommand newCommand = eventCommand.build(
                    modCode,
                    similarTutName,
                    tutIndex,
                    eventIndex,
                    eventName,
                    startTime,
                    endTime);
            if (suggestedCommands.stream()
                    .anyMatch(existingCommand -> existingCommand.equals(newCommand))) {
                continue;
            }
            suggestedCommands.add(newCommand);
            s.append(index).append(". ").append(modCode).append(", ").append(similarTutName).append("\n");
            index++;
        }
        String suggestedCorrections = s.toString();
        model.storeSuggestedCommands(suggestedCommands, suggestedCorrections);
        return suggestedCorrections;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventCommand // instanceof handles nulls
                && targetModCode.equals(((EventCommand) other).targetModCode)
                && targetTutName.equals(((EventCommand) other).targetTutName)
                && targetTutIndex.equals(((EventCommand) other).targetTutIndex)
                && targetEventIndex.equals(((EventCommand) other).targetEventIndex)
                && eventName.equals(((EventCommand) other).eventName)
                && startTime.equals(((EventCommand) other).startTime)
                && endTime.equals(((EventCommand) other).endTime)); // state check
    }
}
