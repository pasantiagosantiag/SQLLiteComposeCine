package com.example.sqllitecomposecine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sqllitecomposecine.ui.theme.SQLLiteComposeCineTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.sqllitecomposecine.model.repositorios.APeliculaRepositorio
import com.example.sqllitecomposecine.model.repositorios.ASalaRepositorio
import com.example.sqllitecomposecine.model.repositorios.ASesionRepositorio
import com.example.sqllitecomposecine.model.repositorios.nativo.PeliculaRepositoryNativo
import com.example.sqllitecomposecine.model.repositorios.nativo.SalaRepositoryNativo
import com.example.sqllitecomposecine.model.repositorios.nativo.SesionRepositoryNativo
import com.example.sqllitecomposecine.model.sqlnativo.AplicacionSQLiteOpenHelper
import com.example.sqllitecomposecine.ui.componentes.Principal
import com.example.sqllitecomposecine.ui.componentes.salas.SalaMain
import com.pasantiago.composesqllite.appModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var conexin= AplicacionSQLiteOpenHelper.getInstance(LocalContext.current)
            KoinApplication(application = {
                androidContext(this@MainActivity)
                modules(appModule)
            }) {
                SQLLiteComposeCineTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                       /* var salaRepository: ASalaRepositorio = koinInject<ASalaRepositorio>()
                        (salaRepository as SalaRepositoryNativo).setDb(conexin.writableDatabase)
                        var peliculaRepository: APeliculaRepositorio = koinInject<APeliculaRepositorio>()
                        (peliculaRepository as PeliculaRepositoryNativo).setDb(conexin.writableDatabase)
                        var sesionRepository: ASesionRepositorio = koinInject<ASesionRepositorio>()
                        (sesionRepository as SesionRepositoryNativo).setDb(conexin.writableDatabase)
*/
                        Principal (
                            modifier=Modifier.padding(innerPadding),
                            salir = {
                                this.finish()
                            }
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SQLLiteComposeCineTheme {
        Greeting("Android")
    }
}