package net.azarquiel.marvelcompose.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import net.azarquiel.marvelcompose.model.Hero
import java.io.IOException
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HeroesDSProvider

@Module
@InstallIn(SingletonComponent::class)
object HeroesDS {
    private val Context.heroesDS by preferencesDataStore(name = "heroes")

    @HeroesDSProvider
    @Singleton
    @Provides
    fun provideHeroesDS(@ApplicationContext context: Context) =
        context.heroesDS

    object Utils {
        suspend fun storeHero(heroesDs: DataStore<Preferences>, hero: Hero) =
            stringPreferencesKey("${hero.id}").let { key ->
                if (!heroesDs.data.first().contains(key))
                    heroesDs.edit { it[key] = Gson().toJson(hero) }
            }

        fun getHeroFlow(heroesDs: DataStore<Preferences>, heroId: String) =
            heroesDs.data.catch {
                if (it is IOException) emit(emptyPreferences())
                else throw it
            }.map {
                it[stringPreferencesKey(heroId)]?.let { heroStr ->
                    Gson().fromJson(heroStr, Hero::class.java)
                }
            }
    }
}
