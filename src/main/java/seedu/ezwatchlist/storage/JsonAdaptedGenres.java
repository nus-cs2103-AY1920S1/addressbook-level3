package seedu.ezwatchlist.storage;

import java.util.List;

import seedu.ezwatchlist.model.show.Genres;

/**
 * Jackson-friendly version of {@link Genres}.
 */
public class JsonAdaptedGenres {

    private List<String> genres;

    /**
     * Constructs a {@code JsonAdaptedGenres} with the given {@code genres}.
     */
    public JsonAdaptedGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * Adds the list of strings into genres.
     * @param jsonGenres a list of strings representing the genres.
     */
    public void addAll(List<String> jsonGenres) {
        genres.addAll(jsonGenres);
    }
    /**
     * Converts this Jackson-friendly adapted genres object into the model's {@code Genres} object.
     */
    public Genres toModelType() {
        return new Genres(genres);
    }

    public List<String> getGenres() {
        return genres;
    }
}
