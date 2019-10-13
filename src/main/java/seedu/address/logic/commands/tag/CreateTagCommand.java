package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Creates a new tag.
 */
public class CreateTagCommand extends Command {

    public static final String COMMAND_WORD = "newtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Creates a new tag type. "
        + "Parameters: "
        + "TAG_NAME \n"
        + "Example: "
        + "newtag exchange";

    public static final String MESSAGE_SUCCESS = "New tag created: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists";
    public static final String MESSAGE_INVALID_TAG_NAME = "This name is reserved for default tags";

    private final String tagName;

    /**
     * Creates an {@code CreateTagCommand} to create a tag with the given name.
     * @param tagName
     */
    public CreateTagCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // TODO: implement this
        /*
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
         */
        return new CommandResult("this is just a temporary holder");
    }

}
