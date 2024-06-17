package pt.ua.deti.icm.pawesomepets.di

import androidx.room.Room
import pt.ua.deti.icm.pawesomepets.authentication.FirebaseAuthRepository
import pt.ua.deti.icm.pawesomepets.database.PawesomePetsDatabase
import pt.ua.deti.icm.pawesomepets.repositories.DogsRepository
import pt.ua.deti.icm.pawesomepets.repositories.UsersRepository
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.SignInViewModel
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.SignUpViewModel
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.DogFormViewModel
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.DogsListViewModel
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.AppViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::DogFormViewModel)
    viewModelOf(::DogsListViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::AppViewModel)

}

val storageModule = module {
    singleOf(::DogsRepository)
    singleOf(::UsersRepository)
    singleOf(::FirebaseAuthRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            PawesomePetsDatabase::class.java, "pawesome-pets.db"
        ).build()
    }
    single {
        get<PawesomePetsDatabase>().dogDao()
    }
}

val firebaseModule = module {
    single {
        Firebase.auth
    }
}