package seedu.ezwatchlist.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ezwatchlist.logic.commands.EditCommand.EditShowDescriptor;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;

/**
 * A utility class to help with building EditShowDescriptor objects.
 */
public class EditShowDescriptorBuilder {

    private EditShowDescriptor descriptor;

    public EditShowDescriptorBuilder() {
        descriptor = new EditShowDescriptor();
    }

    public EditShowDescriptorBuilder(EditShowDescriptor descriptor) {
        this.descriptor = new EditShowDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditShowDescriptor} with fields containing {@code show}'s details
     */
    public EditShowDescriptorBuilder(Show show) {
        descriptor = new EditShowDescriptor();
        descriptor.setName(show.getName());
        descriptor.setType(show.getType());
        descriptor.setDescription(show.getDescription());
        descriptor.setIsWatched(show.isWatched());
        descriptor.setDateOfRelease(show.getDateOfRelease());
        descriptor.setRunningTime(show.getRunningTime());
        descriptor.setActors(show.getActors());
        descriptor.setPoster(show.getPoster());
        descriptor.setGenres(show.getGenres());
        if (show.getType().equals("Tv Show")) {
            descriptor.setTotalNumOfEpisodes(show.getTotalNumOfEpisodes());
            descriptor.setNumOfEpisodesWatched(show.getNumOfEpisodesWatched());
            descriptor.setSeasons(show.getTvSeasons());
        }
    }

    /**
     * Sets the {@code Name} of the {@code EditShowDescriptor} that we are building.
     */
    public EditShowDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code type} of the {@code EditShowDescriptor} that we are building.
     */
    public EditShowDescriptorBuilder withType(String type) {
        descriptor.setType(type);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditShowDescriptor} that we are building.
     */
    public EditShowDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code IsWatched} of the {@code EditShowDescriptor} that we are building.
     */
    public EditShowDescriptorBuilder withIsWatched(String isWatched) {
        descriptor.setIsWatched(new IsWatched(isWatched));
        return this;
    }

    /**
     * Sets the {@code DateOfRelease} of the {@code EditShowDescriptor} that we are building.
     */
    public EditShowDescriptorBuilder withDateOfRelease(String date) {
        descriptor.setDateOfRelease(new Date(date));
        return this;
    }

    /**
     * Sets the {@code RunningTime} of the {@code EditShowDescriptor} that we are building.
     */
    public EditShowDescriptorBuilder withRunningTime(int runningTime) {
        descriptor.setRunningTime(new RunningTime(runningTime));
        return this;
    }

    /**
     * Parses the {@code actors} into a {@code Set<Tag>} and set it to the {@code EditShowDescriptor}
     * that we are building.
     */
    public EditShowDescriptorBuilder withActors(String... actors) {
        Set<Actor> actorSet = Stream.of(actors).map(Actor::new).collect(Collectors.toSet());
        descriptor.setActors(actorSet);
        return this;
    }

    public EditShowDescriptor build() {
        return descriptor;
    }
}
