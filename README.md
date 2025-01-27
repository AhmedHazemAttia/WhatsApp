# Project Setup

This document outlines the steps to set up both the frontend and backend of the project.

## Prerequisites

*   **Frontend:**
    *   Node.js and npm (Node Package Manager) must be installed on your system.
    *   Angular CLI (Command Line Interface) should be installed globally. You can install it using:

        ```bash
        npm install -g @angular/cli
        ```
*   **Backend:**
    *   Docker must be installed on your system.

## Backend Setup

1.  **Navigate to the backend project directory:**

    ```bash
    cd <path/to/your/backend/project>
    ```

2.  **Start the backend services (including Keycloak and the database):**

    ```bash
    docker-compose up
    ```

    This command will build and start the Docker containers defined in your `docker-compose.yml` file, which includes the Keycloak server and the database.

3.  **Access the Keycloak admin panel:** Once the containers are running, navigate to `http://localhost:9090` in your browser to access the Keycloak admin panel.

4.  **Keycloak Configuration:** In the Keycloak admin panel, you can:
    *   Create your realm.
    *   Configure other Keycloak settings (e.g., clients, users, roles).

## Frontend Setup

1.  **Navigate to the frontend project directory:**

    ```bash
    cd <path/to/your/frontend/project>
    ```

2.  **Install dependencies:**

    ```bash
    npm install
    ```

3.  **Running the Development Server:**

    ```bash
    ng serve
    ```

4.  **Access the application:** Open your browser and navigate to the frontend server's port (usually `http://localhost:4200`).

## Keycloak Authentication (Frontend)

After accessing the frontend application in your browser, you will likely be redirected to the Keycloak login page (which is part of the backend setup). Here you can:

*   **Log in:** Using the credentials you configured in the Keycloak admin panel.

## Running the Project (Combined)

1.  Start the backend using `docker-compose up` (as described in the Backend Setup section).
2.  Start the frontend development server using `ng serve` (as described in the Frontend Setup section).
3.  Access the frontend application in your browser. The frontend will interact with the backend and use Keycloak for authentication.

## Troubleshooting

If you encounter any issues during the setup or running of the project, please consult the project documentation or contact the development team.

## Further Information

For more detailed information about the project, please refer to the project's main README or other relevant documentation.
