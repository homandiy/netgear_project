package com.homan.huang.netgearmobiledeveloperexercise2021.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.dao.ImageDao
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ImageItem
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
class ImageDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var db: ImageManifestDatabase

    private lateinit var imageDao: ImageDao

    @Before
    fun setup() {
        hiltRule.inject()
        imageDao = db.imageDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    //region sample test data
    fun sample1(): ImageItem =
        ImageItem(1, "z", 140, "image", "jpg", "unknowA.jpg", 400)

    fun sample2(): ImageItem =
        ImageItem(2, "y", 140, "image", "png", "unknowB.png", 400)

    fun sample3(): ImageItem =
        ImageItem(3, "w", 140, "image", "gif", "unknowC.gif", 400)
    //endregion


    // Test insert() and getImageItem(code)
    @Test
    fun insert_ImagetItem_to_db() = runBlockingTest {
        val sample1 = sample1()
        imageDao.insert(sample1)

        val item = imageDao.getImageItem(sample1.code)
        assertThat(item.name).isEqualTo(sample1.name)
    }

    // Test Delete
    @Test
    fun delete_from_db() = runBlockingTest {
        val sample1 = sample1()

        imageDao.insert(sample1)
        imageDao.delete(sample1)

        val item = imageDao.getImageItem(sample1.code)
        assertThat(item).isNull()
    }

    // Test delete all and count
    @Test
    fun delete_all_from_db() = runBlockingTest {
        imageDao.insert(sample1())
        imageDao.insert(sample2())
        imageDao.insert(sample3())

        var count = imageDao.countCategory()
        assertThat(count).isEqualTo(3)

        imageDao.deleteAll()

        count = imageDao.countCategory()
        assertThat(count).isEqualTo(0)
    }


}