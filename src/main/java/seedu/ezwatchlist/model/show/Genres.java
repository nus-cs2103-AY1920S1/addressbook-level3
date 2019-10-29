package seedu.ezwatchlist.model.show;

import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a list of genres of the given show.
 */
public class Genres {
    private List<String> genres;

    public Genres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Stream<String> stream() {
        return genres.stream();
    }
}
