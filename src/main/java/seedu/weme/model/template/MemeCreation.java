package seedu.weme.model.template;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.weme.model.template.exceptions.MemeCreationException;
import seedu.weme.model.template.exceptions.MemeTextNotFoundException;
import seedu.weme.model.util.ImageUtil;

/**
 * Represents a meme creation session.
 */
public class MemeCreation {

    private final ObservableList<MemeText> textList;
    private final ObservableList<MemeText> unmodifiableObservableTextList;
    private BufferedImage initialImage;

    public MemeCreation() {
        textList = FXCollections.observableArrayList();
        unmodifiableObservableTextList = FXCollections.unmodifiableObservableList(textList);
        initialImage = null;
    }

    /**
     * Start a meme creation session with the given template as the base image.
     *
     * @param template the template to use as the base image
     * @throws IOException if fails to read the image of the template
     */
    public void startWithTemplate(Template template) throws IOException {
        clear();
        Path templateImagePath = template.getImagePath().getFilePath().toAbsolutePath();
        initialImage = ImageIO.read(templateImagePath.toFile());
    }

    /**
     * Adds text to the meme currently being generated.
     *
     * @param text specified the text to be added
     */
    public void addText(MemeText text) {
        requireNonNull(text);
        textList.add(text);
    }

    /**
     * Replaces a meme text in the list with a new meme text.
     *
     * @param target      the {@code MemeText} to replace
     * @param replacement the {@code MemeText} to use as replacement
     */
    public void setText(MemeText target, MemeText replacement) {
        requireAllNonNull(target, replacement);
        int index = textList.indexOf(target);
        if (index == -1) {
            throw new MemeTextNotFoundException();
        }
        textList.set(index, replacement);
    }

    /**
     * Removes the meme text from the list.
     * The meme text must exist in the list.
     */
    public void deleteText(MemeText toDelete) {
        requireNonNull(toDelete);
        if (!textList.remove(toDelete)) {
            throw new MemeTextNotFoundException();
        }
    }

    public ObservableList<MemeText> getMemeTextList() {
        return unmodifiableObservableTextList;
    }

    /**
     * Returns the boundary coordinates of the text on the template.
     *
     * @param text the text to be placed on the template
     * @return a {@code TextBoundaries} object denoting the boundary coordinates of the text
     */
    private TextBoundaries getTextBoundary(MemeText text) {
        Graphics2D graphics = (Graphics2D) initialImage.getGraphics();
        graphics.setFont(new Font(Font.SANS_SERIF, text.getStyle(), text.getSize(initialImage.getHeight())));
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text.getText(), graphics);

        int imageWidth = initialImage.getWidth();
        int imageHeight = initialImage.getHeight();
        int textWidth = (int) rect.getWidth();
        int textHeight = (int) rect.getHeight();

        int startOfTextX = (int) (text.getX() * imageWidth - textWidth / 2);
        int startOfTextY = (int) (text.getY() * imageHeight - textHeight / 2);
        int endOfTextX = startOfTextX + textWidth;
        int endOfTextY = startOfTextY + textHeight;
        return new TextBoundaries(startOfTextX, endOfTextX, startOfTextY, endOfTextY);
    }

    /**
     * Abort the meme creation session.
     */
    public void abort() {
        clear();
    }

    /**
     * Clears this creation session
     */
    private void clear() {
        textList.clear();
        if (initialImage != null) {
            initialImage.getGraphics().dispose();
            initialImage = null;
        }
    }

    public Optional<BufferedImage> getCurrentImage() {
        if (initialImage == null) {
            return Optional.empty();
        } else {
            return Optional.of(render());
        }
    }

    /**
     * Renders the current meme image.
     *
     * The current meme image is generated from the initial image of the template plus all the {@code MemeText} added.
     * @return the current meme image
     */
    private BufferedImage render() {
        if (initialImage == null) {
            throw new RuntimeException("Initial image should have been initialized.");
        }
        BufferedImage newImage = ImageUtil.copyBufferedImage(initialImage);
        Graphics2D graphics = (Graphics2D) newImage.getGraphics();

        for (MemeText text : textList) {
            TextBoundaries boundaries = getTextBoundary(text);
            int x = boundaries.getX1();
            // For some reason the following y gives the most accurate position
            int y = boundaries.getY1() + boundaries.getHeight() * 3 / 4;
            graphics.setFont(new Font(Font.SANS_SERIF, text.getStyle(), text.getSize(initialImage.getHeight())));
            graphics.setColor(text.getColor());
            graphics.drawString(text.getText(), x, y);
        }

        return newImage;
    }

    /**
     * Generates the meme and writes it to the specified path. The current session will be cleared afterwards.
     *
     * @param destination the path to write the meme image file
     * @throws MemeCreationException if an error occurs while attempting to create the meme
     */
    public void generate(Path destination) throws MemeCreationException {
        try {
            ImageIO.write(render(), "jpg", destination.toFile());
            clear();
        } catch (IOException e) {
            throw new MemeCreationException(e);
        }
    }

    /**
     * Replace this meme creation session with {@code otherSession}.
     *
     * @param otherSession the replacement
     */
    public void resetSession(MemeCreation otherSession) {
        textList.clear();
        textList.addAll(otherSession.textList);
        initialImage = ImageUtil.copyBufferedImage(otherSession.initialImage);
    }

    /**
     * Represents the boundary coordinates of a piece of text.
     */
    private static final class TextBoundaries {
        private final int x1;
        private final int x2;
        private final int y1;
        private final int y2;

        TextBoundaries(int x1, int x2, int y1, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        int getX1() {
            return x1;
        }

        int getX2() {
            return x2;
        }

        int getY1() {
            return y1;
        }

        int getY2() {
            return y2;
        }

        int getWidth() {
            return x2 - x1;
        }

        int getHeight() {
            return y2 - y1;
        }
    }
}
