package com.tibagni.logviewer

import com.tibagni.logviewer.log.FileLogReader
import com.tibagni.logviewer.log.LogEntry
import com.tibagni.logviewer.log.LogReaderException
import com.tibagni.logviewer.log.LogStream
import com.tibagni.logviewer.log.parser.LogParser
import com.tibagni.logviewer.log.parser.LogParserException
import java.io.File

class OpenLogsException(message: String?, cause: Throwable): java.lang.Exception(message, cause)

interface LogsRepository {
    val currentlyOpenedLogFiles: List<File>
    val currentlyOpenedLogs: List<LogEntry>
    val availableStreams: Set<LogStream>

    @Throws(OpenLogsException::class)
    fun openLogFiles(files: Array<File>, progressReporter: ProgressReporter)
}

class LogsRepositoryImpl: LogsRepository {
    private val _currentlyOpenedLogFiles = mutableListOf<File>()
    override val currentlyOpenedLogFiles: List<File>
        get() = _currentlyOpenedLogFiles

    private val _currentlyOpenedLogs = mutableListOf<LogEntry>()
    override val currentlyOpenedLogs: List<LogEntry>
        get() = _currentlyOpenedLogs

    private val _availableStreams = hashSetOf<LogStream>()
    override val availableStreams: Set<LogStream>
        get() = _availableStreams

    @Throws(OpenLogsException::class)
    override fun openLogFiles(files: Array<File>, progressReporter: ProgressReporter) {
        try {
            val logParser = LogParser(FileLogReader(files), progressReporter)
            val parsedLogs = logParser.parseLogs()

            _currentlyOpenedLogs.reset(parsedLogs)
            _availableStreams.reset(logParser.availableStreams)

            if (parsedLogs.isNotEmpty()) {
                _currentlyOpenedLogFiles.reset(files)
            } else {
                _currentlyOpenedLogFiles.clear()
            }

            logParser.release()
        } catch (e: Exception) {
            when (e) {
                is LogReaderException,
                is LogParserException -> {
                    throw OpenLogsException(e.message, e)
                }
                else -> throw e
            }
        }
    }
}