package net.azarquiel.marvelcompose.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginDS {
    private val Context.loginDS by preferencesDataStore(name = "login")

    @Singleton
    @Provides
    fun provideLoginDS(@ApplicationContext context: Context) =
        context.loginDS


    object Utils {
        suspend fun logout(loginDS: DataStore<Preferences>) {
            loginDS.edit {
                it.clear()
                it[Field.IsLoggedIn.key] = false
            }
        }
    }

    sealed class Field<T>(val key: Preferences.Key<T>) {
        object IsLoggedIn: Field<Boolean>(booleanPreferencesKey("isLoggedIn"))
        object Id: Field<String>(stringPreferencesKey("id"))
        object Nick: Field<String>(stringPreferencesKey("nick"))
    }
}
