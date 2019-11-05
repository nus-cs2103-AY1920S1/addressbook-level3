package dukecooks.logic.commands.profile;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.model.Model;
import dukecooks.model.profile.UserProfile;

/**
 * Deletes User Profile.
 */
public class DeleteProfileCommand extends DeleteCommand {

    public static final String VARIANT_WORD = "profile";
    public static final String MESSAGE_SUCCESS = "Profile has been deleted!";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setUserProfile(new UserProfile());

        event = Event.getInstance();
        event.set("dashboard", "all");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
