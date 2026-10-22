module org.skaiciuokle.paskolu_skaiciuokle {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.skaiciuokle.paskolu_skaiciuokle to javafx.fxml;
    exports org.skaiciuokle.paskolu_skaiciuokle;
}