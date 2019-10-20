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
public class RemoveDislikeCommand extends RemovePreferenceCommand {

    public static final String COMMAND_WORD = "removedislike";
    private boolean isRemoveAll;

    public RemoveDislikeCommand(boolean isRemoveAll) {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>(), isRemoveAll);
    }

    /**
     * Creates an RemoveLikeCommand to remove the user's recommendations
     */
    public RemoveDislikeCommand(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList,
                                boolean isRemoveAll) {
        super(categoryList, tagList, locationList, isRemoveAll);
        this.isRemoveAll = isRemoveAll;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return this.execute(model, false, isRemoveAll);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveDislikeCommand // instanceof handles nulls
                && categoryList.equals(((RemoveDislikeCommand) other).categoryList))
                && tagList.equals(((RemoveDislikeCommand) other).tagList)
                && locationList.equals(((RemoveDislikeCommand) other).locationList)
                && isRemoveAll == ((RemoveDislikeCommand) other).isRemoveAll;
    }
}
