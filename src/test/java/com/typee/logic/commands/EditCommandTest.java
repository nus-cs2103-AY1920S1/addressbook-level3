package com.typee.logic.commands;

//import static com.typee.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    /*
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditCommand.EditEngagementDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new EngagementList(model.getHistoryManager()), new UserPrefs());
        expectedModel.setEngagement(model.getFilteredEngagementList().get(0), editedPerson);
        expectedModel.saveEngagementList();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredEngagementList().size());
        Person lastPerson = model.getFilteredEngagementList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(CommandTestUtil.VALID_NAME_BOB).build();

        EditCommand.EditEngagementDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_BOB)
                .build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new EngagementList(model.getHistoryManager()), new UserPrefs());


        expectedModel.setEngagement(lastPerson, editedPerson);
        expectedModel.saveEngagementList();


        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditCommand.EditEngagementDescriptor());
        Person editedPerson = model.getFilteredEngagementList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new EngagementList(model.getHistoryManager()), new UserPrefs());
        expectedModel.saveEngagementList();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredEngagementList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new EngagementList(model.getHistoryManager()), new UserPrefs());

        expectedModel.setEngagement(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.saveEngagementList();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredEngagementList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditEngagementDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        CommandTestUtil.showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getHistoryManager().getEngagementList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEngagementList().size() + 1);
        EditCommand.EditEngagementDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_BOB)
                .build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


     */
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    /*
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        CommandTestUtil.showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHistoryManager().getEngagementList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, CommandTestUtil.DESC_AMY);

        // same values -> returns true
        EditCommand.EditEngagementDescriptor copyDescriptor = new EditCommand
                .EditEngagementDescriptor(CommandTestUtil.DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, CommandTestUtil.DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, CommandTestUtil.DESC_BOB)));
    }
     */
}
