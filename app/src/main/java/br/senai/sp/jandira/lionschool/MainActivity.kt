package br.senai.sp.jandira.lionschool

import android.content.Intent
import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                StartScreen()
            }
        }
    }
}

@Composable
fun StartScreen() {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.studant),
                contentDescription = null,
                modifier = Modifier
                    .size(
                        243.dp,
                        409.dp
                    )
            )
            Text(
                text = stringResource(id = R.string.welcome),
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                color = Color(51, 71, 176)
            )
            Button(
                onClick = {
                    val operCourses = Intent(context, CoursesActivity::class.java)
                    context.startActivity(operCourses)
                },
                modifier = Modifier
                    .size(258.dp,77.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(Color(51, 71, 176))
            ) {
                Text(
                    text = stringResource(id = R.string.lestsgo),
                    fontSize = 40.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LionSchoolTheme {
        StartScreen()
    }
}