package com.byted.camp.todolist.db

import android.provider.BaseColumns

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
object TodoContract {
    const val SQL_CREATE_ENTRIES = "create table ${TodoEntry.TABLE_NAME}(" +
            "${TodoEntry.COLUMN_NAME_ID} INTEGER primary key autoincrement," +
            "${TodoEntry.COLUMN_NAME_DATE} datetime default (datetime('now', 'localtime'))," +
            "${TodoEntry.COLUMN_NAME_STATE} INTEGER default 0," +
            "${TodoEntry.COLUMN_NAME_CONTENT} TEXT," +
            "${TodoEntry.COLUMN_NAME_PRIORITY} INTEGER default 0" +
            ")"
    const val SQL_DELETE_ENTRIES = "drop table if exists ${TodoEntry.TABLE_NAME}"

    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_STATE = "state"
        const val COLUMN_NAME_CONTENT = "content"
        const val COLUMN_NAME_PRIORITY = "priority"
    }
}