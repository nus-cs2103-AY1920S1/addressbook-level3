package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exercise.ClearExerciseCommand;
import seedu.address.logic.commands.profile.EditProfileCommand;
import seedu.address.logic.commands.recipe.AddRecipeCommand;
import seedu.address.logic.commands.recipe.DeleteRecipeCommand;
import seedu.address.logic.commands.recipe.EditRecipeCommand;
import seedu.address.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import seedu.address.logic.commands.recipe.FindRecipeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.person.Person;
import seedu.address.model.recipe.components.Recipe;
import seedu.address.model.recipe.components.RecipeNameContainsKeywordsPredicate;
import seedu.address.testutil.profile.EditPersonDescriptorBuilder;
import seedu.address.testutil.profile.PersonBuilder;
import seedu.address.testutil.profile.PersonUtil;
import seedu.address.testutil.recipe.EditRecipeDescriptorBuilder;
import seedu.address.testutil.recipe.RecipeBuilder;
import seedu.address.testutil.recipe.RecipeUtil;

public class DukeCooksParserTest {

    private final DukeCooksParser parser = new DukeCooksParser();

    @Test
    public void parseCommand_addRecipe() throws Exception {
        Recipe recipe = new RecipeBuilder().build();
        AddRecipeCommand command = (AddRecipeCommand) parser.parseCommand(RecipeUtil.getAddRecipeCommand(recipe));
        assertEquals(new AddRecipeCommand(recipe), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearExerciseCommand.COMMAND_WORD) instanceof ClearExerciseCommand);
        assertTrue(parser.parseCommand(ClearExerciseCommand.COMMAND_WORD
                + " 3") instanceof ClearExerciseCommand);
    }

    @Test
    public void parseCommand_deleteRecipe() throws Exception {
        DeleteRecipeCommand command = (DeleteRecipeCommand) parser.parseCommand(
                DeleteRecipeCommand.COMMAND_WORD + " " + DeleteRecipeCommand.VARIANT_WORD
                        + " " + INDEX_FIRST_RECIPE.getOneBased());
        assertEquals(new DeleteRecipeCommand(INDEX_FIRST_RECIPE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditProfileCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditProfileCommand command = (EditProfileCommand) parser.parseCommand(EditProfileCommand.COMMAND_WORD
                + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditProfileCommand(descriptor), command);
    }

    @Test
    public void parseCommand_editRecipe() throws Exception {
        Recipe recipe = new RecipeBuilder().build();
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(recipe).build();
        EditRecipeCommand command = (EditRecipeCommand) parser.parseCommand(EditRecipeCommand.COMMAND_WORD
                + " " + DeleteRecipeCommand.VARIANT_WORD + " " + INDEX_FIRST_RECIPE.getOneBased() + " "
                + RecipeUtil.getEditRecipeDescriptorDetails(descriptor));
        assertEquals(new EditRecipeCommand(INDEX_FIRST_RECIPE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findRecipe() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindRecipeCommand command = (FindRecipeCommand) parser.parseCommand(
                FindRecipeCommand.COMMAND_WORD + " " + DeleteRecipeCommand.VARIANT_WORD
                        + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindRecipeCommand(new RecipeNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listRecipe() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
