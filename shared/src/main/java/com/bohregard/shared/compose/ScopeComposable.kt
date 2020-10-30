package com.bohregard.shared.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.onDispose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

@Composable
fun Scope(children: @Composable CoroutineScope.() -> Unit) {
    val job = Job() + Dispatchers.IO
    val scope = CoroutineScope(job)

    children(scope)

    onDispose(callback = {
        job.cancel()
    })
}