README for fimeped
==========================

# Install Dependencies
```bash
bower install
```
# BDD SQL Server
## With Docker Container

```bash
sudo docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=Administrador2k20' -p 1433:1433 -d mcr.microsoft.com/mssql/server:2017-latest
```
# Start Server
```bash
mvn spring-boot:run
```

# Iniciar Sesion
Local: 		http://127.0.0.1:8080
user:admin
password:admin