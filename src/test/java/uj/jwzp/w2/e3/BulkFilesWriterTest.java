package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import uj.jwzp.w2.e3.logic.wrappers.FilesystemWrapper;
import uj.jwzp.w2.e3.logic.writer.BulkFilesWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;

import static org.hamcrest.core.StringContains.containsString;

public class BulkFilesWriterTest {
    private final static String OUTPUT_PATH = "output";
    private final static String FORMAT = "xml";
    private final static int GENERATED_FILE_ID = 1;
    private final static String EXPECTED_PATH = "output/1.xml";

    @Test
    public void shouldCreateDirectoriesIfNeeded() {
        FilesystemWrapper filesystemWrapper = Mockito.mock(FilesystemWrapper.class);
        BulkFilesWriter uut = new BulkFilesWriter(filesystemWrapper, Paths.get(OUTPUT_PATH), FORMAT);

        try {
            Mockito.when(filesystemWrapper.pathExists(Paths.get(OUTPUT_PATH))).thenReturn(false);
            Mockito.doReturn(EXPECTED_PATH).when(filesystemWrapper).resolve(Mockito.any(), Mockito.anyString());

            uut.ensureOutDir();
            uut.getWriter(GENERATED_FILE_ID);

            Mockito.verify(filesystemWrapper, Mockito.atLeastOnce()).ensurePath(Paths.get(OUTPUT_PATH));
            Mockito.verify(filesystemWrapper).requestFile(EXPECTED_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void shouldNotCreateDirectoriesIfNotNeeded() {
        FilesystemWrapper filesystemWrapper = Mockito.mock(FilesystemWrapper.class);
        BulkFilesWriter uut = new BulkFilesWriter(filesystemWrapper, Paths.get(OUTPUT_PATH), FORMAT);

        try {
            Mockito.when(filesystemWrapper.pathExists(Paths.get(OUTPUT_PATH))).thenReturn(true);
            Mockito.doReturn(EXPECTED_PATH).when(filesystemWrapper).resolve(Mockito.any(), Mockito.anyString());

            uut.ensureOutDir();
            uut.getWriter(GENERATED_FILE_ID);

            Mockito.verify(filesystemWrapper, Mockito.never()).ensurePath(Paths.get(OUTPUT_PATH));
            Mockito.verify(filesystemWrapper).requestFile(EXPECTED_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void shouldLogAndRethrowWhenFailedCreatingDirectory() {
        PrintStream sysOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        FilesystemWrapper filesystemWrapper = Mockito.mock(FilesystemWrapper.class);
        BulkFilesWriter uut = new BulkFilesWriter(filesystemWrapper, Paths.get(OUTPUT_PATH), FORMAT);

        try {
            Mockito.doThrow(new IOException()).when(filesystemWrapper).ensurePath(Paths.get(OUTPUT_PATH));
            uut.ensureOutDir();
            Assert.fail();
        } catch (IOException e) {
            System.setOut(sysOut);
            Assert.assertThat(outContent.toString(), containsString("ERROR"));
            Assert.assertThat(outContent.toString(), containsString("Couldn't create directory output"));
        }
    }
}
