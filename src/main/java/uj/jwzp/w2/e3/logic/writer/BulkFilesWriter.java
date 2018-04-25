package uj.jwzp.w2.e3.logic.writer;

import lombok.Getter;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import uj.jwzp.w2.e3.logic.wrappers.FilesystemWrapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@Lazy
@Component
@NonFinal
public class BulkFilesWriter {
    private FilesystemWrapper filesystemWrapper;

    @Getter
    private Path outDir;

    @Getter
    private String format;

    @Autowired
    public BulkFilesWriter(
            FilesystemWrapper filesystemWrapper,
            @Value("${outDir}") Path outDir,
            @Value("${format}") String format
    ) {
        this.filesystemWrapper = filesystemWrapper;
        this.outDir = outDir;
        this.format = format;
    }

    public void ensureOutDir() throws IOException {
        if (!filesystemWrapper.pathExists(outDir)) {
            log.trace("Creating output directory.");
            try {
                filesystemWrapper.ensurePath(outDir);
            } catch (IOException e) {
                log.error("Couldn't create directory " + outDir);
                throw e;
            }
        }
    }

    public Path getFilePath(int idForFileName) {
        return outDir.resolve(idForFileName + "." + format);
    }

    public FileOutputStream getWriter(int idForFileName) throws IOException {
        return filesystemWrapper.requestFile(getFilePath(idForFileName));
    }
}
