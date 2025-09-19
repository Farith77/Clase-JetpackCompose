package com.example.ejemplo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ejemplo.ui.theme.EjemploTheme
import java.text.DecimalFormat
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EjemploTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BilleteraVirtualApp()
                }
            }
        }
    }
}

@Composable
fun BilleteraVirtualApp() {
    var nombreDestinatario by remember { mutableStateOf("") }
    var montoEnviar by remember { mutableStateOf("") }
    var montoActual by remember { mutableStateOf(1500.0) }
    var mensajeEnvio by remember { mutableStateOf("") }

    val formatter = DecimalFormat("#,##0.00")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier

                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = nombreDestinatario,
                    onValueChange = { nombreDestinatario = it
                        Log.d("DEBUG_BUTTON", it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1976D2),
                        unfocusedBorderColor = Color(0xFF90CAF9)
                    )
                )
                Text(
                    text = "Nombre destinatario",
                    fontSize = 14.sp,
                    color = Color(0xFF544A4A),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-15).dp)
                )

                // Campo Monto a enviar
                OutlinedTextField(
                    value = montoEnviar,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                            montoEnviar = it
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1976D2),
                        unfocusedBorderColor = Color(0xFF90CAF9)
                    )
                )
                Text(
                    text = "Monto a enviar S/",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-15).dp)
                )

                // Campo Monto actual (solo lectura)
                OutlinedTextField(
                    value = formatter.format(montoActual),
                    onValueChange = { },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1976D2),
                        unfocusedBorderColor = Color(0xFF90CAF9),
                        disabledBorderColor = Color(0xFF90CAF9),
                        disabledTextColor = Color.Black
                    )
                )
                Text(
                    text = "Monto actual S/",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-15).dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Boton
                Button(
                    onClick = {
                        if (nombreDestinatario.isNotBlank() && montoEnviar.isNotBlank()) {
                            val monto = montoEnviar.toDoubleOrNull()
                            if (monto != null && monto > 0 && monto <= montoActual) {
                                montoActual -= monto
                                mensajeEnvio = "Se envío: $nombreDestinatario, ${formatter.format(monto)}"
                                // Limpiar campos
                                nombreDestinatario = ""
                                montoEnviar = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2)
                    )
                ) {
                    Text(
                        text = "REGISTRAR",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }

                if (mensajeEnvio.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = mensajeEnvio,
                        fontSize = 16.sp,
                        color = Color(0xFF1976D2),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}


@Composable
fun CounterApp(modifier: Modifier = Modifier) {
    var count by remember { mutableStateOf(0) }
    Column(modifier = modifier) {
        Text("Count: $count")
        Button(onClick = { count++ }) {
            Text("Increment")
        }
    }
}