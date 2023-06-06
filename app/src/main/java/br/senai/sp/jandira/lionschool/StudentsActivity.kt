package br.senai.sp.jandira.lionschool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.components.HeaderComponent
import br.senai.sp.jandira.lionschool.model.Curso
import br.senai.sp.jandira.lionschool.model.CursoList
import br.senai.sp.jandira.lionschool.model.Studant
import br.senai.sp.jandira.lionschool.model.StudantList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                val siglaCurso = intent.getStringExtra("Curso")
                val nomeCurso = intent.getStringExtra("Nome")
                StudentsScreen(siglaCurso.toString(), nomeCurso.toString())
            }
        }
    }
}

@Composable
fun StudentsScreen(curso: String, nome: String) {

    val context = LocalContext.current

    var filterAlunosState by remember {
        mutableStateOf("")
    }

    var listAlunos by remember {
        mutableStateOf(listOf<Studant> ())
    }

    val call = RetrofitFactory().getAlunosService().getAlunosByCurso(curso)

    call.enqueue(object : Callback<StudantList> {
        override fun onResponse(
            call: Call<StudantList>,
            response: Response<StudantList>
        )
        {
            //listCurso = response.body()!!.curso
            listAlunos = response.body()!!.alunos
        }

        override fun onFailure(call: Call<StudantList>, t: Throwable) {
            Log.i("teste", "onFailure: ${t.message}")
        }
    })

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
            Box(
                modifier = Modifier
                    .width(305.dp)
                    .height(1.dp)
                    .background(Color(229, 182, 87))


            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = nome,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color(229, 182, 87)
            )
            LazyColumn(){
                items(listAlunos) {
                    Card(
                       modifier = Modifier
                           .size(217.dp, 260.dp)
                           .padding(horizontal = 0.dp, vertical = 10.dp),
                        backgroundColor = Color(51, 71, 176),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(model = it.foto, contentDescription = "${it.nome} icon")
                            Text(
                                text = it.nome
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    LionSchoolTheme {
        StudentsScreen(curso = "DS", nome = "Desenvolvimento de Sistemas")
    }
}