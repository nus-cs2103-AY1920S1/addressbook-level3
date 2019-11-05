//package seedu.address.testutil;
//
//import seedu.address.model.entry.Category;
//
//public class TypicalCategories {
//
//    public static final Category CATEGORY_FOOD = new CategoryBuilder().withCatType("Expense").withCatName("food")
//                                                    .build();
//    public static final Category CATEGORY_BUSINESS = new CategoryBuilder().withCatType("Income").withCatName("business")
//                                                    .build();
//
//
//    private TypicalCategories() {
//    } // prevents instantiation
//
//    /**
//     * Returns an {@code GuiltTrip} with all the typical persons.
//     */
//    public static GuiltTrip getTypicalGuiltTrip() {
//        GuiltTrip ab = new GuiltTrip(true);
//        for (Entry entry : getTypicalEntries()) {
//            ab.addEntry(entry);
//        }
//        return ab;
//    }
//
//    public static List<Entry> getTypicalEntries() {
//        return new ArrayList<>(Arrays.asList(FOOD_EXPENSE, CLOTHING_EXPENSE));
//    }
//}
