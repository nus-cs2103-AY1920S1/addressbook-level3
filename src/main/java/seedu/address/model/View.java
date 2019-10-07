package seedu.address.model;

public class View {

    private final String togo;

    private final Integer index;

    public View(String string, Integer viewIndex) {
        togo = string;
        index = viewIndex;
    }

    public Integer getIndex() {
        return index;
    }
}
