package seedu.jarvis.testutil;

/**
 * {@code CommandStubHasNoInverse} returns false when checked for having an inverse execution.
 */
public class CommandStubHasNoInverse extends CommandStub {
    @Override
    public boolean hasInverseExecution() {
        return false;
    }
}
