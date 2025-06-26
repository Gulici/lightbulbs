# Instrukcja uruchomienia aplikacji Lightbulbs Backend na Windows

## 1. Uruchomienie aplikacji ręcznie

1. Skopiuj plik zawartość katalogu `\dist` do jednego folderu na komputerze, np. `C:\lightbulbs\`.

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

## 4. Edycja konfiguracji presetów scen i adresów żarówek

Konfiguracja odbywa się poprzez plik `config.json` znajdujący się w tym samym katalogu co aplikacja.

### 1. Edycja scen

- W sekcji `"scenes"` znajdują się nazwy scen (`work`, `relax`, `play`, `gradient` itd.).
- Każda scena zawiera mapowanie numerów żarówek na ich ustawienia świetlne.
- Ustawienia mogą zawierać:
  - `"temp"` – temperatura barwowa światła w Kelwinach (np. 4000),
  - `"dimming"` – jasność w procentach (np. 60),
  - lub kolor RGB (`"r"`, `"g"`, `"b"`) w zakresie 0-255.
- Przykład wpisu sceny:

```json
"work": {
  "2": { "temp": 4000, "dimming": 60 },
  "3": { "temp": 6500, "dimming": 100 }
}
```
- Możesz dodać nowe sceny lub zmieniać istniejące, dopasowując ustawienia do potrzeb.

### 2. Edycja adresów IP żarówek 

- W sekcji `bulbs` znajduje się mapowanie numerów żarówek na ich adresy IP w sieci lokalnej.

```json
"bulbs": {
  "1": "192.168.100.100",
  "2": "192.168.100.101"
}
```

- Zmodyfikuj adresy IP tak, aby odpowiadały faktycznym adresom Twoich żarówek.