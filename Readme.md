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
  
  
  
  # Diagramy przypadków użycia:
  - Logowanie do systemu: https://www.lucidchart.com/invitations/accept/2c575178-9d05-4663-afe4-24800d9f6646
  - Dodawanie nowych dań i edycja istniejących: https://www.lucidchart.com/invitations/accept/10ab636b-438d-4c81-9159-e2e66f15012b
  - Dodawanie i edycja pracowników: https://www.lucidchart.com/invitations/accept/b3fb0938-defd-495e-977b-6cd70496989b
