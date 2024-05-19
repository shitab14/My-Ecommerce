package com.mir.myecommerce.di

import com.mir.myecommerce.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
Created by Shitab Mir on 19/5/24.
shitabmir@gmail.com
 **/

@Module
@InstallIn(SingletonComponent::class)
class NetworkDIModule {
 private val BASE_URL = "https://shitab14.github.io/"

 @Singleton
 @Provides
 @Named("base_okhttp")
 fun provideOkhttp(
  cache: Cache,
  @Named("logging-interceptor") interceptorLogging: Interceptor,
  @Named("common-interceptor") interceptorCommon: Interceptor,
//        @Named("app-update-interceptor") interceptorAppUpdate: Interceptor,
  authenticator: Authenticator
 ): OkHttpClient {

  val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
   override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
   }

   override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
   }

   override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
  })
  val sslContext = SSLContext.getInstance("SSL")
  sslContext.init(null, trustAllCerts, java.security.SecureRandom())

  // Create an ssl socket factory with our all-trusting manager
  val sslSocketFactory = sslContext.socketFactory

  return OkHttpClient.Builder()
   .addInterceptor(interceptorLogging)
   .addInterceptor(interceptorCommon)
//            .addInterceptor(interceptorAppUpdate)
   .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
   .hostnameVerifier { _, _ -> true }
   .authenticator(authenticator)
   .connectTimeout(30, TimeUnit.SECONDS)
   .readTimeout(30, TimeUnit.SECONDS)
   .writeTimeout(30, TimeUnit.SECONDS)
   .cache(cache)
   .build()


 }

 @Singleton
 @Provides
 fun provideRetrofit(
//  client: OkHttpClient
 ): Retrofit {
  return Retrofit.Builder()
   .addConverterFactory(GsonConverterFactory.create())
//   .client(client)
   .baseUrl(BASE_URL)
   .build()
 }

 @Singleton
 @Provides
 fun provideApiService(
  retrofit: Retrofit
 ): ApiService {
  return retrofit.create(ApiService::class.java)
 }

}