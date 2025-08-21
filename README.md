# JAVA-PLUS-GRADUATION
&nbsp; Сервис, предоставляющий информацию о событиях и развлечениях. Пользователи могут создавать события, записываться для участия и получать рекомендации в зависимости от своих предпочтений.

## 🛠️ Стек:
### Языки и фреймворки:
&nbsp; Java | Spring Boot

&nbsp; Spring Cloud: Config Server | Eureka | Gateway | OpenFeign

&nbsp; PostgreSQL | Hibernate | MapStruct

### Инструменты:
&nbsp; Kafka | Maven | gRPC | Docker

&nbsp; IntelliJ IDEA | Postman | DBeaver

## ✏️ Описание:
&nbsp; В проекте реализована многомодульная архитектура. Общение сервисов происходит через OpenFeign client. Для централизованного управления конфигурацией, автоматического обнаружения сервисов и удобной маршрутизации запросов, используются инструменты Spring Cloud.

### Core
&nbsp; Сервисы данного блока отвечают за основную бизнес-логику. Здесь происходит работа с пользователями, событиями, подборками и запросами на участие. Общие DTO вынесены в отдельный модуль.

### Infra
&nbsp; Сюда вынесены все инструменты Spring Cloud.

### Stats
&nbsp; В данном блоке реализована работа со статистикой и рекомендациями. Статистика строится исходя из действий пользователя, а именно: просмотров, лайков и запросов на участие. Действия попадают в сервис collector через gRPC, а дальше через Kafka передаются в aggregator, который собирает данные и передаёт их в analyzer. Тот, в свою очередь, возвращает рекомендации в core через gRPC.

### Взаимодействие между сервисами
#### user-service
&nbsp; ни от кого не зависит
#### event-service
- user-service
- request-service
- category-service
- stats-server
#### request-service
- user-service
- event-service
- category-service
#### category-service
- event-service
#### stats-server
&nbsp; ни от кого не зависит

---
- Спецификация внешней API
[main-api](/ewm-main-service-spec.json)
- Спецификация внутренней API
[stats-server](/ewm-stats-service-spec.json)
