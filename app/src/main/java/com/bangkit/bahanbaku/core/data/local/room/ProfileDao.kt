package com.bangkit.bahanbaku.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.bahanbaku.core.data.local.entity.ProfileEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile LIMIT 1")
    fun getProfile(): Flowable<List<ProfileEntity>>

    @Query("DELETE FROM profile")
    fun deleteAllProfile()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: ProfileEntity): Completable
}