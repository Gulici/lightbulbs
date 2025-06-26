# Instrukcja uruchomienia aplikacji Lightbulbs Backend na Windows

## 1. Uruchomienie aplikacji ręcznie

1. Skopiuj plik `lightbulbs-backend-0.0.1-SNAPSHOT.jar` oraz plik `start-app.bat` do jednego folderu na komputerze, np. `C:\lightbulbs\`.

2. Dwukrotnie kliknij plik `start-app.bat`.  
   - Spowoduje to uruchomienie aplikacji w nowym oknie konsoli.  
   - Logi aplikacji będą zapisywane w pliku `app.log` w tym samym folderze.

## 2. Dodanie aplikacji do autostartu (uruchamianie przy starcie systemu)

1. Naciśnij `Win + R`, wpisz `shell:startup` i naciśnij Enter.  
   - Otworzy się folder Autostartu użytkownika.

2. Skopiuj skrót do pliku `start-app.bat` do tego folderu.  
   - Możesz utworzyć skrót klikając prawym przyciskiem na `start-app.bat` → **Wyślij do** → **Pulpit (utwórz skrót)**, a następnie przenieść ten skrót do folderu Autostartu.

3. Przy następnym uruchomieniu systemu skrypt uruchomi automatycznie aplikację.

## 3. Dostęp do interfejsu frontend przez przeglądarkę

1. Ustal adres IP komputera, na którym działa aplikacja:  
   - Otwórz wiersz polecenia (`cmd`) i wpisz:  
     ```
     ipconfig
     ```  
   - Znajdź adres IPv4 w sekcji połączenia sieciowego (np. `192.168.1.100`).

2. W przeglądarce internetowej wpisz adres: `http://<adres_ip>` 
3. Powinien pojawić się frontend aplikacji.