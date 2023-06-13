package br.senai.sp.jandira.lionschool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.components.HeaderComponent
import br.senai.sp.jandira.lionschool.model.StudentGrade
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Response

class StudantGradeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                val matricula = intent.getStringExtra("Matricula")
                StudentScreen(matricula.toString())
            }
        }
    }
}

@Composable
fun StudentScreen(matricula: String) {

    val context = LocalContext.current

    var aluno by remember {
        mutableStateOf(StudentGrade(
            "",
            "",
            "",
            "",
            emptyList()
        ))
    }

    //Cria uma chamada para o endpoint
    val call = RetrofitFactory().getAlunosService().getAlunosByMatricula(matricula)

    //Executa a chamada
    call.enqueue(object : retrofit2.Callback<StudentGrade> {
        override fun onResponse(
                                call: Call<StudentGrade>,
                                response: Response<StudentGrade>
        ) {
            if (response.isSuccessful) {
                val studentResponse = response.body()
                if (studentResponse != null) {
                    aluno = studentResponse
                }
            } else {
                Log.e("teste", "Erro na resposta da API: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<StudentGrade>, t: Throwable) {
            Log.i("teste", "onFailure: ${t.message} ")
        }
    })

    Surface() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderComponent(context = context)
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .width(305.dp)
                    .height(1.dp)
                    .background(Color(229, 182, 87))
            )
            Spacer(modifier = Modifier.height(15.dp))
            var corStatus = Color(0,0,0)

            if (aluno.status.toString() == "Cursando") {
                corStatus = Color(229, 182, 87)
            } else {
                corStatus = Color(51, 71, 176)
            }
            Card(
                modifier = Modifier
                    .size(336.dp, 618.dp),
                shape = RoundedCornerShape(25.dp),
                backgroundColor = corStatus
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    //Informacoes aluno
                    Column(
                        modifier = Modifier
                            .size(500.dp, 240.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.student_icon),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(190.dp, 206.dp)
//                        )
                        AsyncImage(
                            model = aluno.foto,
                            contentDescription = null,
                            modifier = Modifier
                            .size(190.dp, 206.dp)
                        )
                        Text(
                            text = aluno.nome,
                            fontSize = 22.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                    //Card Notas
                    Card(
                        modifier = Modifier
                            .size(298.dp, 220.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            LazyColumn() {
                                items(aluno.disciplinas){
                                    var barra = 1.5 * it.media.toDouble()

                                    var corBarra = Color(0,0,0)

                                    if (it.media.toDouble() >= 60) {
                                        corBarra = Color(51, 71, 176)
                                    } else if (it.media.toDouble() < 60 && it.media.toDouble() >= 50) {
                                        corBarra = Color(229, 182, 87)
                                    } else {
                                        corBarra = Color(245, 0, 0, 255)
                                    }



                                    Row(
                                        modifier = Modifier
                                            .width(250.dp)
                                            .padding(horizontal = 0.dp, vertical = 5.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = it.sigla,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .width(50.dp)
                                        )
                                        Text(
                                            text = it.media
                                        )
                                        Box(
                                            modifier = Modifier
                                                .background(Color(238, 239, 248))
                                                .width(150.dp)
                                                .height(16.dp)
                                        ) {
                                            //Box da nota
                                            Box(
                                                modifier = Modifier
                                                    .width(barra.dp)
                                                    .fillMaxHeight()
                                                    .background(corBarra)
                                            )
                                        }
                                    }
                                }

                            }
                        }


                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    LionSchoolTheme {
        StudentScreen("Android")
    }
}