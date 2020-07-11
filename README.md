# TravelBot

### Web приложение по управлению собственным туристическим телеграм ботом.
  
1) Телеграм бот выдает пользователю справочную информацию о введенном городе. Например, пользователь вводит: «Москва», чат-бот отвечает: «Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить».  
2) Данные о городах должны храниться в базе данных.  
3) Управлять данными о городах (добавлять новые города и информацию о них, изменять и удалять любую информацию) необходимо через REST WebService.
    
Используемые технологии: 
- SpringBoot
- SpringData
- Hibernate
- Java 1.8
- Maven

 How to start
---
- Create database: 
    
        CREATE DATABASE bot;
- Information about telegram bot:  
    username=TravelInfoBot  
    token=1309842660:AAHOkB09skvTYN3IYOVBEzlXO4vSu-S-T2c
- Run *run.bat*

 How to use
---
### Web
*	***GET:*** */cities - Gets all cities information;
*	***POST:*** */cities - Save city information:

    Body in JSON-format: 
    {
        "name": "Minsk",
        "info": "Beautiful place"
    }

*	***PUT:*** */cities/{id} - Edit city information by id:

	Body in JSON-format:
    {
        "info": "Beautiful place to walk with family"
    }

*	***DELETE:*** */cities/{id} - Delete city information by id.
