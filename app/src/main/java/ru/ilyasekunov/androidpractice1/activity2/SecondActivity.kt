package ru.ilyasekunov.androidpractice1.activity2

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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.ilyasekunov.androidpractice1.activity1.MainActivity
import ru.ilyasekunov.androidpractice1.activity1.ui.theme.AndroidPractice1Theme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val greeting = mutableStateOf("")
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                try {
                    intent.extras?.let {
                        greeting.value = it.getString("Greeting") ?: ""
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
                        { userName: String ->
                            val intent = Intent(this@SecondActivity, MainActivity::class.java)
                            intent.putExtra("name", userName)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        }
                    }

                    Greeting(
                        name = greeting.value,
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
    onButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = name)

        var userName by rememberSaveable { mutableStateOf("") }
        TextField(
            value = userName,
            onValueChange = {
                userName = it
            }
        )

        Button(onClick = { onButtonClick(userName) }) {
            Text(text = "Come back to MainActivity")
        }
    }
}