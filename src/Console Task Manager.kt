fun main() {

    while (true) {
        print(ConsoleManager.Menus.mainMenu())

        when (readlnOrNull()?.toIntOrNull()) {

            1 -> {

                ServiceManager.taskManager.addTask()

            }

            2 -> {

                ServiceManager.taskManager.discardTask()

            }

            3 -> {

                ServiceManager.taskManager.completeTask()

            }

            4 -> {

                ServiceManager.taskManager.showAllTasks()

            }

            5 -> {

                ServiceManager.taskManager.searchTasks()

            }

            6 -> {

                ServiceManager.taskManager.editTask()

            }

            7 -> {

                ServiceManager.taskManager.sortTasks()

            }

            8 -> {

                ServiceManager.taskManager.showStatistics()

            }

            9 -> {

                ConsoleManager.Menus.printFAQ()

            }

            10 -> return

            null -> ConsoleManager.printError(ConsoleManager.Error.IntInputError.message)

            else -> ConsoleManager.printError(ConsoleManager.Error.InvalidMainMenuError.message)

        }
    }

}

object ServiceManager {

    val taskManager = TaskManager()

    fun readInt(arg: String, maxAttempts: Int = Consts.MAX_INT_INPUT_ATTEMPTS): Int? {
        print(arg)

        repeat(maxAttempts) { attempt ->
            val input = readlnOrNull()?.toIntOrNull()

            if (input != null) {
                return input
            }

            if (attempt < maxAttempts - 1) {
                ConsoleManager.printError(
                    ConsoleManager.Error.IntInputError.message
                )
            }
        }

        ConsoleManager.printError(
            ConsoleManager.Error.TooManyIncorrectAttemptsError.message
        )

        return null
    }


}

data class Task(
    val id: Int,
    var name: String,
    var priority: Int,
    var status: TaskStatus = TaskStatus.IN_PROGRESS
)

enum class TaskStatus {
    IN_PROGRESS,
    DONE

}


class TaskManager {
    private val tasks = mutableListOf<Task>()
    private var nextId = 1
    fun addTask() {

        println(ConsoleManager.Features.addTaskFeature())

        print(ConsoleManager.Inputs.taskNameInput())
        val taskName = readlnOrNull()?.trim() ?: return
        val taskPriority = ServiceManager.readInt(ConsoleManager.Inputs.taskPriorityInput()) ?: return

        val result = Task(nextId, taskName, taskPriority)
        if (TaskUtility.isValidTask(result, tasks)) {

            if (tasks.isEmpty()) {
                ConsoleManager.printSuccess(ConsoleManager.Success.FirstTaskAddedSuccess.message)
            } else {
                ConsoleManager.printSuccess(ConsoleManager.Success.TaskAddedSuccess.message)
            }

            tasks.add(result)
            nextId++


        }

    }

    fun discardTask() {
        if (tasks.isEmpty()) {
            ConsoleManager.printError(ConsoleManager.Error.TaskListIsEmpty.message)
            return
        }

        println(ConsoleManager.Features.discardTaskFeature())
        showAllTasks()

        val iDInput = ServiceManager.readInt(ConsoleManager.Inputs.taskIdInput()) ?: return
        val result = TaskUtility.findTaskById(iDInput, tasks)
        if (result == null) {
            ConsoleManager.printError(
                ConsoleManager.Error.TaskNotFoundIDError.message,
                additionalInfo = "Task ID: $iDInput"
            )
        } else {

            tasks.remove(result)
            ConsoleManager.printSuccess(
                ConsoleManager.Success.TaskDiscardedSuccess.message,
                additionalInfo = "Task ID: $iDInput"
            )

        }
    }

    fun completeTask() {

        if (tasks.isEmpty()) {
            ConsoleManager.printError(ConsoleManager.Error.TaskListIsEmpty.message)
            return
        }

        println(ConsoleManager.Features.completeTaskFeature())

        showAllTasks()
        val iDInput = ServiceManager.readInt(ConsoleManager.Inputs.taskIdInput()) ?: return
        val result = TaskUtility.findTaskById(iDInput, tasks)

        if (result == null) {
            ConsoleManager.printError(ConsoleManager.Error.TaskNotFoundIDError.message)
        } else {

            if (result.status == TaskStatus.DONE) {
                ConsoleManager.printError(
                    ConsoleManager.Error.TaskIsAlreadyDone.message,
                    additionalInfo = "Task ID: $iDInput"
                )
                return
            }
            result.status = TaskStatus.DONE
            ConsoleManager.printSuccess(
                ConsoleManager.Success.TaskCompletedSuccess.message,
                additionalInfo = "Task ID: $iDInput"
            )
        }
    }

    fun showAllTasks() {

        if (tasks.isEmpty()) {
            ConsoleManager.printError(ConsoleManager.Error.TaskListIsEmpty.message)
            return
        }

        println(ConsoleManager.Features.showAllTasksFeature())
        tasks.forEach { task ->
            TaskUtility.printTask(task)
        }

    }

    fun searchTasks() {
        if (tasks.isEmpty()) {
            ConsoleManager.printError(ConsoleManager.Error.TaskListIsEmpty.message)
            return
        }

        println(ConsoleManager.Features.searchTasksFeature())
        val searchWayChoose = ServiceManager.readInt(ConsoleManager.Menus.searchMenu()) ?: return

        when (searchWayChoose) {
            1 -> {

                println(ConsoleManager.Features.searchTasksByIdFeature())
                val idInput = ServiceManager.readInt(ConsoleManager.Inputs.taskIdInput()) ?: return
                val result = TaskUtility.findTaskById(idInput, tasks)

                if (result == null) {
                    ConsoleManager.printError(ConsoleManager.Error.TaskNotFoundIDError.message)
                } else {
                    ConsoleManager.printSuccess(
                        ConsoleManager.Success.TaskFoundSuccess.message,
                        additionalInfo = "Task ID: $idInput"
                    )
                    TaskUtility.printTask(result)
                }

            }

            2 -> {

                println(ConsoleManager.Features.searchTasksByNameFeature())

                print(ConsoleManager.Inputs.taskNameInput())
                val nameInput = readln().trim()
                val result = TaskUtility.findTasksByName(nameInput, tasks)

                if (result.isEmpty() || nameInput == "") {
                    ConsoleManager.printError(ConsoleManager.Error.TaskNotFoundNameError.message)
                } else {
                    ConsoleManager.printSuccess(ConsoleManager.Success.TaskFoundSuccess.message)
                    result.forEach { task ->
                        TaskUtility.printTask(task)
                    }
                }

            }

            else -> ConsoleManager.printError(ConsoleManager.Error.OutRangeSearchMenuInput.message)
        }

    }

    fun editTask() {
        if (tasks.isEmpty()) {
            ConsoleManager.printError(ConsoleManager.Error.TaskListIsEmpty.message)
            return
        }

        println(ConsoleManager.Features.editTaskFeature())
        showAllTasks()
        val taskId = ServiceManager.readInt(ConsoleManager.Inputs.taskIdInput()) ?: return
        val result = TaskUtility.findTaskById(taskId, tasks)

        if (result == null) {
            ConsoleManager.printError(ConsoleManager.Error.TaskNotFoundIDError.message, "Task Id : $taskId")

        } else {
            ConsoleManager.printSuccess(ConsoleManager.Success.TaskFoundSuccess.message)
            TaskUtility.printTask(result)

            val editChoose = ServiceManager.readInt(ConsoleManager.Menus.editMenu()) ?: return

            TaskUtility.editSwitcher(editChoose, tasks, result)

        }

    }

    fun sortTasks() {

        if (tasks.isEmpty()) {
            ConsoleManager.printError(ConsoleManager.Error.TaskListIsEmpty.message)
            return
        }

        println(ConsoleManager.Features.sortTasksFeature())
        val sortChoose = ServiceManager.readInt(ConsoleManager.Menus.sortMenu()) ?: return

        TaskUtility.sortSwitcher(sortChoose, tasks)

    }

    fun showStatistics() {

        if (tasks.isEmpty()) {
            ConsoleManager.printError(ConsoleManager.Error.TaskListIsEmpty.message)
            return
        }

        println(ConsoleManager.Features.statisticsFeature())
        TaskUtility.printStatistics(tasks)

    }

}

object TaskUtility {

    fun isValidTask(task: Task, tasks: List<Task>): Boolean {
        return isValidTaskName(task.name, tasks) && isValidTaskPriority(task.priority)
    }

    private fun isValidTaskName(name: String, tasks: List<Task>): Boolean {

        if (name.isBlank()) {
            ConsoleManager.printError(ConsoleManager.Error.BlankTaskNameError.message)
            return false
        }

        if (name.length !in Consts.MIN_TASK_NAME_LENGTH..Consts.MAX_TASK_NAME_LENGTH) {
            ConsoleManager.printError(ConsoleManager.Error.IncorrectTaskLengthError.message)
            return false
        }

        if (tasks.any { it.name.equals(name, true) }) {
            ConsoleManager.printError(
                ConsoleManager.Error.SameTaskNameError.message
            )
            return false
        }

        return true
    }

    private fun isValidTaskPriority(priority: Int): Boolean {

        if (priority !in Consts.MIN_PRIORITY_NUMBER..Consts.MAX_PRIORITY_NUMBER) {
            ConsoleManager.printError(ConsoleManager.Error.OutRangeTaskPriorityError.message)
            return false
        }

        return true

    }

    fun printTask(task: Task) {

        task.run {
            println("[$id] $name ${if (status == TaskStatus.IN_PROGRESS) "⏳" else "✅"} Priority: $priority")
        }

    }

    fun printStatistics(tasks: List<Task>) {

        println(
            """
           🟢 All Tasks Count : ${tasks.size}
           🟢 Done Tasks Count : ${tasks.count { it.status == TaskStatus.DONE }}
           🟢 In-Progress Tasks Count : ${tasks.count { it.status == TaskStatus.IN_PROGRESS }}
           🟢 High Priority Tasks Count (4-5) : ${tasks.count { it.priority == 5 || it.priority == 4 }}
           🟢 Longest Task name : ${tasks.maxByOrNull { it.name.length }?.name}
        """.trimIndent()
        )

    }

    fun findTaskById(id: Int, tasks: List<Task>): Task? {
        return tasks.find { it.id == id }
    }

    fun findTasksByName(name: String, tasks: List<Task>): List<Task> {
        return tasks.filter { it.name.contains(name, true) }
    }

    fun editSwitcher(editMenuInput: Int, tasks: List<Task>, task: Task) {

        when (editMenuInput) {

            1 -> {
                println(ConsoleManager.Features.editTaskNameFeature())
                print(ConsoleManager.Inputs.taskNameInput())
                val newName = readln().trim()

                if (task.name.equals(newName, true)) {
                    ConsoleManager.printError(
                        ConsoleManager.Error.NewNameCouldNotBeSameToOld.message,
                        additionalInfo = "Old Name : ${task.name}"
                    )
                    return
                }

                if (isValidTaskName(newName, tasks)) {
                    ConsoleManager.printSuccess(
                        ConsoleManager.Success.TaskNameChangedSuccess.message,
                        "New Name: $newName"
                    )
                    task.name = newName

                }
            }

            2 -> {
                println(ConsoleManager.Features.editTaskPriorityFeature())
                val newPriority = ServiceManager.readInt(ConsoleManager.Inputs.taskPriorityInput()) ?: return

                if (task.priority == newPriority) {
                    ConsoleManager.printError(
                        ConsoleManager.Error.NewPriorityCouldNotBeSameToOld.message,
                        additionalInfo = "Current Priority : ${task.priority}"
                    )
                    return
                }

                if (isValidTaskPriority(newPriority)) {
                    ConsoleManager.printSuccess(
                        ConsoleManager.Success.TaskPriorityChangedSuccess.message,
                        "New priority: $newPriority"
                    )
                    task.priority = newPriority

                }

            }

            else -> ConsoleManager.printError(ConsoleManager.Error.OutRangeEditMenuInput.message)
        }

    }

    fun sortSwitcher(sortMenuInput: Int, tasks: List<Task>) {
        when (sortMenuInput) {

            1 -> {
                println(ConsoleManager.Features.sortTasksByNameFeature())

                sortTasksByName(tasks).forEach { task ->

                    printTask(task)

                }

            }

            2 -> {
                println(ConsoleManager.Features.sortTasksByPriorityFeature())

                sortTasksByPriority(tasks).forEach { task ->

                    printTask(task)

                }

            }

            3 -> {
                println(ConsoleManager.Features.sortTasksByIDFeature())

                sortTasksByID(tasks).forEach { task ->

                    printTask(task)

                }
            }

            else -> ConsoleManager.printError(ConsoleManager.Error.OutRangeSortMenuInput.message)

        }
    }


    fun sortTasksByID(tasks: List<Task>): List<Task> {
        return tasks.sortedBy { it.id }
    }

    fun sortTasksByName(tasks: List<Task>): List<Task> {
        return tasks.sortedBy { it.name }
    }

    fun sortTasksByPriority(tasks: List<Task>): List<Task> {
        return tasks.sortedByDescending { it.priority }
    }


}

object ConsoleManager {

    sealed class Error(
        val message: String
    ) {
        object InvalidMainMenuError : Error("Invalid Input . Please enter from 1 to ${Consts.MENU_FEATURES_COUNT}")

        object BlankTaskNameError : Error("Task name cannot be empty.")

        object IncorrectTaskLengthError :
            Error("Task name must be on ${Consts.MIN_TASK_NAME_LENGTH} to ${Consts.MAX_TASK_NAME_LENGTH}.")

        object SameTaskNameError : Error("Task name must be new. You entered for another task.")

        object OutRangeTaskPriorityError :
            Error("Task priority must be between ${Consts.MIN_PRIORITY_NUMBER} to ${Consts.MAX_PRIORITY_NUMBER}. ${Consts.MAX_PRIORITY_NUMBER} means Very Important")

        object TooManyIncorrectAttemptsError : Error("Too many in-correct attempts. please try again later")

        object IntInputError : Error("Input must be a valid number .")
        object TaskListIsEmpty : Error("Task list is empty. Please try adding a task first")
        object TaskNotFoundIDError : Error("Task not found. Try entering true task id.")
        object TaskNotFoundNameError : Error("Task not found. Try entering true task name.")
        object TaskIsAlreadyDone : Error("Task is already done.")
        object OutRangeSearchMenuInput : Error("Please enter from 1 to ${Consts.SEARCH_WAYS_NUMBER}")
        object OutRangeEditMenuInput : Error("Please enter from 1 to ${Consts.EDITABLE_STUFFS_NUMBER}")
        object OutRangeSortMenuInput : Error("Please enter from 1 to ${Consts.SORT_WAYS_NUMBER}")
        object NewNameCouldNotBeSameToOld : Error("New name Couldn't be same to old one.")
        object NewPriorityCouldNotBeSameToOld : Error("New priority Couldn't be same to old one.")

    }

    sealed class Success(
        val message: String
    ) {

        object TaskAddedSuccess : Success("Task Added Successfully!")

        object FirstTaskAddedSuccess : Success("Your First task Added Successfully!")

        object TaskDiscardedSuccess : Success("Your task Discarded Successfully!")

        object TaskCompletedSuccess : Success("Your task Completed Successfully!")

        object TaskFoundSuccess : Success("Your task Found successfully!")

        object TaskNameChangedSuccess : Success("Your task Name Changed Successfully!")

        object TaskPriorityChangedSuccess : Success("Your task Priority Changed Successfully!")


    }

    fun printError(msg: String, additionalInfo: String = "") {
        println("❌ $msg $additionalInfo")
    }

    fun printSuccess(msg: String, additionalInfo: String = "") {
        println("✅ $msg $additionalInfo")
    }

    object Menus {

        fun mainMenu() = """
          
            🔥 Task Manager 1.0
            1. Add Task 📝
            2. Discard Task ❌
            3. Complete Task ✅
            4. Show All Tasks 🌍
            5. Search Tasks 🔎
            6. Edit Task 📔
            7. Sort Tasks 📕
            8. Statistics 🔢
            9. FAQ ℹ️
            10. Exit 🫆
            
            Choose > 
        """.trimIndent()

        fun searchMenu() = """
            🔥 How you want to search task ?
            
            1. Entering ID 
            2. Entering Name
             
            Choose > 
        """.trimIndent()

        fun editMenu() = """
            What you want to edit ? 
            
            1. Editing Task Name
            2. Editing Task Priority
          
            Choose > 
        """.trimIndent()

        fun sortMenu() = """
            How you want to sort tasks ? 
            
            1. Sort Tasks by Name (a .. z)
            2. Sort Task by Priority (5 .. 1)
            3. Sort Task by ID (1 .. ∞)
          
            Choose > 
        """.trimIndent()

        fun printFAQ() {
            println(
                """
                Q: What is Task Manager?
                A: A console application for managing tasks.

                Q: How do I add a task?
                A: Select Add Task, then enter its name and priority.

                Q: What is the valid priority range?
                A: From 1 to 5.

                Q: What is the maximum task name length?
                A: 20 characters.

                Q: Can two tasks have the same name?
                A: No, task names must be unique.

                Q: How do I complete a task?
                A: Select Complete Task and enter its ID.

                Q: How do I delete a task?
                A: Select Discard Task and enter its ID.

                Q: How can I search for a task?
                A: You can search by ID or name.

                Q: Does name search support partial names?
                A: Yes.

                Q: Is task name search case-sensitive?
                A: No.

                Q: Can I edit a task?
                A: Yes, you can edit its name or priority.

                Q: How can I sort tasks?
                A: You can sort them by name, priority, or ID.

                Q: What does Statistics show?
                A: It shows task counts, high-priority tasks, and the longest task name.

                Q: How many attempts do I have for invalid number input?
                A: You have 3 attempts. 

                Q: Are tasks saved after exiting?
                A: No, tasks are currently stored only in memory. It will supposed to get improved in next version

            """.trimIndent()
            )
        }

    }

    object Inputs {

        fun taskNameInput() = "Enter task name: "
        fun taskPriorityInput() = "Enter task priority (1/5): "
        fun taskIdInput() = "Enter Task ID: "

    }

    object Features {

        fun addTaskFeature() = "🗒 Add Task️:"
        fun discardTaskFeature() = "❌ Discard Task:"
        fun completeTaskFeature() = "✅ Complete Task:"
        fun showAllTasksFeature() = "🌍 All Tasks:"
        fun searchTasksFeature() = "🔎 Search Tasks:"
        fun searchTasksByIdFeature() = "🔎 Search Tasks By ID:"
        fun searchTasksByNameFeature() = "🔎 Search Tasks By Name:"
        fun editTaskFeature() = "📗 Edit task:"
        fun editTaskNameFeature() = "📗 Edit task name:"
        fun editTaskPriorityFeature() = "📗 Edit task priority: "
        fun sortTasksFeature() = "🔥 Sort Tasks: "
        fun sortTasksByNameFeature() = "🔥 Sorted Tasks by Name: "
        fun sortTasksByPriorityFeature() = "🔥 Sorted Tasks by Priority: "
        fun sortTasksByIDFeature() = "🔥 Sorted Tasks by ID: "
        fun statisticsFeature() = "🚀 Statistics: "


    }


}

object Consts {
    const val MENU_FEATURES_COUNT = 10
    const val MIN_TASK_NAME_LENGTH = 2
    const val MAX_TASK_NAME_LENGTH = 20
    const val MIN_PRIORITY_NUMBER = 1
    const val MAX_PRIORITY_NUMBER = 5
    const val SEARCH_WAYS_NUMBER = 2
    const val SORT_WAYS_NUMBER = 3
    const val EDITABLE_STUFFS_NUMBER = 2
    const val MAX_INT_INPUT_ATTEMPTS = 3

}