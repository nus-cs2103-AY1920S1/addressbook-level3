package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyShoppingList;
import seedu.address.model.ReadOnlyTemplateList;
import seedu.address.model.ReadOnlyWasteList;
import seedu.address.model.ShoppingList;
import seedu.address.model.TemplateList;
import seedu.address.model.WasteList;
import seedu.address.model.food.Amount;
import seedu.address.model.food.ExpiryDate;
import seedu.address.model.food.Food;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.Name;
import seedu.address.model.food.ShoppingItem;
import seedu.address.model.food.TemplateItem;
import seedu.address.model.food.UniqueTemplateItems;
import seedu.address.model.tag.Tag;
import seedu.address.model.waste.WasteMonth;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    // ============================= Grocery Item Sample List ============================= //
    public static Food[] getSampleFoods() {
        return new Food[] {
            new Food(new Name("Alex Yeoh"), new Amount("300g")),
            new Food(new Name("Bernice Yu"), new Amount("300g")),
            new Food(new Name("Charlotte Oliveiro"), new Amount("300g")),
            new Food(new Name("David Li"), new Amount("300g")),
            new Food(new Name("Irfan Ibrahim"), new Amount("300g")),
            new Food(new Name("Roy Balakrishnan"), new Amount("300g"))
        };
    }

    public static GroceryItem[] getSampleGroceryItems() {
        return new GroceryItem[] {
            new GroceryItem(new Name("Alex Yeoh"), new Amount("300g"), new ExpiryDate("10.08.2019"),
                new HashSet<>()),
            new GroceryItem(new Name("Bernice Yu"), new Amount("300g"), new ExpiryDate("10.09.2019"),
                new HashSet<>()),
            new GroceryItem(new Name("Charlotte Oliveiro"), new Amount("300g"), new ExpiryDate("10.09.2019"),
                new HashSet<>()),
            new GroceryItem(new Name("David Li"), new Amount("300g"), new ExpiryDate("10.09.2019"),
                new HashSet<>()),
            new GroceryItem(new Name("Irfan Ibrahim"), new Amount("300g"), new ExpiryDate("10.09.2019"),
                new HashSet<>()),
            new GroceryItem(new Name("Roy Balakrishnan"), new Amount("300g"), new ExpiryDate("10.09.2019"),
                new HashSet<>())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (GroceryItem groceryItem : getSampleGroceryItems()) {
            sampleAb.addPerson(groceryItem);
        }
        return sampleAb;
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
                    new ExpiryDate("12.10.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Apples"), new Amount("2units"),
                    new ExpiryDate("15.10.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("100ml"),
                    new ExpiryDate("18.10.2019"), getTagSet()),

            new GroceryItem(new Name("Potatoes"), new Amount("1500g"),
                    new ExpiryDate("12.09.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Oranges"), new Amount("3units"),
                    new ExpiryDate("15.09.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("200ml"),
                    new ExpiryDate("18.09.2019"), getTagSet()),

            new GroceryItem(new Name("Chicken"), new Amount("400g"),
                new ExpiryDate("12.08.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Pears"), new Amount("3units"),
                    new ExpiryDate("15.08.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("150ml"),
                    new ExpiryDate("18.08.2019"), getTagSet()),

            new GroceryItem(new Name("Salmon"), new Amount("800g"),
                    new ExpiryDate("12.07.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Grapes"), new Amount("10units"),
                    new ExpiryDate("15.07.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("70ml"),
                    new ExpiryDate("18.07.2019"), getTagSet()),

            new GroceryItem(new Name("Potatoes"), new Amount("300g"),
                    new ExpiryDate("12.06.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Lychee"), new Amount("3units"),
                    new ExpiryDate("15.06.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("280ml"),
                    new ExpiryDate("18.06.2019"), getTagSet()),

            new GroceryItem(new Name("Chicken"), new Amount("890g"),
                    new ExpiryDate("12.05.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Lemon"), new Amount("1units"),
                    new ExpiryDate("15.05.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("100ml"),
                    new ExpiryDate("18.05.2019"), getTagSet()),

            new GroceryItem(new Name("Salmon"), new Amount("290g"),
                    new ExpiryDate("12.04.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Kiwi"), new Amount("2units"),
                    new ExpiryDate("15.04.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("0ml"),
                    new ExpiryDate("18.04.2019"), getTagSet()),

            new GroceryItem(new Name("Potatoes"), new Amount("469g"),
                    new ExpiryDate("12.03.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Eggs"), new Amount("6units"),
                    new ExpiryDate("15.03.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("60ml"),
                    new ExpiryDate("18.03.2019"), getTagSet()),

            new GroceryItem(new Name("Chicken"), new Amount("937g"),
                    new ExpiryDate("12.02.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Strawberries"), new Amount("8units"),
                    new ExpiryDate("15.02.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("300ml"),
                    new ExpiryDate("18.02.2019"), getTagSet()),

            new GroceryItem(new Name("Salmon"), new Amount("700g"),
                    new ExpiryDate("12.01.2019"), getTagSet("staples")),
            new GroceryItem(new Name("Pineapples"), new Amount("2units"),
                    new ExpiryDate("15.01.2019"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("200ml"),
                    new ExpiryDate("18.01.2019"), getTagSet()),

            new GroceryItem(new Name("Potatoes"), new Amount("1000g"),
                    new ExpiryDate("12.12.2018"), getTagSet("staples")),
            new GroceryItem(new Name("Plums"), new Amount("4units"),
                    new ExpiryDate("15.12.2018"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("100ml"),
                    new ExpiryDate("18.12.2018"), getTagSet()),

            new GroceryItem(new Name("Chicken"), new Amount("396g"),
                    new ExpiryDate("12.11.2018"), getTagSet("staples")),
            new GroceryItem(new Name("Strawberries"), new Amount("7units"),
                    new ExpiryDate("15.11.2018"), getTagSet("healthy", "fruits")),
            new GroceryItem(new Name("Milk"), new Amount("100ml"),
                    new ExpiryDate("18.11.2018"), getTagSet())

        };
    }

    public static void setSampleWasteItems() {

        WasteMonth[] wasteMonths = getSampleWasteMonths();
        GroceryItem[] wasteItems = getSampleWasteItems();
        // Populate historical data
        for (int i = 0; i < 12; i++) {
            for (int j = i * 3; j < i * 3 + 3; j++) {
                WasteList.addFoodItemToArchive(wasteItems[j], wasteMonths[i]);
            }
        }
    }

    public static ReadOnlyWasteList getSampleWasteList() {
        /*
        WasteList sampleWl = new WasteList();
        GroceryItem[] sampleWasteItems = getSampleWasteItems();
        for (GroceryItem wasteItem : sampleWasteItems) {
            sampleWl.addWasteItem(wasteItem);
        }
        return sampleWl;

         */
        return WasteList.getCurrentWasteList();
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

}
