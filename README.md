
Стек: Java 17, JPA/Hibernate, Docker, Docker Compose, Spring/Spring Boot 2, pattern Command, TelegramBots, Javadoc, IntelliJ IDEA,
LogBack, Lombok, Git/GitHub

Для работы приложения нужен [Docker](https://www.docker.comhttps://www.docker.com):

1. В рабочей IDE задайте переменную окружения TELEGRAM_BOT_API_KEY и присвойте ей значение вашего API key
2. Запустите Docker Desktop (или Docker Engine)
3. Запустите в терминале команду:  
```bash
    docker compose up -d
```
4. Продолжите работу в чате с ботом

Если хотите запустить из IDEA:
1. В рабочей IDE задайте переменную окружения TELEGRAM_BOT_API_KEY и присвойте ей значение вашего API key
2. Поменяйте хост postgres на localhost в application.properties в настройках базы
2. проверьте маппинг портов в docker compose (ports: - "5432:5432")
3. Откройте Docker (или Docker Engine) и запустите команду:
```bash
    docker compose up postgres -d
``` 
4. Запустите приложение через IDEA и продолжите в чате с ботом

