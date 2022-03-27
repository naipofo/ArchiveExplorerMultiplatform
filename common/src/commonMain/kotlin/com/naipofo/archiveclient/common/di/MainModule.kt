package com.naipofo.archiveclient.common.di

import com.naipofo.archiveclient.common.data.remote.archiveorgheadlines.HeadlinesApi
import com.naipofo.archiveclient.common.data.remote.archiveorgheadlines.RemoteHeadlinesRepository
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val mainModule = DI {
    bindSingleton {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    bindSingleton { HeadlinesApi(instance()) }
    bindSingleton { RemoteHeadlinesRepository(instance()) }
}