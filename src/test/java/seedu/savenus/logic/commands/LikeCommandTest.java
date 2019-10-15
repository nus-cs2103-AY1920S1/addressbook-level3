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

public class LikeCommandTest {

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
    }

    @Test
    public void constructor_nullSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LikeCommand(null, null, null));
        assertThrows(NullPointerException.class, () -> new LikeCommand(null, new HashSet<>(), new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new LikeCommand(new HashSet<>(), null, new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new LikeCommand(new HashSet<>(), new HashSet<>(), null));
    }

    @Test
    public void equals() {
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
    public void executeSuccess() throws CommandException {
        model.clearDislikes();
        model.clearLikes();
        CommandResult result = new LikeCommand(testCategory, testTag, testLocation).execute(model);

        assertTrue(result.getFeedbackToUser().contains(LikeCommand.MESSAGE_SUCCESS));
    }

    @Test
    public void categoryFoundInOppositeList_executeFailure() throws CommandException {
        model.addDislikes(testCategory, new HashSet<>(), new HashSet<>());

        Set<Category> addCategory = new HashSet<>();
        addCategory.add(SECOND_CATEGORY);

        LikeCommand likeCommand = new LikeCommand(addCategory, new HashSet<>(), new HashSet<>());
        assertThrows(CommandException.class, LikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                likeCommand.execute(model));
    }

    @Test
    public void tagFoundInOppositeList_executeFailure() throws CommandException {
        model.addDislikes(new HashSet<>(), testTag, new HashSet<>());

        Set<Tag> addTag = new HashSet<>();
        addTag.add(FIRST_TAG);

        LikeCommand likeCommand = new LikeCommand(new HashSet<>(), addTag, new HashSet<>());
        assertThrows(CommandException.class, LikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                likeCommand.execute(model));
    }

    @Test
    public void locationFoundInOppositeList_executeFailure() throws CommandException {
        model.addDislikes(new HashSet<>(), new HashSet<>(), testLocation);

        Set<Location> addLocation = new HashSet<>();
        addLocation.add(SECOND_LOCATION);

        LikeCommand likeCommand = new LikeCommand(new HashSet<>(), new HashSet<>(), addLocation);
        assertThrows(CommandException.class, LikeCommand.DUPLICATE_FOUND_IN_OPPOSITE_LIST, () ->
                likeCommand.execute(model));
    }
}
