package com.example.navigationlab.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    ScreenScaffold(title = "Profile", onBack = onBack) {
        IconAvatar(
            icon = Icons.Default.AccountCircle,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Navigation Compose Lab",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                InfoRow("Nama", "Niquita Aislam Az Zahara")
                HorizontalDivider()
                InfoRow("NIM", "245150207111057")
                HorizontalDivider()
                InfoRow("Program Studi", "S1 Teknik Informatika — FILKOM UB")
                HorizontalDivider()
                InfoRow("Mata Kuliah", "Pemrograman Aplikasi Perangkat Bergerak")
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Kembali")
        }
    }
}