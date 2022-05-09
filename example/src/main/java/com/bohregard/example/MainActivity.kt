package com.bohregard.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bohregard.animatedtextfield.AnimatedTextField
import com.bohregard.example.ui.*
import com.bohregard.exoplayercomposable.DataStoreCache

class MainActivity : ComponentActivity() {
    private val datastoreCache by lazy { DataStoreCache(this, R.string.app_name) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "samples"
                    ) {
                        composable("samples") {
                            SamplePickerUi {
                                navController.navigate(it)
                            }
                        }
                        composable("animated-textfield") {
                            AnimatedTextFieldUi()
                        }
                        composable("datetime-picker") {
                            DateTimePickerUi()
                        }
                        composable("exoplayer") {
                            ExoplayerUi(datastoreCache)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewAnimatedTextField() {
    ExampleTheme {
        AnimatedTextField(
            onClear = {},
            onValueChange = {},
            text = "Some Neat thing or whatever"
        )
    }
}