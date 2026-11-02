# DataMed IPS - API RESTful de Gestión Clínica

DataMed IPS es el backend centralizado para un sistema de gestión de centros médicos o IPS (Institución Prestadora de Servicios de Salud). Permite administrar el ciclo de vida del paciente, la verificación de convenios EPS, la autenticación basada en JWT, el caching con Redis Cloud y el mantenimiento de registros con política de borrado lógico.

---

## Arquitectura Hexagonal (Ports and Adapters)

El proyecto está diseñado bajo los principios de Arquitectura Hexagonal, desacoplando el núcleo de dominio de los frameworks externos y bases de datos:

```text
src/main/java/com/clinica/medica/
├── domain/                         # Núcleo de Dominio (Puro, sin dependencias de frameworks)
│   ├── models/                     # Entidades del dominio (Usuario, Eps, Paciente, Cita, etc.)
│   └── exceptions/                 # Excepciones de reglas de negocio
├── application/                    # Capa de Aplicación
│   ├── ports/                      # Puertos de Entrada y Salida
│   │   ├── input/                  # Interfaces UseCase (Casos de uso)
│   │   └── output/                 # Interfaces Repository (Puertos de persistencia)
│   └── services/                   # Implementaciones de servicios y lógica de negocio
└── infrastructure/                 # Capa de Infraestructura (Adaptadores externos)
    ├── adapters/
    │   ├── input/rest/             # Controladores REST HTTP (Endpoints, DTOs, Mappers)
    │   └── output/persistence/     # Adaptadores de JPA / PostgreSQL (Entidades JPA, Mappers)
    ├── security/                   # Spring Security, Filtros JWT, UserDetailsService
    ├── config/                     # Configuración de OpenAPI (Swagger) y Redis Cache
    └── exceptions/                 # Controlador global de excepciones (GlobalExceptionHandler)
```

---

## Características Principales

1. Autenticación Stateless con JWT: Seguridad basada en Spring Security y tokens JWT signing/validation.
2. Persistencia Remota en PostgreSQL (Supabase): Conexión IPv4 mediante Supabase Session Pooler (puerto 6543) con SSL obligatorio.
3. Caché de Alto Rendimiento (Redis Cloud): Caching de catálogos frecuentes (GET /api/v1/eps) reduciendo tiempos de latencia y carga en la base de datos.
4. Borrado Lógico (Soft Delete): Mantenimiento de integridad de datos garantizando que ninguna entidad sea eliminada físicamente (activo = false).
5. Documentación OpenAPI 3 / Swagger UI: Interfaz interactiva para probar endpoints protegidos con autenticación Bearer JWT.
6. Manejo Global de Excepciones: Respuestas estandarizadas con códigos de estado HTTP semánticos (400, 401, 403, 404, 409).

---

## Tecnologías Utilizadas

- Lenguaje: Java 17
- Framework Principal: Spring Boot 3
- Seguridad: Spring Security, JWT (jjwt)
- Persistencia: Spring Data JPA, Hibernate, Driver PostgreSQL
- Base de Datos Remota: PostgreSQL en Supabase (Session Pooler)
- Caché Remota: Redis Cloud (spring-boot-starter-data-redis)
- Documentación: SpringDoc OpenAPI 3 (swagger-ui)
- Utilidades: Lombok

---

## Guía de Ejecución Local

### Prerrequisitos
- Java 17 o superior instalado.
- Git instalado.
- Terminal PowerShell o Bash.

### 1. Clonar el repositorio
Clona el repositorio desde GitHub e ingresa al directorio del proyecto:

```bash
git clone https://github.com/Diego-8520/DataMed-ips.git
cd DataMed-ips
```

### 2. Configurar Variables de Entorno
El proyecto utiliza externalización de configuraciones mediante variables de entorno para evitar almacenar credenciales sensibles en el repositorio.

Puedes iniciar la aplicación usando el script rápido de PowerShell `run.ps1`:

```powershell
.\run.ps1
```

O ejecutando:

```powershell
.\mvnw.cmd spring-boot:run
```

---

## Documentación y Pruebas con Swagger UI

Una vez iniciado el servidor, accede a la interfaz gráfica interactiva en tu navegador abriendo la URL de Swagger UI en localhost (`http://localhost:8080/swagger-ui.html`).

### Flujo de Pruebas Recomendado

1. Registro de Usuario (`POST /api/v1/auth/register`):
   Provee correo y contraseña para crear la cuenta inicial.
2. Promoción a ADMIN: En el panel web de Supabase, en la tabla usuario, modifica la columna rol a ADMIN.
3. Obtener JWT Token (`POST /api/v1/auth/login`):
   Inicia sesión y copia la cadena token devuelta en la respuesta.
4. Autorizar Swagger UI:
   Haz clic en el botón verde Authorize, pega el token JWT y confirma.
5. Probar Endpoints Protegidos:
   - Crear EPS (`POST /api/v1/eps`): Registra una nueva Entidad Promotora de Salud (Requiere rol ADMIN).
   - Consultar EPS (`GET /api/v1/eps`): Consulta el catálogo (Servido mediante Redis Cache).
   - Borrado Lógico (`DELETE /api/v1/eps/{id}`): Marca la EPS como inactiva (activo = false).

---

## Equipo de Desarrollo

- Diego (@Diego-8520)
- Angie Muñoz (@angiemunoz960)
