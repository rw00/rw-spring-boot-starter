# Test Application for rw-starter

### Prerequisites

1. Java21
2. Docker

### Build and Test

####

```shell
./mvnw clean verify
```

This creates a database container and runs the unit and integration tests.

### Run

For local development, start the database and server:

#### Database

Start the database by running this command:

```shell
docker run -d --rm --name app-db -e POSTGRES_USER=root -e POSTGRES_PASSWORD=r00t -e POSTGRES_DB=app-db -p 51289:5432 postgres:16.6
```

#### Application

Start the application by running this command:

```shell
./mvnw spring-boot:run
```

Or simply run \
`com.rw.apps.starter.test.RwStarterTestApplication.main`

### Check

1. Go to http://localhost:8080/login
1. Enter `user=rw.test@gmail.com`
1. Enter `pass=any`
1. Go to http://localhost:8080/admin/api-docs

### Stop

Stop the database by running this command:

```shell
docker stop app-db
```

Stop the application by pressing `Ctrl+C` or `Ctrl+Z`

### Troubleshooting

To enter the database container, run this command:

```shell
docker exec -it app-db psql app-db
```
