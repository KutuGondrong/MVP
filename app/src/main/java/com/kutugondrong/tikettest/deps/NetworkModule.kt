package com.kutugondrong.tikettest.deps

import android.content.Context

import com.github.aurae.retrofit2.LoganSquareConverterFactory
import com.google.gson.Gson
import com.kutugondrong.network.networks.Endpoint
import com.kutugondrong.network.networks.NetworkEndpoint
import com.kutugondrong.network.networks.NetworkService
import com.kutugondrong.network.networks.Service
import com.kutugondrong.network.rx.SchedulerProvider
import com.kutugondrong.network.sdk.NetworkSDK
import com.kutugondrong.tikettest.R
import com.readystatesoftware.chuck.ChuckInterceptor

import java.io.File
import java.util.concurrent.TimeUnit

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptorLevel(context: Context): HttpLoggingInterceptor.Level {
        val level = context.getString(R.string.okhttp_log_level)
        return when (level) {
            "NONE" -> HttpLoggingInterceptor.Level.NONE
            "BASIC" -> HttpLoggingInterceptor.Level.BASIC
            "HEADERS" -> HttpLoggingInterceptor.Level.HEADERS
            "BODY" -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @Named("GsonService")
    internal fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(logLevel: HttpLoggingInterceptor.Level): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(logLevel)
    }


    @Provides
    @Singleton
    fun providesChuckInterceptor(context: Context): ChuckInterceptor {
        return ChuckInterceptor(context)
    }

    @Provides
    @Singleton
    @Named("AuthOkHttp")
    fun providesAuthOkHttpClient(context: Context,
                                 loggingInterceptor: HttpLoggingInterceptor,
                                 chuckInterceptor: ChuckInterceptor): OkHttpClient {
        val cacheDir = File(context.cacheDir, "http-auth_cache")
        val cache = Cache(cacheDir, (1024 * 1024 * 10).toLong())

        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        val level = context.getString(R.string.okhttp_log_level)
        return when (level) {
            "BODY" -> OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chuckInterceptor)
                .cache(cache)
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .build()
            else -> OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .build()
        }
    }

    @Provides
    @Singleton
    @Named("Endpoint")
    internal fun provideEndpoint(context: Context): Endpoint {
        return NetworkEndpoint(context)
    }

    @Provides
    @Singleton
    @Named("Retrofit")
    internal fun provideRetrofit(@Named("AuthOkHttp") okHttpClient: OkHttpClient,
                                 @Named("Endpoint") endpoint: Endpoint): Retrofit {
        return Retrofit.Builder()
                .baseUrl(endpoint.endpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideService(@Named("Retrofit") retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }

    @Provides
    @Singleton
    internal fun provideNetworkService(service: Service): NetworkService {
        return NetworkService(service, SchedulerProvider.getInstance())
    }


    @Provides
    @Singleton
    internal fun provideNetworkSDK(service: NetworkService): NetworkSDK {
        return NetworkSDK(service)
    }

}
