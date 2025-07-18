package ru.caloriescalculator.calories.presentation.composable

import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.caloriescalculator.calories.presentation.model.ConfirmDeleteDialogState

@Composable
fun ConfirmDeleteDialogView(
    state: ConfirmDeleteDialogState,
    onDismissClick: () -> Unit,
    onConfirmDeleteClick: (Long) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmDeleteClick(state.id)
                }
            ) {
                Text("Удалить")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissClick()
                }
            ) {
                Text("Отмена")
            }
        },
        text = {
            Text(text = "Вы уверены, что хотите удалить продукт?")
        }
    )
}