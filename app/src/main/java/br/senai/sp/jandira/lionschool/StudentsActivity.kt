package br.senai.sp.jandira.lionschool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.components.HeaderComponent
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme

class StudentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                val siglaCurso = intent.getStringExtra("Curso")
                StudentsScreen(siglaCurso.toString())
            }
        }
    }
}

@Composable
fun StudentsScreen(curso: String) {

    val context = LocalContext.current

    var filterAlunosState by remember {
        mutableStateOf("")
    }

    Surface() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderComponent(context = context)
            Spacer(modifier = Modifier.height(14.dp))
            OutlinedTextField(
                value = filterAlunosState,
                onValueChange = {
                    filterAlunosState = it
                },
                colors = TextFieldDefaults
                    .textFieldColors(
                        backgroundColor =  Color(51, 71, 176),
                        focusedIndicatorColor = Color(51, 71, 176),
                        unfocusedIndicatorColor = Color(51, 71, 176)
                    ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.lupa),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp),
                        tint = Color.White
                    )
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .height(53.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    LionSchoolTheme {
        StudentsScreen(curso = "DS")
    }
}