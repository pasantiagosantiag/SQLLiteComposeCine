# Práctica ROOM

## Acceso a la Cámara

### Configuración

Para acceder a la cámara y hacer una fotografía se ha de pedir permisos. Recordar añadir al fichero
Manifest los permisos oportunos:

```xml

<uses-permission android:name="android.permission.CAMERA" />
<uses-feature
android:name="android.hardware.camera" android:required="false" />

```

También es necesario establecer un proveedor de recursos, en este caso de ficheros, para ello añadir
a la aplicación en el fichero Manifest
la siguiente entrada

```xml

<provider android:authorities="${applicationId}.fileprovider" android:exported="false"
    android:grantUriPermissions="true" android:name="androidx.core.content.FileProvider">
    <meta-data android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>

```

En el que se indica que los ficheros de este proveedor se definen en @xml/file_paths (en la carpeta
res), cuyo contenido es:

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <files-path name="images" path="images/" />
    <cache-path name="cache" path="." />

</paths>
```

Definiendo las rutas para almacenar las imágenes y para que la imagen se realice en la caché

### Solicitando permisos

El uso de la cámara solicita permisos cada vez que se desee utilizar, para ello se crea un contrato
solicitando permisos

``` Kotlin

 val permissionLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
                if (permissionGranted) {
                    //si se tiene permisos se abre la cámara, pasando la uri en la
                    //que se depositará el jpg
                    val dir = File(contexto.cacheDir, "images").apply { if (!exists()) mkdirs() }
                    val f = File(dir, "photo_${System.currentTimeMillis()}.jpg")
                     photoUri.value = FileProvider.getUriForFile(contexto, "com.example.camara.fileprovider", f)
                    cameraLauncher.launch(photoUri.value)
                } else {
                    Toast.makeText(contexto, "Permisos no concedidos", Toast.LENGTH_LONG)

                }
            }
```
Si se tiene permiso, se abre la actividad externa que proporciona el sistema operativo, definiendo el contrato:

```Kotlin
 val cameraLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isImageSaved ->
                //se ha almacenado la imagen en
                if (isImageSaved) {
                    //se ejecuta la lambda, pasando como parámetro la uri en la que se ha almacenado
                    //la imagen
                    onImageSelected(photoUri.value)
                } else {
                    Toast.makeText(contexto, "Ha ocurrido un fallo, puede ser por el proveedor de ficheros", Toast.LENGTH_LONG)

                }
            }
```
