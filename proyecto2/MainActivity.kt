package com.example.bd

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIPrincipal()
        }
    }
}

@SuppressLint("Recycle", "Range")
@Composable
fun UIPrincipal() {
    val auxSQLite = DBHelper(LocalContext.current)
    val base = auxSQLite.writableDatabase
    val cursor: Cursor = base.rawQuery("SELECT * FROM producto", null)
    val productos = mutableListOf<Producto>()

    while (cursor.moveToNext()) {
        val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
        val precio = cursor.getDouble(cursor.getColumnIndex("precio"))
        val descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
        val imagenBase64 = cursor.getString(cursor.getColumnIndex("imagen"))
        productos.add(Producto(nombre, precio, descripcion, imagenBase64))
    }

    cursor.close()
    base.close()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "Productos disponibles",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            items(productos) { producto ->
                ProductoCard(producto = producto)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        IconButton(
            onClick = { /* No Funcional */ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar producto",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ProductoCard(producto: Producto) {
    val bitmap = producto.imagenBase64?.let { base64ToBitmap(it) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5DC))
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 16.dp)
                ) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Imagen del producto",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                            contentDescription = "Imagen por defecto",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("$${producto.precio}", fontWeight = FontWeight.SemiBold, color = Color(0xFF008000))
                    Text(producto.descripcion ?: "Sin descripción", fontSize = 12.sp)
                }

                Row {
                    IconButton(onClick = { /* No Funcional */ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar ${producto.nombre}",
                            tint = Color(0xFFFFA500)
                        )
                    }
                    IconButton(onClick = { /* No Funcional */ }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar ${producto.nombre}",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

fun base64ToBitmap(base64String: String): Bitmap? {
    return try {
        val decodedString = Base64.decode(base64String, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

data class Producto(
    val nombre: String,
    val precio: Double,
    val descripcion: String?,
    val imagenBase64: String?
)

@Preview(showBackground = true)
@Composable
fun Previsualizacion() {
    UIPrincipal()
}
