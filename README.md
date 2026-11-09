# Booking Spring MVC Application

Веб-приложение для управления клиентами и бронированиями с типовой трехслойной архитектурой на Spring Framework.

## Стек технологий

* **Язык:** Java 21
* **Фреймворк:** Spring Boot 3.2 (Spring MVC, Spring Data JPA)
* **ORM:** Hibernate
* **Шаблонизатор:** Thymeleaf
* **База данных:** PostgreSQL 16
* **Контейнеризация:** Docker, Docker Compose
* **Сборщик:** Maven

## Архитектура проекта

Приложение реализовано в соответствии с трехслойной архитектурой:
1. **Слой данных (Persistence Layer):**
   * Сущности: `Client`, `Booking`, перечисление `BookingStatus`
   * Репозитории: `ClientRepository`, `BookingRepository` (Spring Data JPA)
2. **Бизнес-слой (Service Layer):**
   * `ClientService` — управление клиентами, валидация уникальности данных
   * `BookingService` — управление бронированиями, валидация корректности дат
   * Исключения: `BusinessException`
3. **Слой представления (Presentation Layer):**
   * Контроллеры: `ClientController`, `BookingController`, `HomeController`, `BaseController`
   * Представления: Thymeleaf-шаблоны (`index.html`, фрагменты `clients.html`, `bookings.html`)

## Запуск приложения

### Через Docker Compose (рекомендуемый способ)

1. Создайте файл переменных окружения из шаблона:
   ```bash
   cp .env.example .env
