# Запуск сервиса:
Для того чтобы запустить сервис нужно иметь на своем компьютере СУБД MySQL, далее создать в ней базу данных labtest. 
Далее нужно клонировать этот git репозиторий, после чего интегрировать его в среду разработки или собрать, используя для этого Maven,
благодаря  Flyway, SQL-скрипт мигрирует все таблицы и базовые данные для работы с программой. После чего можно приступать к тестироватнию данного сервиса.

# Оффтоп:
При работающем приложенни можно перейти по этой ссылке: http://localhost:8080/swagger-ui/index.html#/, чтобы посмореть описание API, для этого был использован Swagger.
Версия java-17.