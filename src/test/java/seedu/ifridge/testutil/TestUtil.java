package seedu.ifridge.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.parser.grocerylist.GroceryListParser;
import seedu.ifridge.logic.parser.templatelist.TemplateListParser;
import seedu.ifridge.logic.parser.templatelist.template.TemplateItemParser;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidIndex(Model model, String list) {
        switch (list) {
        case GroceryListParser.LIST_TYPE_WORD:
            return Index.fromOneBased(model.getFilteredGroceryItemList().size() / 2);
        case TemplateListParser.LIST_TYPE_WORD:
            return Index.fromOneBased(model.getFilteredTemplateList().size() / 2);
        case TemplateItemParser.LIST_TYPE_WORD:
            return Index.fromOneBased(model.getFilteredTemplateToBeShown().size() / 2);
        default:
            System.out.println("Add new case to use this method for a new list.");
        }
        return null;
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model, String list) {
        switch (list) {
        case GroceryListParser.LIST_TYPE_WORD:
            return Index.fromOneBased(model.getFilteredGroceryItemList().size());
        case TemplateListParser.LIST_TYPE_WORD:
            return Index.fromOneBased(model.getFilteredTemplateList().size());
        case TemplateItemParser.LIST_TYPE_WORD:
            return Index.fromOneBased(model.getFilteredTemplateToBeShown().size());
        default:
            System.out.println("Add new case to use this method for a new list.");
        }
        return null;
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Food getPerson(Model model, Index index) {
        return model.getFilteredGroceryItemList().get(index.getZeroBased());
    }

    /**
     * Returns the template in the {@code model}'s template list at {@code index}.
     */
    public static UniqueTemplateItems getTemplate(Model model, Index index) {
        return model.getFilteredTemplateList().get(index.getZeroBased());
    }

    /**
     * Returns the template item in the {@code model}'s template at {@code index}.
     */
    public static TemplateItem getTemplateItem(Model model, Index templateIndex, Index itemIndex) {
        UniqueTemplateItems template = model.getFilteredTemplateList().get(templateIndex.getZeroBased());
        return template.get(itemIndex.getZeroBased());
    }
}
