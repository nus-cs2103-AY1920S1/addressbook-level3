package seedu.ezwatchlist.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.UniqueShowList;

/**
 * Wraps all data at the EzWatchlist level
 * Duplicates are not allowed (by .isSameShow comparison)
 */
public class WatchList implements ReadOnlyWatchList {

    private final UniqueShowList shows;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        shows = new UniqueShowList();
    }

    public WatchList() {}

    /**
     * Creates an WatchList using the Shows in the {@code toBeCopied}
     */
    public WatchList(ReadOnlyWatchList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the watchlist with {@code shows}.
     * {@code shows} must not contain duplicate shows.
     */
    public void setShows(List<Show> shows) {
        this.shows.setShows(shows);
    }

    /**
     * Resets the existing data of this {@code WatchList} with {@code newData}.
     */
    public void resetData(ReadOnlyWatchList newData) {
        requireNonNull(newData);

        setShows(newData.getShowList());
    }

    //// show-level operations
    /**
     * Returns true if a show with the same identity as {@code show} exists in the WatchList.
     * @param show The show to be checked if contained in the unique watchlist.
     * @return True if the show is contained in the unique watchlist.
     */
    public boolean hasShow(Show show) {
        requireNonNull(show);
        return shows.contains(show);
    }

    /**
     * Returns true if a show with the same name as {@code name} exists in the WatchList.
     * @param name The name of the show to be checked.
     * @return True if the show with the same name as {@code name} exists in the WatchList.
     */
    public boolean hasName(Name name) {
        requireNonNull(name);
        return shows.hasShowName(name);
    }

    /**
     * Returns the list of shows that has the same name as {@code showName} as the current watch list.
     * @param showName The name of the show to be checked.
     * @return The list of shows that has the same name as {@code showName} as the current watch list.
     */
    public List<Show> getShowIfHasName(Name showName) {
        requireNonNull(showName);
        return shows.getShowIfHasName(showName);
    }

    /**
     * Returns true if a show with any of the actor as that in {@code actorSet} exists in the WatchList.
     * @param actorSet The set of the actors to be checked.
     * @return True if the show with any of the actor as that in {@code actorSet} exists in the WatchList.
     */
    public boolean hasActor(Set<Actor> actorSet) {
        requireNonNull(actorSet);
        return shows.hasActor(actorSet);
    }

    /**
     * Returns the list of shows that has the same name as {@code actorSet} as the current watch list.
     * @param actorSet The set of the actors to be checked.
     * @return The list of shows that has the same name as {@code actorSet} as the current watch list.
     */
    public List<Show> getShowIfHasActor(Set<Actor> actorSet) {
        requireNonNull(actorSet);
        return shows.getShowIfHasActor(actorSet);
    }

    /**
     * Returns the list of shows that has the same genre as {@code actorSet} as the current watch list.
     * @param genreSet The set of the genres to be checked.
     * @return The list of shows that has the same genre as {@code actorSet} as the current watch list.
     */
    public List<Show> getShowIfIsGenre(Set<Genre> genreSet) {
        requireNonNull(genreSet);
        return shows.getShowIfIsGenre(genreSet);
    }

    /**
     * Adds a show to the watchlist.
     * The show must not already exist in the watchlist.
     * @param s The show to be added.
     */
    public void addShow(Show s) {
        shows.add(s);
    }

    /**
     * Replaces the given show {@code target} in the list with {@code editedShow}.
     * {@code target} must exist in the watchlist.
     * The show identity of {@code editedShow} must not be the same as another existing show in the watchlist.
     */
    public void setShow(Show target, Show editedShow) {
        requireNonNull(editedShow);

        shows.setShow(target, editedShow);
    }

    /**
     * Removes {@code key} from this {@code WatchList}.
     * {@code key} must exist in the watchlist.
     */
    public void removeShow(Show key) {
        shows.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return shows.asUnmodifiableObservableList().size() + " shows";
        // TODO: refine later
    }

    @Override
    public ObservableList<Show> getShowList() {
        return shows.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WatchList // instanceof handles nulls
                && shows.equals(((WatchList) other).shows));
    }

    @Override
    public int hashCode() {
        return shows.hashCode();
    }
}
