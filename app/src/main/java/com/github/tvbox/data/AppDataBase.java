package com.github.tvbox.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.github.tvbox.cache.Cache;
import com.github.tvbox.cache.CacheDao;
import com.github.tvbox.cache.VodCollect;
import com.github.tvbox.cache.VodCollectDao;
import com.github.tvbox.cache.VodRecord;
import com.github.tvbox.cache.VodRecordDao;


/**
 * 类描述:
 *
 * @author pj567
 * @since 2020/5/15
 */
@Database(entities = {Cache.class, VodRecord.class, VodCollect.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CacheDao getCacheDao();

    public abstract VodRecordDao getVodRecordDao();

    public abstract VodCollectDao getVodCollectDao();
}