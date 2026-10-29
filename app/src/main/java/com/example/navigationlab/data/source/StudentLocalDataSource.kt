package com.example.navigationlab.data.source

import com.example.navigationlab.data.model.Student

class StudentLocalDataSource {

    // Daftar yang tampil di Home (5 orang)
    private val students = listOf(
        Student(2026, "Alya Putri", "Teknik Informatika", 2026),
        Student(2027, "Bima Pratama", "Teknik Informatika", 2026),
        Student(2028, "Citra Lestari", "Teknik Informatika", 2026),
        Student(2029, "Dimas Saputra", "Teknik Informatika", 2025),
        Student(2030, "Eka Wulandari", "Sistem Informasi", 2025)
    )

    // Hanya ID ini yang punya data detail.
    // 2029 dan 2030 sengaja tidak punya detail, untuk menguji state "tidak ditemukan".
    private val idsWithDetail = setOf(2026, 2027, 2028)

    fun getStudentById(id: Int): Student? =
        students.find { it.id == id && it.id in idsWithDetail }

    fun getStudents(): List<Student> = students
}