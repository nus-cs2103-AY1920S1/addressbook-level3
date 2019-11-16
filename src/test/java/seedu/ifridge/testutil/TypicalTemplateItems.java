package seedu.ifridge.testutil;

import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_AMOUNT_CHEESE;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_AMOUNT_TOMATO_JUICE;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_CHEESE;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_TOMATO_JUICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;


/**
 * A utility class containing a list of {@code TemplateItem} objects to be used in tests.
 */
public class TypicalTemplateItems {
    public static final Name TEMPLATE_NAME_WEEKLY_NECESSITIES = new Name("Weekly Necessities");

    public static final TemplateItem MINCEDPORK = new TemplateItemBuilder().withName("Ground Pork")
            .withAmount("300g").build();
    public static final TemplateItem MINCEDBEEF = new TemplateItemBuilder().withName("Ground Beef")
            .withAmount("300g").build();
    public static final TemplateItem MINCEDCHICKEN = new TemplateItemBuilder().withName("Ground Chicken")
            .withAmount("300g").build();
    public static final TemplateItem TOMATO = new TemplateItemBuilder().withName("Tomato")
            .withAmount("2units").build();
    public static final TemplateItem MILK = new TemplateItemBuilder().withName("FullFat Milk")
            .withAmount("1L").build();
    public static final TemplateItem WINE = new TemplateItemBuilder().withName("Red Wine")
            .withAmount("1L").build();

    // Manually added
    public static final TemplateItem EGGPLANT = (TemplateItem) new TemplateItemBuilder().withName("Eggplant")
            .withAmount("3units").build();
    public static final TemplateItem JUICE = (TemplateItem) new TemplateItemBuilder().withName("Apple Juice")
            .withAmount("300ml").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final TemplateItem CHEESE = new TemplateItemBuilder().withName(VALID_NAME_CHEESE)
            .withAmount(VALID_AMOUNT_CHEESE).build();
    public static final TemplateItem TOMATO_JUICE = new TemplateItemBuilder().withName(VALID_NAME_TOMATO_JUICE)
            .withAmount(VALID_AMOUNT_TOMATO_JUICE).build();

    private TypicalTemplateItems() {} // prevents instantiation

    /**
     * Returns an {@code TemplateList} with all the typical persons.
     */
    public static UniqueTemplateItems getTypicalTemplate() {
        UniqueTemplateItems ab = new UniqueTemplateItems(TEMPLATE_NAME_WEEKLY_NECESSITIES);
        for (TemplateItem templateItem : getTypicalTemplateItems()) {
            ab.add(templateItem);
        }
        return ab;
    }

    public static List<TemplateItem> getTypicalTemplateItems() {
        return new ArrayList<>(Arrays.asList(MINCEDBEEF, MINCEDPORK, MINCEDCHICKEN, TOMATO, MILK));
    }
}
