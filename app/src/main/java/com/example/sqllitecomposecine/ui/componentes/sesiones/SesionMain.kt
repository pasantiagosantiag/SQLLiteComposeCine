package com.example.sqllitecomposecine.ui.componentes.sesiones

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sqllitecomposecine.model.entidades.Sesion
import com.example.sqllitecomposecine.ui.componentes.peliculas.PeliculaBuscador
import com.example.sqllitecomposecine.ui.viewmodels.PeliculaViewModel
import com.example.sqllitecomposecine.ui.viewmodels.SalaViewModel
import com.example.sqllitecomposecine.ui.viewmodels.SesionViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SesionMain(
    modifier: Modifier = Modifier,
    vm: SesionViewModel = koinViewModel(),
    peliculasvm: PeliculaViewModel = koinViewModel(),
    salasvm: SalaViewModel = koinViewModel()
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Sesion>()
    val elementos = vm.items.collectAsState()
    val formularioEditable = remember {
        mutableStateOf(false)
    }
    val item = remember {
        mutableStateOf(vm.selected.value)
    }
    val isListAndDetailVisible =
        navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded && navigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded
    val searchview =
        navigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    Scaffold(
        floatingActionButton = {
            //if(searchview) {
            FloatingActionButton(onClick = {
                vm.unSelect()
                formularioEditable.value = true
                navigator.navigateTo(
                    ListDetailPaneScaffoldRole.Detail,
                    item.value
                )
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
            //}
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Column(modifier = Modifier.padding(horizontal = 2.dp)) {
                if (searchview) {

                    Text("Sesiones")

                    SesionBuscador(
                        modifier = Modifier,
                        vm.buscadorpelicula,
                        vm.buscadorsala,

                        )
                } else

                    Text("Editor de sesiones")

            }

            ListDetailPaneScaffold(modifier = Modifier,
                directive = navigator.scaffoldDirective,
                value = navigator.scaffoldValue,
                listPane = {



                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 32.dp)
                        ) {

                            items(elementos.value.size) {
                                SesionItem(elementos.value.get(it), ver = {
                                    run {
                                        vm.setSelected(elementos.value.get(it))
                                        //item.value=elementos.value.get(it).pelicula
                                        formularioEditable.value = false;

                                        navigator.navigateTo(
                                            ListDetailPaneScaffoldRole.Detail,
                                            elementos.value.get(it)
                                        )
                                    }
                                }, editar = {
                                    run {
                                        vm.setSelected(elementos.value.get(it))
                                        formularioEditable.value = true;
                                        item.value = elementos.value.get(it)
                                        navigator.navigateTo(
                                            ListDetailPaneScaffoldRole.Detail,
                                            elementos.value.get(it)
                                        )
                                    }
                                }, borrar = {
                                    run {
                                        vm.removeSesion(elementos.value.get(it))
                                    }
                                })

                            }
                        }

                },
                detailPane = {
                    navigator.currentDestination?.content?.let {

                        SesionFormulario(expandido = true,
                            editable = formularioEditable,
                            save = {
                                vm.save(it)
                                vm.unSelect()
                                item.value = vm.selected.value
                            },
                            peliculas = peliculasvm.items.collectAsState(),
                            salas = salasvm.items.collectAsState(),
                            item = it,
                            atras = {
                                run {
                                    if (navigator.canNavigateBack()) navigator.navigateBack()
                                }
                            })

                    }
                })
        }
    }
}



