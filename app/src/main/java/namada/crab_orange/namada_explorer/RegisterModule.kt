package namada.crab_orange.namada_explorer

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import namada.crab_orange.namada_explorer.apis.IndexerApis
import namada.crab_orange.namada_explorer.apis.StakePoolApis
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {
    @Provides
    @Singleton
    fun registerOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun registerGson(): Gson =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    @Provides
    @Singleton
    fun registerIndexerApis(
        factory: Gson,
        okHttpClient: OkHttpClient
    ): IndexerApis = Retrofit.Builder()
        .baseUrl("https://indexer.craborange.homes/")
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpClient)
        .build()
        .create()

    @Provides
    @Singleton
    fun registerStakePoolApis(
        factory: Gson,
        okHttpClient: OkHttpClient
    ): StakePoolApis = Retrofit.Builder()
        .baseUrl("https://namada.api.explorers.guru/")
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpClient)
        .build()
        .create()

    @Singleton
    @Provides
    fun registerHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also { interceptor ->
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
}