package com.tibagni.logviewer.log;

import java.util.Set;

public interface LogReader {
  void readLogs() throws LogReaderException;

  int size();

  String get(String logName);

  public Set<String> getAvailableLogsNames();
}