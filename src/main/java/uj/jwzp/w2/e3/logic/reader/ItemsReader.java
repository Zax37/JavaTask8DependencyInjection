package uj.jwzp.w2.e3.logic.reader;

import uj.jwzp.w2.e3.model.Item;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface ItemsReader extends Closeable {
    List<Item> read() throws IOException;
}
