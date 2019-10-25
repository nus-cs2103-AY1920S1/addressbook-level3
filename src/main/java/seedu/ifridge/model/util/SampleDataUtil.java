package seedu.ifridge.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.ReadOnlyShoppingList;
import seedu.ifridge.model.ReadOnlyTemplateList;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.ExpiryDate;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.tag.Tag;
import seedu.ifridge.model.waste.WasteMonth;

/**
 * Contains utility methods for populating {@code GroceryList} with sample data.
 */
public class SampleDataUtil {

    // ============================= Grocery Item Sample List ============================= //
    public static GroceryItem[] getSampleGroceryItems() {
        return new GroceryItem[] {
            new GroceryItem(new Name("Minced beef"), new Amount("300g"), new ExpiryDate("30/09/2019"),
                    getTagSet("meat")),
            new GroceryItem(new Name("Spaghetti"), new Amount("1unit"), new ExpiryDate("20/11/2019"),
                    getTagSet("dish", "dinner")),
            new GroceryItem(new Name("Apples"), new Amount("6units"), new ExpiryDate("15/10/2019"),
                    getTagSet("healthy", "fruit")),
            new GroceryItem(new Name("Orange juice"), new Amount("500ml"), new ExpiryDate("22/11/2019"),
                    getTagSet("juice", "diet")),
            new GroceryItem(new Name("Green tea latte"), new Amount("10units"), new ExpiryDate("30/10/2019"),
                    getTagSet("drink", "boba"))
        };
    }

    public static ReadOnlyGroceryList getSampleGroceryList() {
        GroceryList sampleGl = new GroceryList();
        for (GroceryItem groceryItem : getSampleGroceryItems()) {
            sampleGl.addGroceryItem(groceryItem);
        }
        return sampleGl;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    // =============================== Waste List Sample =============================== //
    /**
     * Generates sample of past 12 months
     * @return WasteMonth array of past 12 months
     */
    public static WasteMonth[] getSampleWasteMonths() {
        LocalDate date = LocalDate.now();
        WasteMonth[] wasteMonths = new WasteMonth[12];
        wasteMonths[0] = new WasteMonth(date);
        for (int i = 1; i <= 11; i++) {
            wasteMonths[i] = new WasteMonth(date.minusMonths(i));
        }
        return wasteMonths;
    }

    public static GroceryItem[] getSampleWasteItems() {
        return new GroceryItem[] {
            new GroceryItem(new Name("Salmon"), new Amount("300g"),
                    new ExpiryDate("12/10/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Apples"), new Amount("2units"),
                    new ExpiryDate("15/10/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("100ml"),
                    new ExpiryDate("18/10/2019"), getTagSet()),

            new GroceryItem(new Name("Potatoes"), new Amount("1500g"),
                    new ExpiryDate("12/09/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Oranges"), new Amount("3units"),
                    new ExpiryDate("15/09/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("200ml"),
                    new ExpiryDate("18/09/2019"), getTagSet()),

            new GroceryItem(new Name("Chicken"), new Amount("400g"),
                    new ExpiryDate("12/08/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Pears"), new Amount("3units"),
                    new ExpiryDate("15/08/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("150ml"),
                    new ExpiryDate("18/08/2019"), getTagSet()),

            new GroceryItem(new Name("Salmon"), new Amount("800g"),
                    new ExpiryDate("12/07/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Grapes"), new Amount("10units"),
                    new ExpiryDate("15/07/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("70ml"),
                    new ExpiryDate("18/07/2019"), getTagSet()),

            new GroceryItem(new Name("Potatoes"), new Amount("300g"),
                    new ExpiryDate("12/06/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Lychee"), new Amount("3units"),
                    new ExpiryDate("15/06/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("280ml"),
                    new ExpiryDate("18/06/2019"), getTagSet()),

            new GroceryItem(new Name("Chicken"), new Amount("890g"),
                    new ExpiryDate("12/05/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Lemon"), new Amount("1units"),
                    new ExpiryDate("15/05/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("100ml"),
                    new ExpiryDate("18/05/2019"), getTagSet()),

            new GroceryItem(new Name("Salmon"), new Amount("290g"),
                    new ExpiryDate("12/04/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Kiwi"), new Amount("2units"),
                    new ExpiryDate("15/04/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("0ml"),
                    new ExpiryDate("18/04/2019"), getTagSet()),

            new GroceryItem(new Name("Potatoes"), new Amount("469g"),
                    new ExpiryDate("12/03/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Eggs"), new Amount("6units"),
                    new ExpiryDate("15/03/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("60ml"),
                    new ExpiryDate("18/03/2019"), getTagSet()),

            new GroceryItem(new Name("Chicken"), new Amount("937g"),
                    new ExpiryDate("12/02/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Strawberries"), new Amount("8units"),
                    new ExpiryDate("15/02/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("300ml"),
                    new ExpiryDate("18/02/2019"), getTagSet()),

            new GroceryItem(new Name("Salmon"), new Amount("700g"),
                    new ExpiryDate("12/01/2019"), getTagSet("staples")),
            new GroceryItem(new Name("Pineapples"), new Amount("2units"),
                    new ExpiryDate("15/01/2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("200ml"),
                    new ExpiryDate("18/01/2019"), getTagSet()),

            new GroceryItem(new Name("Potatoes"), new Amount("1000g"),
                    new ExpiryDate("12/12/2018"), getTagSet("staples")),
            new GroceryItem(new Name("Plums"), new Amount("4units"),
                    new ExpiryDate("15/12/2018"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("100ml"),
                    new ExpiryDate("18/12/2018"), getTagSet()),

            new GroceryItem(new Name("Chicken"), new Amount("396g"),
                    new ExpiryDate("12/11/2018"), getTagSet("staples")),
            new GroceryItem(new Name("Strawberries"), new Amount("7units"),
                    new ExpiryDate("15/11/2018"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("100ml"),
                    new ExpiryDate("18/11/2018"), getTagSet())
        };
    }

    public static TreeMap<WasteMonth, WasteList> getSampleWasteArchive() {
        TreeMap<WasteMonth, WasteList> wasteArchive = new TreeMap<>();
        WasteMonth[] wasteMonths = getSampleWasteMonths();
        GroceryItem[] wasteItems = getSampleWasteItems();
        for (int i = 0; i < 12; i++) {
            WasteMonth wm = wasteMonths[i];
            WasteList wl = new WasteList(wm);
            for (int j = i * 3; j < i * 3 + 3; j++) {
                wl.addWasteItem(wasteItems[j]);
            }
            wasteArchive.put(wm, wl);
        }
        return wasteArchive;
    }

    // =============================== Template List Sample =============================== //

    public static TemplateItem[] getSampleTemplateItems() {
        return new TemplateItem[] {
            new TemplateItem(new Name("Minced Beef"), new Amount("300g")),
            new TemplateItem(new Name("FullFat Milk"), new Amount("1L")),
            new TemplateItem(new Name("Red Wine"), new Amount("1L")),
            new TemplateItem(new Name("Minced Chicken"), new Amount("300g")),
            new TemplateItem(new Name("Tomato"), new Amount("2units"))
        };
    }

    /**
     * Generates sample of template list
     * @return Template array
     */
    public static UniqueTemplateItems[] getSampleTemplates() {
        return new UniqueTemplateItems[] {
            new UniqueTemplateItems(new Name("Weekly Necessities")),
            new UniqueTemplateItems(new Name("Birthday Party")),
            new UniqueTemplateItems(new Name("Diet Plan"))
        };
    }

    public static ReadOnlyTemplateList getSampleTemplateList() {
        TemplateList sampleAc = new TemplateList();
        for (UniqueTemplateItems sampleTemplates: getSampleTemplates()) {
            for (TemplateItem sampleTemplateItem : getSampleTemplateItems()) {
                sampleTemplates.add(sampleTemplateItem);
            }
            sampleAc.addTemplate(sampleTemplates);
        }
        return sampleAc;
    }

    // =============================== Shopping List Sample =============================== //

    public static ShoppingItem[] getSampleShoppingItems() {
        return new ShoppingItem[] {
            new ShoppingItem(new Name("Minced Beef"), new Amount("400g")),
            new ShoppingItem(new Name("FullFat Milk"), new Amount("3L")),
            new ShoppingItem(new Name("White Wine"), new Amount("0.5L")),
            new ShoppingItem(new Name("Minced Chicken"), new Amount("1000g")),
            new ShoppingItem(new Name("Applex"), new Amount("2units"))
        };
    }

    public static ReadOnlyShoppingList getSampleShoppingList() {
        ShoppingList sampleSl = new ShoppingList();
        for (ShoppingItem sampleShoppingItem : getSampleShoppingItems()) {
            sampleSl.addShoppingItem(sampleShoppingItem);
        }
        return sampleSl;
    }

    // ============================= Bought Item Sample List ============================= //
    public static GroceryItem[] getSampleBoughtItems() {
        return new GroceryItem[] {
            new GroceryItem(new Name("Minced beef"), new Amount("300g"), new ExpiryDate("30/09/2019"),
                    getTagSet()),
            new GroceryItem(new Name("Spaghetti"), new Amount("1unit"), new ExpiryDate("20/11/2019"),
                    getTagSet()),
            new GroceryItem(new Name("Apples"), new Amount("6units"), new ExpiryDate("15/10/2019"),
                    getTagSet()),
            new GroceryItem(new Name("Orange juice"), new Amount("500ml"), new ExpiryDate("22/11/2019"),
                    getTagSet()),
            new GroceryItem(new Name("Green tea latte"), new Amount("10units"), new ExpiryDate("30/10/2019"),
                    getTagSet())
        };
    }

    public static ReadOnlyGroceryList getSampleBoughtList() {
        GroceryList sampleAb = new GroceryList();
        for (GroceryItem boughtItem : getSampleBoughtItems()) {
            sampleAb.addGroceryItem(boughtItem);
        }
        return sampleAb;
    }

}
