# Car Management Web Service

A **Spring Boot RESTful API** for managing cars and their parts. Supports multiple car types (Electrical, Sport, Two-Wheels), CRUD operations, and automatic retrieval of related car parts.

---

## Features

* **Car Types:** Electrical, Sport, Two-Wheels
* **Car Parts:** Each car can have multiple parts (engine, battery, wheels, etc.)
* **CRUD Operations:** Create, Read, Update, Delete cars and parts
* **Flexible Queries:** Filter cars by category, name, brand, or release year
* **UUID-based IDs:** All entities use `UUID` as primary key
* **Soft Delete:** Cars and parts are not removed from DB, just marked as deleted

---

## Technologies

* Java 17
* Spring Boot
* H2 (in-memory database, can switch to MySQL/Postgres)
* JPA / Hibernate
* Maven
* REST API (tested via Postman)

---

## Entity Structure

### Car

* `id`: UUID
* `name`: String
* `brand`: String
* `releasedYear`: Integer
* `color`: Enum (`CarColor`)
* `price`: Double
* `parts`: List of `CarPart`
* `isDeleted`: Boolean (soft delete)

### ElectricalCar (extends Car)

* `batteryCapacity`: Integer
* `chargingTime`: Integer

### SportCar (extends Car)

* `horsePower`: Integer
* `topSpeed`: Double

### TwoWheelsCar (extends Car)

* `engineCC`: Double
* `hasSideCar`: Boolean

### CarPart

* `id`: UUID
* `name`: String
* `category`: Enum (`CarPartCategory`)
* `brand`: String
* `price`: Double
* `car`: Parent `Car`
* `isDeleted`: Boolean

---

## API Endpoints

### Cars

| Method | Endpoint     | Description                                                                   |
| ------ | ------------ | ----------------------------------------------------------------------------- |
| GET    | `/cars`      | List all cars (optional filters: `category`, `name`, `brand`, `releasedYear`) |
| POST   | `/cars`      | Create a new car                                                              |
| PUT    | `/cars/{id}` | Update a car                                                                  |
| DELETE | `/cars/{id}` | Soft delete a car                                                             |

### Car Parts

| Method | Endpoint       | Description                  |
|--------|----------------|------------------------------|
| GET    | `/car-parts`   | Get car parts                |
| POST   | `/car-parts`   | Add a part to a specific car |

---

## Example: Add Electrical Car

**Request:**

```
POST /cars
Content-Type: application/json
```

```json
{
  "category": "electrical",
  "name": "car1",
  "brand": "Lumbogini",
  "color": "black",
  "battery_capacity": 7000,
  "charging_time": 3
}
```

**Response:**

```json
{
  "data": {
    "id": "379d1ac0-01e7-4fa2-b753-397df92f0de8",
    "name": "car1",
    "releasedYear": null,
    "brand": "Lumbogini",
    "color": "BLACK",
    "price": null,
    "parts": [],
    "batteryCapacity": 7000,
    "chargingTime": 3,
    "notDeleted": true
  },
  "type": null,
  "message": "Creation success",
  "isSuccess": true
}
```
---

## Running the Project

1. Clone the repository:

```bash
git clone <repo-url>
```

2. Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

3. Open Postman and test endpoints:

```
http://localhost:8080/cars
```

