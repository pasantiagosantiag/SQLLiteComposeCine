package com.example.sqllitecomposecine.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sqllitecomposecine.model.entidades.Sala
import com.example.sqllitecomposecine.model.repositorios.ASalaRepositorio
import com.example.sqllitecomposecine.model.repositorios.IRepositorio
import com.example.sqllitecomposecine.model.repositorios.memoria.SalaRepositioEnMemoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject

class SalaViewModel(private val salaRepositorio: ASalaRepositorio) : ViewModel() {
    private val _selected = MutableStateFlow<Sala>(Sala())

    private var _items = MutableStateFlow<MutableList<Sala>>(mutableListOf())

    var selected: StateFlow<Sala> = _selected

    //para el buscador
    var buscadornombre = MutableStateFlow("")
    var buscadordescripcion = MutableStateFlow("")
    val items = combine(_items, buscadornombre, buscadordescripcion) { items, nombre, descripcion ->
        items.filter {
            if (nombre.isBlank())
                true
            else {
                it.nombre.lowercase().contains(nombre.lowercase())
            }
        }.filter {
            if (descripcion.isBlank())
                true
            else
                it.descripcion.lowercase().contains(descripcion.lowercase())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = _items.value
    )
    init {
        viewModelScope.launch {
            _items.value.addAll(salaRepositorio.getAll().toList())
        }
    }

    fun save(item: Sala) {
        viewModelScope.launch {
            //se actualiza el listado principal
            var nueva = _items.value.toMutableList()


            if (item.id == 0L) {
                salaRepositorio.add(item)
                nueva.add(item)
                _items.value = nueva
            } else {
                salaRepositorio.update(item)
                nueva[nueva.indexOfFirst { it.id == item.id }] = item
                _items.value = nueva
            }
            /* var tempo=_items.value;
             tempo.add(sala)
             salaRepositorio.add(sala)
             _items.value=tempo*/
        }
    }

    fun unSelect() {
        this._selected.value = Sala()
    }

    fun setSelected(item: Sala) {
        _selected.value = Sala();
        _selected.value = item.copy()
    }

    fun removeSala(item: Sala) {
        viewModelScope.launch {
            var nuevo = _items.value.toMutableList()
            nuevo.remove(item)
            _items.value = nuevo
            salaRepositorio.removeById(item.id)
            //si el seleccionado es el mismo se cambia
            if (item.id == selected.value.id) {
                unSelect()
            }
        }
    }

}