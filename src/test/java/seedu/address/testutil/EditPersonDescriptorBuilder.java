package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.medical.MedicalHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setMedicalHistories(person.getMedicalHistories());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code medicalHistories} into a {@code Set<MedicalHistory>}
     * and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withMedicalHistories(String... medicalHistories) {
        Set<MedicalHistory> medicalHistorySet = Stream.of(medicalHistories)
                .map(MedicalHistory::new).collect(Collectors.toSet());
        descriptor.setMedicalHistories(medicalHistorySet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
