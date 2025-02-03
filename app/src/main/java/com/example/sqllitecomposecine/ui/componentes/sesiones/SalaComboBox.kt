package com.example.sqllitecomposecine.ui.componentes.sesiones

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.sqllitecomposecine.model.entidades.Sala

import org.koin.androidx.compose.koinViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SalaComboBox(items: State<List<Sala>?>, item: State<Sala>, alcambiar:   (Sala) -> Unit) {


    // State to manage the selected item and menu visibility
    var selectedSala by remember { mutableStateOf<Sala?>(item.value) }

    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(item) {
       item.let {
            selectedSala = it.value


        }

    }


    //selected.categoria?.let { Text( it.nombre) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        // Textfield that shows the selected item or a hint
        TextField(
            value = selectedSala?.nombre ?: "Selecciona una localidad",
            onValueChange = {

            },
            readOnly = true,
            label = { Text("Sala") },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        // Dropdown menu with the list of localidades
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.value?.forEach { cat ->
                DropdownMenuItem(
                    onClick = {
                        run {
                            selectedSala = cat
                             alcambiar(cat)
                            expanded = false
                        }
                    },
                    text = { Text(text = cat.nombre) },

                )
               /* DropdownMenuItem(onClick = {
                   /* selectedSala = item
                    expanded = false

                    alcambiar(localidad.pueblo)*/
                }) {
                    Text(text = localidad.pueblo)
                }*/
            }
        }
    }
}