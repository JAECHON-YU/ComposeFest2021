package com.example.composebasic

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Outline
import android.os.Bundle
import android.telephony.SignalStrengthUpdateRequest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebasic.ui.theme.ComposeBasicTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicTheme {
                MyApp()
            }
        }
    }
}


@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    var shouldShowOnboarding by remember {
        mutableStateOf(true)
    }
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320 )
@Composable
fun OnboardingPreview() {
    ComposeBasicTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}


@Composable
private fun MyApp() {

    var shouldShowOnboarding by rememberSaveable {
        mutableStateOf(true)
    }
    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false})
    } else {
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) {"$it"}) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expanded by remember { mutableStateOf(false)}
    val extraPadding by animateDpAsState (
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(color = MaterialTheme.colors.primary,
    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))) {
                Text(text = "Hello, ")
                Text(text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ))

            }
            OutlinedButton(
                onClick = { expanded = !expanded}) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }

    }

}
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeBasicTheme {
        Greetings()
    }
}