package thrift.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import thrift.logic.commands.UpdateCommand;
import thrift.logic.commands.UpdateCommand.UpdateTransactionDescriptor;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;

/**
 * A utility class to help with building UpdateTransactionDescriptor objects.
 */
public class UpdateTransactionDescriptorBuilder {

    private UpdateCommand.UpdateTransactionDescriptor descriptor;

    public UpdateTransactionDescriptorBuilder() {
        descriptor = new UpdateTransactionDescriptor();
    }

    public UpdateTransactionDescriptorBuilder(UpdateTransactionDescriptor descriptor) {
        this.descriptor = new UpdateTransactionDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdateTransactionDescriptor} with fields containing {@code Transaction}'s details
     */
    public UpdateTransactionDescriptorBuilder(Transaction transaction) {
        descriptor = new UpdateTransactionDescriptor();
        descriptor.setDescription(transaction.getDescription());
        descriptor.setValue(transaction.getValue());
        descriptor.setDate(transaction.getDate());
        descriptor.setTags(transaction.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code UpdateTransactionDescriptor} that we are building.
     */
    public UpdateTransactionDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Value} of the {@code UpdateTransactionDescriptor} that we are building.
     */
    public UpdateTransactionDescriptorBuilder withValue(String value) {
        descriptor.setValue(new Value(value));
        return this;
    }

    /**
     * Sets the {@code TransactionDate} of the {@code UpdateTransactionDescriptor} that we are building.
     */
    public UpdateTransactionDescriptorBuilder withDate(String date) {
        descriptor.setDate(new TransactionDate(date));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code UpdateTransactionDescriptor}
     * that we are building.
     */
    public UpdateTransactionDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public UpdateCommand.UpdateTransactionDescriptor build() {
        return descriptor;
    }
}
