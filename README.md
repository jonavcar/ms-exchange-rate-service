# ms-exchange-rate-service

## Descripción
Microservicio para la consulta de tipo de cambio, desarrollado para el banco "ABC". Permite consultar el tipo de cambio del día, registrando cada consulta por cliente (DNI) y limitando el número de consultas diarias a 10 por cliente.

## Requerimientos del caso práctico
- Endpoint GET para consultar tipo de cambio, con parámetro obligatorio `dni`.
- Registro de cada consulta en base de datos, asociada al DNI y fecha.
- Consulta del número de consultas por cliente y validación de límite diario (10 consultas).
- Consumo de API externa: [https://free.e-api.net.pe/tipo-cambio/today.json](https://free.e-api.net.pe/tipo-cambio/today.json).
- Tecnologías: JDK21, Quarkus, Panache, Rest Jackson, Rest Client Jackson.

## Arquitectura
- **Controlador REST**: `ExchangeRateController` expone el endpoint GET `/exchange-rate`.
- **Servicio**: `ExchangeRateService` gestiona la lógica de negocio, validación y persistencia.
- **Cliente REST**: `ExchangeRateClient` consume la API externa.
- **Repositorio**: `ConsultaRepository` gestiona el acceso a la base de datos usando Panache.
- **Modelo**: `ConsultaRegistro` representa el registro de consulta en BD.
- **Validación**: `DniValidator` valida el parámetro DNI.

## Endpoints
### Consulta de tipo de cambio
- **Método:** GET
- **URL:** `/api/v1/exchange-rate?dni={dni}`
- **Parámetros:**
  - `dni` (String, obligatorio, 8 dígitos)
- **Respuesta exitosa:**
```json
{
  "fecha": "2025-10-03",
  "sunat": 3.546,
  "compra": 3.54,
  "venta": 3.552
}
```
- **Errores:**
  - Si el DNI es inválido: `400 Bad Request`
  - Si supera el límite diario: `Ha excedido el límite de 10 consultas diarias`
  - Si hay error externo: `503 Service Unavailable`

## Reglas de negocio
- El cliente puede consultar hasta 10 veces por día (por DNI).
- Cada consulta se registra en la base de datos con DNI, fecha y valores de tipo de cambio.
- Si supera el límite, se devuelve un error y no se realiza la consulta externa.

## Tecnologías utilizadas
- JDK 21
- Quarkus
- Hibernate ORM Panache
- RESTEasy Jackson
- REST Client Jackson
- H2 Database (memoria)

## Configuración
La configuración principal se encuentra en `src/main/resources/application.properties`:
```properties
quarkus.datasource.db-kind=h2
quarkus.datasource.username=sa
quarkus.datasource.password=
quarkus.datasource.jdbc.url=jdbc:h2:mem:tipo_cambio_db;DB_CLOSE_DELAY=-1
quarkus.hibernate-orm.database.generation=update
quarkus.rest-client.exchange-rate-api.url=https://free.e-api.net.pe
consulta.diaria.limite=10
quarkus.http.port=8080
quarkus.http.root-path=/api/v1
```

## Ejecución
1. Clona el repositorio.
2. Asegúrate de tener JDK 21 y Maven instalados.
3. Ejecuta el microservicio:
   ```cmd
   mvn clean compile quarkus:dev
   ```
4. Accede al endpoint:
   ```
   GET http://localhost:8080/api/v1/exchange-rate?dni=12345678
   ```

## Ejemplo de respuesta
```json
{
  "fecha": "2025-10-03",
  "sunat": 3.546,
  "compra": 3.54,
  "venta": 3.552
}
```

## Ejemplo de error por límite excedido
```json
{
  "error": "Ha excedido el límite de 10 consultas diarias."
}
```

## Pruebas
Incluye pruebas unitarias y de integración para la lógica de negocio y el endpoint REST.

## Autor
Jon Av Carr
