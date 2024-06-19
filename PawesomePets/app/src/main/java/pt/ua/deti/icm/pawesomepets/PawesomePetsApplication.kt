package pt.ua.deti.icm.pawesomepets

import android.app.Application
import pt.ua.deti.icm.pawesomepets.di.appModule
import pt.ua.deti.icm.pawesomepets.di.firebaseModule
import pt.ua.deti.icm.pawesomepets.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PawesomePetsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Graph.provide(this)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PawesomePetsApplication)
            modules(
                appModule,
                storageModule,
                firebaseModule
            )
        }
    }


}