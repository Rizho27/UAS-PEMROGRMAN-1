package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static Connection koneksi;

    public static Connection getKoneksi() {

        if (koneksi == null) {

            try {

                Class.forName("com.mysql.jdbc.Driver");

                String url = "jdbc:mysql://localhost:3306/hotel_reservasi";
                String user = "root";
                String password = "";

                koneksi = DriverManager.getConnection(url, user, password);

                System.out.println("Koneksi Berhasil");

            } catch (ClassNotFoundException | SQLException e) {

                System.out.println("Koneksi Gagal");
                System.out.println(e.getMessage());

            }

        }

        return koneksi;
    }

}