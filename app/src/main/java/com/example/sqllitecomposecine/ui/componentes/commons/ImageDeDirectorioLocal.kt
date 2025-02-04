package com.example.sqllitecomposecine.ui.componentes.commons

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun ImageDeDirectorioLocal(fileName: String, context: Context, modifier: Modifier) {
    val file = File(context.filesDir, fileName)
    val uri = file.toUri()

    Image(
        painter = rememberAsyncImagePainter(uri),
        contentDescription = fileName,
        modifier = modifier
    )
}
