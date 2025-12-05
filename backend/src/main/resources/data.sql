INSERT INTO authors (name, email, bio) VALUES
('Spring Team', 'spring@example.com', 'Core Spring Framework maintainers'),
('Josh Long', 'josh@example.com', 'Spring advocate and developer advocate at VMware'),
('Minovich Dev', 'natalia@example.com', 'Java backend developer and Spring enthusiast');

-- 🗂 Категории
INSERT INTO categories (name, description) VALUES
('Spring Core', 'Dependency Injection, Beans, Context, Lifecycle'),
('Spring Boot', 'Auto-configuration, starters, and rapid development'),
('Spring Security', 'Authentication, Authorization, OAuth2'),
('Spring Data JPA', 'Repositories, Entities, Transactions, Queries'),
('Spring Cloud', 'Microservices, Config Server, Eureka, Feign, Gateway');

-- 🏷 Теги
INSERT INTO tags (name) VALUES
('Java'),
('Dependency Injection'),
('Microservices'),
('Security'),
('Performance'),
('Testing'),
('Architecture'),
('Best Practices');

-- 📦 Версии
INSERT INTO versions (number, release_date) VALUES
('Spring Boot 3.3.0', '2024-07-01'),
('Spring Boot 3.2.0', '2023-11-01'),
('Spring Framework 6.1.0', '2024-02-01');