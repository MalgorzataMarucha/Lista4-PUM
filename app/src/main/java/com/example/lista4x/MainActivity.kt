package com.example.lista4x
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text

import androidx.compose.ui.unit.dp
import android.annotation.SuppressLint
import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.lista4x.ui.theme.Lista4xTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lista4xTheme {
                MyApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lista4xTheme {
        MyApp()
    }
}
data class NavItemState(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)
object SubjectListCounter {
    private val subjectCounts = mutableMapOf<String, Int>()

    fun getListNumberForSubject(subject: String): Int {
        val count = subjectCounts.getOrPut(subject) { 0 }
        subjectCounts[subject] = count + 1
        return subjectCounts[subject] ?: 1
    }
}

@Composable
fun ScreenE3(exerciseList: ExerciseList, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Row {
            Text(
                text = "${exerciseList.subject.name} ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 5.dp)

            )
        }
        Row {
            Text(
                text = "Lista ${SubjectListCounter.getListNumberForSubject(exerciseList.subject.name)}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        LazyColumn {
            itemsIndexed(exerciseList.exercises) { index, exercise ->
                val taskNumber = index + 1
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = Color(0xFFC6E967)),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row{
                            Text(text = "Zadanie $taskNumber")
                        }
                        Row {
                            Text(text = exercise.content)
                        }
                    }
                    Column {
                        Text(text = "pkt:1")
                    }
                }
            }
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val items = listOf(
        NavItemState(
            title = "Listy Zadań",
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
        ),
        NavItemState(
            title = "Oceny",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
        ),
    )
    var bottomNavState: Int by rememberSaveable {
        mutableIntStateOf(0)
    }

    val dummyData by remember {
        mutableStateOf(generateDummyData())
    }
    val navController = rememberNavController()



    NavHost(navController = navController, startDestination = "listScreen") {
        composable("listScreen") {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (bottomNavState == 0) {
                                    Text(text = "Moje Listy Zadań", fontWeight = FontWeight.Bold)
                                } else {
                                    Text(text = "Moje Oceny", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar(
                        modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        containerColor = Color(0xFFEED1A7)
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = bottomNavState == index,
                                onClick = {
                                    bottomNavState = index
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (bottomNavState == index) item.selectedIcon
                                        else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                },
                                label = { Text(text = item.title) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color(0xFF000000),
                                    selectedTextColor = Color(0xFF000000),
                                    indicatorColor = Color(0xFFCE9F5B)
                                )
                            )
                        }
                    }
                },
            ) { contentPadding ->
                Column(
                    modifier = modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (bottomNavState == 0) {
                        LazyColumn {
                            items(dummyData) { exerciseList ->
                                val subjectCount =
                                    SubjectListCounter.getListNumberForSubject(exerciseList.subject.name)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                        .background(color = Color(0xFFC6E967))
                                        .clickable {
                                            navController.navigate("E3/${exerciseList.subject.name}/$subjectCount")
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = exerciseList.subject.name,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(text = "Liczba zadań: ${exerciseList.exercises.size}")
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                    ) {

                                        Text(text = "Lista $subjectCount")
                                        Text(text = "Ocena: ${exerciseList.grade}")

                                    }
                                }
                            }
                        }
                    } else {
                        LazyColumn {
                            val groupedData = generateDummyData().groupBy { it.subject.name }

                            groupedData.forEach { (subjectName, subjectData) ->
                                item {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp)
                                            .background(color = Color(0xFFC6E967))
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(10.dp)
                                        ) {
                                            Text(text = subjectName, fontWeight = FontWeight.Bold)
                                            Text(text = "Liczba List: ${subjectData.size}")
                                        }
                                        Column(
                                            horizontalAlignment = Alignment.End,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp)
                                        ) {
                                            val averageGrade =
                                                subjectData.map { it.grade }.average()
                                            Text(text = "Średnia: ${"%.2f".format(averageGrade)}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        composable(
            route = "E3/{subject}/{subjectCount}",
            arguments = listOf(
                navArgument("subject") { type = NavType.StringType },
                navArgument("subjectCount") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val subject = backStackEntry.arguments?.getString("subject")
            val subjectCount = backStackEntry.arguments?.getInt("subjectCount")

            ScreenE3(exerciseList = dummyData.first(), onBack = {
            })
        }
    }
}

