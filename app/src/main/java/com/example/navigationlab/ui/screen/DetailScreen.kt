package com.example.navigationlab.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.navigationlab.ui.detail.DetailUiState

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    onOpenProfile: () -> Unit,
    onBack: () -> Unit
) {
    ScreenScaffold(title = "Detail Student", onBack = onBack) {
        val student = uiState.student
        val error = uiState.errorMessage

        if (error != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Data tidak ditemukan",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(error)
                    Button(onClick = onBack) { Text("Kembali ke Home") }
                }
            }
        } else if (student != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconAvatar(icon = Icons.Default.Person)
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    InfoRow(label = "ID", value = student.id.toString())
                    InfoRow(label = "Nama", value = student.name)
                    InfoRow(label = "Program", value = student.program)
                    InfoRow(label = "Angkatan", value = student.year.toString())
                }
            }

            Button(
                onClick = onOpenProfile,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buka Profile")
            }
        }
    }
}