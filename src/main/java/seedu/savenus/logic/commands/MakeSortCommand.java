package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.savenus.model.Model;

/**
 * Creates a simple command to create a custom comparator for the user.
 */
public class MakeSortCommand extends Command {

    public static final String COMMAND_WORD = "makesort";
    public static final String EXAMPLE_USAGE = "Example Usage: " + COMMAND_WORD + " PRICE ASC NAME DESC";
    public static final String MESSAGE_SUCCESS = "You have successfully overridden your own custom comparator! \n"
            + "Current CustomSorter: %s";

    private List<String> fields;

    /**
     * Constructus a new command based on the given list of fields.
     * @param fields the given list of fields.
     */
    public MakeSortCommand(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCustomSorter(fields);
        String newCustomSorterAsString = model.getCustomSorter().toString();
        return new CommandResult(String.format(MESSAGE_SUCCESS, newCustomSorterAsString));
    }

    public List<String> getFields() {
        return this.fields;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof MakeSortCommand) {
            return this.getFields().equals(((MakeSortCommand) obj).getFields());
        } else {
            return false;
        }
    }
}
