package seedu.module.model.module;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import seedu.module.model.module.exceptions.LinkAccessException;

/**
 * Represents a Link in a TrackedModule
 */
public class Link {

    public final String url;
    public final String name;
    private boolean marked = false;

    public Link(String name, String url) {
        requireNonNull(name);
        requireNonNull(url);
        this.name = name;
        this.url = url;
    }

    public Link(String name, String url, boolean marked) {
        requireNonNull(name);
        requireNonNull(url);
        this.name = name;
        this.url = url;
        this.marked = marked;
    }

    /**
     * Launches the url in this Link in the user's default browser
     */
    public void launch() {
        String runningSystem = System.getProperty("os.name").toLowerCase();

        try {
            if (Desktop.isDesktopSupported()) { // Probably Windows
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(URI.create(url));
            } else { // Definitely Non-windows
                Runtime runtime = Runtime.getRuntime();
                if (runningSystem.contains("mac")) { // Apples
                    runtime.exec("open " + url);
                } else if (runningSystem.contains("nix") || runningSystem.contains("nux")) { // Linux flavours
                    runtime.exec("xdg-open " + url);
                    throw new LinkAccessException("Unable/unwilling to launch a browser in your OS.");
                }
            }
        } catch (IOException e) {
            throw new LinkAccessException("Couldn't open system browser: " + e.getMessage());
        }
    }

    public boolean isMarked() {
        return this.marked;
    }

    public boolean setMarked() {
        if (this.marked) {
            return false;
        } else {
            this.marked = true;
            return true;
        }
    }

    public boolean setUnmarked() {
        if (!this.marked) {
            return false;
        } else {
            this.marked = false;
            return true;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && name.equals(((Link) other).name))
                && url.equals(((Link) other).url); // state check
    }

}
