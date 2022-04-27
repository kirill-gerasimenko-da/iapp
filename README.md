*Description*

Helper for entering password for the client-banking like Ing Bank.

![Screenshot](/screenshot.png)

*Build uberjar*

```shell
deps -M -e "(compile 'iapp.core) (javafx.application.Platform/exit)"
deps -M:uberjar-uberdeps --main-class iapp.core
```
