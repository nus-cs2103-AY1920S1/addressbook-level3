package dream.fcard.model.exceptions;

public class DeckNotFoundException extends Throwable {
    public DeckNotFoundException(Exception cause) {
        super(cause);
    }
}
