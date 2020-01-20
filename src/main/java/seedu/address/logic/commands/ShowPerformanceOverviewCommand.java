package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.performanceoverview.PerformanceOverview;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;

public class ShowPerformanceOverviewCommand extends Command {

    public static final String COMMAND_WORD = "showPerformanceOverview";

    public static final String MESSAGE_SUCCESS = "Showing performance overview of: %1$s";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        Project currWorkingProject = model.getWorkingProject().get();
        List<Person> memberList = model.getMembersOf(currWorkingProject);

        if (memberList.isEmpty()) {
            throw new CommandException("Unable to generate performance overview with no members");
        }

        PerformanceOverview overview = new PerformanceOverview(currWorkingProject, memberList);
        model.setPerformanceOverview(overview);

        return new CommandResult(String.format(MESSAGE_SUCCESS, currWorkingProject.getTitle().title), COMMAND_WORD);
    }
}
