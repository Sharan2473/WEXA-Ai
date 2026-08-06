# Movie Graph Explorer 🎬🕸️

A full-stack web application that allows users to explore and discover movies. Built as a comprehensive Java Full Stack and React engineering project, this application leverages a Neo4j graph database to search for movies by title and genre, and features a built-in recommendation engine that suggests similar movies based on shared graph connections.

## ✨ Features
* **Universal Search:** Filter the database by typing a movie title or clicking a specific genre button.
* **Graph-Powered Recommendations:** Click on any movie to open a detailed modal that automatically traverses the graph database to recommend connected movies ("You might also like...").
* **Responsive UI:** A clean, modern interface built with React and Bootstrap that works seamlessly across devices.
* **REST API:** A custom Java Spring Boot backend that handles dynamic Cypher queries to interact with the database.

## 🛠️ Tech Stack
* **Frontend:** React, Vite, JavaScript, Bootstrap
* **Backend:** Java, Spring Boot, Maven
* **Database:** Neo4j (hosted via CognoDB)

---

## 🚀 How to Run the Project Locally

To run this application, you will need to start both the Java Spring Boot backend and the React Vite frontend in separate terminal windows.

### 1. Start the Backend (Spring Boot)
        1. Open a terminal and navigate to the backend directory:
            ```bash
            cd backend/movie-explorer-api/movie-explorer-api

        2. Set your temporary environment variables for the CognoDB connection (using Windows PowerShell):

            $env:COGNO_DB_URI="bolt+s://db-57378d2a.databases.cognodb.com"
            $env:COGNO_DB_USER="cognodb"
            $env:COGNO_DB_PASSWORD="ebeaea4a10842a4bef9fe9005f339556"

        3. Run the application using the Maven wrapper:

            .\mvnw.cmd spring-boot:run

2. Start the Frontend (React + Vite):
    1. Open a second, separate terminal and navigate to your frontend directory:
        cd frontend

    2. Install the necessary Node dependencies:
        npm install

    3. Start the Vite development server:
        npm run dev

    4. Open your web browser and navigate to http://localhost:5173 to view the application!

## 2. Data Model Diagram
The data model is deliberately straightforward to highlight the multi-hop traversal capability.

```text
  [ Node: Movie ]                             [ Node: Genre ]
 -------------------                         -----------------
  + id (String)                               + name (String)
  + title (String)          -[:IN_GENRE]->   
  + plot (String)
  + posterUrl (String)