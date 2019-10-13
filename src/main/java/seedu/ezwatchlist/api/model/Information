package seedu.ezwatchlist.api.model;

import java.util.List;
import java.util.Optional;

/**
 * Interface to contain information retrieved from API.
 */
public abstract class Information {
    public Optional<String> name;
    public Optional<String> description;
    public Optional<List<String>> actors;
    public Optional<String> date;

    public void addName(String name) {
        this.name = Optional.ofNullable(name);
    }

    public void addDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    public void addActors(List<String> actors) {
        this.actors = Optional.ofNullable(actors);
    }

    public void addDate(String date) {
        this.date = Optional.ofNullable(date);
    }
}
