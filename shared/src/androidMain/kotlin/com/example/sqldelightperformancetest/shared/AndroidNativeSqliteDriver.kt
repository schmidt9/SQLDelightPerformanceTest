package com.example.sqldelightperformancetest.shared

import androidx.sqlite.db.SupportSQLiteStatement
import app.cash.sqldelight.Query
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlPreparedStatement

/**
 * https://github.com/BoD/sqldelight/blob/f887db3a96ea0be33460731638dc8ee90cb578e5/drivers/android-driver/src/main/java/app/cash/sqldelight/driver/android/AndroidSqliteDriver.kt
 */
class AndroidNativeSqliteDriver : SqlDriver {

    private val listeners = linkedMapOf<String, MutableSet<Query.Listener>>()

    override fun addListener(vararg queryKeys: String, listener: Query.Listener) {
        synchronized(listeners) {
            queryKeys.forEach {
                listeners[it]?.remove(listener)
            }
        }
    }

    override fun removeListener(vararg queryKeys: String, listener: Query.Listener) {
        synchronized(listeners) {
            queryKeys.forEach {
                listeners[it]?.remove(listener)
            }
        }
    }

    override fun notifyListeners(vararg queryKeys: String) {
        val listenersToNotify = linkedSetOf<Query.Listener>()
        synchronized(listeners) {
            queryKeys.forEach { listeners[it]?.let(listenersToNotify::addAll) }
        }
        listenersToNotify.forEach(Query.Listener::queryResultsChanged)
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun currentTransaction(): Transacter.Transaction? {
        TODO("Not yet implemented")
    }

    override fun execute(
        identifier: Int?,
        sql: String,
        parameters: Int,
        binders: (SqlPreparedStatement.() -> Unit)?
    ): QueryResult<Long> {
        TODO("Not yet implemented")
    }

    override fun <R> executeQuery(
        identifier: Int?,
        sql: String,
        mapper: (SqlCursor) -> QueryResult<R>,
        parameters: Int,
        binders: (SqlPreparedStatement.() -> Unit)?
    ): QueryResult<R> {
        TODO("Not yet implemented")
    }

    override fun newTransaction(): QueryResult<Transacter.Transaction> {
        TODO("Not yet implemented")
    }

}

internal interface AndroidStatement : SqlPreparedStatement {
    fun execute(): Long
    fun <R> executeQuery(mapper: (SqlCursor) -> QueryResult<R>): R
    fun close()
}

private class AndroidPreparedStatement(
    private val statement: SupportSQLiteStatement,
) : AndroidStatement {
    override fun bindBytes(index: Int, bytes: ByteArray?) {
        if (bytes == null) statement.bindNull(index + 1) else statement.bindBlob(index + 1, bytes)
    }

    override fun bindLong(index: Int, long: Long?) {
        if (long == null) statement.bindNull(index + 1) else statement.bindLong(index + 1, long)
    }

    override fun bindDouble(index: Int, double: Double?) {
        if (double == null) statement.bindNull(index + 1) else statement.bindDouble(index + 1, double)
    }

    override fun bindString(index: Int, string: String?) {
        if (string == null) statement.bindNull(index + 1) else statement.bindString(index + 1, string)
    }

    override fun bindBoolean(index: Int, boolean: Boolean?) {
        if (boolean == null) {
            statement.bindNull(index + 1)
        } else {
            statement.bindLong(index + 1, if (boolean) 1L else 0L)
        }
    }

    override fun <R> executeQuery(mapper: (SqlCursor) -> QueryResult<R>): R = throw UnsupportedOperationException()

    override fun execute(): Long {
        return statement.executeUpdateDelete().toLong()
    }

    override fun close() {
        statement.close()
    }
}