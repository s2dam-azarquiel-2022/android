package net.azarquiel.marvelcompose.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
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
        fun getIsLoggedInFlow(loginDS: DataStore<Preferences>) =
            loginDS.data.catch {
                if (it is IOException) emit(emptyPreferences())
                else throw it
            }.map { it[Field.IsLoggedIn.key] ?: false }

        suspend fun logout(loginDS: DataStore<Preferences>) =
            loginDS.edit {
                it.clear()
                it[Field.IsLoggedIn.key] = false
            }
    }

    sealed class Field<T>(val key: Preferences.Key<T>) {
        object IsLoggedIn : Field<Boolean>(booleanPreferencesKey("isLoggedIn"))
        object Id : Field<String>(stringPreferencesKey("id"))
        object Nick : Field<String>(stringPreferencesKey("nick"))
    }
}
