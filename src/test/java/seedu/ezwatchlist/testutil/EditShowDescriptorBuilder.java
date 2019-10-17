package seedu.ezwatchlist.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ezwatchlist.logic.commands.EditCommand.EditShowDescriptor;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.*;

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
    public EditShowDescriptorBuilder withIsWatched(boolean IsWatched) {
        descriptor.setIsWatched(new IsWatched(IsWatched));
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
        Set<Actor> ActorSet = Stream.of(actors).map(Actor::new).collect(Collectors.toSet());
        descriptor.setActors(ActorSet);
        return this;
    }

    public EditShowDescriptor build() {
        return descriptor;
    }
}
