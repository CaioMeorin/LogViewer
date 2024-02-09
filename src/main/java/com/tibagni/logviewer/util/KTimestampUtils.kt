package com.tibagni.logviewer.util

import com.tibagni.logviewer.log.LogEntry
import com.tibagni.logviewer.log.LogTimestamp
import com.tibagni.logviewer.view.SearchableTable

public fun timestampFromSelection(
                filteredLogList: SearchableTable,
                logList: SearchableTable
): LogTimestamp? {
        var ts: LogTimestamp? = null
        if (filteredLogList.table.hasFocus() && filteredLogList.table.selectedRow >= 0) {
                val selectedEntry =
                                filteredLogList.table.model.getValueAt(
                                                filteredLogList.table.selectedRow,
                                                filteredLogList.table.selectedColumn
                                ) as
                                                LogEntry
                ts = selectedEntry.timestamp
        } else if (logList.table.hasFocus() && logList.table.selectedRow >= 0) {
                val selectedEntry =
                                logList.table.model.getValueAt(
                                                logList.table.selectedRow,
                                                logList.table.selectedColumn
                                ) as
                                                LogEntry
                ts = selectedEntry.timestamp
        }
        return ts
}
