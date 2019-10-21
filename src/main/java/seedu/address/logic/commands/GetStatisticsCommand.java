package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.mapping.Mapping;
import seedu.address.model.mapping.UniqueMappingList;
import seedu.address.model.member.Member;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.task.Task;

public class GetStatisticsCommand extends Command {
    public static final String COMMAND_WORD = "get-stats";

    public static final String MESSAGE_SUCCESS = "Calculated statistics";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
        model.updateFilteredMembersList(PREDICATE_SHOW_ALL_MEMBERS);
        List<Task> tasks = model.getFilteredTasksList();
        List<Member> members = model.getFilteredMembersList();
        List<Mapping> mappings = model.getFilteredMappingsList();
        Statistics stats = new Statistics(members, tasks, mappings);

        if(!stats.equals(model.getStatistics())) {
            model.setStatistics(stats);
            stats.doCalculations();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
