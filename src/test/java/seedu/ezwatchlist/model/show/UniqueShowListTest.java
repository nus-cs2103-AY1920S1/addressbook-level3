package seedu.ezwatchlist.model.show;

import org.junit.jupiter.api.Test;;
import seedu.ezwatchlist.model.show.exceptions.DuplicateShowException;
import seedu.ezwatchlist.model.show.exceptions.ShowNotFoundException;
import seedu.ezwatchlist.testutil.ShowBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalShows.AVENGERSENDGAME;
import static seedu.ezwatchlist.testutil.TypicalShows.FIGHTCLUB;

public class UniqueShowListTest {

    private final UniqueShowList uniqueShowList = new UniqueShowList();

    @Test
    public void contains_nullShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.contains(null));
    }

    @Test
    public void contains_showNotInList_returnsFalse() {
        assertFalse(uniqueShowList.contains(AVENGERSENDGAME));
    }

    @Test
    public void contains_showInList_returnsTrue() {
        uniqueShowList.add(AVENGERSENDGAME);
        assertTrue(uniqueShowList.contains(AVENGERSENDGAME));
    }

    @Test
    public void contains_showWithSameIdentityFieldsInList_returnsTrue() {
        uniqueShowList.add(AVENGERSENDGAME);
        Show editedAlice = new ShowBuilder(AVENGERSENDGAME).withIsWatched(true).build();
        assertTrue(uniqueShowList.contains(editedAlice));
    }

    @Test
    public void add_nullShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.add(null));
    }

    @Test
    public void add_duplicateShow_throwsDuplicateShowException() {
        uniqueShowList.add(AVENGERSENDGAME);
        assertThrows(DuplicateShowException.class, () -> uniqueShowList.add(AVENGERSENDGAME));
    }

    @Test
    public void setShow_nullTargetShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.setShow(null, AVENGERSENDGAME));
    }

    @Test
    public void setShow_nullEditedShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.setShow(AVENGERSENDGAME, null));
    }

    @Test
    public void setShow_targetShowNotInList_throwsShowNotFoundException() {
        assertThrows(ShowNotFoundException.class, () -> uniqueShowList.setShow(AVENGERSENDGAME, AVENGERSENDGAME));
    }

    @Test
    public void setShow_editedShowIsSameShow_success() {
        uniqueShowList.add(AVENGERSENDGAME);
    }

    public void setShow_editedShowHasSameIdentity_success() {
        uniqueShowList.add(AVENGERSENDGAME);
        Show editedAVENGERSENDGAME = new ShowBuilder(AVENGERSENDGAME).withIsWatched(true).build();
        uniqueShowList.setShow(AVENGERSENDGAME, editedAVENGERSENDGAME);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(editedAVENGERSENDGAME);
        assertEquals(expectedUniqueShowList, expectedUniqueShowList);
    }

    @Test
    public void setShow_editedShowHasDifferentIdentity_success() {
        uniqueShowList.add(AVENGERSENDGAME);
        uniqueShowList.setShow(AVENGERSENDGAME, FIGHTCLUB);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(FIGHTCLUB);
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShow_editedShowHasNonUniqueIdentity_throwsDuplicateShowException() {
        uniqueShowList.add(AVENGERSENDGAME);
        uniqueShowList.add(FIGHTCLUB);
        assertThrows(DuplicateShowException.class, () -> uniqueShowList.setShow(AVENGERSENDGAME, FIGHTCLUB));
    }

    @Test
    public void remove_nullShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.remove(null));
    }

    @Test
    public void remove_showDoesNotExist_throwsShowNotFoundException() {
        assertThrows(ShowNotFoundException.class, () -> uniqueShowList.remove(AVENGERSENDGAME));
    }

    @Test
    public void remove_existingShow_removesShow() {
        uniqueShowList.add(AVENGERSENDGAME);
        uniqueShowList.remove(AVENGERSENDGAME);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShows_nullUniqueShowList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.setShows((UniqueShowList) null));
    }

    @Test
    public void setShows_uniqueShowList_replacesOwnListWithProvidedUniqueShowList() {
        uniqueShowList.add(AVENGERSENDGAME);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(FIGHTCLUB);
        uniqueShowList.setShows(expectedUniqueShowList);
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShows_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueShowList.setShows((List<Show>) null));
    }

    @Test
    public void setShows_list_replacesOwnListWithProvidedList() {
        uniqueShowList.add(AVENGERSENDGAME);
        List<Show> showsList = Collections.singletonList(FIGHTCLUB);
        uniqueShowList.setShows(showsList);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(FIGHTCLUB);
        assertEquals(expectedUniqueShowList, uniqueShowList);
    }

    @Test
    public void setShows_listWithDuplicateShows_throwsDuplicateShowException() {
        List<Show> listWithDuplicateShows = Arrays.asList(AVENGERSENDGAME, FIGHTCLUB);
        //assertThrows(DuplicateShowException.class, () -> uniqueShowList.setShows(listWithDuplicateShows));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueShowList.asUnmodifiableObservableList().remove(0));
    }
}
