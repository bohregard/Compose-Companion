package com.bohregard.shared.compose.exoplayer

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.staticCompositionLocalOf
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import java.io.File

val LocalDataStoreCache = staticCompositionLocalOf<DataStoreCache> {
    error("Cache must be defined")
}

class DataStoreCache(
    private val context: Context,
    @StringRes private val appName: Int
) : DataSource.Factory {

    private val maxCacheSize = 100 * 1024 * 1024L
    private val maxFileSize = 5 * 1024 * 1024L

    private val dataSource by lazy {
        val bm = DefaultBandwidthMeter.Builder(context)
            .build()
        val userAgent = Util.getUserAgent(context, context.getString(appName))
        DefaultDataSourceFactory(
            context,
            bm,
            DefaultHttpDataSource.Factory()
                .setUserAgent(userAgent)
                .setTransferListener(bm)
        )
    }

    private val simpleCache by lazy {
        SimpleCache(
            File(context.cacheDir, "exo_player_cache"),
            LeastRecentlyUsedCacheEvictor(maxCacheSize),
            ExoDatabaseProvider(context)
        )
    }

    override fun createDataSource(): DataSource {
        return CacheDataSource(
            simpleCache,
            dataSource.createDataSource(),
            FileDataSource(),
            CacheDataSink(simpleCache, maxFileSize),
            CacheDataSource.FLAG_BLOCK_ON_CACHE or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
            null
        )
    }
}