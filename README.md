# Exchange Rate Service

## Descripción
Microservicio para la consulta de tipo de cambio desarrollado para el banco "ABC". Permite consultar el tipo de cambio del día, registrando cada consulta por cliente (DNI) y limitando el número de consultas diarias a 10 por cliente.

## Características JDK 21 y Quarkus Imperativo
- ✅ **Virtual Threads**: Habilitados para alta concurrencia en operaciones I/O
- ✅ **Pattern Matching**: Switch expressions optimizadas con yield
- ✅ **Var Keyword**: Inferencia de tipos para código más limpio
- ✅ **Text Blocks**: Responses JSON formateados
- ✅ **RESTEasy Classic**: Consistencia en stack imperativo
- ✅ **Panache ORM**: Acceso simplificado a datos

## Tecnologías Principales
- **JDK 21** - Virtual threads y pattern matching
- **Quarkus 3.19.1** - Framework imperativo
- **Panache ORM** - Gestión de base de datos
- **RESTEasy Jackson** - REST endpoints
- **RESTEasy Client Jackson** - Cliente REST
- **H2 Database** - Base de datos en memoria
- **Bean Validation** - Validación de parámetros

## Arquitectura
- **Controller**: `ExchangeRateController` - Endpoints REST con virtual threads
- **Service**: `ExchangeRateService` - Lógica de negocio optimizada JDK 21
- **Repository**: `QueryRepository` - Acceso a datos con Panache
- **Proxy**: `ExchangeRateProxy` - Cliente para API externa
- **Model**: `QueryRecord` - Entidad JPA optimizada
- **Exception**: Manejo global de errores

## API Endpoints

### Consulta de Tipo de Cambio
```http
GET /api/v1/exchange-rate?dni={dni}
```

**Parámetros:**
- `dni` (String, requerido): DNI de 8 dígitos

**Respuesta exitosa (200):**
```json
{
  "fecha": "2025-10-05",
  "sunat": 3.546,
  "compra": 3.54,
  "venta": 3.552
}
```

**Errores:**
- `400` - DNI inválido
- `429` - Límite diario excedido (10 consultas)
- `503` - Servicio externo no disponible

### Swagger UI
```http
GET http://localhost:8080/swagger-ui
```
Documentación interactiva de la API

### OpenAPI Specification
```http
GET http://localhost:8080/openapi
```
Especificación OpenAPI en formato JSON

### Health Checks
```http
GET http://localhost:8080/health
```
Estado de salud del microservicio y dependencias

### Métricas
```http
GET http://localhost:8080/metrics
```
Métricas de rendimiento en formato Prometheus

## Instalación y Ejecución

### Prerrequisitos
- JDK 21+
- Maven 3.8+

### Desarrollo
```bash
# Clonar repositorio
git clone <repository-url>
cd ms-exchange-rate-service

# Ejecutar en modo desarrollo
mvn quarkus:dev
```

### Producción
```bash
# Compilar aplicación
mvn clean package

# Ejecutar JAR
java -jar target/exchange-rate-service-1.0.0-SNAPSHOT-runner.jar
```

### Docker (Nativo)
```bash
# Compilar imagen nativa
mvn clean package -Pnative -Dquarkus.native.container-build=true

# Construir imagen Docker
docker build -f src/main/docker/Dockerfile.native -t exchange-rate-service .

# Ejecutar contenedor
docker run -i --rm -p 8080:8080 exchange-rate-service
```

## Configuración

### Variables de Entorno
```bash
# Puerto de la aplicación
QUARKUS_HTTP_PORT=8080

# Límite de consultas diarias
DAILY_QUERY_LIMIT=10

# URL API externa
QUARKUS_REST_CLIENT_EXCHANGE_RATE_API_URL=https://free.e-api.net.pe
```

### Base de Datos
Por defecto usa H2 en memoria. Para usar PostgreSQL:

```properties
# PostgreSQL Configuration
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=postgres
quarkus.datasource.password=password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/exchange_rate_db
```

## Observabilidad

### Logs
- **Formato**: Timestamp, Level, Logger, Thread, Message
- **Niveles**: INFO para aplicación, WARN para frameworks
- **Estrategia**: Logs mínimos y estratégicos

### Métricas Disponibles
- Contadores de requests HTTP
- Tiempo de respuesta de endpoints
- Estado de conexiones de base de datos
- Métricas de virtual threads

### Health Checks
- **Liveness**: Verificación básica de la aplicación
- **Readiness**: Verificación de dependencias externas (API)

## Testing

### Ejecutar Tests
```bash
# Tests unitarios
mvn test

# Tests de integración
mvn verify
```

### Test Manual
```bash
# Consulta válida
curl "http://localhost:8080/api/v1/exchange-rate?dni=12345678"

# DNI inválido
curl "http://localhost:8080/api/v1/exchange-rate?dni=123"

# Verificar límite diario (ejecutar 11 veces)
for i in {1..11}; do
  curl "http://localhost:8080/api/v1/exchange-rate?dni=12345678"
done
```

## Optimizaciones JDK 21

### Virtual Threads
- Habilitados en controladores para alta concurrencia
- Optimización para operaciones I/O intensivas
- Mejor escalabilidad con menos recursos

### Pattern Matching
```java
return switch (Long.compare(todayQueries, dailyQueryLimit)) {
  case -1 -> { yield true; }
  case 0, 1 -> { yield false; }
  default -> false;
};
```

### Var Keyword
```java
var today = LocalDate.now();
var response = exchangeRateProxy.getExchangeRate();
var record = new QueryRecord(dni, date, sunatRate, buyRate, sellRate);
```

## Autor
**Joaquin Navarro Carrasco**
- Email: jonavcar@github.com
- GitHub: @jonavcar

## Licencia
MIT License
