package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.CursoList
import retrofit2.Call
import retrofit2.http.GET

interface CursoService {

    @GET("cursos")
    fun getCurso(): Call<CursoList>


}