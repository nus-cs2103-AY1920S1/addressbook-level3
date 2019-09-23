package seedu.address.model.book;

public class Book {

    private String title = "dead sea scroll";

    public Book() {}

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
