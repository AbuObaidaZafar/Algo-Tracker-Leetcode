# Algo Tracker & Complexity Analyzer ??

An AI-powered Algorithmic Performance Tracker built with Java 21, Spring Boot 3.3.4, and Spring AI.

## ?? Features
* **RESTful API**: Ingests raw Java text code blocks via endpoints.
* **Structured AI Outputs**: Utilizes BeanOutputConverter to mandate clean JSON data schemas via Ollama (Llama3).
* **Persistence Logs**: Stores code assessment analysis metrics inside an in-memory H2 Database Engine.

## ?? Endpoints
* **POST** http://localhost:8080/api/algorithms/analyze (Analyzes code)
* **GET** http://localhost:8080/api/algorithms/history (Fetches logs)
