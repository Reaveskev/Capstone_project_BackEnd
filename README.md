# Capstone_project_BackEnd
BackEnd Java Code for our Capstone project.

# Install Java (JDK 21-LTS)
https://adoptium.net/temurin/releases/?utm_source=chatgpt.com
- during installation, make sure to set "JAVA_HOME" & "Add to PATH"
- open cmd and verify installation with "java -version"
- confirm compiler as well: "javac -version"

# Install Eclipse
https://www.eclipse.org/downloads/?utm_source=chatgpt.com
- during installation, select "Eclipse IDE for Enterprise Java and Web Development"

# Spring Boot project initialized with:
https://start.spring.io/?utm_source=chatgpt.com
- Project: Maven
- Language: Java
- Spring Boot: 4.1.0
- Group: com.capstone
- Artifact: backend
- Package name: com.capstone.backend
- Packaging: Jar
- Configuration: Properties
- Jave: 21
- Dependencies: 
- - Spring Web
- - Spring Boot DevTools

# Load the Spring Boot project with Eclipe
- File --> Import
- Maven --> Existing Maven Project

# Troubleshooting Port 8080 already in use
- C:\Users\lenovo>netstat -ano | findstr :8080
- C:\Users\lenovo>tasklist /FI "PID eq 12096"
- C:\Users\lenovo>taskkill /PID 12096 /F
