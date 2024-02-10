package com.example.learningdatastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learningdatastore.ui.theme.LearningDataStoreTheme
import com.example.learningdatastore.ui.theme.ToggleUiState
import com.example.learningdatastore.ui.theme.ToggleViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ToggleViewModel = viewModel(factory = ToggleViewModel.Factory)
            val toggleUiState by viewModel.toggleUiState.collectAsState()
            LearningDataStoreTheme(darkTheme = toggleUiState.isDarkTheme) {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ToggleApp(
                        toggleUiState = toggleUiState,
                        onDarkThemeChanged = { viewModel.saveToggledValue(it) })
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppPreview() {
    ToggleApp()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToggleApp(
    toggleUiState: ToggleUiState = ToggleUiState(),
    onDarkThemeChanged: (Boolean) -> Unit = {}
) {

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }) { paddingValues ->
        ToggleAppBody(
            modifier = Modifier.padding(paddingValues),
            checked = toggleUiState.isDarkTheme,
            onDarkThemeChanged = onDarkThemeChanged
        )
    }
}

@Composable
fun ToggleAppBody(
    modifier: Modifier = Modifier, checked: Boolean, onDarkThemeChanged: (Boolean) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Dark Theme", modifier = Modifier.padding(end = 15.dp))
            Switch(checked = checked, onCheckedChange = onDarkThemeChanged)
        }
    }
}