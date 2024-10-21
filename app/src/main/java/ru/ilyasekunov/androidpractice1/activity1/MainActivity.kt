package ru.ilyasekunov.androidpractice1.activity1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.ilyasekunov.androidpractice1.activity1.ui.theme.AndroidPractice1Theme
import ru.ilyasekunov.androidpractice1.activity2.SecondActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val name = mutableStateOf("")
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                try {
                    intent.extras?.let {
                        name.value = it.getString("name") ?: ""
                    }
                } catch (_: Exception) {
                }
            }
        }

        setContent {
            AndroidPractice1Theme {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val onButtonClick = remember {
                        {
                            val intent = Intent(this@MainActivity, SecondActivity::class.java)
                            intent.putExtra("Greeting", "Hello, from MainActivity!")
                            startActivity(intent)
                        }
                    }

                    Greeting(
                        name = name.value,
                        onButtonClick = onButtonClick
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (name.isNotBlank()) {
            Text(text = "Hello $name!")
        }

        Button(onClick = onButtonClick) {
            Text(text = "Open second activity")
        }
    }
}