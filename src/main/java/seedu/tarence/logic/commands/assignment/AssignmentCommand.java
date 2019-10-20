package seedu.tarence.logic.commands.assignment;

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
 * Modifies an assignment in a specified tutorial.
 * Keyword matching is case insensitive.
 */
public abstract class AssignmentCommand extends Command {

    protected final Optional<ModCode> targetModCode;
    protected final Optional<TutName> targetTutName;
    protected final Optional<Index> targetTutIndex;
    protected final Optional<Index> targetAssignIndex;
    protected final Optional<String> assignName;
    protected final Optional<Integer> maxScore;
    protected final Optional<Date> startDate;
    protected final Optional<Date> endDate;

    public AssignmentCommand(ModCode modCode, TutName tutName, Index tutIndex, Index assignIndex,
            String assignName, Integer maxScore, Date startDate, Date endDate) {
        this.targetModCode = Optional.ofNullable(modCode);
        this.targetTutName = Optional.ofNullable(tutName);
        this.targetTutIndex = Optional.ofNullable(tutIndex);
        this.targetAssignIndex = Optional.ofNullable(assignIndex);
        this.assignName = Optional.ofNullable(assignName);
        this.maxScore = Optional.ofNullable(maxScore);
        this.startDate = Optional.ofNullable(startDate);
        this.endDate = Optional.ofNullable(endDate);
    }

    public AssignmentCommand() {
        this.targetModCode = Optional.empty();
        this.targetTutName = Optional.empty();
        this.targetTutIndex = Optional.empty();
        this.targetAssignIndex = Optional.empty();
        this.assignName = Optional.empty();
        this.maxScore = Optional.empty();
        this.startDate = Optional.empty();
        this.endDate = Optional.empty();
    }

    public abstract AssignmentCommand build(ModCode modCode, TutName tutName, Index tutIndex, Index assignIndex,
        String assignName, Integer maxScore, Date startDate, Date endDate);

    /**
     * Handles the creating and processing of suggested {@code AssignmentCommand}s, if the user's input does not
     * match any combination of modules and tutorials.
     *
     * @param model The model to search in.
     * @param assignmentCommand The command used to build suggested commands.
     * @return a string representation of the suggested alternative commands to the user's invalid input.
     * @throws CommandException if no suggested commands can be found.
     */
    protected CommandResult handleSuggestedCommands(Model model,
            AssignmentCommand assignmentCommand) throws CommandException {
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
                similarTutNames, model, assignmentCommand);
        model.storePendingCommand(new SelectSuggestionCommand());
        return new CommandResult(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                modCode.toString() + " " + tutName.toString()) + suggestedCorrections);
    }

    /**
     * Generates and stores {@code AssignmentCommand}s from a list of {@code ModCode}s and {@code TutName}s.
     *
     * @param similarModCodes List of {@code ModCode}s similar to the user's input.
     * @param similarTutNames List of {@code TutName}s similar to the user's input.
     * @param model The {@code Model} in which to store the generated commands.
     * @param assignmentCommand The command used to build suggested comands.
     * @return string representing the generated suggestions and their corresponding indexes for user selection.
     */
    protected String createSuggestedCommands(List<ModCode> similarModCodes, List<TutName> similarTutNames,
                                           Model model, AssignmentCommand assignmentCommand) {
        ModCode modCode = targetModCode.get();
        TutName tutName = targetTutName.get();
        Index tutIndex = null;
        Index assignIndex = targetAssignIndex.orElse(null);
        String assignName = this.assignName.orElse(null);
        Integer maxScore = this.maxScore.orElse(null);
        Date startDate = this.startDate.orElse(null);
        Date endDate = this.endDate.orElse(null);
        List<Command> suggestedCommands = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int index = 1;
        for (ModCode similarModCode : similarModCodes) {
            suggestedCommands.add(assignmentCommand.build(
                    similarModCode,
                    tutName,
                    tutIndex,
                    assignIndex,
                    assignName,
                    maxScore,
                    startDate,
                    endDate));
            s.append(index).append(". ").append(similarModCode).append(", ").append(tutName).append("\n");
            index++;
        }
        for (TutName similarTutName: similarTutNames) {
            AssignmentCommand newCommand = assignmentCommand.build(
                    modCode,
                    similarTutName,
                    tutIndex,
                    assignIndex,
                    assignName,
                    maxScore,
                    startDate,
                    endDate);
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
    public boolean needsInput() {
        return false;
    }

    @Override
    public boolean needsCommand(Command command) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentCommand // instanceof handles nulls
                && targetModCode.equals(((AssignmentCommand) other).targetModCode)
                && targetTutName.equals(((AssignmentCommand) other).targetTutName)
                && targetTutIndex.equals(((AssignmentCommand) other).targetTutIndex)
                && assignName.equals(((AssignmentCommand) other).assignName)
                && maxScore.equals(((AssignmentCommand) other).maxScore)
                && startDate.equals(((AssignmentCommand) other).startDate)
                && endDate.equals(((AssignmentCommand) other).endDate)); // state check
    }
}
