package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.DUPLICATE_DISLIKED_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.DUPLICATE_DISLIKED_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.DUPLICATE_DISLIKED_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.DUPLICATE_LIKED_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.DUPLICATE_LIKED_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.DUPLICATE_LIKED_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_TAG_SET;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.Wallet;

//@@author jon-chua
/**
 * Contains tests for both LikeCommand and DislikeCommand
 */
public class PreferenceCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
        model.clearDislikes();
        model.clearLikes();
    }

    @Test
    public void likeConstructor_nullSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LikeCommand(null, null, null, false));
        assertThrows(NullPointerException.class, () -> new LikeCommand(null, new HashSet<>(), new HashSet<>(), false));
        assertThrows(NullPointerException.class, () -> new LikeCommand(new HashSet<>(), null, new HashSet<>(), false));
        assertThrows(NullPointerException.class, () -> new LikeCommand(new HashSet<>(), new HashSet<>(), null, false));
    }

    @Test
    public void dislikeConstructor_nullSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new DislikeCommand(null, null, null, false));
        assertThrows(NullPointerException.class, () ->
                new DislikeCommand(null, new HashSet<>(), new HashSet<>(), false));
        assertThrows(NullPointerException.class, () ->
                new DislikeCommand(new HashSet<>(), null, new HashSet<>(), false));
        assertThrows(NullPointerException.class, () ->
                new DislikeCommand(new HashSet<>(), new HashSet<>(), null, false));
    }

    @Test
    public void likeCommand_equals() {
        LikeCommand first = new LikeCommand(LIKED_CATEGORY_SET, LIKED_TAG_SET, LIKED_LOCATION_SET, false);
        LikeCommand second = new LikeCommand(LIKED_CATEGORY_SET, LIKED_TAG_SET, LIKED_LOCATION_SET, false);
        LikeCommand third = new LikeCommand(new HashSet<>(), LIKED_TAG_SET, LIKED_LOCATION_SET, false);
        LikeCommand fourth = new LikeCommand(LIKED_CATEGORY_SET, new HashSet<>(), LIKED_LOCATION_SET, false);
        LikeCommand fifth = new LikeCommand(LIKED_CATEGORY_SET, LIKED_TAG_SET, new HashSet<>(), false);

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertFalse(first.equals(third));
        assertFalse(first.equals(fourth));
        assertFalse(first.equals(fifth));
    }

    @Test
    public void dislikeCommand_equals() {
        DislikeCommand first =
                new DislikeCommand(DISLIKED_CATEGORY_SET, DISLIKED_TAG_SET, DISLIKED_LOCATION_SET, false);
        DislikeCommand second =
                new DislikeCommand(DISLIKED_CATEGORY_SET, DISLIKED_TAG_SET, DISLIKED_LOCATION_SET, false);
        DislikeCommand third =
                new DislikeCommand(new HashSet<>(), DISLIKED_TAG_SET, DISLIKED_LOCATION_SET, false);
        DislikeCommand fourth =
                new DislikeCommand(DISLIKED_CATEGORY_SET, new HashSet<>(), DISLIKED_LOCATION_SET, false);
        DislikeCommand fifth =
                new DislikeCommand(DISLIKED_CATEGORY_SET, DISLIKED_TAG_SET, new HashSet<>(), false);

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertFalse(first.equals(third));
        assertFalse(first.equals(fourth));
        assertFalse(first.equals(fifth));
    }

    @Test
    public void likeCommand_executeSuccessAndList_contains() throws CommandException {
        CommandResult result =
                new LikeCommand(LIKED_CATEGORY_SET, LIKED_TAG_SET, LIKED_LOCATION_SET, false).execute(model);

        assertTrue(result.getFeedbackToUser().contains(LikeCommand.MESSAGE_SUCCESS));

        for (Category c : LIKED_CATEGORY_SET) {
            assertTrue(result.getFeedbackToUser().contains(c.category));
        }
        for (Tag t : LIKED_TAG_SET) {
            assertTrue(result.getFeedbackToUser().contains(t.tagName));
        }
        for (Location l : LIKED_LOCATION_SET) {
            assertTrue(result.getFeedbackToUser().contains(l.location));
        }

        result = new LikeCommand(true).execute(model);
        for (Category c : LIKED_CATEGORY_SET) {
            assertTrue(result.getFeedbackToUser().contains(c.category));
        }
        for (Tag t : LIKED_TAG_SET) {
            assertTrue(result.getFeedbackToUser().contains(t.tagName));
        }
        for (Location l : LIKED_LOCATION_SET) {
            assertTrue(result.getFeedbackToUser().contains(l.location));
        }
    }

    @Test
    public void dislikeCommand_executeSuccessAndList_contains() throws CommandException {
        CommandResult result =
                new DislikeCommand(DISLIKED_CATEGORY_SET, DISLIKED_TAG_SET, DISLIKED_LOCATION_SET, false)
                        .execute(model);

        assertTrue(result.getFeedbackToUser().contains(DislikeCommand.MESSAGE_SUCCESS));

        for (Category c : DISLIKED_CATEGORY_SET) {
            assertTrue(result.getFeedbackToUser().contains(c.category));
        }
        for (Tag t : DISLIKED_TAG_SET) {
            assertTrue(result.getFeedbackToUser().contains(t.tagName));
        }
        for (Location l : DISLIKED_LOCATION_SET) {
            assertTrue(result.getFeedbackToUser().contains(l.location));
        }

        result = new DislikeCommand(true).execute(model);
        for (Category c : DISLIKED_CATEGORY_SET) {
            assertTrue(result.getFeedbackToUser().contains(c.category));
        }
        for (Tag t : DISLIKED_TAG_SET) {
            assertTrue(result.getFeedbackToUser().contains(t.tagName));
        }
        for (Location l : DISLIKED_LOCATION_SET) {
            assertTrue(result.getFeedbackToUser().contains(l.location));
        }
    }

    @Test
    public void likeCommand_categoryFoundInOppositeList_executeFailure() {
        model.addDislikes(LIKED_CATEGORY_SET, new HashSet<>(), new HashSet<>());

        LikeCommand likeCommand =
                new LikeCommand(DUPLICATE_LIKED_CATEGORY_SET, new HashSet<>(), new HashSet<>(), false);
        assertThrows(CommandException.class, LikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                likeCommand.execute(model));
    }
    @Test
    public void dislikeCommand_categoryFoundInOppositeList_executeFailure() {
        model.addLikes(DISLIKED_CATEGORY_SET, new HashSet<>(), new HashSet<>());

        DislikeCommand dislikeCommand =
                new DislikeCommand(DUPLICATE_DISLIKED_CATEGORY_SET, new HashSet<>(), new HashSet<>(), false);
        assertThrows(CommandException.class, DislikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                dislikeCommand.execute(model));
    }

    @Test
    public void likeCommand_tagFoundInOppositeList_executeFailure() {
        model.addDislikes(new HashSet<>(), LIKED_TAG_SET, new HashSet<>());

        LikeCommand likeCommand =
                new LikeCommand(new HashSet<>(), DUPLICATE_LIKED_TAG_SET, new HashSet<>(), false);
        assertThrows(CommandException.class, LikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                likeCommand.execute(model));
    }

    @Test
    public void dislikeCommand_tagFoundInOppositeList_executeFailure() {
        model.addLikes(new HashSet<>(), DISLIKED_TAG_SET, new HashSet<>());

        DislikeCommand dislikeCommand =
                new DislikeCommand(new HashSet<>(), DUPLICATE_DISLIKED_TAG_SET, new HashSet<>(), false);
        assertThrows(CommandException.class, DislikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                dislikeCommand.execute(model));
    }

    @Test
    public void likeCommand_locationFoundInOppositeList_executeFailure() {
        model.addDislikes(new HashSet<>(), new HashSet<>(), LIKED_LOCATION_SET);

        LikeCommand likeCommand =
                new LikeCommand(new HashSet<>(), new HashSet<>(), DUPLICATE_LIKED_LOCATION_SET, false);
        assertThrows(CommandException.class, LikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                likeCommand.execute(model));
    }

    @Test
    public void dislikeCommand_locationFoundInOppositeList_executeFailure() {
        model.addLikes(new HashSet<>(), new HashSet<>(), DISLIKED_LOCATION_SET);

        DislikeCommand dislikeCommand =
                new DislikeCommand(new HashSet<>(), new HashSet<>(), DUPLICATE_DISLIKED_LOCATION_SET, false);
        assertThrows(CommandException.class, DislikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                dislikeCommand.execute(model));
    }

    @Test
    public void wrong_execute_error() {
        assertThrows(
                AssertionError.class, () ->
                        new PreferenceCommand(LIKED_CATEGORY_SET, LIKED_TAG_SET, LIKED_LOCATION_SET, false)
                                .execute(model)
        );
    }
}
