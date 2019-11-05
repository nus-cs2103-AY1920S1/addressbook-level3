package dukecooks.testutil.profile;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dukecooks.logic.commands.profile.EditProfileCommand;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.BloodType;
import dukecooks.model.profile.person.DoB;
import dukecooks.model.profile.person.Gender;
import dukecooks.model.profile.person.Height;
import dukecooks.model.profile.person.Name;
import dukecooks.model.profile.person.Person;
import dukecooks.model.profile.person.Weight;

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
        descriptor.setBloodType(person.getBloodType());
        descriptor.setGender(person.getGender());
        descriptor.setHeight(person.getHeight());
        descriptor.setWeight(person.getWeight());
        descriptor.setDateOfBirth(person.getDateOfBirth());
        descriptor.addMedicalHistories(person.getMedicalHistories());
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person personTo, Person personFrom) {
        descriptor = new EditProfileCommand.EditPersonDescriptor();
        descriptor.setName(personTo.getName());
        descriptor.setBloodType(personTo.getBloodType());
        descriptor.setGender(personTo.getGender());
        descriptor.setHeight(personTo.getHeight());
        descriptor.setWeight(personTo.getWeight());
        descriptor.setDateOfBirth(personTo.getDateOfBirth());
        descriptor.addMedicalHistories(personTo.getMedicalHistories());
        descriptor.removeMedicalHistories(personTo.getMedicalHistories());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code bloodType} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBloodType(String bloodType) {
        descriptor.setBloodType(new BloodType(bloodType));
        return this;
    }

    /**
     * Sets the {@code dateOfBirth} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDoB(String dateOfBirth) {
        descriptor.setDateOfBirth(new DoB(dateOfBirth));
        return this;
    }

    /**
     * Sets the {@code height} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withHeight(String height) {
        descriptor.setHeight(new Height(height));
        return this;
    }

    /**
     * Sets the {@code weight} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withWeight(String weight) {
        descriptor.setWeight(new Weight(weight));
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
