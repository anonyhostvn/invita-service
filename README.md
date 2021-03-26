![Java CI with Maven](https://github.com/anonyhostvn/invita-service/workflows/Java%20CI%20with%20Maven/badge.svg?branch=develop)

Build Package: `mvn clean package`

Build Docker Image: `docker build --tag invitation:<version> .`
Example: `docker build --tag invitation:1.0 .`

Run Docker Container: `docker run -p 8080:8080 invitation:<versionn>`
Example: ` docker run -p 8080:8080 invitation:1.0`
