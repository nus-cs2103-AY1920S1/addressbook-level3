package dukecooks.testutil.profile;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dukecooks.logic.commands.profile.EditProfileCommand;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.Name;
import dukecooks.model.profile.person.Person;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditProfileCommand.EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditProfileCommand.EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditProfileCommand.EditPersonDescriptor descriptor) {
        this.descriptor = new EditProfileCommand.EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditProfileCommand.EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setAddMedicalHistories(person.getMedicalHistories());
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
        descriptor.setAddMedicalHistories(medicalHistorySet);
        return this;
    }

    public EditProfileCommand.EditPersonDescriptor build() {
        return descriptor;
    }
}
