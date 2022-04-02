package com.naipofo.archiveclient.common.di

import com.naipofo.archiveclient.common.data.remote.archiveorgheadlines.HeadlinesApi
import com.naipofo.archiveclient.common.data.remote.archiveorgheadlines.RemoteHeadlinesRepository
import com.naipofo.archiveclient.common.data.remote.archiveorgsearch.RemoteSearchRepository
import com.naipofo.archiveclient.common.data.remote.archiveorgsearch.SearchApi
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.serialization.kotlinx.json.*
import io.realm.Realm
import io.realm.RealmConfiguration
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

    bindSingleton("db_config") { RealmConfiguration.Builder(
        setOf()
    ).build() }

    bindSingleton { Realm.open(instance("db_config")) }

    bindSingleton { HeadlinesApi(instance()) }
    bindSingleton { RemoteHeadlinesRepository(instance()) }

    bindSingleton { SearchApi(instance()) }
    bindSingleton { RemoteSearchRepository(instance()) }
}