package br.senai.sp.jandira.lionschool.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val BASE_URL = "https://api-lion-school-2023.cyclic.app/v1/lion-school/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCursoService(): CursoService {
        return retrofitFactory.create((CursoService::class.java))
    }

    fun getAlunosService(): AlunoService {
        return retrofitFactory.create((AlunoService::class.java))
    }
}