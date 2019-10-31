package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.util.CommandUtil.findIndexOfAccommodation;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.logic.commands.util.HelpExplanation;
import seedu.address.model.Model;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.contact.Contact;

/**
 * Adds an accommodation to the itinerary.
 */
public class AddAccommodationCommand extends AddCommand {
    public static final String SECOND_COMMAND_WORD = "accommodation";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Adds an Accommodation to the itinerary.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " " + PREFIX_NAME + "NAME "
                    + PREFIX_ADDRESS + "ADDRESS "
                    + PREFIX_PHONE + "NUMBER "
                    + "[" + PREFIX_TAG + "TAG]...",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
                    + PREFIX_NAME + "Hotel 81 "
                    + PREFIX_ADDRESS + "Geylang "
                    + PREFIX_TAG + "cheap"
    );

    public static final String MESSAGE_SUCCESS = "New accommodation added: %1s";
    public static final String MESSAGE_DUPLICATE_ACCOMMODATION = "This accommodation already exists in the itinerary.";

    private final Index index;
    private final Accommodation toAdd;

    /**
     * Creates an AddAccommodationCommand to add the specified {@Accommodation}
     */
    public AddAccommodationCommand(Accommodation accommodation) {
        requireNonNull(accommodation);
        toAdd = accommodation;
        index = null;
    }

    public AddAccommodationCommand(Index index, Accommodation accommodation) {
        requireAllNonNull(index, accommodation);
        toAdd = accommodation;
        this.index = index;
    }

    public Accommodation getToAdd() {
        return toAdd;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAccommodation(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOMMODATION);
        }

        if (toAdd.getContact().isPresent()) {
            if (model.hasPhone(toAdd.getContact().get().getPhone())) {
                Contact contact = model.getContactByPhone(toAdd.getContact().get().getPhone()).get();
                Accommodation linkedAccommodation = new Accommodation(toAdd.getName(), toAdd.getAddress(), contact,
                        toAdd.getTags());
                model.addAccommodation(linkedAccommodation);
                return new CommandResult(
                    String.format(MESSAGE_SUCCESS, linkedAccommodation),
                    new ResultInformation[] {
                        new ResultInformation(
                                linkedAccommodation,
                                findIndexOfAccommodation(model, linkedAccommodation),
                                String.format(MESSAGE_SUCCESS, "")
                        )
                    },
                    new UiFocus[]{UiFocus.ACCOMMODATION, UiFocus.INFO}
                );
            } else {
                if (index == null) {
                    model.addAccommodation(toAdd);
                } else {
                    model.addAccommodationAtIndex(index, toAdd);
                }
            }
        } else {
            if (index == null) {
                model.addAccommodation(toAdd);
            } else {
                model.addAccommodationAtIndex(index, toAdd);
            }
        }
        return new CommandResult(
            String.format(MESSAGE_SUCCESS, toAdd),
            new ResultInformation[]{
                new ResultInformation(
                        toAdd,
                        findIndexOfAccommodation(model, toAdd),
                        String.format(MESSAGE_SUCCESS, "")
                )
            },
            new UiFocus[]{UiFocus.ACCOMMODATION, UiFocus.INFO}
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAccommodationCommand
                && toAdd.equals(((AddAccommodationCommand) other).toAdd));
    }
}
