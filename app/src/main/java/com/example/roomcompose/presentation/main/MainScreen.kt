package com.example.roomcompose.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomcompose.domain.models.UserUI


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val viewModel = hiltViewModel<MainViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getAllUsers()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addUser() }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    tint = Color.White,
                    contentDescription = "add"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(state.users) {
                UserItem(modifier = Modifier.fillMaxWidth(), userUI = it) {
                    viewModel.deleteUser(it)
                }
            }
        }

        if (state.tempAddUserUI != null) {
            AddUserItem(viewModel = viewModel, state = state)
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddUserItem(viewModel: MainViewModel, state: MainState) {
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(10.dp)),
        onDismissRequest = { viewModel.onDismissAddUser() },
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.tempAddUserUI?.name.orEmpty(),
                onValueChange = { viewModel.changeName(it) })

            OutlinedTextField(
                value = state.tempAddUserUI?.lastName.orEmpty(),
                onValueChange = { viewModel.changeLastName(it) })

            OutlinedTextField(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                value = (state.tempAddUserUI?.age ?: 0).toString(),
                onValueChange = { viewModel.changeAge(it) })

            Button(
                onClick = { viewModel.addUser() },
                modifier = Modifier
                    .height(40.dp)
                    .width(200.dp)
            ) {
                Text(text = "Сохранить")
            }
        }

    }
}


@Composable
private fun UserItem(modifier: Modifier, userUI: UserUI, delete: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(7.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "${userUI.name} ${userUI.lastName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                )

                Text(
                    text = "Возраст: ${userUI.age}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            IconButton(onClick = { delete() }, Modifier) {
                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,

                    )
            }
        }
    }
}