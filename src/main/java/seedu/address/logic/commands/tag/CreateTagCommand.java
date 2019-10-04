package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;

public class CreateTagCommand extends Command {

    public static final String COMMAND_WORD = "newtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Creates a new tag type. "
        + "Parameters: "
        + "TAG_NAME \n"
        + "Example: "
        +  "newtag exchange";

    public static final String MESSAGE_SUCCESS = "New tag created: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This tag already exists";

    private final String tagName;

    /**
     * Creates an {@code CreateTagCommand} to create a tag with the given name.
     * @param tagName
     */
    public CreateTagCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName;
    }

}
