package seedu.address.logic.commands.util;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.classid.ClassId;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Picture;
import seedu.address.model.person.Result;



/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public class EditPersonDescriptor {
    private Name name;
    private Picture picture;
    private ClassId classId;
    private Attendance attendance;
    private Result result;
    private Participation participation;

    public EditPersonDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditPersonDescriptor(EditPersonDescriptor toCopy) {
        setName(toCopy.name);
        setPicture(toCopy.picture);
        setAttendance(toCopy.attendance);
        setClassId(toCopy.classId);
        setResult(toCopy.result);
        setParticipation(toCopy.participation);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, picture, classId, result, attendance, participation);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Optional<Picture> getPicture() {
        return Optional.ofNullable(picture);
    }

    public void setClassId(ClassId classId) {
        this.classId = classId;
    }

    public Optional<ClassId> getClassId() {
        return Optional.ofNullable(classId);
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Optional<Result> getResult() {
        return Optional.ofNullable(result);
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Optional<Attendance> getAttendance() {
        return Optional.ofNullable(attendance);
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }

    public Optional<Participation> getParticipation() {
        return Optional.ofNullable(participation);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonDescriptor)) {
            return false;
        }

        // state check
        EditPersonDescriptor e = (EditPersonDescriptor) other;

        return getName().equals(e.getName())
                && getClassId().equals(e.getClassId())
                && getResult().equals(e.getResult())
                && getParticipation().equals(e.getParticipation())
                && getPicture().equals(e.getPicture())
                && getAttendance().equals(e.getAttendance());
    }
}
