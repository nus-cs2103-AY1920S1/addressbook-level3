package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;


import static java.util.Objects.requireNonNull;

public class AddProjectCommand extends Command {
    public static final String COMMAND_WORD = "add_project";

    public static final String MESSAGE_SUCCESS = "New project added: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to the project list. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "PHONE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2103 TP "
            + PREFIX_DESCRIPTION + "To create an app to manage projects";

    public static final String MESSAGE_DUPLICATE_PROJECT = "Project list contains duplicate project(s).";

    private final Project toAdd;

    public AddProjectCommand(Project project) {
        requireNonNull(project);
        toAdd = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasProject(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.addProject(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
