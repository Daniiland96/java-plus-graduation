# JAVA-PLUS-GRADUATION

## Отдельные сервисы

--- 

- category-service
- event-service
- request-service
- user-service
- stats-server

## Взаимодействие между сервисами
### user-service
ни от кого не зависит
### event-service
- user-service
- request-service
- category-service
- stats-server
### request-service
- user-service
- event-service
- category-service
### category-service
- event-service
### stats-server
ни от кого не зависит
## Спецификация внешней АПИ
[main-api](/ewm-main-service-spec.json)

## Спецификация внутренней АПИ
[stats-server](/ewm-stats-service-spec.json)

## Конфигурация
- Все конфигурирование выполняется через  spring-cloud-config в модуле infra

- Общие DTO вынесены в модуль interaction-api, который подключается как зависимость