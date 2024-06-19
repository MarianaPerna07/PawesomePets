package pt.ua.deti.icm.pawesomepets

import android.content.Context
import pt.ua.deti.icm.pawesomepets.data.room.PawesomePetsDB
import pt.ua.deti.icm.pawesomepets.ui.repository.Repository

object Graph {
    lateinit var db:PawesomePetsDB
        private set

    val repository by lazy {
        Repository(
            listDao = db.listDao(),
            breedDao = db.breedDao(),
            petDao = db.petDao()
        )
    }

    fun provide(context:Context){
        db = PawesomePetsDB.getDatabase(context)
    }










}