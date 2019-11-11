package seedu.address.testutil;

import seedu.address.logic.commands.UpdateEarningsCommand.EditEarningsDescriptor;
import seedu.address.model.classid.ClassId;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.earnings.Type;

/**
 * A utility class to help with building UpdateEarningsDescriptor objects.
 */
public class UpdateEarningsDescriptorBuilder {

    private EditEarningsDescriptor descriptor;

    public UpdateEarningsDescriptorBuilder() {
        descriptor = new EditEarningsDescriptor();
    }

    public UpdateEarningsDescriptorBuilder(EditEarningsDescriptor descriptor) {
        this.descriptor = new EditEarningsDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public UpdateEarningsDescriptorBuilder(Earnings earnings) {
        descriptor = new EditEarningsDescriptor();
        descriptor.setDate(earnings.getDate());
        descriptor.setType(earnings.getType());
        descriptor.setClassId(earnings.getClassId());
        descriptor.setAmount(earnings.getAmount());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public UpdateEarningsDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public UpdateEarningsDescriptorBuilder withType(String type) {
        descriptor.setType(new Type(type));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public UpdateEarningsDescriptorBuilder withClassId(String classId) {
        descriptor.setClassId(new ClassId(classId));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public UpdateEarningsDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }


    public EditEarningsDescriptor build() {
        return descriptor;
    }
}
