package ikish.aftab.ws.android.di

import android.app.Application
import android.content.Context
import androidx.room.Room

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.securepreferences.SecurePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ikish.aftab.ws.android.fetchData.TodoDao
import ikish.aftab.ws.android.classes.retrofit.API
import ikish.aftab.ws.android.db.RoomDataBase
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


   @Provides
   @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context?): SecurePreferences {
        return SecurePreferences(
            context,
            "7d1daf18d3716f8c60b50beadb5bf314c6618140ffaf5223bcffec5bcce09322",
            "Ikish_Pref"
        )
    }


    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        client.readTimeout(30, TimeUnit.SECONDS)
        client.connectTimeout(30, TimeUnit.SECONDS)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://ikish.aftabproducts.com/api/v1/")
            .client(okHttpClient)
            .build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }


    @Provides
    fun provideActivityDao(todoDatabase: RoomDataBase): TodoDao{
        return todoDatabase.todoDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RoomDataBase {
        return Room.databaseBuilder(
            appContext,
            RoomDataBase::class.java,
            "IKish"
        ).build()
    }


}