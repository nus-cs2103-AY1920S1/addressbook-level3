import os
import sys
import tempfile
from random import shuffle

# takes all `.jpg` files in target directory, shuffles them,
# then renames them with 3-digit numbers starting from `index`
def seq_rename_all_files(file_dir, index=0):
    JPEG_SUFFIX = ".jpg"
    paths = os.listdir(file_dir)

    for filename in paths:
        if filename.endswith(JPEG_SUFFIX):
            temp_filename = os.path.join(file_dir, next(tempfile._get_candidate_names()) + JPEG_SUFFIX)
            old_filename = os.path.join(file_dir, filename)
            os.rename(old_filename, temp_filename)
            print("Found", old_filename)

    paths = os.listdir(file_dir)
    shuffle(paths)

    for filename in paths:
        if filename.endswith(JPEG_SUFFIX):
            new_filename = os.path.join(file_dir, ("%03d" % index) + JPEG_SUFFIX)
            old_filename = os.path.join(file_dir, filename)
            os.rename(old_filename, new_filename)
            print("Renamed to", new_filename)
            index += 1


# Driver Code
if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Usage: python shuffling_renamer.py <folder_name_containing_images>")
        sys.exit()
    seq_rename_all_files(sys.argv[1])

