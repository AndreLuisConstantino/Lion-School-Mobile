package br.senai.sp.jandira.lionschool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.CursoList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                    CoursesScreen()
            }
        }
    }
}

@Composable
fun CoursesScreen() {
    val context = LocalContext.current

    var filterCoursesState by remember {
        mutableStateOf("")
    }

    var listCurso by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Curso>( ))
    }

    val call = RetrofitFactory().getCursoService().getCurso()

    call.enqueue(object : Callback<CursoList>{
        override fun onResponse(
            call: Call<CursoList>,
            response: Response<CursoList>
        )
        {
            listCurso = response.body()!!.cursos
        }

        override fun onFailure(call: Call<CursoList>, t: Throwable) {
            Log.i("teste", "onFailure: ${t.message}")
        }
    })

    Surface() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(196.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(58.dp, 70.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(229, 182, 87)
                    )
                }
                TextButton(onClick = {
                    val openStart = Intent(context, MainActivity::class.java)
                    context.startActivity(openStart)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            }
            OutlinedTextField(
                value = filterCoursesState,
                onValueChange = {
                    filterCoursesState = it
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
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .width(305.dp)
                    .height(1.dp)
                    .background(Color(229, 182, 87))


            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.courses),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color(229, 182, 87)
            )
            LazyColumn(){
                items(listCurso){
                    Card(
                        modifier = Modifier
                            .size(314.dp, 162.dp)
                            .padding(horizontal = 0.dp, vertical = 10.dp)
                            .clickable {
                                           var openStudents = Intent(context, StudentsActivity::class.java)
                                        openStudents.putExtra("Curso", "${it.sigla}")
                                        openStudents.putExtra("Nome", "${it.nome}")
                                        context.startActivity(openStudents)
                            },
                        backgroundColor = Color(51, 71, 176),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            AsyncImage(
                                model = it.icone,
                                contentDescription = "${it.nome} icon",
                                modifier = Modifier
                                    .size(122.dp, 102.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                            Column {
                                Text(
                                    text = it.sigla,
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,

                                )
                                Text(
                                    text = it.nome,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Row(
                                    modifier = Modifier
                                        .width(70.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.relogio),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(20.dp),
                                        tint = Color.White
                                    )
                                    Text(
                                        text = "${it.carga}h",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    LionSchoolTheme {
        CoursesScreen()
    }
}