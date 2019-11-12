package seedu.ezwatchlist.model.show;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ezwatchlist.commons.util.CollectionUtil;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.exceptions.DuplicateShowException;
import seedu.ezwatchlist.model.show.exceptions.ShowNotFoundException;

/**
 * A list of shows that enforces uniqueness between its elements and does not allow nulls.
 * A show is considered unique by comparing using {@code show#isSameShow(Show)}. As such, adding and updating of
 * shows uses Show#isSameShow(show) for equality so as to ensure that the show being added or updated is
 * unique in terms of identity in the UniqueShowList. However, the removal of a show uses Show#equals(Object) so
 * as to ensure that the show with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Show#isSameShow(Show)
 */
public class UniqueShowList implements Iterable<Show> {

    public static final String MESSAGE_SHOW_NOT_FOUND =
            "The indicated show is not part of your watchlist, please add it first.";

    private final ObservableList<Show> internalList = FXCollections.observableArrayList();
    private final ObservableList<Show> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent show as the given argument.
     * @param toCheck The show to be checked if present.
     * @return True if the show to present in the internal list.
     */
    public boolean contains(Show toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameShow);
    }

    /**
     * Returns true if the list contains an equivalent show name as the given argument.
     * @param showName Name of show to be searched.
     * @return True if a show has the same name as showName.
     */
    public boolean hasShowName(Name showName) {
        requireNonNull(showName);
        Show movie = new Movie(showName, new Description(), new IsWatched("false"), new Date(),
                new RunningTime(), new HashSet<>(new ArrayList<>()));
        Show tvShow = new TvShow(showName, new Description(), new IsWatched("false"), new Date(),
                new RunningTime(), new HashSet<>(new ArrayList<>()),
                0, 0, new ArrayList<>());
        return internalList.stream().anyMatch(movie::isSameName) && internalList.stream().anyMatch(tvShow::isSameName);
    }

    /**
     * Returns the list of shows that has the same name as showName.
     * @param showName Name of show to be searched.
     * @return List of Show that has the same name as showName.
     */
    public List<Show> getShowIfHasName(Name showName) {
        requireNonNull(showName);
        Show currentMovie = new Movie(showName, new Description(), new IsWatched(), new Date(),
                new RunningTime(), new HashSet<>(new ArrayList<>()));
        Show currentTvShow = new TvShow(showName, new Description(), new IsWatched(), new Date(),
                new RunningTime(), new HashSet<>(new ArrayList<>()),
                0, 0, new ArrayList<>());
        return internalList.stream()
                .filter(show -> show.hasNameWithWord(currentMovie) || show.hasNameWithWord(currentTvShow))
                .collect(Collectors.toList());
    }

    /**
     * Returns true if the list contains any of the actors in actorSet.
     * @param actorSet Set of actors to be searched.
     * @return True if a show has the same name as showName.
     */
    public boolean hasActor(Set<Actor> actorSet) {
        requireNonNull(actorSet);
        Show movie = new Movie(new Name(), new Description(), new IsWatched(), new Date(), new RunningTime(), actorSet);
        Show tvShow = new TvShow(new Name(), new Description(), new IsWatched(),
                new Date(), new RunningTime(), actorSet, 0, 0, new ArrayList<>());
        return internalList.stream().anyMatch(movie::hasActorWithName)
                || internalList.stream().anyMatch(tvShow::hasActorWithName);
    }

    /**
     * Returns the list of shows that has any of the actor in actorSet.
     * @param actorSet Set of actor(s) to be searched.
     * @return List of shows that has the actor.
     */
    public List<Show> getShowIfHasActor(Set<Actor> actorSet) {
        requireNonNull(actorSet);
        Show currentMovie = new Movie(new Name(), new Description(), new IsWatched("false"), new Date(),
                new RunningTime(), actorSet);
        Show currentTvShow = new TvShow(new Name(), new Description(), new IsWatched("false"), new Date(),
                new RunningTime(), actorSet,
                0, 0, new ArrayList<>());
        return internalList.stream().filter(show -> show.hasActorWithName(currentMovie)
                || show.hasActorWithName(currentTvShow)).collect(Collectors.toList());
    }

    /**
     * Returns the list of shows that has any of the genre in genreSet.
     * @param genreSet Set of genre(s) to be searched.
     * @return List of shows that has the genre.
     */
    public List<Show> getShowIfIsGenre(Set<Genre> genreSet) {
        requireNonNull(genreSet);
        Show currentMovie = new Movie(new Name(), new Description(), new IsWatched("false"), new Date(),
                new RunningTime(), new HashSet<>(new ArrayList<>()));
        currentMovie.addGenres(genreSet);
        Show currentTvShow = new TvShow(new Name(), new Description(), new IsWatched("false"), new Date(),
                new RunningTime(), new HashSet<>(new ArrayList<>()),
                0, 0, new ArrayList<>());
        currentTvShow.addGenres(genreSet);
        return internalList.stream().filter(show -> show.hasGenre(currentMovie)
                || show.hasGenre(currentTvShow)).collect(Collectors.toList());
    }

    /**
     * Adds a show to the list.
     * The show must not already exist in the list.
     * @param toAdd Show to be added.
     */
    public void add(Show toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateShowException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the show {@code target} in the list with {@code editedShow}.
     * {@code target} must exist in the list.
     * The show identity of {@code editedShow} must not be the same as another existing show in the list.
     */
    public void setShow(Show target, Show editedShow) {
        CollectionUtil.requireAllNonNull(target, editedShow);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ShowNotFoundException(MESSAGE_SHOW_NOT_FOUND);
        }

        if (!target.isSameShow(editedShow) && contains(editedShow)) {
            throw new DuplicateShowException();
        }

        internalList.set(index, editedShow);
    }

    /**
     * Removes the equivalent show from the list.
     * The show must exist in the list.
     */
    public void remove(Show toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ShowNotFoundException(MESSAGE_SHOW_NOT_FOUND);
        }
    }

    public void setShows(UniqueShowList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code shows}.
     * {@code shows} must not contain duplicate shows.
     */
    public void setShows(List<Show> shows) {
        CollectionUtil.requireAllNonNull(shows);
        //currently remove the check if shows are unique
        //if (!showsAreUnique(shows)) {
        //throw new DuplicateShowException();
        //}

        internalList.setAll(shows);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Show> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Show> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueShowList // instanceof handles nulls
                && internalList.equals(((UniqueShowList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code shows} contains only unique shows.
     */
    private boolean showsAreUnique(List<Show> shows) {
        for (int i = 0; i < shows.size() - 1; i++) {
            for (int j = i + 1; j < shows.size(); j++) {
                if (shows.get(i).isSameShow(shows.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean showsAreUniquePublic(List<Show> shows) {
        return showsAreUnique(shows);
    }

    public ObservableList<Show> getInternalList() {
        return internalList;
    }

    public ObservableList<Show> getInternalUnmodifiableList() {
        return internalUnmodifiableList;
    }
}
