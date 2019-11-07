//package seedu.guilttrip.logic.parser;
//
//public class EditCategoryCommandParserTest {
//
//    private static final String TAG_EMPTY = " " + PREFIX_TAG;
//
//    private static final String MESSAGE_INVALID_FORMAT =
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExpenseCommand.MESSAGE_USAGE);
//
//    private EditExpenseCommandParser parser = new EditExpenseCommandParser();
//
//    @Test
//    public void parse_missingParts_failure() {
//        // no index specified
//        assertParseFailure(parser, VALID_DESC_FOOD_EXPENSE, MESSAGE_INVALID_FORMAT);
//
//        // no field specified
//        assertParseFailure(parser, "1", EditExpenseCommand.MESSAGE_NOT_EDITED);
//
//        // no index and no field specified
//        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidPreamble_failure() {
//        // negative index
//        assertParseFailure(parser, "-5" + NAME_DESC_FOOD_EXPENSE, MESSAGE_INVALID_FORMAT);
//
//        // zero index
//        assertParseFailure(parser, "0" + NAME_DESC_FOOD_EXPENSE, MESSAGE_INVALID_FORMAT);
//
//        // invalid arguments being parsed as preamble
//        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
//
//        // invalid prefix being parsed as preamble
//        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidValue_failure() {
//        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Description.MESSAGE_CONSTRAINTS); // invalid name
//        assertParseFailure(parser, "1" + INVALID_AMOUNT, Amount.MESSAGE_CONSTRAINTS); // invalid phone
//        assertParseFailure(parser, "1" + INVALID_DATE, Date.MESSAGE_CONSTRAINTS_FOR_ENTRIES); // invalid email
//        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
//
//        // invalid description followed by valid date
//        assertParseFailure(parser, "1" + INVALID_NAME_DESC + AMOUNT_FOOD_EXPENSE,
//                Description.MESSAGE_CONSTRAINTS);
//
//        // valid date followed by invalid tag. The test case for invalid phone followed by valid phone
//        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
//        assertParseFailure(parser, "1" + DATE_FOOD_EXPENSE + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);
//
//        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
//        // parsing it together with a valid tag results in error
//        assertParseFailure(parser, "1" + TAG_DESC_CLOTHING + TAG_DESC_FOOD + TAG_EMPTY,
//                Tag.MESSAGE_CONSTRAINTS);
//        assertParseFailure(parser, "1" + TAG_DESC_CLOTHING + TAG_EMPTY + TAG_DESC_FOOD,
//                Tag.MESSAGE_CONSTRAINTS);
//        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_CLOTHING + TAG_DESC_FOOD,
//                Tag.MESSAGE_CONSTRAINTS);
//
//        // multiple invalid values, but only the first invalid value is captured
//        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_AMOUNT + INVALID_DATE + INVALID_TAG_DESC,
//                Description.MESSAGE_CONSTRAINTS);
//    }
//
//    @Test
//    public void parse_allFieldsSpecified_success() {
//        Index targetIndex = INDEX_SECOND_ENTRY;
//        String userInput = targetIndex.getOneBased() + NAME_DESC_FOOD_EXPENSE + TAG_DESC_WANT + TAG_DESC_FOOD
//                + AMOUNT_FOOD_EXPENSE + DATE_FOOD_EXPENSE + CATEGORY_FOOD_EXPENSE;
//
//        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withDescription(VALID_DESC_FOOD_EXPENSE)
//                .withAmount(VALID_AMOUNT_FOOD_EXPENSE).withDate(VALID_DATE_FOOD_EXPENSE)
//                .withCategory(VALID_CATEGORY_NAME_EXPENSE_FOOD).withTags(VALID_TAG_FOOD, VALID_TAG_CLOTHING_WANT)
//                .build();
//        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_someFieldsSpecified_success() {
//        Index targetIndex = INDEX_FIRST_ENTRY;
//        String userInput = targetIndex.getOneBased() + NAME_DESC_FOOD_EXPENSE + TAG_DESC_WANT + AMOUNT_FOOD_EXPENSE;
//
//        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withDescription(VALID_DESC_FOOD_EXPENSE)
//                .withTags(VALID_TAG_CLOTHING_WANT).withAmount(VALID_AMOUNT_FOOD_EXPENSE).build();
//        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
//
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_oneFieldSpecified_success() {
//        // description
//        Index targetIndex = INDEX_THIRD_ENTRY;
//        String userInput = targetIndex.getOneBased() + NAME_DESC_FOOD_EXPENSE;
//        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withDescription(VALID_DESC_FOOD_EXPENSE)
//                .build();
//        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//        // amount
//        userInput = targetIndex.getOneBased() + AMOUNT_FOOD_EXPENSE;
//        descriptor = new EditExpenseDescriptorBuilder().withAmount(VALID_AMOUNT_FOOD_EXPENSE).build();
//        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//        // date
//        userInput = targetIndex.getOneBased() + DATE_FOOD_EXPENSE;
//        descriptor = new EditExpenseDescriptorBuilder().withDate(VALID_DATE_FOOD_EXPENSE).build();
//        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//        // category
//        userInput = targetIndex.getOneBased() + CATEGORY_NAME_EXPENSE_FOR_ENTRIES;
//        descriptor = new EditExpenseDescriptorBuilder().withCategory(VALID_CATEGORY_FOOD_EXPENSE).build();
//        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//        // tags
//        userInput = targetIndex.getOneBased() + TAG_DESC_CLOTHING;
//        descriptor = new EditExpenseDescriptorBuilder().withTags(VALID_TAG_CLOTHING_CLOTHES).build();
//        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_multipleRepeatedFields_acceptsLast() {
//        Index targetIndex = INDEX_FIRST_ENTRY;
//        String userInput = targetIndex.getOneBased() + NAME_DESC_FOOD_EXPENSE + TAG_DESC_WANT + TAG_DESC_FOOD
//                + AMOUNT_FOOD_EXPENSE + DATE_FOOD_EXPENSE + CATEGORY_FOOD_EXPENSE + TAG_DESC_CLOTHING
//                + NAME_DESC_CLOTHING_EXPENSE + AMOUNT_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE
//                + CATEGORY_CLOTHING_EXPENSE;
//
//        //TODO: WILL ACCEPT TAGS EVEN IF SPREAD OUT
//        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
//                .withDescription(VALID_DESC_CLOTHING_EXPENSE).withAmount(VALID_AMOUNT_CLOTHING_EXPENSE)
//                .withDate(VALID_DATE_CLOTHING_EXPENSE).withCategory(VALID_CATEGORY_CLOTHING_EXPENSE)
//                .withTags(VALID_TAG_CLOTHING_CLOTHES, VALID_TAG_CLOTHING_WANT, VALID_TAG_FOOD)
//                .build();
//
//        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
//
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_invalidValueFollowedByValidValue_failure() {
//        // invalid value followed by valid value
//        Index targetIndex = INDEX_FIRST_ENTRY;
//        String userInput = targetIndex.getOneBased() + INVALID_AMOUNT + NAME_DESC_FOOD_EXPENSE;
//        assertParseFailure(parser, userInput, Amount.MESSAGE_CONSTRAINTS);
//
//        // valid value followed by invalid value
//        userInput = targetIndex.getOneBased() + NAME_DESC_FOOD_EXPENSE + INVALID_AMOUNT;
//        assertParseFailure(parser, userInput, Amount.MESSAGE_CONSTRAINTS);
//
//        //valid amount, valid Description, invalid tags
//        userInput = targetIndex.getOneBased() + AMOUNT_FOOD_EXPENSE + NAME_DESC_FOOD_EXPENSE + INVALID_TAG_DESC;
//        assertParseFailure(parser, userInput, Tag.MESSAGE_CONSTRAINTS);
//    }
//
//    @Test
//    public void parse_resetTags_success() {
//        Index targetIndex = INDEX_THIRD_ENTRY;
//        String userInput = targetIndex.getOneBased() + TAG_EMPTY;
//
//        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withTags().build();
//        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
//
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//}
