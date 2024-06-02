package com.mir.testermodule.presentation.home

import com.mir.testermodule.data.dynamicviewdto.DynamicViewPageResponse

/**
Created by Shitab Mir on 2/6/24.
shitabmir@gmail.com
 **/
object HomePageCache {
    var homePageDataCache: DynamicViewPageResponse? = null
    var lastCacheTimestamp: Long? = null

    const val CACHE_INTERVAL: Long = 10000
}