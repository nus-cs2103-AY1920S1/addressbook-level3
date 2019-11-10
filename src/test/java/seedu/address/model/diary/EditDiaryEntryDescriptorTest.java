package seedu.address.model.diary;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1_COPY;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_1_TO_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_2;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_2_TO_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_3;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_3_TO_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_4;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_4_TO_STRING;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_5;
import static seedu.address.model.diary.DiaryTestUtil.TEST_ENTRY_5_TO_STRING;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

/**
 * Mix of unit and integration tests of {@link EditDiaryEntryDescriptor} using {@link DiaryEntry}.
 */
class EditDiaryEntryDescriptorTest {

    @Test
    void buildTrip_testDiaryEntryNoEdits_diaryEntriesEquals() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        assertEquals(editDescriptor.buildDiaryEntry(), TEST_ENTRY_1);
    }

    @Test
    void getDiaryText_testDiaryEntryNoEdits_diaryTextEquals() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        assertEquals(editDescriptor.getDiaryText(), "");
    }

    @Test
    void setDiaryText_randomText_textPlusNewLineEquals() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        String textToSet = DiaryTestUtil.generateRandomText() + "a";
        editDescriptor.setDiaryText(textToSet);

        assertEquals(editDescriptor.getDiaryText(), textToSet + "\n");
    }

    @Test
    void setDiaryText_randomTextHasNewLine_doesNotAddNewLine() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        String textToSet = DiaryTestUtil.generateRandomText() + '\n';
        editDescriptor.setDiaryText(textToSet);

        assertEquals(editDescriptor.getDiaryText(), textToSet);
    }

    @Test
    void addNewTextLine_testDiaryEntry_textAppended() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        String textToAppend = DiaryTestUtil.generateRandomText();
        editDescriptor.addNewTextLine(textToAppend);

        assertEquals(editDescriptor.getDiaryText(), textToAppend + "\n");
    }

    @Test
    void insertTextLine_validInBetweenLineNumber_lineInserted() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        for (int i = 0; i < 5; i++) {
            editDescriptor.addNewTextLine(DiaryTestUtil.generateRandomText());
        }
        String textToInsert = DiaryTestUtil.generateRandomText();
        Index validInBetweenIndex = Index.fromOneBased(2);
        editDescriptor.insertTextLine(textToInsert, validInBetweenIndex);

        String[] newTextLines = editDescriptor.getDiaryText().split("\n", -1);
        assertEquals(textToInsert, newTextLines[validInBetweenIndex.getZeroBased()]);
    }

    @Test
    void insertTextLine_positiveOutOfBoundsNumber_emptyNewLinesAndLineInserted() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        for (int i = 0; i < 5; i++) {
            editDescriptor.addNewTextLine(DiaryTestUtil.generateRandomText());
        }
        String textToInsert = DiaryTestUtil.generateRandomText();
        Index positiveOutOfBoundsIndex = Index.fromZeroBased(10);
        editDescriptor.insertTextLine(textToInsert, positiveOutOfBoundsIndex);

        String[] newTextLines = editDescriptor.getDiaryText().split("\n", -1);
        for (int i = 5; i < 10; i++) {
            assertTrue(newTextLines[i].isEmpty());
        }

        assertEquals(textToInsert, newTextLines[positiveOutOfBoundsIndex.getZeroBased()]);
    }

    @Test
    void editTextLine_outOfBoundsNumber_returnsFalse() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_4);
        for (int i = 0; i < 5; i++) {
            editDescriptor.addNewTextLine(DiaryTestUtil.generateRandomText());
        }

        Index outOfBoundsIndex = Index.fromOneBased(10);
        assertFalse(editDescriptor.editTextLine("", outOfBoundsIndex));
    }

    @Test
    void editTextLine_validInBetweenLineNumber_returnsTrueAndLineEdited() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_3);
        for (int i = 0; i < 5; i++) {
            editDescriptor.addNewTextLine(DiaryTestUtil.generateRandomText());
        }
        String textToEdit = DiaryTestUtil.generateRandomText();
        Index validInBetweenIndex = Index.fromOneBased(2);
        assertTrue(editDescriptor.editTextLine(textToEdit, validInBetweenIndex));

        String[] newTextLines = editDescriptor.getDiaryText().split("\n", -1);
        assertEquals(textToEdit, newTextLines[validInBetweenIndex.getZeroBased()]);
    }

    @Test
    void deleteParagraph_outOfBoundsNumber_returnsFalse() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_2);
        for (int i = 0; i < 5; i++) {
            editDescriptor.addNewTextLine(DiaryTestUtil.generateRandomText());
        }

        Index outOfBoundsIndex = Index.fromOneBased(10);
        assertFalse(editDescriptor.deleteTextParagraph(outOfBoundsIndex));
    }

    @Test
    void deleteParagraph_validInBetweenLineNumber_returnsTrueAndLineDeleted() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_5);
        for (int i = 0; i < 5; i++) {
            editDescriptor.addNewTextLine(DiaryTestUtil.generateRandomText());
        }
        int lengthBeforeDelete = editDescriptor.getDiaryText().split("\n", -1).length;

        Index validInBetweenIndex = Index.fromOneBased(2);
        assertTrue(editDescriptor.deleteTextParagraph(validInBetweenIndex));

        String[] newTextLines = editDescriptor.getDiaryText().split("\n", -1);
        assertEquals(lengthBeforeDelete - 1, newTextLines.length);
    }

    @Test
    void toString_diaryTextBeyondDisplayLength_textTruncated() {
        EditDiaryEntryDescriptor editDescriptor5 = new EditDiaryEntryDescriptor(TEST_ENTRY_5);
        assertEquals(TEST_ENTRY_5_TO_STRING, editDescriptor5.toString());
    }

    @Test
    void toString_descriptorFromTestDiaryEntry_equalsDiaryEntryToString() {
        EditDiaryEntryDescriptor editDescriptor1 = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        EditDiaryEntryDescriptor editDescriptor2 = new EditDiaryEntryDescriptor(TEST_ENTRY_2);
        EditDiaryEntryDescriptor editDescriptor3 = new EditDiaryEntryDescriptor(TEST_ENTRY_3);
        EditDiaryEntryDescriptor editDescriptor4 = new EditDiaryEntryDescriptor(TEST_ENTRY_4);

        assertAll("Diary text below display length", () ->
                assertEquals(TEST_ENTRY_1_TO_STRING, editDescriptor1.toString()), () ->
                assertEquals(TEST_ENTRY_2_TO_STRING, editDescriptor2.toString()), () ->
                assertEquals(TEST_ENTRY_3_TO_STRING, editDescriptor3.toString()), () ->
                assertEquals(TEST_ENTRY_4_TO_STRING, editDescriptor4.toString()));
    }

    @Test
    void equals_sameInstance_returnsTrue() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        assertEquals(editDescriptor, editDescriptor);
    }

    @Test
    void equals_notInstanceOfEditDiaryEntryDescriptor_returnsFalse() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        assertNotEquals(editDescriptor, new Object());
    }

    @Test
    void equals_unequalFieldsNoEdits_returnsFalse() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        EditDiaryEntryDescriptor editDescriptor2 = new EditDiaryEntryDescriptor(TEST_ENTRY_2);

        assertNotEquals(editDescriptor, editDescriptor2);
    }

    @Test
    void equals_unequalFieldsSomeEdits_returnsFalse() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        EditDiaryEntryDescriptor editDescriptor2 = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        editDescriptor2.addNewTextLine(DiaryTestUtil.generateRandomText());

        assertNotEquals(editDescriptor, editDescriptor2);
    }

    @Test
    void equals_equalFieldsNoEdits_returnsTrue() {
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        EditDiaryEntryDescriptor editDescriptor2 = new EditDiaryEntryDescriptor(TEST_ENTRY_1_COPY);

        assertEquals(editDescriptor, editDescriptor2);
    }

    @Test
    void equals_equalFieldsSomeEdits_returnsTrue() {
        String textToAppend = DiaryTestUtil.generateRandomText();
        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(TEST_ENTRY_1);
        EditDiaryEntryDescriptor editDescriptor2 = new EditDiaryEntryDescriptor(TEST_ENTRY_1_COPY);
        editDescriptor.addNewTextLine(textToAppend);
        editDescriptor2.addNewTextLine(textToAppend);

        assertEquals(editDescriptor, editDescriptor2);
    }
}
