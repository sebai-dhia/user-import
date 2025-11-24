# 📂 Bulk User Import System

A simple system for **bulk importing users from CSV files**. The backend is built with **Spring Boot and Kotlin**, and the frontend is built with **React.js**. Users can upload CSV files, which are validated and processed asynchronously.

---

## 📚 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Backend Setup](#backend-setup)
- [Frontend Setup](#frontend-setup)
- [Running the Application](#running-the-application)
- [Design Choices](#design-choices)
- [CSV File Structure Example](#csv-file-structure-example)
- [License](#license)

---

## 🔍 Overview

This application allows uploading CSV files containing user information. Each row is validated, and invalid rows are logged but do not block processing of other rows.

---

## ✨ Features

- Bulk CSV file upload.
- Asynchronous processing using **Kotlin Coroutines**.
- Validation of `id`, `firstName`, `lastName`, and `email`.
- Clear feedback on frontend for success and errors.

---

## ⚙️ Prerequisites

### Backend

- Java 17
- Gradle 8+ (or use Gradle wrapper)

### Frontend

- Node.js 20+
- npm 9+

---

## ✅ Project Structure

### Backend
```text
backend/
├── src/main/kotlin/com/example/userimport
│   ├── controller
│   │   └── CsvUploadController.kt
│   ├── event
│   │   ├── EventBus.kt
│   │   └── FileUploadedEvent.kt
│   ├── model
│   │   └── User.kt
│   ├── service
│   │   └── CsvProcessorService.kt
│   └── BulkUserImportSystemApplication.kt
├── build.gradle.kts
└── settings.gradle.kts
````

### Frontend

```text
frontend/
├── node_modules/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   └── FileUploadForm.js
│   ├── services/
│   │   └── api.js
│   ├── utils/
│   │   └── validators.js
│   ├── App.css
│   ├── App.js
│   ├── index.css
│   └── index.js
├── .gitignore
├── package-lock.json
└── package.json
```

---

## 🛠 Backend Setup

1. **Clone the repository**

```bash
git clone https://github.com/sebai-dhia/user-import.git
cd user-import/backend
```

2. **Build the project**

```bash
./gradlew clean build
```

3. **Run the application**

```bash
./gradlew bootRun
```

* Backend URL: **[http://localhost:8082](http://localhost:8082)**
* Upload endpoint: `POST /api/files/upload`

---

## 🛠 Frontend Setup

1. Navigate to the frontend folder:

```bash
cd ../frontend
```

2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
npm start
```

* Frontend URL: **[http://localhost:3000](http://localhost:3000)**

---

## 🚀 Running the Application

1. Start the backend (`./gradlew bootRun`)
2. Start the frontend (`npm start`)
3. Open **[http://localhost:3000](http://localhost:3000)**
4. Upload a CSV file
5. Backend processes rows asynchronously

---

## 💡 Design Choices

### Pub/Sub Implementation with Kotlin Coroutines

* Lightweight, non-blocking concurrency
* 4 worker coroutines for balanced throughput
* `Channel.UNLIMITED` ensures no dropped events

### File Storage

* Uses OS-specific `java.io.tmpdir`
* UUID prefix prevents filename collisions

### Model Structure

* Non-nullable fields in `User` model
* Prevents null-related errors during parsing

### Validation

* Jakarta Validation: `@NotBlank`, `@Email`, `@Pattern`
* Invalid rows logged without blocking processing

---

## 📄 CSV File Structure Example

| id | firstName | lastName | email                                       |
| -- | --------- | -------- | ------------------------------------------- |
| 1  | John      | Doe      | [john@company.com](mailto:john@company.com) |
| 2  | Jane      | Smith    | [jane@manu.com](mailto:jane@manu.com)       |
| 3  | Bob       | Johnson  | [bob@barca.com](mailto:bob@barca.com)       |

**Notes:**

* Extra columns ignored
* Missing columns → row skipped
* Header optional
* `sample.csv` included in project

---

## 📜 License

This project is licensed under the MIT License.
