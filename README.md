# 🔥 Task Manager

A simple and structured **console-based Task Manager** built with **Kotlin**.

This project was created as a practical Kotlin project to apply concepts such as data classes, enums, collections, functions, validation, searching, sorting, and basic project structure.

> **Current Version: 1.0**

---

## ✨ Features

* 📝 Add new tasks
* ❌ Discard tasks
* ✅ Complete tasks
* 🌍 Display all tasks
* 🔎 Search tasks

  * Search by ID
  * Search by name
  * Partial name matching
  * Case-insensitive search
* 📗 Edit tasks

  * Edit task name
  * Edit task priority
* 📕 Sort tasks

  * By name
  * By priority
  * By ID
* 🔢 Task statistics
* ℹ️ Built-in FAQ
* 🛡️ Input validation and error handling
* 🔢 Limited attempts for invalid integer input
* 🆔 Automatic task ID generation

---

## 🛠️ Tech Stack

* **Kotlin**
* Kotlin Collections
* Data Classes
* Enums
* Functions
* Objects
* Sealed Classes
* Null Safety
* Lambda Expressions
* Higher-Order Functions
* Basic Object-Oriented Programming

---

## 📋 Task Structure

Each task contains:

```text
ID
Name
Priority
Status
```

Example:

```text
[1] Learn Kotlin ⏳ Priority: 5
[2] Build Task Manager ✅ Priority: 4
```

### Task Status

Tasks can have one of two statuses:

* `IN_PROGRESS` ⏳
* `DONE` ✅

---

## 🔎 Search

Tasks can be searched using:

### By ID

```text
Enter Task ID: 3
```

### By Name

Name search supports:

* Partial matches
* Case-insensitive matching

For example, searching for:

```text
kotlin
```

can find:

```text
Learn Kotlin
Practice Kotlin Collections
Build Kotlin Project
```

---

## 📕 Sorting

Tasks can be sorted using three different methods:

```text
1. Sort Tasks by Name
2. Sort Tasks by Priority
3. Sort Tasks by ID
```

Priority sorting is performed from:

```text
5 → 1
```

---

## 🔢 Statistics

The statistics section provides information such as:

* Total task count
* Completed task count
* In-progress task count
* High-priority task count
* Longest task name

---

## 🛡️ Validation

The application validates task input before adding or editing tasks.

### Task Name

* Cannot be empty
* Must be between **2 and 20 characters**
* Cannot duplicate another task's name

### Task Priority

Priority must be between:

```text
1 → 5
```

where `5` represents the highest priority.

### Integer Input

Invalid integer input is limited to **3 attempts**.

---

## 📂 Project Structure

The project is organized around several components:

```text
Main
 ├── ServiceManager
 ├── TaskManager
 ├── TaskUtility
 ├── ConsoleManager
 ├── Consts
 ├── Task
 └── TaskStatus
```

### `Task`

Represents an individual task.

### `TaskManager`

Handles the main task-management operations.

### `TaskUtility`

Contains validation, searching, sorting, printing, and statistics-related logic.

### `ConsoleManager`

Contains menus, messages, inputs, errors, successes, and console UI.

### `ServiceManager`

Provides shared services such as integer input handling.

### `Consts`

Contains application constants and configuration values.

---

## 🚀 Getting Started

### Requirements

* Kotlin
* A Kotlin-compatible IDE such as IntelliJ IDEA

### Run

Clone the repository:

```bash
git clone <YOUR-REPOSITORY-URL>
```

Open the project in your IDE and run:

```text
main()
```

The application will start in the console.

---

## 🖥️ Main Menu

The application currently provides:

```text
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
10. Exit
```

---

## 💾 Data Storage

**Version 1.0 stores tasks only in memory.**

This means all tasks are lost when the application exits.

Persistent storage is planned for a future version.

---

## 🗺️ Roadmap

Possible improvements for future versions:

* [ ] Persistent task storage
* [ ] Load tasks when the application starts
* [ ] Save tasks when the application exits
* [ ] More advanced filtering
* [ ] More statistics
* [ ] Better task editing
* [ ] Improved architecture
* [ ] Unit tests
* [ ] More advanced task management features

---

## 📌 Version

### v1.0

Initial stable version featuring:

* Task creation
* Task deletion
* Task completion
* Task editing
* Task searching
* Task sorting
* Statistics
* Validation
* FAQ
* Console-based UI

---

## 👨‍💻 Author

**Taha Pishnahad**

Built as a practical Kotlin project to improve programming fundamentals and prepare for larger software projects.

---

## 📄 License

This project is available for learning and personal use.

## ©️ Copy Right 
© 2026 Taha Pishnahad. All rights reserved. README.md wrote with help of AI assistance.
