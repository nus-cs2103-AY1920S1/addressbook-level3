package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.testutil.CardBuilder;

public class UniqueCardListTest {

    private final UniqueCardList uniqueCardList = new UniqueCardList();

    @Test
    public void contains_nullCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCardList.contains(null));
    }

    @Test
    public void contains_cardNotInList_returnsFalse() {
        assertFalse(uniqueCardList.contains(ABRA));
    }

    @Test
    public void contains_cardInList_returnsTrue() {
        uniqueCardList.add(ABRA);
        assertTrue(uniqueCardList.contains(ABRA));
    }

    @Test
    public void contains_cardWithSameNameInList_returnsTrue() {
        uniqueCardList.add(ABRA);
        Card editedAbra = new CardBuilder(ABRA).withMeaning(VALID_MEANING_BUTTERFREE)
                .withTags(VALID_TAG_PSYCHIC)
                .build();
        assertTrue(uniqueCardList.contains(editedAbra));
    }

    @Test
    public void add_nullCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCardList.add(null));
    }

    @Test
    public void add_duplicateCard_throwsDuplicateCardException() {
        uniqueCardList.add(ABRA);
        assertThrows(DuplicateCardException.class, () -> uniqueCardList.add(ABRA));
    }

    @Test
    public void setCard_nullTargetCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCardList.setCard(null, ABRA));
    }

    @Test
    public void setCard_nullEditedCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCardList.setCard(ABRA, null));
    }

    @Test
    public void setCard_targetCardNotInList_throwsCardNotFoundException() {
        assertThrows(CardNotFoundException.class, () -> uniqueCardList.setCard(ABRA, ABRA));
    }

    @Test
    public void setCard_editedCardIsSameName_success() {
        uniqueCardList.add(ABRA);
        uniqueCardList.setCard(ABRA, ABRA);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(ABRA);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCard_editedCardHasSameName_success() {
        uniqueCardList.add(ABRA);
        Card editedAbra = new CardBuilder(ABRA).withMeaning(VALID_MEANING_BUTTERFREE).withTags(VALID_TAG_BUG)
                .build();
        uniqueCardList.setCard(ABRA, editedAbra);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(editedAbra);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCard_editedCardHasDifferentName_success() {
        uniqueCardList.add(ABRA);
        uniqueCardList.setCard(ABRA, BUTTERFREE);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(BUTTERFREE);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCard_editedCardHasNonUniqueName_throwsDuplicateCardException() {
        uniqueCardList.add(ABRA);
        uniqueCardList.add(BUTTERFREE);
        assertThrows(DuplicateCardException.class, () -> uniqueCardList.setCard(ABRA, BUTTERFREE));
    }

    @Test
    public void remove_nullCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCardList.remove(null));
    }

    @Test
    public void remove_cardDoesNotExist_throwsCardNotFoundException() {
        assertThrows(CardNotFoundException.class, () -> uniqueCardList.remove(ABRA));
    }

    @Test
    public void remove_existingCard_removesCard() {
        uniqueCardList.add(ABRA);
        uniqueCardList.remove(ABRA);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCards_nullUniqueCardList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCardList.setCards((UniqueCardList) null));
    }

    @Test
    public void setCards_uniqueCardList_replacesOwnListWithProvidedUniqueCardList() {
        uniqueCardList.add(ABRA);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(BUTTERFREE);
        uniqueCardList.setCards(expectedUniqueCardList);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCards_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCardList.setCards((List<Card>) null));
    }

    @Test
    public void setCards_list_replacesOwnListWithProvidedList() {
        uniqueCardList.add(ABRA);
        List<Card> cardList = Collections.singletonList(BUTTERFREE);
        uniqueCardList.setCards(cardList);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(BUTTERFREE);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCards_listWithDuplicateCards_throwsDuplicateCardException() {
        List<Card> listWithDuplicateCards = Arrays.asList(ABRA, ABRA);
        assertThrows(DuplicateCardException.class, () -> uniqueCardList.setCards(listWithDuplicateCards));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCardList.asUnmodifiableObservableList().remove(0));
    }
}
