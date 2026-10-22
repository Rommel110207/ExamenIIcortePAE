module ni.edu.uam.examenpae {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.examenpae to javafx.fxml;
    exports ni.edu.uam.examenpae;
}