package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;

/**
 * Contains tests for both LikeCommand and DislikeCommand
 */
public class PreferenceCommandTest {

    private static final Category FIRST_CATEGORY = new Category("category 123");
    private static final Category SECOND_CATEGORY = new Category("another category 456");

    private static final Tag FIRST_TAG = new Tag("dnfbkdngbkjsnb");
    private static final Tag SECOND_TAG = new Tag("qwrbqirbqirb");

    private static final Location FIRST_LOCATION = new Location("location 123");
    private static final Location SECOND_LOCATION = new Location("another test location 456");

    private Set<Category> testCategory;
    private Set<Tag> testTag;
    private Set<Location> testLocation;
    private Model model;

    @BeforeEach
    public void setUp() {
        testCategory = new HashSet<>();
        testTag = new HashSet<>();
        testLocation = new HashSet<>();

        testCategory.add(FIRST_CATEGORY);
        testCategory.add(SECOND_CATEGORY);

        testTag.add(FIRST_TAG);
        testTag.add(SECOND_TAG);

        testLocation.add(FIRST_LOCATION);
        testLocation.add(SECOND_LOCATION);

        model = new ModelManager(getTypicalMenu(), new UserPrefs());
        model.clearDislikes();
        model.clearLikes();
    }

    @Test
    public void likeConstructor_nullSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LikeCommand(null, null, null));
        assertThrows(NullPointerException.class, () -> new LikeCommand(null, new HashSet<>(), new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new LikeCommand(new HashSet<>(), null, new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new LikeCommand(new HashSet<>(), new HashSet<>(), null));
    }

    @Test
    public void dislikConstructor_nullSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DislikeCommand(null, null, null));
        assertThrows(NullPointerException.class, () -> new DislikeCommand(null, new HashSet<>(), new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new DislikeCommand(new HashSet<>(), null, new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new DislikeCommand(new HashSet<>(), new HashSet<>(), null));
    }

    @Test
    public void likeCommand_equals() {
        LikeCommand first = new LikeCommand(testCategory, testTag, testLocation);
        LikeCommand second = new LikeCommand(testCategory, testTag, testLocation);
        LikeCommand third = new LikeCommand(new HashSet<>(), testTag, testLocation);
        LikeCommand fourth = new LikeCommand(testCategory, new HashSet<>(), testLocation);
        LikeCommand fifth = new LikeCommand(testCategory, testTag, new HashSet<>());

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertFalse(first.equals(third));
        assertFalse(first.equals(fourth));
        assertFalse(first.equals(fifth));
    }

    @Test
    public void dislikeCommand_equals() {
        DislikeCommand first = new DislikeCommand(testCategory, testTag, testLocation);
        DislikeCommand second = new DislikeCommand(testCategory, testTag, testLocation);
        DislikeCommand third = new DislikeCommand(new HashSet<>(), testTag, testLocation);
        DislikeCommand fourth = new DislikeCommand(testCategory, new HashSet<>(), testLocation);
        DislikeCommand fifth = new DislikeCommand(testCategory, testTag, new HashSet<>());

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertFalse(first.equals(third));
        assertFalse(first.equals(fourth));
        assertFalse(first.equals(fifth));
    }

    @Test
    public void likeCommand_executeSuccess() throws CommandException {
        CommandResult result = new LikeCommand(testCategory, testTag, testLocation).execute(model);

        assertTrue(result.getFeedbackToUser().contains(LikeCommand.MESSAGE_SUCCESS));
    }

    @Test
    public void dislikeCommand_executeSuccess() throws CommandException {
        CommandResult result = new DislikeCommand(testCategory, testTag, testLocation).execute(model);

        assertTrue(result.getFeedbackToUser().contains(DislikeCommand.MESSAGE_SUCCESS));
    }

    @Test
    public void likeCommand_addCategory_contains() throws CommandException {
        CommandResult result = new LikeCommand(testCategory, new HashSet<>(), new HashSet<>()).execute(model);

        assertTrue(result.getFeedbackToUser().contains(LikeCommand.MESSAGE_SUCCESS));
        for (Category c : testCategory) {
            assertTrue(result.getFeedbackToUser().contains(c.category));
        }
    }

    @Test
    public void dislikeCommand_addCategory_contains() throws CommandException {
        CommandResult result = new DislikeCommand(testCategory, new HashSet<>(), new HashSet<>()).execute(model);

        assertTrue(result.getFeedbackToUser().contains(DislikeCommand.MESSAGE_SUCCESS));
        for (Category c : testCategory) {
            assertTrue(result.getFeedbackToUser().contains(c.category));
        }
    }

    @Test
    public void likeCommand_addTags_contains() throws CommandException {
        CommandResult result = new LikeCommand(new HashSet<>(), testTag, new HashSet<>()).execute(model);

        assertTrue(result.getFeedbackToUser().contains(LikeCommand.MESSAGE_SUCCESS));
        for (Tag t : testTag) {
            assertTrue(result.getFeedbackToUser().contains(t.tagName));
        }
    }

    @Test
    public void dislikeCommand_addTags_contains() throws CommandException {
        CommandResult result = new DislikeCommand(new HashSet<>(), testTag, new HashSet<>()).execute(model);

        assertTrue(result.getFeedbackToUser().contains(DislikeCommand.MESSAGE_SUCCESS));
        for (Tag t : testTag) {
            assertTrue(result.getFeedbackToUser().contains(t.tagName));
        }
    }

    @Test
    public void likeCommand_addLocation_contains() throws CommandException {
        CommandResult result = new LikeCommand(new HashSet<>(), new HashSet<>(), testLocation).execute(model);

        assertTrue(result.getFeedbackToUser().contains(LikeCommand.MESSAGE_SUCCESS));
        for (Location l : testLocation) {
            assertTrue(result.getFeedbackToUser().contains(l.location));
        }
    }

    @Test
    public void dislikeCommand_addLocation_contains() throws CommandException {
        CommandResult result = new DislikeCommand(new HashSet<>(), new HashSet<>(), testLocation).execute(model);

        assertTrue(result.getFeedbackToUser().contains(DislikeCommand.MESSAGE_SUCCESS));
        for (Location l : testLocation) {
            assertTrue(result.getFeedbackToUser().contains(l.location));
        }
    }

    @Test
    public void likeCommand_categoryFoundInOppositeList_executeFailure() {
        model.addDislikes(testCategory, new HashSet<>(), new HashSet<>());

        Set<Category> addCategory = new HashSet<>();
        addCategory.add(SECOND_CATEGORY);

        LikeCommand likeCommand = new LikeCommand(addCategory, new HashSet<>(), new HashSet<>());
        assertThrows(CommandException.class, LikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                likeCommand.execute(model));
    }
    @Test
    public void dislikeCommand_categoryFoundInOppositeList_executeFailure() {
        model.addLikes(testCategory, new HashSet<>(), new HashSet<>());

        Set<Category> addCategory = new HashSet<>();
        addCategory.add(SECOND_CATEGORY);

        DislikeCommand dislikeCommand = new DislikeCommand(addCategory, new HashSet<>(), new HashSet<>());
        assertThrows(CommandException.class, DislikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                dislikeCommand.execute(model));
    }

    @Test
    public void likeCommand_tagFoundInOppositeList_executeFailure() {
        model.addDislikes(new HashSet<>(), testTag, new HashSet<>());

        Set<Tag> addTag = new HashSet<>();
        addTag.add(FIRST_TAG);

        LikeCommand likeCommand = new LikeCommand(new HashSet<>(), addTag, new HashSet<>());
        assertThrows(CommandException.class, LikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                likeCommand.execute(model));
    }

    @Test
    public void dislikeCommand_tagFoundInOppositeList_executeFailure() {
        model.addLikes(new HashSet<>(), testTag, new HashSet<>());

        Set<Tag> addTag = new HashSet<>();
        addTag.add(FIRST_TAG);

        DislikeCommand dislikeCommand = new DislikeCommand(new HashSet<>(), addTag, new HashSet<>());
        assertThrows(CommandException.class, DislikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                dislikeCommand.execute(model));
    }

    @Test
    public void likeCommand_locationFoundInOppositeList_executeFailure() {
        model.addDislikes(new HashSet<>(), new HashSet<>(), testLocation);

        Set<Location> addLocation = new HashSet<>();
        addLocation.add(SECOND_LOCATION);

        LikeCommand likeCommand = new LikeCommand(new HashSet<>(), new HashSet<>(), addLocation);
        assertThrows(CommandException.class, LikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                likeCommand.execute(model));
    }

    @Test
    public void dislikeCommand_locationFoundInOppositeList_executeFailure() {
        model.addLikes(new HashSet<>(), new HashSet<>(), testLocation);

        Set<Location> addLocation = new HashSet<>();
        addLocation.add(SECOND_LOCATION);

        DislikeCommand dislikeCommand = new DislikeCommand(new HashSet<>(), new HashSet<>(), addLocation);
        assertThrows(CommandException.class, DislikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                dislikeCommand.execute(model));
    }
}
