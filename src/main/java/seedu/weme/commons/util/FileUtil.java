package seedu.weme.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javafx.scene.image.Image;

import seedu.weme.model.path.DirectoryPath;


/**
 * Writes and reads files
 */
public class FileUtil {

    public static final String MESSAGE_READ_FILE_FAILURE = "Error encountered while reading the file %s";
    public static final String MESSAGE_COPY_FAILURE_SOURCE_DOES_NOT_EXIST = "Copy failed: source file does not exist";
    public static final String MESSAGE_COPY_FAILURE_INVALID_DIRECTORY = "Copy failed: Invalid Directory Given";
    public static final String MESSAGE_COPY_FAILURE_INTERNAL_ERROR = "Copy failed: Internal Error Encountered";
    private static final int INITIAL_FILE_LABEL = 1;


    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Copies a list of files given by their path to a given directory.
     * Files are named in the following format: number.extension, where
     * number starts from 1 and increments. e.g. 1.jpg, 2.jpg ...
     *
     * @param pathList list of paths containing files.
     * @param folderPath directory path to copy to.
     * @throws IOException error encountered while copying.
     */
    public static void copyFiles(List<Path> pathList, Path folderPath) throws IOException {
        try {
            int fileLabel = INITIAL_FILE_LABEL;
            for (Path path : pathList) {
                String newFilePath;
                do {
                    newFilePath = buildFilePath(folderPath, String.valueOf(fileLabel), path);
                    fileLabel = incrementFileLabel(fileLabel);
                } while (isFileExists(Paths.get(newFilePath)));

                if (isValidPath(newFilePath)) {
                    copy(path, Paths.get(newFilePath));
                } else {
                    throw new IOException(MESSAGE_COPY_FAILURE_INVALID_DIRECTORY);
                }
            }
        } catch (IOException e) {
            throw new IOException(MESSAGE_COPY_FAILURE_INTERNAL_ERROR);
        }
    }

    /**
     * Returns a list of valid Image files found in the given directory path.
     *
     * @param directoryPath Path containing memes to load.
     * @return List of loadable paths.
     */
    public static List<Path> loadImagePath(DirectoryPath directoryPath) throws IOException {
        List<Path> imageList = new ArrayList<>();
        recursiveLoad(imageList, directoryPath);
        return imageList;
    }

    /**
     * Recursively loads all valid image files in a given directory.
     *
     * @param imageList
     * @param directoryPath
     * @throws IOException
     */
    private static void recursiveLoad(List<Path> imageList, DirectoryPath directoryPath) throws IOException {
        final File folder = toFile(directoryPath);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                recursiveLoad(imageList, new DirectoryPath(fileEntry.getPath())); // recursive call
            } else if (isFileExists(fileEntry.toPath())
                    && isValidImageFile(fileEntry.toPath())) {
                imageList.add(Paths.get(fileEntry.getPath()));
            }
        }
    }


    /**
     * Builds a new file path in string representation based on the given parameters
     *
     * @param newDirectoryPath New directory path of the file.
     * @param fileLabel The label of the file.
     * @param initialPath The initial path of the file.
     * @return
     */
    public static String buildFilePath(Path newDirectoryPath, String fileLabel, Path initialPath) {
        StringBuilder newFilePath = new StringBuilder();
        newFilePath
                .append(newDirectoryPath)
                .append("/")
                .append(fileLabel)
                .append(".")
                .append(getExtension(initialPath).orElse(""));
        return newFilePath.toString();
    }

    private static int incrementFileLabel(int fileLabel) {
        return fileLabel + 1;
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String, String...)}},
     * otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String, String...)}
     * and {@link Files#exists(Path, LinkOption...)}, otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidDirectoryPath(String path) {
        return Files.isDirectory(Paths.get(path));
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file cannot be created.
     */
    public static void createFileIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a directory if it is missing.
     *
     * @throws IOException if the directory cannot be created.
     */
    public static void createDirectoryIfMissing(Path directoryPath) throws IOException {
        if (!isFileExists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }
        createParentDirsOfFile(file);
        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Gets the canonical path of the folder where the application is.
     */
    public static Path getApplicationFolderPath(String folderName) throws IOException {
        String canonicalPath = new File(".").getCanonicalPath();
        Path folderDirectoryPath = Paths.get(canonicalPath, folderName);
        createDirectoryIfMissing(folderDirectoryPath);
        return folderDirectoryPath;
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Returns a randomly generated UUID String
     */
    public static String generateUuidString() {
        return UUID.randomUUID().toString();
    }

    /**
     * Copies the file from a directory to another directory.
     *
     * @param from the source
     * @param to   the destination
     * @throws IOException if the source file does not exist.
     */
    public static void copy(Path from, Path to) throws IOException {
        if (isFileExists(from)) {
            createParentDirsOfFile(to);
            Files.copy(from, to);
        } else {
            throw new IOException(MESSAGE_COPY_FAILURE_SOURCE_DOES_NOT_EXIST);
        }
    }

    /**
     * Copies the file from a given InputStream to a file path.
     *
     * @param from the source
     * @param to   the destination
     * @throws IOException if the copy failed
     */
    public static void copy(InputStream from, Path to) throws IOException {
        createParentDirsOfFile(to);
        Files.copy(from, to);
    }

    public static File toFile(DirectoryPath directoryPath) {
        return new File(directoryPath.toString());
    }

    /**
     * Checks if the given filePath represents a valid image file.
     *
     * @param filePath Given file path.
     * @return True if the file is a valid image file path.
     * @throws IOException Unexpected error encountered from probeContentType().
     */
    public static boolean isValidImageFile(Path filePath) throws IOException {
        return !(new Image(filePath.toUri().toURL().toString()).isError());
    }

    /**
     * Gets the extension of {@code Path}.
     *
     * @param path the {@code Path} to extract the extension from
     * @return the extension if present, or {@code Optional#empty()} if there is none
     */
    public static Optional<String> getExtension(Path path) {
        String pathString = path.toString();
        if (pathString.contains(".")) {
            return Optional.of(pathString.substring(pathString.lastIndexOf(".") + 1));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets the filename of a path represented by a string.
     * @param pathString the String to extract the file name from
     * @return the filename
     */
    public static String getFileName(String pathString) {
        return Paths.get(pathString).getFileName().toString();
    }

}
