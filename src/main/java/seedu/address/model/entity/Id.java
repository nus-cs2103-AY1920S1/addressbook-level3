package seedu.address.model.entity;

import java.util.Objects;

public class Id {
    private PrefixType prefix;
    private final int number;

    public Id(PrefixType prefix, int number) {
        this.prefix = prefix;
        this.number = number;
    }

    // Getter

    public PrefixType getPrefix() {
        return prefix;
    }

    public int getNumber() {
        return number;
    }

    // Setter

    public void setPrefix(PrefixType prefix) {
        this.prefix = prefix;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.prefix, this.number);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Id)) {
            return false;
        }

        Id otherId = ((Id) other);
        return otherId.getPrefix() == this.getPrefix()
                && otherId.getNumber() == this.getNumber();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getPrefix())
                .append("-")
                .append(getNumber());
        return builder.toString();
    }
}
