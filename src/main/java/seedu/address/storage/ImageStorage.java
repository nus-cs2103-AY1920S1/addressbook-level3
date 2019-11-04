package seedu.address.storage;


import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.util.FileUtil;

public class ImageStorage {

	private Path path;

	public ImageStorage(Path path) {
		this.path = path;
	}

	public Path getImagePath() {
		return this.path;
	}

	public void createImageFile() throws IOException {
		FileUtil.createIfMissing(this.path);
	}

//	public void copyFile() throws IOException {
//		FileUtil.copy(getClass().getResourceAsStream("/images/default.png"),  path.toString());
//	}
}
