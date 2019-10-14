package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class SelectCommand extends Command {

	public static final String COMMAND_WORD = "select";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a person by their name and display"
			+ "their personal information.\n"
			+ "Parameters : NAME\n"
			+ "Example: " + COMMAND_WORD + "Erwin Chan Guo Xin";

	private final NameContainsKeywordsPredicate predicate;

	public SelectCommand(NameContainsKeywordsPredicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public CommandResult execute(Model model) {
		requireNonNull(model);
		return new CommandResult(String.format(Messages.MESSAGE_PERSON_SELECTED, 1));
	}
}
