package x.ctrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

class SourceCodeFiles {

    private final Stream<Path> files;
    private final KnownSourceFileType type;

    SourceCodeFiles(Path directory, KnownSourceFileType type) throws IOException {
        if (!directory.toFile().isDirectory()) {
            System.err.println(directory + " is not a directory");
            System.exit(1610121905);
        }
        this.files = Files.find(directory, 999, this::isFileOfInterest);
        this.type = type;
    }

    private boolean isFileOfInterest(Path path, BasicFileAttributes basicFileAttributes) {
        return basicFileAttributes.isRegularFile() && path.getFileName().toString().matches("^.+\\." + type.getSuffix() + "$") && path.toFile().canRead() && path.toFile().canWrite();
    }

    void forEach(Consumer<SourceCodeFile> consumer) throws IOException {
        files.parallel().forEach(f -> consumer.accept(new SourceCodeFile(f)));
    }
}