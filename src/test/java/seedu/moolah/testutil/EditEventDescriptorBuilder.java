package seedu.moolah.testutil;

import seedu.moolah.logic.commands.event.EditEventCommand;
import seedu.moolah.model.general.Category;
import seedu.moolah.model.general.Description;
import seedu.moolah.model.event.Event;
import seedu.moolah.model.general.Price;
import seedu.moolah.model.general.Timestamp;


/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventCommand.EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventCommand.EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventCommand.EditEventDescriptor descriptor) {
        this.descriptor = new EditEventCommand.EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventCommand.EditEventDescriptor();
        descriptor.setDescription(event.getDescription());
        descriptor.setPrice(event.getPrice());
        descriptor.setCategory(event.getCategory());
        descriptor.setTimestamp(event.getTimestamp());
    }

    /**
     * Sets the {@code Description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(new Category(category));
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTimestamp(String rawTimestamp) {
        descriptor.setTimestamp(Timestamp.createTimestampIfValid(rawTimestamp).get());
        return this;
    }

    public EditEventCommand.EditEventDescriptor build() {
        return descriptor;
    }
}
