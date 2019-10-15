package seedu.address.testutil;

import static seedu.address.testutil.TypicalTemplateItems.MILK;
import static seedu.address.testutil.TypicalTemplateItems.MINCEDBEEF;
import static seedu.address.testutil.TypicalTemplateItems.MINCEDCHICKEN;
import static seedu.address.testutil.TypicalTemplateItems.TOMATO;
import static seedu.address.testutil.TypicalTemplateItems.WINE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TemplateList;
import seedu.address.model.food.Name;
import seedu.address.model.food.UniqueTemplateItems;

/**
 * A utility class containing a list of {@code TemplateItem} objects to be used in tests.
 */
public class TypicalTemplateList {
    public static final UniqueTemplateItems WEEKLY_NECESSITIES = new UniqueTemplateItemsBuilder(
            new Name("Weekly Necessities")).withTemplateItem(MINCEDBEEF).withTemplateItem(MILK).build();
    public static final UniqueTemplateItems DIET_PLAN = new UniqueTemplateItemsBuilder(
            new Name("Diet Plan")).withTemplateItem(TOMATO).withTemplateItem(MINCEDCHICKEN).build();
    public static final UniqueTemplateItems BIRTHDAY_PARTY = new UniqueTemplateItemsBuilder(
            new Name("Birthday Party")).withTemplateItem(WINE).withTemplateItem(MINCEDBEEF).build();

    private TypicalTemplateList() {} // prevents instantiation

    /**
     * Returns an {@code TemplateList} with all the typical persons.
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
