
![MG Build Workflow](https://github.com/MG4Code/mg-football-be/workflows/MG%20Build%20Workflow/badge.svg)


# Sample backend application

## Features

### Swagger
tbd
### Logging
tbd
### Oauth2
tbd

## API

### Swagger UI

http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

### List all players
```
curl -X GET http://localhost:8080/player
```

### Add player
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{
            "firstName": "David",
            "lastName": "de Gea",
            "club": "Manchester United",
            "shirtNumber": 1,
            "position": "GOAL_KEEPER"
          }' \
  http://localhost:8080/player
```
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{
            "firstName": "Wayne",
            "lastName": "Rooney",
            "club": "Manchester United",
            "shirtNumber": 10,
            "position": "FORWARD"
          }' \
  http://localhost:8080/player
```
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{
            "firstName": "Cristiano ",
            "lastName": "Ronaldo",
            "club": "Manchester United",
            "shirtNumber": 7,
            "position": "FORWARD"
          }' \
  http://localhost:8080/player
```

### Modify player by id

```
curl --header "Content-Type: application/json" \
  --request PUT \
  --data '{
             "shirtNumber": 99
          }' \
  http://localhost:8080/player/5e44173220a3263ccaa597b7
```

### Get player by id
```
curl -X GET http://localhost:8080/player/5e440e8d066e3d607b3d8dbf
```

### Delete player by id
```
curl -X DELETE http://localhost:8080/player/5e440e8d066e3d607b3d8dbf
```

### Get all players for a club
```
curl -X GET http://localhost:8080/player/club?q="chester"
```

### Get first GOAL_KEEPER
```
curl -X GET http://localhost:8080/player/goalkeeper"
```
