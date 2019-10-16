package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class SelectCommand extends Command {

	public static final String COMMAND_WORD = "select";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a person by their name and display"
			+ "their personal information.\n"
			+ "Parameters : NAME\n"
			+ "Example: " + COMMAND_WORD + "Erwin Chan Guo Xin";

	public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Person selected!";

	private final Index targetIndex;

	private Person selectedPerson;

	public SelectCommand(Index targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute(Model model) throws CommandException {
		requireNonNull(model);
		List<Person> lastShownList = model.getFilteredPersonList();

		if(targetIndex.getZeroBased() >= lastShownList.size()) {
			throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
		}

		selectedPerson = lastShownList.get(targetIndex.getZeroBased());
		model.storePerson(selectedPerson);
		return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, selectedPerson));
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof SelectCommand // instanceof handles nulls
				&& targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
	}
}
