package seedu.address.commons.stub;


public class NameStub {
    public final String name;

    public NameStub(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NameStub
                && name.equals(((NameStub) other).name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
