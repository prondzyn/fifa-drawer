package com.prondzyn.fifadrawer.io;

import com.prondzyn.fifadrawer.utils.StringUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements Closeable {

  private static final int LINES_SKIPPED = 1;

  private final au.com.bytecode.opencsv.CSVReader internalReader;
  private int lineNumber = LINES_SKIPPED;

  public CSVReader(Reader reader) {
    if (reader == null) {
      throw new IllegalArgumentException("Reader cannot be null.");
    }
    internalReader = new au.com.bytecode.opencsv.CSVReader(reader, ',', '"', LINES_SKIPPED);
  }

  public List<String> readNext() throws IOException {
    String[] line = internalReader.readNext();
    if (line == null) {
      return null;
    }
    lineNumber += 1;
    if (isBlankLine(line)) {
      return readNext();
    }
    List<String> values = new ArrayList<>();
    for (String element : line) {
      values.add(StringUtils.trim(element));
    }
    return values;
  }

  private boolean isBlankLine(String[] line) {
    return line.length == 1 && StringUtils.trimToNull(line[0]) == null;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  @Override
  public void close() throws IOException {
    internalReader.close();
  }
}
