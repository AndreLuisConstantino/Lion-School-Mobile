package br.senai.sp.jandira.lionschool.service

import android.telecom.Call
import br.senai.sp.jandira.lionschool.model.StudantList
import br.senai.sp.jandira.lionschool.model.StudentGrade
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlunoService {

    @GET("alunos")
    fun getAlunosByCurso(@Query("curso") curso: String): retrofit2.Call<StudantList>

    @GET("alunos/{matricula}")
    fun getAlunosByMatricula(@Path("matricula") matricula: String): retrofit2.Call<StudentGrade>
}