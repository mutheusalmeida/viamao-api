## ViaMão REST API

ViaMão is a travel planner application built to help users manage their trips. This API was built with Java, Spring Boot, and MySQL to be consumed by ViaMão front-end application.

## Features & Technologies

- Spring Security
- Lombok
- Flyway
- Authentication
- Create, read, update, and delete a trip
- Add, update, and delete places to visit

## API Reference

#### Get all trips

```http
  GET /trips
```

#### Get trip

```http
  GET /trip/${id}
```

| Parameter | Type   | Description                   |
| :-------- | :----- | :---------------------------- |
| `id`      | `Long` | **Required**. Id of the trip. |

#### Create trip

```http
  POST /trips
```

| Body          | Type        | Description                                |
| :------------ | :---------- | :----------------------------------------- |
| `title`       | `String`    | **Required**. The title of the trip.       |
| `description` | `String`    | **Required**. The description of the trip. |
| `destination` | `String`    | **Required**. The destination of the trip. |
| `start_date`  | `LocalDate` | **Required**. The start date of the trip.  |
| `end_date`    | `LocalDate` | **Required**. The end date of the trip.    |

#### Add place to visit

```http
  POST /trips/${id}/places
```

| Body          | Type     | Description                                 |
| :------------ | :------- | :------------------------------------------ |
| `name`        | `String` | **Required**. The name of the place.        |
| `description` | `String` | **Required**. The description of the place. |

## Documentation

For a better understanding of the API, see the
[Swagger Documentation](https://viamao-api-production.up.railway.app/swagger-ui/index.html).

## Authors

- [@mutheusalmeida](https://www.github.com/mutheusalmeida)
