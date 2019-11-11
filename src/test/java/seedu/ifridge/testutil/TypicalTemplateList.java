package seedu.ifridge.testutil;

import static seedu.ifridge.testutil.TypicalTemplateItems.MILK;
import static seedu.ifridge.testutil.TypicalTemplateItems.MINCEDBEEF;
import static seedu.ifridge.testutil.TypicalTemplateItems.MINCEDCHICKEN;
import static seedu.ifridge.testutil.TypicalTemplateItems.TOMATO;
import static seedu.ifridge.testutil.TypicalTemplateItems.WINE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * A utility class containing a list of {@code UniqueTemplateItems} objects to be used in tests.
 */
public class TypicalTemplateList {
    public static final UniqueTemplateItems WEEKLY_NECESSITIES = new UniqueTemplateItemsBuilder().withName(
            new Name("Weekly Necessities")).withTemplateItem(MINCEDBEEF).withTemplateItem(MILK).build();
    public static final UniqueTemplateItems DIET_PLAN = new UniqueTemplateItemsBuilder().withName(
            new Name("Diet Plan")).withTemplateItem(TOMATO).withTemplateItem(MINCEDCHICKEN).build();
    public static final UniqueTemplateItems BIRTHDAY_PARTY = new UniqueTemplateItemsBuilder().withName(
            new Name("Birthday Party")).withTemplateItem(WINE).withTemplateItem(MINCEDBEEF).build();
    public static final UniqueTemplateItems BULK_UP = new UniqueTemplateItemsBuilder().withName(
            new Name("BulkUp")).withTemplateItem(TOMATO).withTemplateItem(MILK).build();

    private TypicalTemplateList() {} // prevents instantiation

    /**
     * Returns an {@code TemplateList} with all the typical templates.
     */
    public static TemplateList getTypicalTemplateList() {
        TemplateList ab = new TemplateList();
        for (UniqueTemplateItems templates: getTypicalTemplates()) {
            ab.addTemplate(templates);
        }
        return ab;
    }

    public static List<UniqueTemplateItems> getTypicalTemplates() {
        return new ArrayList<>(Arrays.asList(WEEKLY_NECESSITIES, BIRTHDAY_PARTY, DIET_PLAN));
    }
}
