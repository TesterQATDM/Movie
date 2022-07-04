package com.ru.tdm.movie.network.base

import com.ru.tdm.movie.app.utils.BackendException
import com.ru.tdm.movie.app.utils.ConnectionException
import com.ru.tdm.movie.app.utils.ParseBackendResponseException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

open class BaseRetrofitSource(
    retrofitConfig: RetrofitConfig
) {

    val retrofit: Retrofit = retrofitConfig.retrofit

    private val moshi: Moshi = retrofitConfig.moshi
    private val errorAdapter = moshi.adapter(Root::class.java)

    /**
     * Map network and parse exceptions into in-app exceptions.
     * @throws ParseBackendResponseException
     * @throws ConnectionException
     */
    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        // moshi
        } catch (e: JsonDataException) {
            throw ParseBackendResponseException(e)
        } catch (e: JsonEncodingException) {
            throw ParseBackendResponseException(e)
        // retrofit
        } catch (e: HttpException) {
            throw createBackendException(e)
        // mostly retrofit
        } catch (e: IOException) {
            throw ConnectionException(e)
        }
    }

    private fun createBackendException(e: HttpException): Exception {
        return try {
            val errorBody: Root = errorAdapter.fromJson(
                e.response()!!.errorBody()!!.string()
            )!!
            BackendException(e.code(), errorBody.error.message)
        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }

    data class Error(
        var code: Int,
        var message: String
    )

    data class Root(
        var error: Error
    )
}