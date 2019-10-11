package seedu.tarence.testutil;

import java.util.Optional;

import seedu.tarence.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditStudentDescriptorBuilder {

    private EditStudentDescriptor descriptor;

    public EditStudentDescriptorBuilder() {
        descriptor = new EditStudentDescriptor();
    }

    public EditStudentDescriptorBuilder(EditStudentDescriptor descriptor) {
        this.descriptor = new EditStudentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStudentDescriptor} with fields containing {@code student}'s details
     */
    public EditStudentDescriptorBuilder(Student student) {
        descriptor = new EditStudentDescriptor();
        descriptor.setName(student.getName());
        descriptor.setEmail(student.getEmail());
        descriptor.setModCode(student.getModCode());
        descriptor.setTutName(student.getTutName());
        descriptor.setNusnetId(student.getNusnetId());
        descriptor.setMatricNum(student.getMatricNum());
    }

    /**
     * Sets the {@code Name} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code ModCode} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withModCode(ModCode modCode) {
        descriptor.setModCode(modCode);
        return this;
    }

    /**
     * Sets the {@code TutName} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withTutName(TutName tutName) {
        descriptor.setTutName(tutName);
        return this;
    }

    /**
     * Sets the {@code Matric No} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withMatricNum (Optional<MatricNum> matricNum) {
        descriptor.setMatricNum(matricNum);
        return this;
    }

    /**
     * Sets the {@code withNusnetId} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withNusnetId (Optional<NusnetId> nusnetId) {
        descriptor.setNusnetId(nusnetId);
        return this;
    }

    public EditStudentDescriptor build() {
        return descriptor;
    }
}
