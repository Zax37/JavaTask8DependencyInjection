package uj.jwzp.w2.e3;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import uj.jwzp.w2.e3.logic.writer.BulkFilesWriter;
import uj.jwzp.w2.e3.logic.writer.TransactionWriter;
import uj.jwzp.w2.e3.model.Transaction;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.core.StringContains.containsString;

public class TransactionWriterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream sysOut;

    @Before
    public void setUpStreams() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldLogAndRethrowIOException() throws IOException {
        Transaction transaction = Mockito.mock(Transaction.class);
        BulkFilesWriter writer = Mockito.mock(BulkFilesWriter.class);
        TransactionWriter uut = new TransactionWriter(writer) {
            @Override
            public String parseTransaction(Transaction transaction) {
                return null;
            }
        };

        Mockito.when(writer.getFormat()).thenReturn("any");
        Mockito.when(writer.getWriter(Mockito.anyInt())).thenThrow(new FileNotFoundException());

        uut.write(transaction);

        Assert.assertThat(outContent.toString(), containsString("ERROR"));
    }

    @After
    public void restoreStreams() {
        System.setOut(sysOut);
    }
}
