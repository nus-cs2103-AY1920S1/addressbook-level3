package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.SubjectName;

class AlfredParserUtilTest {

    @Test
    void parseIndex_correctIndexFormat_noExceptionThrown() throws ParseException {
        Id id1 = new Id(PrefixType.P, 2);
        Id id2 = new Id(PrefixType.M, 9);
        Id id3 = new Id(PrefixType.T, 24);
        assertEquals(id1, AlfredParserUtil.parseIndex("P-2", PrefixType.P));
        assertEquals(id2, AlfredParserUtil.parseIndex("M-9", PrefixType.M));
        assertEquals(id3, AlfredParserUtil.parseIndex("T-24", PrefixType.T));
    }

    @Test
    void parseIndex_incorrectIndexFormat_parseExceptionThrown() throws ParseException {
        Id id = new Id(PrefixType.P, 2);
        try {
            assertEquals(id, AlfredParserUtil.parseIndex("P2", PrefixType.P));
        } catch (ParseException pe) {
            assertEquals(Messages.MESSAGE_INVALID_INDEX, pe.getMessage());
        }
    }

    @Test
    void parseSubjectName_validSubjectName_appropriateEnumReturned() throws ParseException {
        assertEquals(SubjectName.SOCIAL, AlfredParserUtil.parseSubject("Social"));
        assertEquals(SubjectName.ENVIRONMENTAL, AlfredParserUtil.parseSubject("Environmental"));
        assertEquals(SubjectName.HEALTH, AlfredParserUtil.parseSubject("Health"));
        assertEquals(SubjectName.EDUCATION, AlfredParserUtil.parseSubject("Education"));
    }
}
