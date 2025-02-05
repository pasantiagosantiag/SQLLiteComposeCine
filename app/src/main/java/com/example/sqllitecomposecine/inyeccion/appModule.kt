package com.pasantiago.composesqllite



import com.example.sqllitecomposecine.model.ormroom.RoomDB
import com.example.sqllitecomposecine.model.repositorios.APeliculaRepositorio
import com.example.sqllitecomposecine.model.repositorios.ASalaRepositorio
import com.example.sqllitecomposecine.model.repositorios.ASesionRepositorio
import com.example.sqllitecomposecine.model.repositorios.room.PeliculaRepositoryRoom
import com.example.sqllitecomposecine.model.repositorios.room.SalaRepositoryRoom
import com.example.sqllitecomposecine.model.repositorios.room.SesionRepositoryRoom

import com.example.sqllitecomposecine.ui.viewmodels.PeliculaViewModel
import com.example.sqllitecomposecine.ui.viewmodels.SalaViewModel
import com.example.sqllitecomposecine.ui.viewmodels.SesionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    /* repositorios */
    //en memoria
    /*single<ASalaRepositorio> { SalaRepositioEnMemoria() }*/
    //single<APeliculaRepositorio> { PeliculaRepositioEnMemoria() }
    //single<ASesionRepositorio>{ SesionRepositioEnMemoria(get(),get()) }

    //nativo
    //single<ASalaRepositorio> { SalaRepositoryNativo() }
    //single<APeliculaRepositorio> { PeliculaRepositoryNativo() }
    //single<ASesionRepositorio> { SesionRepositoryNativo() }

    //room
    single<ASalaRepositorio>{ SalaRepositoryRoom(RoomDB.getDatabase((androidContext()))) }
    single<APeliculaRepositorio>{ PeliculaRepositoryRoom(RoomDB.getDatabase((androidContext()))) }
    single<ASesionRepositorio>{ SesionRepositoryRoom(RoomDB.getDatabase((androidContext()))) }


    viewModel { SalaViewModel(get()) }
    viewModel { PeliculaViewModel(get(),androidContext()) }
    viewModel { SesionViewModel(get()) }

    /* single{ Session()}*/
}