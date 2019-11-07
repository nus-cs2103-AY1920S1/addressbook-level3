package seedu.pluswork.logic.commands;

import seedu.pluswork.model.Model;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.statistics.Statistics;
import seedu.pluswork.model.task.Task;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.model.Model.*;

public class GetStatisticsCommand extends Command {
    public static final String COMMAND_WORD_MEMBER = "member-stats";
    public static final String COMMAND_WORD_TASK = "task-stats";
    public static final String PREFIX_USAGE = "";

    public static final String MESSAGE_SUCCESS = "Calculated statistics";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
        model.updateFilteredMembersList(PREDICATE_SHOW_ALL_MEMBERS);
        model.updateFilteredMappingsList(PREDICATE_SHOW_ALL_MAPPINGS);
        List<Task> tasks = model.getFilteredTasksList();
        List<Member> members = model.getFilteredMembersList();
        List<TasMemMapping> tasMemMappings = model.getFilteredTasMemMappingsList();
        List<InvMemMapping> invMemMappings = model.getFilteredInvMemMappingsList();

        Statistics stats = new Statistics(members, tasks, tasMemMappings, invMemMappings);
        model.setStatistics(stats);
        stats.doCalculations();

        /*if(!stats.equals(model.getStatistics())) {
            model.setStatistics(stats);
            stats.doCalculations();
        }*/

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
