package dukecooks.testutil.health;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dukecooks.logic.commands.health.EditRecordCommand;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Remark;
import dukecooks.model.health.components.Timestamp;
import dukecooks.model.health.components.Type;
import dukecooks.model.health.components.Value;

/**
 * A utility class to help with building EditRecordDescriptor objects.
 */
public class EditRecordDescriptorBuilder {

    private EditRecordCommand.EditRecordDescriptor descriptor;

    public EditRecordDescriptorBuilder() {
        descriptor = new EditRecordCommand.EditRecordDescriptor();
    }

    public EditRecordDescriptorBuilder(EditRecordCommand.EditRecordDescriptor descriptor) {
        this.descriptor = new EditRecordCommand.EditRecordDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecordDescriptor} with fields containing {@code record}'s details
     */
    public EditRecordDescriptorBuilder(Record record) {
        descriptor = new EditRecordCommand.EditRecordDescriptor();
        descriptor.setType(record.getType());
        descriptor.addRemarks(record.getRemarks());
        descriptor.setValue(record.getValue());
        descriptor.setTimestamp(record.getTimestamp());
    }

    /**
     * Returns an {@code EditRecordDescriptor} with fields containing {@code record}'s details
     */
    public EditRecordDescriptorBuilder(Record recordFrom, Record recordTo) {
        descriptor = new EditRecordCommand.EditRecordDescriptor();
        descriptor.setType(recordTo.getType());
        descriptor.addRemarks(recordTo.getRemarks());
        descriptor.removeRemarks(recordFrom.getRemarks());
        descriptor.setValue(recordTo.getValue());
        descriptor.setTimestamp(recordTo.getTimestamp());
    }

    /**
     * Sets the {@code Type} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withType(String type) {
        descriptor.setType(Type.valueOf(type));
        return this;
    }

    /**
     * Parses the {@code remarks} to be added into a {@code Set<Remark>}
     * and set it to the {@code EditRecordDescriptor}
     * that we are building.
     */
    public EditRecordDescriptorBuilder withRemarksToAdd(String... remarks) {
        Set<Remark> remarkSet = Stream.of(remarks).map(Remark::new).collect(Collectors.toSet());
        descriptor.addRemarks(remarkSet);
        return this;
    }

    /**
     * Parses the {@code remarks} for removal into a {@code Set<Remark>}
     * and set it to the {@code EditRecordDescriptor}
     * that we are building.
     */
    public EditRecordDescriptorBuilder withRemarksToRemove(String... remarks) {
        Set<Remark> remarkSet = Stream.of(remarks).map(Remark::new).collect(Collectors.toSet());
        descriptor.removeRemarks(remarkSet);
        return this;
    }

    /**
     * Sets the {@code Value} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withValue(String value) {
        descriptor.setValue(new Value(value));
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withTimestamp(String timestamp) {
        descriptor.setTimestamp(new Timestamp(timestamp));
        return this;
    }

    public EditRecordCommand.EditRecordDescriptor build() {
        return descriptor;
    }
}
