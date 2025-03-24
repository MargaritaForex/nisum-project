# API de Usuarios

## 🚀 Instalación
1. Clona el repositorio: `git clone https://github.com/MargaritaForex/nisum-project.git`
2. Entra al proyecto: `cd repo`
3. Ejecuta: `./gradlew bootRun`

## 📝 Endpoints
### Registro de usuario
**POST /api/users**
```json
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}