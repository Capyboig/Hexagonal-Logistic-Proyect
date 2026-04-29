# 📦 Sistema Logístico Hexagonal - Core API

API REST empresarial para la gestión integral de paquetes, generación de etiquetas y trazabilidad, construida bajo los principios de la Arquitectura Hexagonal y Domain-Driven Design (DDD).

## 🚀 Características (v1.5)
* **Arquitectura Limpia:** Separación estricta de Dominio, Aplicación e Infraestructura.
* **Seguridad Robusta:** Autenticación y Autorización basada en JWT.
* **Ingesta de Datos:** Soporte para lectura masiva de archivos.
* **Generación Física:** Creación automatizada de etiquetas PDF con Códigos QR dinámicos.
* **Trazabilidad:** Uso de identificadores UUID para evitar colisiones y rastreo seguro.

## 🛠️ Stack Tecnológico
* Java 21 / Spring Boot
* PostgreSQL (Dockerizado)
* Spring Security + JWT
* Apache PDFBox / ZXing (Códigos QR)
* Swagger / OpenAPI

## 📚 Documentación de la API
La documentación interactiva de los endpoints está disponible mediante Swagger UI.
Con la aplicación levantada, accede a: `http://localhost:8080/swagger-ui.html`

## ⚙️ Estructura del Proyecto
El proyecto se divide en:
* `domain/`: Modelos de negocio, Enums y Puertos (Interfaces). Libre de frameworks.
* `application/`: Casos de uso e interfaces de servicio.
* `infrastructure/`: Adaptadores web (Controladores), persistencia (JPA), seguridad (JWT) y herramientas externas (PDF, Excel).