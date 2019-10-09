package mams.model.appeal.exceptions;

public class DuplicateAppealException extends RuntimeException{
    public DuplicateAppealException(){
        super("Operation would result in duplicate appeal");
    }
}
