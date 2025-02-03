package com.pasantiago.composesqllite


import com.example.sqllitecomposecine.model.ormroom.RoomDB
import com.example.sqllitecomposecine.model.repositorios.APeliculaRepositorio
import com.example.sqllitecomposecine.model.repositorios.ASalaRepositorio
import com.example.sqllitecomposecine.model.repositorios.ASesionRepositorio
import com.example.sqllitecomposecine.model.repositorios.memoria.PeliculaRepositioEnMemoria
import com.example.sqllitecomposecine.model.repositorios.memoria.SalaRepositioEnMemoria
import com.example.sqllitecomposecine.model.repositorios.memoria.SesionRepositioEnMemoria
import com.example.sqllitecomposecine.model.repositorios.nativo.PeliculaRepositoryNativo
import com.example.sqllitecomposecine.model.repositorios.nativo.SalaRepositoryNativo
import com.example.sqllitecomposecine.model.repositorios.nativo.SesionRepositoryNativo
import com.example.sqllitecomposecine.model.repositorios.room.PeliculaRepositoryRoom
import com.example.sqllitecomposecine.model.repositorios.room.SalaRepositoryRoom
import com.example.sqllitecomposecine.model.sqlnativo.AplicacionSQLiteOpenHelper
import com.example.sqllitecomposecine.ui.viewmodels.PeliculaViewModel
import com.example.sqllitecomposecine.ui.viewmodels.SalaViewModel
import com.example.sqllitecomposecine.ui.viewmodels.SesionViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    /* repositorios */
    //en memoria
    /*single<ASalaRepositorio> { SalaRepositioEnMemoria() }*/
    single<APeliculaRepositorio> { PeliculaRepositioEnMemoria() }
    single<ASesionRepositorio>{ SesionRepositioEnMemoria(get(),get()) }

    //nativo
    //single<ASalaRepositorio> { SalaRepositoryNativo() }
    //single<APeliculaRepositorio> { PeliculaRepositoryNativo() }
    //single<ASesionRepositorio> { SesionRepositoryNativo() }


    //room
    single<ASalaRepositorio>{ SalaRepositoryRoom(RoomDB.getDatabase((androidContext()))) }
    single<APeliculaRepositorio>{ PeliculaRepositoryRoom(RoomDB.getDatabase((androidContext()))) }

    viewModel { SalaViewModel(get()) }
    viewModel { PeliculaViewModel(get()) }
    viewModel { SesionViewModel(get()) }

    /* single{ Session()}*/
}