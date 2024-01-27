package com.example.lista4x

import androidx.compose.runtime.Composable
import kotlin.random.Random

data class Exercise(val content: String, val points: Int)

data class Subject(val name: String)

data class ExerciseList(val exercises: List<Exercise>, val subject: Subject, val grade: Float)

@Composable
fun Dane(name: String, description: String, image: Int) {}

fun generateDummyData(): List<ExerciseList> {
    val subjects = listOf("Matematyka", "PUM", "Fizyka", "Elektronika", "Algorytmy")
    val dummyData = mutableListOf<ExerciseList>()

    repeat(20) {
        val numExercises = Random.nextInt(1, 11)
        val exercises = List(numExercises) {
            Exercise("Treść zadania", Random.nextInt(1, 11))
        }
        val subject = Subject(subjects.random())
        val grade = (Random.nextInt(6, 11) / 2.0).toFloat()
        dummyData.add(ExerciseList(exercises, subject, grade))
    }

    return dummyData
}
fun List<ExerciseList>.getListNumberForSubject(subject: String): Int {
    val subjectMap = mutableMapOf<String, Int>()

    forEachIndexed { index, exerciseList ->
        val currentSubject = exerciseList.subject.name
        if (!subjectMap.containsKey(currentSubject)) {
            subjectMap[currentSubject] = subjectMap.size + 1
        }
    }

    return subjectMap[subject] ?: 1
}
