package br.senai.sp.jandira.lionschool.service

import android.telecom.Call
import br.senai.sp.jandira.lionschool.model.StudantList
import retrofit2.http.GET
import retrofit2.http.Query

interface AlunoService {

    @GET("alunos")
    fun getAlunosByCurso(@Query("curso") curso: String): retrofit2.Call<StudantList>
}