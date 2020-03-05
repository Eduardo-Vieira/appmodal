package com.br.appmodal.di

import androidx.room.Room
import com.br.appmodal.database.AppDatabase
import com.br.appmodal.network.AuthInterceptor
import com.br.appmodal.network.provideGithubapi
import com.br.appmodal.network.provideOkHttpClient
import com.br.appmodal.network.provideRetrofit
import com.br.appmodal.repository.RepoRepository
import com.br.appmodal.repository.local.RepoLocal
import com.br.appmodal.repository.remote.RepoRemote
import com.br.appmodal.ui.fragment.repo.RepoViewModel
import com.br.appmodal.ui.fragment.repo.detail.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val NOME_BANCO_DE_DADOS = "appmodaldb.db"

val appModule = module {
    single { RepoRemote(get()) }
    single { RepoLocal(get(), get(), get()) }
    factory { RepoRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { RepoViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val networkModule = module {

    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideGithubapi(get()) }
    single { provideRetrofit(get()) }
}
val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }
}

val daoModule = module {
    single { get<AppDatabase>().userRepoDao() }
    single { get<AppDatabase>().readmeDao() }
    single { get<AppDatabase>().pages() }
}