package se.magictechnology.pia12androidprojekt.tab1

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Shopping(goItemdetail : () -> Unit) {
    Column {
        Text("LIST")
        Button(onClick = { goItemdetail() }) {
            Text("GO")
        }
    }
}