# PRIME-NUMBER-SELECTOR

## Running the application
```bash
## Run the app
docker-compose up -d
```

## Usage
Producer service has two endpoints exposed - /start and /stop. Use them to start or stop producing random numbers

### Request
`POST http://localhost:8080/producer/start`

```json
{
  "rate": 1000,
  "chunkSize": 5,
  "lowerBound": 1,
  "upperBound": 100
}
```

### Response
200 OK

or

400 Bad Request

```json
{
    "path": "/producer/start",
    "validationErrors": {
        "rate": "Message sending rate cannot be less than 1 ms"
    },
    "error": "Validation Failed",
    "message": "Input data has validation errors",
    "timestamp": "2024-08-17T16:56:00.1921117",
    "status": 400
}
```

### Request
`POST http://localhost:8080/producer/stop`

### Response

200 OK