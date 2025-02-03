package com.example.sqllitecomposecine.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sqllitecomposecine.model.entidades.Sesion
import com.example.sqllitecomposecine.model.repositorios.ASesionRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SesionViewModel(val repositorio: ASesionRepositorio ) : ViewModel() {
    private val _selected = MutableStateFlow<Sesion>(Sesion())

    private var _items = MutableStateFlow<MutableList<Sesion>>(mutableListOf())

    var selected: StateFlow<Sesion> = _selected

    //para el buscador
    var buscadornombrepelicula = MutableStateFlow("")
   // var buscadordescripcion = MutableStateFlow("")
    val items = combine(_items, buscadornombrepelicula //, buscadordescripcion
    ) { items, nombre //, descripcion
       ->
        items.filter {
            if (nombre.isBlank())
                true
            else {
                it.pelicula.nombre.lowercase().contains(nombre.lowercase())
            }
        }/*.filter {
            if (descripcion.isBlank())
                true
            else
                it.descripcion.lowercase().contains(descripcion.lowercase())
        }*/
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = _items.value
    )
    init {
        viewModelScope.launch {
              _items.value.addAll(repositorio.getAll().toList())
        }
    }

    fun save(item: Sesion) {
        viewModelScope.launch {
            //se actualiza el listado principal
            var nueva = _items.value.toMutableList()


            if (item.id == 0L) {
                repositorio.add(item)
                nueva.add(item)
                _items.value = nueva
            } else {
                repositorio.update(item)
                nueva[nueva.indexOfFirst { it.id == item.id }] = item
                _items.value = nueva
            }

        }
    }

    fun unSelect() {
        this._selected.value = Sesion()
    }

    fun setSelected(item: Sesion) {
        _selected.value = Sesion();
        _selected.value = item.copy()
        selected=_selected.asStateFlow()
    }

    fun removeSesion(item: Sesion) {
        viewModelScope.launch {
            var nuevo = _items.value.toMutableList()
            nuevo.remove(item)
            _items.value = nuevo
            //repositorio.removeById(item.id)
            //si el seleccionado es el mismo se cambia
            if (item.id == selected.value.id) {
                unSelect()
            }
        }
    }

}