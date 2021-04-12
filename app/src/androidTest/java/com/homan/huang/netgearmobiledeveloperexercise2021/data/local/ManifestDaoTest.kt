package com.homan.huang.netgearmobiledeveloperexercise2021.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.dao.ManifestDao
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ManifestDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var db: ImageManifestDatabase

    private lateinit var manifestDao: ManifestDao

    @Before
    fun setup() {
        hiltRule.inject()
        manifestDao = db.manifestDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    //region sample test data
    fun sample1(): ManifestData =
        ManifestData(1, 22, "Group 22", "imageA")

    fun sample2(): ManifestData =
        ManifestData(2, 24, "Group 24", "imageB")

    fun sample3(): ManifestData =
        ManifestData(3, 25, "Group 25", "imageC")


    // Test insert() and getAll()
    @Test
    fun insert_ImagetItem_to_db() = runBlockingTest {
        val sample1 = sample1()
        manifestDao.insert(sample1)

        val mList = manifestDao.getAll()

        assertThat(mList.contains(sample1)).isTrue()
    }

    // Test Delete
    @Test
    fun delete_from_db() = runBlockingTest {
        val sample2 = sample2()

        manifestDao.insert(sample1())
        manifestDao.insert(sample2())
        manifestDao.insert(sample3())

        manifestDao.delete(sample2)

        val mList = manifestDao.getAll()

        assertThat(mList.contains(sample2)).isFalse()
    }

    // Test delete all and count
    @Test
    fun delete_all_from_db() = runBlockingTest {
        manifestDao.insert(sample1())
        manifestDao.insert(sample2())
        manifestDao.insert(sample3())

        var count = manifestDao.countCategory()
        assertThat(count).isEqualTo(3)

        manifestDao.deleteAll()

        count = manifestDao.countCategory()
        assertThat(count).isEqualTo(0)
    }


}