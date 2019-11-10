package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalShows.AVENGERSENDGAME;
import static seedu.ezwatchlist.testutil.TypicalShows.FIGHTCLUB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.exceptions.DuplicateShowException;
import seedu.ezwatchlist.model.show.exceptions.ShowNotFoundException;
import seedu.ezwatchlist.testutil.ShowBuilder;

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
        Show editedAlice = new ShowBuilder(AVENGERSENDGAME).withIsWatched("true").withType("Movie").build();
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
        Show editedAvengersEndGame = new ShowBuilder(AVENGERSENDGAME).withIsWatched("true").build();
        uniqueShowList.setShow(AVENGERSENDGAME, editedAvengersEndGame);
        UniqueShowList expectedUniqueShowList = new UniqueShowList();
        expectedUniqueShowList.add(editedAvengersEndGame);
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
        List<Show> listWithDuplicateShows = Arrays.asList(AVENGERSENDGAME, AVENGERSENDGAME);
        //assertThrows(DuplicateShowException.class, () -> uniqueShowList.setShows(listWithDuplicateShows));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueShowList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void hasShowNameTest() {
        Show test = new ShowBuilder(AVENGERSENDGAME).withName("test").build();
        uniqueShowList.add(test);
        List<Show> showList = new ArrayList<>();
        showList.add(test);
        assertEquals(uniqueShowList.hasShowName(new Name("test")), true);
    }

    @Test
    public void getShowIfHasNameTest() {
        Show test = new ShowBuilder(AVENGERSENDGAME).withName("test").build();
        uniqueShowList.add(test);
        List<Show> showList = new ArrayList<>();
        showList.add(test);
        assertEquals(uniqueShowList.getShowIfHasName(new Name("test")), showList);
    }

    @Test
    public void hasActorTest() {
        Show test = new ShowBuilder(AVENGERSENDGAME).withActors("test").build();
        Show test2 = (TvShow) new ShowBuilder(AVENGERSENDGAME).withType("tv").build();
        uniqueShowList.add(test);
        List<Show> showList = new ArrayList<>();
        showList.add(test);
        Set<Actor> actors = new HashSet<>();
        actors.add(new Actor("test"));
        assertEquals(uniqueShowList.hasActor(actors), true);
        showList.clear();
        Show tvShow = new TvShow(new Name(), new Description(), new IsWatched(),
                new Date(), new RunningTime(), actors, 0, 0, new ArrayList<>());
        assertEquals(uniqueShowList.hasActor(actors), true);
    }

    @Test
    public void getShowIfHasActorTest() {
        Show test = new ShowBuilder(AVENGERSENDGAME).withActors("test").build();
        uniqueShowList.add(test);
        List<Show> showList = new ArrayList<>();
        showList.add(test);
        Set<Actor> actors = new HashSet<>();
        actors.add(new Actor("test"));
        assertEquals(uniqueShowList.getShowIfHasActor(actors), showList);
    }

    @Test
    public void getShowIfIsGenreTest() {
        Show test = new ShowBuilder(AVENGERSENDGAME).build();
        uniqueShowList.add(test);
        List<Show> showList = new ArrayList<>();
        showList.add(test);
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("test"));
        assertEquals(uniqueShowList.getShowIfIsGenre(genres), new ArrayList<>());
    }
    @Test
    public void iteratorTest() {
        assertTrue(uniqueShowList.iterator() instanceof Iterator);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(uniqueShowList.hashCode(), uniqueShowList.getInternalList().hashCode());
    }

    @Test
    public void getInternalUnmodifiableListTest() {
        assertTrue(uniqueShowList.getInternalUnmodifiableList() instanceof ObservableList);
    }

    @Test
    public void showsAreUniqueTest() {
        Show show = new ShowBuilder(AVENGERSENDGAME).build();
        Show show1 = new ShowBuilder(FIGHTCLUB).build();
        List<Show> showList = new ArrayList<>();
        showList.add(show);
        showList.add(show1);
        assertTrue(uniqueShowList.showsAreUniquePublic(showList));
        showList.add(show);
        assertFalse(uniqueShowList.showsAreUniquePublic(showList));
    }
}
