# Flyway + MySQL 8.0.40

Este proyecto es una práctica de integración de Flyway con MySQL 8.0.40 para gestionar la migración de bases de datos de manera eficiente.

## Requisitos previos

- Java 17 o superior
- MySQL 8.0.40 instalado
- Docker y Docker Compose
- Spring Boot

## Configuración de MySQL con Docker

1. Crear un archivo `docker-compose.yml` con el siguiente contenido:
   ```yaml
   services:
    mysql:    
    image: 'mysql:8.0.40'
    environment:
      MYSQL_ROOT_PASSWORD: root_password
      MYSQL_DATABASE: flyway_db
      MYSQL_USER: user
      MYSQL_PASSWORD: password
    ports:
      - "3309:3306"
   ```
   
2. Levantar el contenedor con:
   ```bash
   docker-compose up -d
   ```

## Configuración de Flyway con Spring Boot

Agregar la dependencia de Flyway en el `pom.xml`:

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
    <version>9.0.0</version>
</dependency>
```

Configurar `application.yml` de Spring Boot:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3309/flyway_db
    username: user
    password: password
 
```

## Estructura del proyecto
El directorio de migraciones debe estar ubicado en:

```
src
└── main
    └── resources
        └── db
            └── migration
```

Cada archivo de migración debe seguir la convención `V1__descripcion.sql`, por ejemplo:
```
V1__create_table_users.sql
V2__add_email_column.sql
```

## Ejecución de Migraciones
1. Compilar el proyecto: `mvn clean package`
2. Levantar el contenedor MySQL con Docker (`docker-compose up -d`).
3. Ejecutar la aplicación Spring Boot: `mvn spring-boot:run`

## Verificación de Migraciones
Para verificar que las migraciones se aplicaron correctamente:
```sql
SELECT * FROM flyway_schema_history;
```

## Rollback (Opcional)
Flyway no soporta rollback automático de forma nativa, pero se puede realizar de forma manual siguiendo estos pasos:

1. Crear un script de reversión manualmente:
Por cada migración, genera un archivo de reversión, por ejemplo:

`V1__create_table_users.sql`

`U1__drop_table_users.sql`

2. Ejecutar la reversión manualmente:
Aplica el script de reversión ejecutándolo directamente sobre la base de datos.

3. Borrar la entrada del historial:
Flyway almacena el historial de migraciones en la tabla `flyway_schema_history`. Si reviertes una migración, elimina la entrada manualmente:
```sql
DELETE FROM flyway_schema_history WHERE version = '1';
```

4. Migraciones de compensación:
Alternativamente, crea un nuevo archivo de migración para revertir los cambios realizados en un archivo previo.

## Buenas Prácticas
- Seguir la numeración secuencial en los archivos de migración.
- Probar las migraciones en un entorno de desarrollo antes de producción.
- Mantener los scripts de migración versionados.
