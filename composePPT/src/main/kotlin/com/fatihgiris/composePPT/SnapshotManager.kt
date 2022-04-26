package com.fatihgiris.composePPT

import androidx.compose.runtime.snapshots.Snapshot
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Manages registering the callback for the global snapshot states and sending an apply
 * notification for each callback checking if has not started with [started]. You typically
 * call [ensureStarted] before creating a composition. This is a slightly different implementation
 * of androidx.compose.ui.platform.GlobalSnapshotManager.
 */
internal object SnapshotManager {
    private val started = AtomicBoolean(false)

    /**
     * Registers an observer to global snapshot states and sends an apply notification
     * for each callback if it has not already started.
     */
    suspend fun ensureStarted() = coroutineScope {
        if (started.compareAndSet(false, true)) {
            val channel = Channel<Unit>(Channel.CONFLATED)
            launch {
                channel.consumeEach {
                    Snapshot.sendApplyNotifications()
                }
            }
            Snapshot.registerGlobalWriteObserver {
                channel.trySend(Unit)
            }
        }
    }
}