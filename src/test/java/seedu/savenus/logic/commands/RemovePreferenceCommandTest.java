package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;
import static seedu.savenus.testutil.TypicalRecs.ADDED_ANOTHER_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.ADDED_ANOTHER_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.ADDED_ANOTHER_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.getTypicalRecs;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.PurchaseHistory;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.sorter.CustomSorter;
import seedu.savenus.model.tag.Tag;


/**
 * Contains tests for both RemoveLikeCommand and RemoveDislikeCommand
 */
public class RemovePreferenceCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new CustomSorter(), new SavingsAccount());
        model.getRecommendationSystem().setUserRecommendations(getTypicalRecs());
    }

    @Test
    public void removeLikeConstructor_nullSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new RemoveLikeCommand(null, null, null, false));
        assertThrows(NullPointerException.class, () ->
                new RemoveLikeCommand(null, new HashSet<>(), new HashSet<>(), false));
        assertThrows(NullPointerException.class, () ->
                new RemoveLikeCommand(new HashSet<>(), null, new HashSet<>(), false));
        assertThrows(NullPointerException.class, () ->
                new RemoveLikeCommand(new HashSet<>(), new HashSet<>(), null, false));
    }

    @Test
    public void removeDislikeConstructor_nullSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new RemoveDislikeCommand(null, null, null, false));
        assertThrows(NullPointerException.class, () ->
                new RemoveDislikeCommand(null, new HashSet<>(), new HashSet<>(), false));
        assertThrows(NullPointerException.class, () ->
                new RemoveDislikeCommand(new HashSet<>(), null, new HashSet<>(), false));
        assertThrows(NullPointerException.class, () ->
                new RemoveDislikeCommand(new HashSet<>(), new HashSet<>(), null, false));
    }

    @Test
    public void removeLikeCommand_equals() {
        RemoveLikeCommand first =
                new RemoveLikeCommand(LIKED_CATEGORY_SET, LIKED_TAG_SET, LIKED_LOCATION_SET, false);
        RemoveLikeCommand second =
                new RemoveLikeCommand(LIKED_CATEGORY_SET, LIKED_TAG_SET, LIKED_LOCATION_SET, false);
        RemoveLikeCommand third =
                new RemoveLikeCommand(new HashSet<>(), LIKED_TAG_SET, LIKED_LOCATION_SET, false);
        RemoveLikeCommand fourth =
                new RemoveLikeCommand(LIKED_CATEGORY_SET, new HashSet<>(), LIKED_LOCATION_SET, false);
        RemoveLikeCommand fifth =
                new RemoveLikeCommand(LIKED_CATEGORY_SET, LIKED_TAG_SET, new HashSet<>(), false);

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertFalse(first.equals(third));
        assertFalse(first.equals(fourth));
        assertFalse(first.equals(fifth));
    }

    @Test
    public void removeDislikeCommand_equals() {
        RemoveDislikeCommand first =
                new RemoveDislikeCommand(DISLIKED_CATEGORY_SET, DISLIKED_TAG_SET, DISLIKED_LOCATION_SET, false);
        RemoveDislikeCommand second =
                new RemoveDislikeCommand(DISLIKED_CATEGORY_SET, DISLIKED_TAG_SET, DISLIKED_LOCATION_SET, false);
        RemoveDislikeCommand third =
                new RemoveDislikeCommand(new HashSet<>(), DISLIKED_TAG_SET, DISLIKED_LOCATION_SET, false);
        RemoveDislikeCommand fourth =
                new RemoveDislikeCommand(DISLIKED_CATEGORY_SET, new HashSet<>(), DISLIKED_LOCATION_SET, false);
        RemoveDislikeCommand fifth =
                new RemoveDislikeCommand(DISLIKED_CATEGORY_SET, DISLIKED_TAG_SET, new HashSet<>(), false);

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertFalse(first.equals(third));
        assertFalse(first.equals(fourth));
        assertFalse(first.equals(fifth));
    }

    @Test
    public void removeLikeCommand_executeSuccess() throws CommandException {
        model.addLikes(ADDED_ANOTHER_CATEGORY_SET, ADDED_ANOTHER_TAG_SET, ADDED_ANOTHER_LOCATION_SET);
        CommandResult result =
                new RemoveLikeCommand(ADDED_ANOTHER_CATEGORY_SET, ADDED_ANOTHER_TAG_SET,
                        ADDED_ANOTHER_LOCATION_SET, false)
                        .execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveLikeCommand.MESSAGE_SUCCESS));
    }

    @Test
    public void removeDislikeCommand_executeSuccess() throws CommandException {
        model.addDislikes(ADDED_ANOTHER_CATEGORY_SET, ADDED_ANOTHER_TAG_SET, ADDED_ANOTHER_LOCATION_SET);
        CommandResult result =
                new RemoveDislikeCommand(ADDED_ANOTHER_CATEGORY_SET, ADDED_ANOTHER_TAG_SET,
                        ADDED_ANOTHER_LOCATION_SET, false)
                        .execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveDislikeCommand.MESSAGE_SUCCESS));
    }


    @Test
    public void removeLikeCommand_removeCategory_contains() throws CommandException {
        model.addLikes(ADDED_ANOTHER_CATEGORY_SET, new HashSet<>(), new HashSet<>());
        CommandResult result =
                new RemoveLikeCommand(ADDED_ANOTHER_CATEGORY_SET, new HashSet<>(), new HashSet<>(), false)
                        .execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveLikeCommand.MESSAGE_SUCCESS));
        for (Category c : ADDED_ANOTHER_CATEGORY_SET) {
            assertTrue(result.getFeedbackToUser().contains(c.category));
        }
    }

    @Test
    public void removeDislikeCommand_removeCategory_contains() throws CommandException {
        model.addDislikes(ADDED_ANOTHER_CATEGORY_SET, new HashSet<>(), new HashSet<>());
        CommandResult result =
                new RemoveDislikeCommand(ADDED_ANOTHER_CATEGORY_SET, new HashSet<>(), new HashSet<>(), false)
                        .execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveDislikeCommand.MESSAGE_SUCCESS));
        for (Category c : ADDED_ANOTHER_CATEGORY_SET) {
            assertTrue(result.getFeedbackToUser().contains(c.category));
        }
    }

    @Test
    public void removeLikeCommand_removeTags_contains() throws CommandException {
        model.addLikes(new HashSet<>(), ADDED_ANOTHER_TAG_SET, new HashSet<>());
        CommandResult result =
                new RemoveLikeCommand(new HashSet<>(), ADDED_ANOTHER_TAG_SET, new HashSet<>(), false)
                        .execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveLikeCommand.MESSAGE_SUCCESS));
        for (Tag t : ADDED_ANOTHER_TAG_SET) {
            assertTrue(result.getFeedbackToUser().contains(t.tagName));
        }
    }

    @Test
    public void removeDislikeCommand_removeTags_contains() throws CommandException {
        model.addDislikes(new HashSet<>(), ADDED_ANOTHER_TAG_SET, new HashSet<>());

        CommandResult result =
                new RemoveDislikeCommand(new HashSet<>(), ADDED_ANOTHER_TAG_SET, new HashSet<>(), false)
                        .execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveDislikeCommand.MESSAGE_SUCCESS));
        for (Tag t : ADDED_ANOTHER_TAG_SET) {
            assertTrue(result.getFeedbackToUser().contains(t.tagName));
        }
    }

    @Test
    public void removeLikeCommand_removeLocation_contains() throws CommandException {
        model.addLikes(new HashSet<>(), new HashSet<>(), ADDED_ANOTHER_LOCATION_SET);
        CommandResult result =
                new RemoveLikeCommand(new HashSet<>(), new HashSet<>(), ADDED_ANOTHER_LOCATION_SET, false)
                        .execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveLikeCommand.MESSAGE_SUCCESS));
        for (Location l : ADDED_ANOTHER_LOCATION_SET) {
            assertTrue(result.getFeedbackToUser().contains(l.location));
        }
    }

    @Test
    public void removeDislikeCommand_removeLocation_contains() throws CommandException {
        model.addDislikes(new HashSet<>(), new HashSet<>(), ADDED_ANOTHER_LOCATION_SET);
        CommandResult result =
                new RemoveDislikeCommand(new HashSet<>(), new HashSet<>(), ADDED_ANOTHER_LOCATION_SET, false)
                        .execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveDislikeCommand.MESSAGE_SUCCESS));
        for (Location l : ADDED_ANOTHER_LOCATION_SET) {
            assertTrue(result.getFeedbackToUser().contains(l.location));
        }
    }

    @Test
    public void removeLikeCommand_removeAll_success() throws CommandException {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new CustomSorter(), new SavingsAccount());
        model.addLikes(ADDED_ANOTHER_CATEGORY_SET, ADDED_ANOTHER_TAG_SET, ADDED_ANOTHER_LOCATION_SET);
        CommandResult result = new RemoveLikeCommand(true).execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveLikeCommand.MESSAGE_SUCCESS));
        assertEquals(0, model.getRecommendationSystem().getUserRecommendations().getLikedCategories().size());
        assertEquals(0, model.getRecommendationSystem().getUserRecommendations().getLikedTags().size());
        assertEquals(0, model.getRecommendationSystem().getUserRecommendations().getLikedLocations().size());
    }

    @Test
    public void removeDislikeCommand_removeAll_success() throws CommandException {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new CustomSorter(), new SavingsAccount());
        model.addDislikes(ADDED_ANOTHER_CATEGORY_SET, ADDED_ANOTHER_TAG_SET, ADDED_ANOTHER_LOCATION_SET);
        CommandResult result = new RemoveDislikeCommand(true).execute(model);

        assertTrue(result.getFeedbackToUser().contains(RemoveLikeCommand.MESSAGE_SUCCESS));
        assertEquals(0, model.getRecommendationSystem()
                .getUserRecommendations().getDislikedCategories().size());
        assertEquals(0, model.getRecommendationSystem()
                .getUserRecommendations().getDislikedTags().size());
        assertEquals(0, model.getRecommendationSystem()
                .getUserRecommendations().getDislikedLocations().size());
    }

    @Test
    public void removeLikeCommand_notFound_failure() {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new CustomSorter(), new SavingsAccount());
        model.addLikes(ADDED_ANOTHER_CATEGORY_SET, ADDED_ANOTHER_TAG_SET, ADDED_ANOTHER_LOCATION_SET);
        RemoveLikeCommand command = new RemoveLikeCommand(new HashSet<>(), LIKED_TAG_SET, new HashSet<>(),
                false);

        assertThrows(CommandException.class, RemoveLikeCommand.NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void removeDislikeCommand_notFound_failure() {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new CustomSorter(), new SavingsAccount());
        model.addDislikes(ADDED_ANOTHER_CATEGORY_SET, ADDED_ANOTHER_TAG_SET, ADDED_ANOTHER_LOCATION_SET);
        RemoveDislikeCommand command = new RemoveDislikeCommand(LIKED_CATEGORY_SET, new HashSet<>(), new HashSet<>(),
                false);

        assertThrows(CommandException.class, RemoveDislikeCommand.NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void wrong_execute_error() {
        assertThrows(
                AssertionError.class, () ->
                        new RemovePreferenceCommand(LIKED_CATEGORY_SET, LIKED_TAG_SET, LIKED_LOCATION_SET, false)
                                .execute(model)
        );
    }
}
