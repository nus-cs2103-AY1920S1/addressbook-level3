package unrealunity.visit.commons.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import unrealunity.visit.model.person.Address;
import unrealunity.visit.model.person.Email;
import unrealunity.visit.model.person.Name;
import unrealunity.visit.model.person.Phone;
import unrealunity.visit.model.person.VisitList;
import unrealunity.visit.model.person.VisitReport;
import unrealunity.visit.model.tag.Tag;
import unrealunity.visit.testutil.Assert;
import unrealunity.visit.testutil.TypicalVisits;

/**
 * Contains unit tests for ProfileUnitTest.
 */
public class ProfileUtilTest {

    /*
     * Test for various stringify methods for the various Person attributes
     * Invalid equivalence partition for null, and valid partitions for valid attributes
     * (and empty if valid) are tested.
     */

    //---------------- Tests for stringifyName -----------------------------------------------

    @Test
    public void stringifyName_nullName_throwsNullPointerException() {
        Name name = null;
        Assert.assertThrows(NullPointerException.class, "Name cannot be null.", ()
            -> ProfileUtil.stringifyName(name));
    }

    @Test
    public void stringifyName_validInput_correctResult() {
        Name name = new Name("Test Name");
        Assertions.assertEquals("Test Name", ProfileUtil.stringifyName(name));
    }


    //---------------- Tests for stringifyTags -----------------------------------------------

    @Test
    public void stringifyTags_nullTagSet_throwsNullPointerException() {
        Set<Tag> tagSet = null;
        Assert.assertThrows(NullPointerException.class, "Set<Tag> cannot be null.", ()
            -> ProfileUtil.stringifyTags(tagSet));
    }

    @Test
    public void stringifyTags_validEmptyTagSet_correctResult() {
        Set<Tag> tagSet = new HashSet<>();
        Assertions.assertEquals("-", ProfileUtil.stringifyTags(tagSet));
    }

    @Test
    public void stringifyTags_validFilledTagSet_correctResult() {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("flu"));
        tagSet.add(new Tag("suspectedH1N1"));
        Assertions.assertEquals("flu; suspectedH1N1; ", ProfileUtil.stringifyTags(tagSet));
    }

    //---------------- Tests for stringifyPhone ----------------------------------------------

    @Test
    public void stringifyPhone_nullPhone_throwsNullPointerException() {
        Phone phone = null;
        Assert.assertThrows(NullPointerException.class, "Phone cannot be null.", ()
            -> ProfileUtil.stringifyPhone(phone));
    }

    @Test
    public void stringifyPhone_validInput_correctResult() {
        Phone phone = new Phone("12345678");
        Assertions.assertEquals("12345678", ProfileUtil.stringifyPhone(phone));
    }

    //---------------- Tests for stringifyEmail ----------------------------------------------

    @Test
    public void stringifyEmail_nullEmail_throwsNullPointerException() {
        Email email = null;
        Assert.assertThrows(NullPointerException.class, "Email cannot be null.", ()
            -> ProfileUtil.stringifyEmail(email));
    }

    @Test
    public void stringifyEmail_validInput_correctResult() {
        Email email = new Email("test@gmail.com");
        Assertions.assertEquals("test@gmail.com", ProfileUtil.stringifyEmail(email));
    }

    //---------------- Tests for stringifyAddress --------------------------------------------

    @Test
    public void stringifyAddress_nullAddress_throwsNullPointerException() {
        Address address = null;
        Assert.assertThrows(NullPointerException.class, "Address cannot be null.", ()
            -> ProfileUtil.stringifyAddress(address));
    }

    @Test
    public void stringifyAddress_validInput_correctResult() {
        Address address = new Address("Address");
        Assertions.assertEquals("Address", ProfileUtil.stringifyAddress(address));
    }

    //---------------- Tests for stringifyDate -----------------------------------------------

    @Test
    public void stringifyDate_nullDate_throwsNullPointerException() {
        LocalDateTime date = null;
        Assert.assertThrows(NullPointerException.class, "LocalDateTime time cannot be null.", ()
            -> ProfileUtil.stringifyDate(date));
    }

    @Test
    public void stringifyDate_validInput_correctResult() {
        LocalDateTime date = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        Assertions.assertEquals("01-01-2019 00-00-00", ProfileUtil.stringifyDate(date));
    }

    //---------------- Tests for stringifyVisit ----------------------------------------------

    @Test
    public void stringifyVisit_nullVisitList_correctResult() {
        VisitList visitList = null;
        Assertions.assertEquals("-", ProfileUtil.stringifyVisit(visitList));
    }

    @Test
    public void stringifyVisit_validEmptyVisitList_correctResult() {
        VisitList visitList = new VisitList(new ArrayList<>());
        Assertions.assertEquals("-", ProfileUtil.stringifyVisit(visitList));
    }

    @Test
    public void stringifyVisit_validSingleVisitReportVisitList_correctResult() {
        VisitList visitList = TypicalVisits.getShortTypicalVisitList("Test");

        StringBuilder expectedOutput = new StringBuilder();
        for (VisitReport report : visitList.getRecords()) {
            expectedOutput.append(ProfileUtil.BREAKLINE);
            expectedOutput.append(ProfileUtil.stringifyVisitReport(report));
            expectedOutput.append("\n");
        }

        Assertions.assertEquals(expectedOutput.toString(), ProfileUtil.stringifyVisit(visitList));
    }

    @Test
    public void stringifyVisit_validMultipleVisitReportVisitList_correctResult() {
        VisitList visitList = TypicalVisits.getLongTypicalVisitList("Test");

        StringBuilder expectedOutput = new StringBuilder();
        for (VisitReport report : visitList.getRecords()) {
            expectedOutput.append(ProfileUtil.BREAKLINE);
            expectedOutput.append(ProfileUtil.stringifyVisitReport(report));
            expectedOutput.append("\n");
        }

        Assertions.assertEquals(expectedOutput.toString(), ProfileUtil.stringifyVisit(visitList));
    }

    //---------------- Tests for stringifyVisitReport ----------------------------------------

    @Test
    public void stringifyVisitReport_nullVisitReport_correctResult() {
        VisitReport visit = null;
        Assert.assertThrows(NullPointerException.class, "VisitReport cannot be null.", ()
            -> ProfileUtil.stringifyVisitReport(visit));
    }

    @Test
    public void stringifyVisitReport_validVisitReport_correctResult() {
        VisitReport visit = TypicalVisits.REPORT_1;

        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append("[ Report on the " + visit.date + " ]\n\n");
        expectedOutput.append(ProfileUtil.paragraph(ProfileUtil.HEADER_DIAG, visit.getDiagnosis()));
        expectedOutput.append(ProfileUtil.paragraph(ProfileUtil.HEADER_MED, visit.getMedication()));
        expectedOutput.append(ProfileUtil.paragraph(ProfileUtil.HEADER_REMARK, visit.getRemarks()));

        Assertions.assertEquals(expectedOutput.toString(), ProfileUtil.stringifyVisitReport(visit));
    }

}
