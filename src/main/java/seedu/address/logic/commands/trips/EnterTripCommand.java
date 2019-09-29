package seedu.address.logic.commands.trips;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pagestatus.PageType;
import seedu.address.ui.Ui;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

public class EnterTripCommand {
    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the main landing page of the trip. "
            + "Parameters: "
            + PREFIX_INDEX + "Index of trip";

    public static final String MESSAGE_SUCCESS = "Entered your trip!";

    private final Index indexToEnter;

    public EnterTripCommand(Index indexToEnter) {
        this.indexToEnter = indexToEnter;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireNonNull(model);

        List<Trip> lastShownList = model.getFilteredPersonList();

        if (indexToDelete.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.setPageStatus(new PageStatus(yada yada));

        ui.switchWindow(DayViewPage.class);



        Person personToDelete = lastShownList.get(indexToDelete.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));

        model.getPageStatus().setPageType(PageType.ADD_TRIP);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DeleteTripCommand;
    }
}
