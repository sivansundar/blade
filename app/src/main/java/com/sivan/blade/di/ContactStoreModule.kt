package com.sivan.blade.di

import android.content.Context
import com.alexstyl.contactstore.ContactStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactStoreModule {

    @Singleton
    @Provides
    fun provideContactStore(
        @ApplicationContext context: Context
    ): ContactStore {
        return ContactStore.newInstance(context)
    }
}
