package com.salama.penny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salama.penny.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PennyTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf("expenses") }
    val showDialog = remember { mutableStateOf(false) }
    val expenses = remember { mutableStateListOf<Pair<String, String>>() }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = DarkPink) {
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_expenses), contentDescription = "Expenses") },
                    label = { Text("Expenses") },
                    selected = selectedTab == "expenses",
                    onClick = { selectedTab = "expenses" }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_savings), contentDescription = "Savings") },
                    label = { Text("Savings") },
                    selected = selectedTab == "savings",
                    onClick = { selectedTab = "savings" }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_reports), contentDescription = "Reports") },
                    label = { Text("Reports") },
                    selected = selectedTab == "reports",
                    onClick = { selectedTab = "reports" }
                )
            }
        },
        floatingActionButton = {
            if (selectedTab == "expenses") {
                FloatingActionButton(
                    onClick = { showDialog.value = true },
                    containerColor = DarkPink,
                    contentColor = BlackText
                ) { Text("+", style = MaterialTheme.typography.titleLarge) }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "expenses" -> ExpensesScreen(expenses)
                "savings" -> SavingsScreen()
                "reports" -> ReportsScreen()
            }

            if (showDialog.value) {
                AddExpenseDialog(
                    onDismiss = { showDialog.value = false },
                    onSave = { title, amount ->
                        expenses.add(title to amount)
                        showDialog.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun ExpensesScreen(expenses: List<Pair<String, String>>) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkPink)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Expenses", style = MaterialTheme.typography.titleLarge, color = WhiteText)
        }

        // Cards list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BabyPink)
                .padding(16.dp)
        ) {
            if (expenses.isEmpty()) {
                item {
                    Text(
                        "No expenses yet 💸",
                        color = BlackText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            } else {
                items(expenses) { expense ->
                    ExpenseCard(title = expense.first, amount = expense.second)
                }
            }
        }
    }
}

@Composable
fun ExpenseCard(title: String, amount: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BlackText)
            Text("Ksh $amount", fontSize = 16.sp, color = DarkPink, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun SavingsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().background(DarkPink).padding(16.dp),
            contentAlignment = Alignment.Center
        ) { Text("Savings", style = MaterialTheme.typography.titleLarge, color = WhiteText) }

        Box(
            modifier = Modifier.fillMaxSize().background(BabyPink).padding(16.dp)
        ) { Text("Manage your savings goals 🏦", color = BlackText) }
    }
}

@Composable
fun ReportsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().background(DarkPink).padding(16.dp),
            contentAlignment = Alignment.Center
        ) { Text("Reports", style = MaterialTheme.typography.titleLarge, color = WhiteText) }

        Box(
            modifier = Modifier.fillMaxSize().background(BabyPink).padding(16.dp)
        ) { Text("View your reports 📊", color = BlackText) }
    }
}

@Composable
fun AddExpenseDialog(onDismiss: () -> Unit, onSave: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                if (title.isNotBlank() && amount.isNotBlank()) {
                    onSave(title, amount)
                }
            }) { Text("Save", color = DarkPink) }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) { Text("Cancel", color = BlackText) }
        },
        title = { Text("Add Expense", color = BlackText) },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") })
            }
        },
        containerColor = BabyPink
    )
}