package seedu.address.logic.commands.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.NoteFragment;
import seedu.address.model.note.Title;

public class NoteFeatureUtilTest {
    private static final String VALID_TITLE = "Valid title";
    private static final String VALID_CONTENT = "Valid content";
    private static final String INVALID_NOTE_FRAGMENT = "Invalid note fragment";
    private static final String VALID_NOTE_1 = "Valid /* TAG/test1 TAG/test2 C/note */ fragment";
    private static final String VALID_NOTE_1_FRAGMENT_1 = "TAG/test1 TAG/test2 C/note";
    private static final String VALID_NOTE_2 = "/* C/Valid TAG/test1 */ /* TAG/test2 TAG/test3 "
            + "C/note */ /* " + "TAG/test4 C/fragment */";
    private static final String VALID_NOTE_2_FRAGMENT_1 = " C/Valid TAG/test1";
    private static final String VALID_NOTE_2_FRAGMENT_2 = " TAG/test2 TAG/test3 C/note";
    private static final String VALID_NOTE_2_FRAGMENT_3 = " TAG/test4 C/fragment";
    private static final String EXPECTED_NOTE_2_FRAGMENT_1 = "C/Valid TAG/test1";
    private static final String EXPECTED_NOTE_2_FRAGMENT_2 = "TAG/test2 TAG/test3 C/note";
    private static final String EXPECTED_NOTE_2_FRAGMENT_3 = "TAG/test4 C/fragment";

    @Test
    public void parseNoteFragmentMatches_stringWithOneMatch_returnsListWithOneNoteFragment() throws Exception {
        List<String> expectedList = List.of(VALID_NOTE_1_FRAGMENT_1);
        assertEquals(expectedList, NoteFeatureUtil.parseNoteFragmentMatches(VALID_NOTE_1));
    }

    @Test
    public void parseNoteFragmentMatches_stringWithMultipleMatches_returnsListWithMultipleStrings() {
        List<String> expectedList = List.of(EXPECTED_NOTE_2_FRAGMENT_1, EXPECTED_NOTE_2_FRAGMENT_2,
                EXPECTED_NOTE_2_FRAGMENT_3);
        assertEquals(expectedList, NoteFeatureUtil.parseNoteFragmentMatches(VALID_NOTE_2));
    }

    @Test
    public void parseNoteFragmentMatches_stringWithNoMatches_returnsEmptyList() {
        assertEquals(Collections.emptyList(), NoteFeatureUtil.parseNoteFragmentMatches(INVALID_NOTE_FRAGMENT));
    }

    @Test
    public void parseNoteFragmentsFromString_listWithMultipleStrings_returnsListWithMultipleNoteFragments()
            throws Exception {

        NoteFragment frag1 = new NoteFragment(new Title(VALID_TITLE),
                NoteFeatureUtil.parseContentFromNoteFragment(VALID_NOTE_2_FRAGMENT_1),
                NoteFeatureUtil.parseTagsFromNoteFragment(VALID_NOTE_2_FRAGMENT_1));
        NoteFragment frag2 = new NoteFragment(new Title(VALID_TITLE),
                NoteFeatureUtil.parseContentFromNoteFragment(VALID_NOTE_2_FRAGMENT_2),
                NoteFeatureUtil.parseTagsFromNoteFragment(VALID_NOTE_2_FRAGMENT_2));
        NoteFragment frag3 = new NoteFragment(new Title(VALID_TITLE),
                NoteFeatureUtil.parseContentFromNoteFragment(VALID_NOTE_2_FRAGMENT_3),
                NoteFeatureUtil.parseTagsFromNoteFragment(VALID_NOTE_2_FRAGMENT_3));

        List<NoteFragment> expectedList = List.of(frag1, frag2, frag3);

        assertEquals(expectedList, NoteFeatureUtil.parseNoteFragmentsFromString(new Title(VALID_TITLE),
                List.of(VALID_NOTE_2_FRAGMENT_1, VALID_NOTE_2_FRAGMENT_2, VALID_NOTE_2_FRAGMENT_3)));
    }

    @Test
    public void parseNoteFragmentsFromString_emptyList_returnsEmptyList() throws Exception {
        List<NoteFragment> expectedList = Collections.emptyList();
        assertEquals(expectedList, NoteFeatureUtil.parseNoteFragmentsFromString(new Title(VALID_TITLE),
                Collections.emptyList()));
    }

    @Test
    public void parseNoteFragmentsFromString_listWithInvalidString_throwsParseException() {
        List<String> actualList = List.of(INVALID_NOTE_FRAGMENT);
        assertThrows(ParseException.class, () -> NoteFeatureUtil
                .parseNoteFragmentsFromString(new Title(VALID_TITLE), actualList));
    }
}
