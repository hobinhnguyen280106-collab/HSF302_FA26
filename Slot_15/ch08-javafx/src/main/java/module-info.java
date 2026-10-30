module com.fu.ch08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;      // Cho phần T5
    requires javafx.media;
    requires java.desktop;    // Cho phần T5

    // Dành cho các file ở thư mục gốc (HelloApplication, HelloController)
    opens com.fu.ch08.b1 to javafx.fxml;
    exports com.fu.ch08.b1;

    exports com.fu.ch08.b2;

    exports com.fu.ch08.b3;

    opens com.fu.ch08.b4 to javafx.fxml;
    exports com.fu.ch08.b4;

    // Dành cho thư mục common chứa AlertUtil
    exports com.fu.ch08.common;

    // FXMLLoader cần reflection vào controller (@FXML private field/method)
//    opens com.fu.ch08.ch08javafx.b4 to javafx.fxml;
//    opens com.fu.ch08.ch08javafx.b5 to javafx.fxml;
//    opens com.fu.ch08.ch08javafx.b6 to javafx.fxml;
//    opens com.fu.ch08.ch08javafx.t1 to javafx.fxml, javafx.base;   // javafx.base: PropertyValueFactory (TableView)
//    opens com.fu.ch08.ch08javafx.t4 to javafx.fxml, javafx.base;

    // Application class phải được export để javafx.graphics khởi tạo được
    // b1–b3 viết UI bằng code (không FXML) → chỉ cần exports, không cần opens
//    exports com.fu.ch08.ch08javafx.b1;
//    exports com.fu.ch08.ch08javafx.b2;
//    exports com.fu.ch08.ch08javafx.b3;
//    exports com.fu.ch08.ch08javafx.b4;
//    exports com.fu.ch08.ch08javafx.b5;
//    exports com.fu.ch08.ch08javafx.b6;
//    exports com.fu.ch08.ch08javafx.t1;
//    exports com.fu.ch08.ch08javafx.t2;
//    exports com.fu.ch08.ch08javafx.t3;
//    exports com.fu.ch08.ch08javafx.t4;
//    exports com.fu.ch08.ch08javafx.t5;
//    exports com.fu.ch08.ch08javafx.t6;
}