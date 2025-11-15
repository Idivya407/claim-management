# Claim Management System (Spring Boot Monolithic)

This is a minimal starter skeleton for a Claim Management System built with Spring Boot:
- Spring Security + JWT for authentication
- JPA/Hibernate with H2 in-memory database
- Basic modules: User, Policy, Claim, Approval, Notification
- Example controllers: /auth, /claims, /admin

How to run:
1. Update `app.jwt.secret` in `src/main/resources/application.properties` to a strong secret (at least 32 bytes).
2. Build: `mvn clean package`
3. Run: `mvn spring-boot:run` or `java -jar target/claim-management-0.0.1-SNAPSHOT.jar`
4. H2 Console: http://localhost:8080/h2-console (jdbc:h2:mem:claimdb)

Notes:
- This project is a skeleton to get you started. Add DTOs, validations, logging, unit/integration tests, and external notification/email providers as needed.
- For production, switch to a persistent DB, secure secrets, and harden security configuration.
