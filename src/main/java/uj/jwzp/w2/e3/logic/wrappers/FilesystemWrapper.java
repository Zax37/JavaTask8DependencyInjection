package uj.jwzp.w2.e3.logic.wrappers;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FilesystemWrapper {
    public boolean pathExists(Path path) {
        return Files.exists(path);
    }

    public void ensurePath(Path path) throws IOException {
        Files.createDirectories(path);
    }

    public FileOutputStream requestFile(Path filePath) throws IOException {
        return new FileOutputStream(filePath.toFile());
    }
}
