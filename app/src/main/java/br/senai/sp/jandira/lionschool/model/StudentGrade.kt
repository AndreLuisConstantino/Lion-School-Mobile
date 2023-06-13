package br.senai.sp.jandira.lionschool.model

data class StudentGrade(
    var nome: String,
    var foto: String,
    var matricula: String,
    var status: String,
    var disciplinas: List<Disciplina>
)
