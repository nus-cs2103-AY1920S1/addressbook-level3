package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.book.Book;
import seedu.address.model.book.Author;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.Title;
import seedu.address.model.genre.Genre;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditBookDescriptorBuilder {

    private EditCommand.EditBookDescriptor descriptor;

    public EditBookDescriptorBuilder() {
        descriptor = new EditCommand.EditBookDescriptor();
    }

    public EditBookDescriptorBuilder(EditCommand.EditBookDescriptor descriptor) {
        this.descriptor = new EditCommand.EditBookDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditBookDescriptorBuilder(Book book) {
        descriptor = new EditCommand.EditBookDescriptor();
        descriptor.setTitle(book.getTitle());
        descriptor.setSerialNumber(book.getSerialNumber());
        descriptor.setAuthor(book.getAuthor());
        descriptor.setGenres(book.getGenres());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withSerialNumber(String serialNumber) {
        descriptor.setSerialNumber(new SerialNumber(serialNumber));
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withAuthor(String author) {
        descriptor.setAuthor(new Author(author));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Genre>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditBookDescriptorBuilder withGenres(String... genres) {
        Set<Genre> genreSet = Stream.of(genres).map(Genre::new).collect(Collectors.toSet());
        descriptor.setGenres(genreSet);
        return this;
    }

    public EditCommand.EditBookDescriptor build() {
        return descriptor;
    }
}
