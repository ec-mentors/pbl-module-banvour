# <img src="src/main/resources/static/images/tomato.svg" alt="Logo" width="30px"> Pomogatto

This is my first ever backend coding project, a Pomodoro timer application using the well known <a href="https://en.wikipedia.org/wiki/Pomodoro_Technique">time management method</a> developed by Francesco Cirillo. It allows users to create and manage personalized work/break presets, track their sessions, and associate break periods with specific, repeatable activities.

This project serves as a comprehensive full-stack application, featuring a robust backend built with Java and Spring Boot, and a dynamic frontend for user interaction.

## ✏️ UI/UX Design
<p align="center">
<img width="1915" height="766" alt="pomogatto_screenshots" src="https://github.com/user-attachments/assets/6bbbbde8-cd90-4025-91df-5763923f1604" /></p>

I aimed for a design that is minimal, yet soft and comforting. As you can see, the color beige does that for me, i think my brain associates it with a cozy feeling because it reminds me of my daily coffee.
In the future i plan to make the color palette customizable and implement my own artwork to visualize the activities and add extra motivation.

You can also take a look at my <a href="https://www.figma.com/design/S5SNLSv1zWtwsKyKI5OW3x/pomogatto?node-id=0-1&t=Y0CNrIhc7I3zvwQ8-1">Wireframes</a> on Figma.

## 📌 Features

*   **Immutable Preset Management:** Create and update custom work presets. An advanced versioning system ensures that session history remains accurate even when presets are changed over time.
*   **Activity Linking:** Associate presets with specific break-time activities (e.g., "Stretch," "Do 20 push-ups").
*   **Historical Session Logging:** A complete REST API for logging completed Pomodoro sessions.
*   **Dynamic UI:** The frontend is powered by vanilla JavaScript, consuming a backend API to create a responsive, single-page experience without page reloads.

## 📚 Tech Stack

### Backend
*   **Java 17+** & **Spring Boot 3**
*   **Spring Data JPA / Hibernate:** For object-relational mapping.
*   **PostgreSQL:** Relational database for persistent storage.
*   **Maven:** Dependency management.

### Frontend
*   **HTML5, CSS3, JavaScript (ES6+)**

## 📐 Architecture

This project is built using a modern, decoupled, architecture to ensure a clean separation of concerns and maintainability
    
### Backend Architecture
Follows a standard layered pattern:

1.  **Controller Layer (`@RestController`):** Handles HTTP requests, validates input, and delegates work to the service layer. Communicates using DTOs.
2.  **Service Layer (`@Service`):** Contains all business logic and is the transactional boundary of the application.
3.  **Mapper Layer (`@Component`):** Converts internal database Entities into public-facing DTOs to create a stable API contract.
4.  **Repository Layer (`JpaRepository`):** The data access layer that communicates with the PostgreSQL database.
5.  **Entity Layer (`@Entity`):** Java objects representing the database schema.

### Frontend Architecture
The frontend is a static application served by Spring Boot. It operates as a single-page application (SPA) by loading a simple HTML skeleton and using JavaScript to make asynchronous `fetch` calls to the backend's REST API, dynamically building the UI.

## 📝 Future Work

*   [ ] Implement the **frontend timer logic** (countdown, work/break states).
*   [ ] Build the frontend mechanism to **automatically log completed sessions** by calling the `POST /api/sessions` endpoint.
*   [ ] Migrate the frontend from vanilla JavaScript to a modern framework like React for more complex state management.
*   [ ] Package the final application as a cross-platform desktop app using Tauri.
