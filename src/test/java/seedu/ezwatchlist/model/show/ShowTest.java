package seedu.ezwatchlist.model.show;

import org.junit.jupiter.api.Test;
import seedu.ezwatchlist.testutil.ShowBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalShows.AVENGERSENDGAME;
import static seedu.ezwatchlist.testutil.TypicalShows.FIGHTCLUB;

public class ShowTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Show show = new ShowBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> show.getActors().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(AVENGERSENDGAME.isSameShow(AVENGERSENDGAME));

        // null -> returns false
        assertFalse(AVENGERSENDGAME.isSameShow(null));

        // different isWatched and RunningTime -> returns false
        Show editedAVENGERSENDGAME = new ShowBuilder(AVENGERSENDGAME).withIsWatched(true).withRunningTime(122).build();
        //assertFalse(AVENGERSENDGAME.isSameShow(editedAVENGERSENDGAME));

        // different name -> returns false
        editedAVENGERSENDGAME = new ShowBuilder(AVENGERSENDGAME).withName("FIGHTCLUB").build();
        assertFalse(AVENGERSENDGAME.isSameShow(editedAVENGERSENDGAME));

        // same name, same description, different attributes -> returns true
        editedAVENGERSENDGAME = new ShowBuilder(AVENGERSENDGAME).withIsWatched(true).withDateofRelease("2019")
                .withRunningTime(122).build();
        //assertTrue(AVENGERSENDGAME.isSameShow(editedAVENGERSENDGAME));

        // same name, same dateofrelease, different attributes -> returns true
        editedAVENGERSENDGAME = new ShowBuilder(AVENGERSENDGAME).withRunningTime(122).withDescription("DESCRIPTION")
                .withIsWatched(true).build();
        assertTrue(AVENGERSENDGAME.isSameShow(editedAVENGERSENDGAME));

        // same name, same dateofrelease, same description, different attributes -> returns true
        editedAVENGERSENDGAME = new ShowBuilder(AVENGERSENDGAME).withIsWatched(true).withRunningTime(122).build();
        assertTrue(AVENGERSENDGAME.isSameShow(editedAVENGERSENDGAME));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Show avengerendgameCopy = new ShowBuilder(AVENGERSENDGAME).build();
        assertTrue(AVENGERSENDGAME.equals(avengerendgameCopy));

        // same object -> returns true
        assertTrue(AVENGERSENDGAME.equals(AVENGERSENDGAME));

        // null -> returns false
        assertFalse(AVENGERSENDGAME.equals(null));

        // different type -> returns false
        assertFalse(AVENGERSENDGAME.equals(5));

        // different shows -> returns false
        assertFalse(AVENGERSENDGAME.equals(FIGHTCLUB));

        // different name -> returns false
        Show editedAvengersendgame = new ShowBuilder(AVENGERSENDGAME).withName("FIGHTCLUB").build();
        assertFalse(AVENGERSENDGAME.equals(editedAvengersendgame));

        // different description -> returns false
        editedAvengersendgame = new ShowBuilder(AVENGERSENDGAME).withDescription("DESCRIPTION").build();
        assertFalse(AVENGERSENDGAME.equals(editedAvengersendgame));

        // different iswatched -> returns false
        editedAvengersendgame = new ShowBuilder(AVENGERSENDGAME).withIsWatched(true).build();
        assertFalse(AVENGERSENDGAME.equals(editedAvengersendgame));

        // different dateofrelease -> returns false
        editedAvengersendgame = new ShowBuilder(AVENGERSENDGAME).withDateofRelease("2019").build();
        assertFalse(AVENGERSENDGAME.equals(editedAvengersendgame));

        // different Actors -> returns false
        editedAvengersendgame = new ShowBuilder(AVENGERSENDGAME).withActors("actors").build();
        assertFalse(AVENGERSENDGAME.equals(editedAvengersendgame));
    }
}
