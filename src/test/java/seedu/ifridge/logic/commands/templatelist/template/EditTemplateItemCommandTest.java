package seedu.ifridge.logic.commands.templatelist.template;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditTemplateItemCommandTest {
    /**
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        TemplateItem editedTemplateItem = new TemplateItemBuilder().build();
        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder(editedTemplateItem).build();
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditTemplateItemCommand.MESSAGE_EDIT_TEMPLATE_ITEM_SUCCESS,
            editedTemplateItem);

        Model expectedModel = new ModelManager(new TemplateList(model.getTemplate()), new UserPrefs());
        expectedModel.setGroceryItem(model.getFilteredTemplateList().get(0), editedTemplateItem);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTemplateItem = Index.fromOneBased(model.getFilteredTemplateList().size());
        TemplateItem lastTemplateItem = model.getFilteredTemplateList().get(indexLastTemplateItem.getZeroBased());

        TemplateItemBuilder templateItemInList = new TemplateItemBuilder(lastTemplateItem);
        TemplateItem editedTemplateItem = templateItemInList.withName(VALID_NAME_BOB)
                .withAmount(VALID_AMOUNT_BOB).build();

        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder().withName(VALID_NAME_BOB)
                .withAmount(VALID_AMOUNT_BOB).build();
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(indexLastTemplateItem, descriptor);

        String expectedMessage = String.format(EditTemplateItemCommand.MESSAGE_EDIT_TEMPLATE_ITEM_SUCCESS,
            editedTemplateItem);

        Model expectedModel = new ModelManager(new TemplateList(model.getTemplate()), new UserPrefs());
        expectedModel.setTemplateItem(lastTemplateItem, editedTemplateItem);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(INDEX_FIRST_PERSON,
            new EditTemplateItemDescriptor());
        TemplateItem editedTemplateItem = model.getFilteredTemplateList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditTemplateItemCommand.MESSAGE_EDIT_TEMPLATE_ITEM_SUCCESS,
            editedTemplateItem);

        Model expectedModel = new ModelManager(new TemplateList(model.getTemplate()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTemplateItemAtIndex(model, INDEX_FIRST_PERSON);

        TemplateItem templateItemsInFilteredList = model.getFilteredTemplateList()
            .get(INDEX_FIRST_PERSON.getZeroBased());
        TemplateItem editedTemplateItem = new TemplateItemBuilder(templateItemsInFilteredList).withName(VALID_NAME_BOB).
                withAmount(VALID_AMOUNT_BOB).build();
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(INDEX_FIRST_PERSON,
                new EditTemplateItemDescriptorBuilder().withName(VALID_NAME_BOB).withAmount(VALID_AMOUNT_BOB).build());

        String expectedMessage = String.format(EditTemplateItemCommand.MESSAGE_EDIT_TEMPLATE_ITEM_SUCCESS,
            editedTemplateItem);

        Model expectedModel = new ModelManager(new TemplateList(model.getTemplate()), new UserPrefs());
        expectedModel.setTemplateItem(model.getFilteredTemplateList().get(0), editedTemplateItem);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTemplateItemUnfilteredList_failure() {
        TemplateItem firstTemplateItem = model.getFilteredTemplateList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder(firstTemplateItem).build();
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditTemplateItemCommand.MESSAGE_DUPLICATE_TEMPLATE_ITEM);
    }

    @Test
    public void execute_duplicateTemplateItemFilteredList_failure() {
        showTemplateItemAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        TemplateItem templateItemInList = model.getTemplate().getTemplate().get(INDEX_SECOND_PERSON.getZeroBased());
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(INDEX_FIRST_PERSON,
                new EditTemplateItemDescriptorBuilder(templateItemInList).build());

        assertCommandFailure(editCommand, model, EditTemplateItemCommand.MESSAGE_DUPLICATE_TEMPLATE_ITEM);
    }
    */

    /**
    @Test
    public void execute_invalidTemplateItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTemplateList().size() + 1);
        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder().withName(VALID_NAME_BOB).
                withAmount(VALID_AMOUNT_BOB).build();
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TEMPLATE_ITEM_DISPLAYED_INDEX);
    }
    */

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of template list.
     */
    /**
    @Test
    public void execute_invalidTemplateItemIndexFilteredList_failure() {
        showTemplateItemAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTemplate().getTemplate().size());

        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(outOfBoundIndex,
                new EditTemplateItemDescriptorBuilder().withName(VALID_NAME_BOB).withAmount(VALID_AMOUNT_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TEMPLATE_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // Need to set a new DESC_AMY for CommandTestUtil with a TemplateItemDescriptor
        final EditTemplateItemCommand standardCommand = new EditTemplateItemCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditTemplateItemDescriptor copyDescriptor = new EditTemplateItemDescriptor(DESC_AMY);
        EditTemplateItemCommand commandWithSameValues = new EditTemplateItemCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearTemplateItemCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTemplateItemCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTemplateItemCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }
    */
}
