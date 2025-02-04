package com.example.sqllitecomposecine.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.model.repositorios.APeliculaRepositorio
import com.example.sqllitecomposecine.servicios.PeliculaServicio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PeliculaViewModel(private val repositorio: APeliculaRepositorio, androidContext: Context) : ViewModel() {
    private val _selected = MutableStateFlow<Pelicula>(Pelicula())
    private val servicio = PeliculaServicio(repositorio,androidContext)

    private var _items = MutableStateFlow<MutableList<Pelicula>>(mutableListOf())

    var selected: StateFlow<Pelicula> = _selected

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
            _items.value.addAll(servicio.getAll())
        }
    }

    fun save(item: Pelicula) {
        viewModelScope.launch {
            //se actualiza el listado principal
            var nueva = _items.value.toMutableList()


            if (item.id == 0L) {

                servicio.insert(item)
                //repositorio.add(item)
                nueva.add(item)
                _items.value = nueva
            } else {
                repositorio.update(item)
                nueva[nueva.indexOfFirst { it.id == item.id }] = item
                _items.value = nueva
            }
            /* var tempo=_items.value;
             tempo.add(sala)
             repositorio.add(sala)
             _items.value=tempo*/
        }
    }

    fun unSelect() {
        this._selected.value = Pelicula()
    }

    fun setSelected(item: Pelicula) {
        _selected.value = Pelicula();
        _selected.value = item.copy()
    }

    fun removePelicula(item: Pelicula) {
        viewModelScope.launch {
            var nuevo = _items.value.toMutableList()
            nuevo.remove(item)
            _items.value = nuevo
            servicio.delete(item)
            //si el seleccionado es el mismo se cambia
            if (item.id == selected.value.id) {
                unSelect()
            }
        }
    }

}