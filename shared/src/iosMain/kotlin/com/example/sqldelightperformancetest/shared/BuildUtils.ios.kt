package com.example.sqldelightperformancetest.shared

import kotlin.experimental.ExperimentalNativeApi

/**
 * https://stackoverflow.com/a/64139443/3004003
 */
@OptIn(ExperimentalNativeApi::class)
actual val isDebug: Boolean
    get() = Platform.isDebugBinary