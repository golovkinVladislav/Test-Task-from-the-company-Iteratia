# Test-Task-from-the-company-Iteratia
![N|Solid](https://thumb.tildacdn.com/tild6164-3739-4163-b130-343965316166/-/resize/320x/-/format/webp/photo.png)



Тестовое задание Java (конвертер валют)

Описание
При запуске приложения необходимо получить список актуальных валют и их курсов с сайта ЦБРФ http://www.cbr.ru/scripts/XML_daily.asp (дополнительная информация https://cbr.ru/development/sxml/) и записать их в базу данных (идентификаторы, коды, названия), а также курсы (привязанные к валюте) на дату запроса.

Пользователь попадает на главный экран, где может выбрать из какой валюты и в какую будет конвертация, указывает количество переводимых средств и нажать кнопку "Конвертировать". После чего происходит запрос в БД на получение актуального курса на текущую дату, если данные на текущую дату отсутствуют, необходимо произвести получение курсов с сайта ЦБ и добавить новые записи в базу данных.

Также, в конвертере должна вестись история произведенных конвертаций с записью в базу данных с указанием курса, по которому была произведена конвертация.

Для пар валют, с которыми проводилась конвертация, необходимо подсчитать статистику - средний курс конвертации и суммарный объём конвертаций по каждой паре за неделю.

Статистику и историю конвертаций можно посмотреть на той же странице конвертера или отдельной вкладке (возможна реализация базовых фильтров).


## Требования к результату

- Код приложения необходимо снабдить комментариями.
- Приложение должно собираться при помощи maven без установки или настройки каких-либо дополнительных компонент;
- Должен быть заполнен текстовый файл readme.md с инструкцией по сборке, настройке, конфигурированию и развертыванию приложения (если необходимо);
- Результаты должны быть загружены на GitHub/GitLab (желательно вести последовательную разработку: один коммит - одна фича).

## Используемые технологии
Spring Data, 
Spring Boot, 
Flyway,
Lombok <br>
Building a project: Maven <br>
Database: PostgreSQL

## Настройки
port: 8888
```sh
postgres:
username admin
password postgres
schema iteratia
``` 

## Сборка
- Установить Apache Maven в Windows, скачать zip-файл Maven, разархивировать его в папку и настроить переменные среды Windows


```sh
git clone https://github.com/golovkinVladislav/Test-Task-from-the-company-Iteratia.git
cd Test-Task-from-the-company-Iteratia
mvn spring-boot:run
```

```sh
http://localhost:8888/convector
``` 















 
  
