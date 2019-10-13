package seedu.address.model.member.exceptions;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException() {
        super("Operation would result in duplicate member");
    }
}
