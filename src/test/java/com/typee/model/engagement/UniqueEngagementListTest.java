package com.typee.model.engagement;

import static com.typee.testutil.TypicalEngagements.GOOGLE_INTERVIEW;
import static com.typee.testutil.TypicalEngagements.TEAM_MEETING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.typee.model.engagement.exceptions.DuplicateEngagementException;
import com.typee.model.engagement.exceptions.EngagementNotFoundException;
import com.typee.testutil.EngagementBuilder;

class UniqueEngagementListTest {

    private static final String GOOGLE = "Googs";
    private final UniqueEngagementList uniqueEngagementList = new UniqueEngagementList();

    /**
     * Builds an {@code Interview} that matches the interview in {@code TypicalEngagements}.
     *
     * @return google interview.
     */
    private Engagement buildGoogleInterview() {
        EngagementBuilder engagementBuilder = new EngagementBuilder();
        engagementBuilder.withDescription(GOOGLE);
        engagementBuilder.withStartAndEndTime(
                LocalDateTime.of(2019, Month.NOVEMBER, 20, 12, 0),
                LocalDateTime.of(2019, Month.NOVEMBER, 20, 13, 0));
        return engagementBuilder.buildAsInterview();
    }

    @Test
    public void contains_nullEngagement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEngagementList.contains(null));
    }

    @Test
    public void contains_engagementNotInList_returnsFalse() {
        assertFalse(uniqueEngagementList.contains(GOOGLE_INTERVIEW));
    }

    @Test
    public void contains_engagementInList_returnsTrue() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        assertTrue(uniqueEngagementList.contains(GOOGLE_INTERVIEW));
    }

    @Test
    public void contains_engagementWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        Engagement editedEngagement = buildGoogleInterview();
        assertTrue(uniqueEngagementList.contains(editedEngagement));
    }

    @Test
    public void add_nullEngagement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEngagementList.add(null));
    }

    @Test
    public void add_duplicateEngagement_throwsDuplicateEngagementException() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        assertThrows(DuplicateEngagementException.class, () -> uniqueEngagementList.add(GOOGLE_INTERVIEW));
    }

    @Test
    public void setEngagement_nullTargetEngagement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEngagementList.setEngagement(
                null, GOOGLE_INTERVIEW));
    }

    @Test
    public void setEngagement_nullEditedEngagement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEngagementList.setEngagement(
                GOOGLE_INTERVIEW, null));
    }

    @Test
    public void setEngagement_targetEngagementNotInList_throwsEngagementNotFoundException() {
        assertThrows(EngagementNotFoundException.class, () -> uniqueEngagementList.setEngagement(
                GOOGLE_INTERVIEW, GOOGLE_INTERVIEW));
    }

    @Test
    public void setEngagement_editedEngagementIsSameEngagement_success() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        uniqueEngagementList.setEngagement(GOOGLE_INTERVIEW, GOOGLE_INTERVIEW);
        UniqueEngagementList expectedUniqueEngagementList = new UniqueEngagementList();
        expectedUniqueEngagementList.add(GOOGLE_INTERVIEW);
        assertEquals(expectedUniqueEngagementList, uniqueEngagementList);
    }

    @Test
    public void setEngagement_editedEngagementHasSameIdentity_success() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        Engagement newEngagement = buildGoogleInterview();
        uniqueEngagementList.setEngagement(GOOGLE_INTERVIEW, newEngagement);
        UniqueEngagementList expectedUniqueEngagementList = new UniqueEngagementList();
        expectedUniqueEngagementList.add(newEngagement);
        assertEquals(expectedUniqueEngagementList, uniqueEngagementList);
    }

    @Test
    public void setEngagement_editedEngagementHasDifferentIdentity_success() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        uniqueEngagementList.setEngagement(GOOGLE_INTERVIEW, TEAM_MEETING);
        UniqueEngagementList expectedUniqueEngagementList = new UniqueEngagementList();
        expectedUniqueEngagementList.add(TEAM_MEETING);
        assertEquals(expectedUniqueEngagementList, uniqueEngagementList);
    }

    @Test
    public void setEngagement_editedEngagementHasNonUniqueIdentity_throwsDuplicateEngagementException() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        uniqueEngagementList.add(TEAM_MEETING);
        assertThrows(DuplicateEngagementException.class, () -> uniqueEngagementList.setEngagement(
                GOOGLE_INTERVIEW, TEAM_MEETING));
    }

    @Test
    public void remove_nullEngagement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEngagementList.remove(null));
    }

    @Test
    public void remove_engagementDoesNotExist_throwsEngagementNotFoundException() {
        assertThrows(EngagementNotFoundException.class, () -> uniqueEngagementList.remove(GOOGLE_INTERVIEW));
    }

    @Test
    public void remove_existingEngagement_removesEngagement() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        uniqueEngagementList.remove(GOOGLE_INTERVIEW);
        UniqueEngagementList uniqueEngagementList = new UniqueEngagementList();
        assertEquals(uniqueEngagementList, this.uniqueEngagementList);
    }

    @Test
    public void setEngagements_nullUniqueEngagementList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEngagementList.setEngagements(
                (UniqueEngagementList) null));
    }

    @Test
    public void setEngagements_uniqueEngagementList_replacesOwnListWithProvidedUniqueEngagementList() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        UniqueEngagementList expectedUniqueEngagementList = new UniqueEngagementList();
        expectedUniqueEngagementList.add(TEAM_MEETING);
        uniqueEngagementList.setEngagements(expectedUniqueEngagementList);
        assertEquals(expectedUniqueEngagementList, uniqueEngagementList);
    }

    @Test
    public void setEngagements_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEngagementList.setEngagements((List<Engagement>) null));
    }

    @Test
    public void setEngagements_list_replacesOwnListWithProvidedList() {
        uniqueEngagementList.add(GOOGLE_INTERVIEW);
        List<Engagement> engagementList = Collections.singletonList(TEAM_MEETING);
        uniqueEngagementList.setEngagements(engagementList);
        UniqueEngagementList expectedUniqueEngagementList = new UniqueEngagementList();
        expectedUniqueEngagementList.add(TEAM_MEETING);
        assertEquals(expectedUniqueEngagementList, uniqueEngagementList);
    }

    @Test
    public void setEngagements_listWithDuplicateEngagements_throwsDuplicateEngagementException() {
        List<Engagement> listWithDuplicateEngagements = Arrays.asList(GOOGLE_INTERVIEW, GOOGLE_INTERVIEW);
        assertThrows(DuplicateEngagementException.class, () -> uniqueEngagementList.setEngagements(
                listWithDuplicateEngagements));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueEngagementList.asUnmodifiableObservableList()
                .remove(0));
    }

}
