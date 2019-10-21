package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;


/**
 * Removes the user's liked recommendations from the $aveNUS recommendation system.
 */
public class RemoveLikeCommand extends RemovePreferenceCommand {

    public static final String COMMAND_WORD = "removelike";
    private boolean isRemoveAll;

    public RemoveLikeCommand(boolean isRemoveAll) {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>(), isRemoveAll);
    }

    /**
     * Creates an RemoveLikeCommand to remove the user's recommendations
     */
    public RemoveLikeCommand(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList,
                             boolean isRemoveAll) {
        super(categoryList, tagList, locationList, isRemoveAll);
        this.isRemoveAll = isRemoveAll;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return this.execute(model, true, isRemoveAll);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveLikeCommand // instanceof handles nulls
                && categoryList.equals(((RemoveLikeCommand) other).categoryList))
                && tagList.equals(((RemoveLikeCommand) other).tagList)
                && locationList.equals(((RemoveLikeCommand) other).locationList)
                && isRemoveAll == ((RemoveLikeCommand) other).isRemoveAll;
    }
}
