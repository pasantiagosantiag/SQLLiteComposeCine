package com.example.sqllitecomposecine.ui.componentes.peliculas

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.ui.viewmodels.PeliculaViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun PeliculaMain(modifier: Modifier = Modifier, vm: PeliculaViewModel = koinViewModel()) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Pelicula>()
    val elementos = vm.items.collectAsState()
    val formularioEditable = remember {
        mutableStateOf(false)
    }
    val isListAndDetailVisible =
        navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded && navigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded
    val searchview =
        navigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    Column(modifier = modifier.padding(start = 4.dp)) {
        if (searchview) PeliculaBuscador(
            modifier = Modifier,
            vm.buscadornombre,
            vm.buscadordescripcion,

            )
        ListDetailPaneScaffold(modifier = Modifier,
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                Box() {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        // modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = "Nuevo",
                            // tint = MaterialTheme.colorScheme.primary, // Cambia el color
                            modifier = Modifier.clickable {
                                vm.unSelect()
                                formularioEditable.value = true
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)

                            }// Cambia el tamaño

                        )
                        Text("Nuevo")
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp)
                    ) {

                        items(elementos.value.size) {


                            PeliculaItem(elementos.value.get(it), ver = {
                                run {
                                    vm.setSelected(elementos.value.get(it))
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
                                    navigator.navigateTo(
                                        ListDetailPaneScaffoldRole.Detail, elementos.value.get(it)
                                    )
                                }
                            }, borrar = {
                                run {
                                    vm.removePelicula(elementos.value.get(it))
                                }
                            })

                        }
                    }
                }
            },
            detailPane = {

                PeliculaFormulario(expandido = true, editable = formularioEditable, save = {
                    vm.save(it)
                    vm.unSelect()
                }, atras = {
                    //run {
                    if (navigator.canNavigateBack()) navigator.navigateBack()
                    //}
                })


            })
    }
}

@Composable
private fun PeliculaItem(
    item: Pelicula,

    ver: () -> Unit,
    editar: () -> Unit,
    borrar: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text("${item.nombre}",
            modifier = Modifier.clickable {
                ver()


            }.weight(0.50f))
        Button(onClick = {

            editar()

        }, modifier = Modifier.weight(0.25f)) {
            Text(text = "Editar")
        }
        Button(onClick = {
            borrar()

        }, modifier = Modifier.weight(0.25f)) {
            Text(text = "Borrar")
        }
    }
}


