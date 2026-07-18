# Régi módszer

Ezzel, csaka  konzolja írja ki az api válaszokat.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN"> <!-- Log4g-nek a belső működésére vonatkozik.  -->

    <Appenders>
    <!-- A konzolja írja ki a szöveget. -->
        <Console name="Console" target="SYSTEM_OUT">
        <!-- Megjelenítési forma.  -->
            <PatternLayout
                pattern="%d{HH:mm:ss} %-5level %logger - %msg%n"/>
        </Console>
    </Appenders>

    <Loggers>
        <Root level="INFO"> <!-- Infó szintől felfelé kerül megjelenítésre a loggolás. -->
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>

</Configuration>
```