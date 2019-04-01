# Podstawy bazy danych
 - Xampp: https://www.apachefriends.org/pl/index.html
 - Po zainstalowaniu należy uruchomić w xamppie 2 serwery: Apache i MySQL
 - Potem kliknąć w opcję "Admin" przy MySQL i w przeglądarce uruchomi się phpmyadmin
 - Potem uruchomić program. W phpmyadmin po odświeżeniu pojawi się baza danych i tabele
 - Aplikacja ma zaimplementowane metody do tworzenia bazy danych i tabel jeśli nie istnieją
 # Dodawanie nowych tabel:
  - w klasie ConnectionClass istnieje metoda createTables
  - należy do niej (albo gdziekolwiek potrzeba w kodzie) dodać odpowiednie zapytanie:
    - String sql = "CREATE TABLE IF NOT EXISTS tablename( atributes );";
    - statement.execute( sql );
 # Dodawanie danych do tabeli:
  - przykład w LoginController -> signInButtonAction()
  - począwszy do: ConnectionClass connectionClass = new ConnectionClass();
 # Wyciąganie danych z tabeli:
  - przykład w RegisterController -> signUpAction()
  - począwszy do: ConnectionClass connectionClass = new ConnectionClass();
  
  
  
  # Diagramy UML
  - strona do otwierania/edycji plików UML: https://www.draw.io/
  - pliki UML znajdują się w lokalizacji Restaurant-Manager\RestaurantApp\uml
