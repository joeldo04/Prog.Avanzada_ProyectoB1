package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexiones {
    
    public Connection con;
    public Connection getConnection () throws ClassNotFoundException, 
            SQLException  {
        String driver =  "com.mysql.cj.jdbc.Driver"; 
        String url = "jdbc:mysql://localhost:3306/gestionpaquetes";
        Class.forName(driver);
        return DriverManager.getConnection(url,"root","joel115054");
    }
    public Connection AbrirConexion() throws ClassNotFoundException, SQLException {
        con = getConnection();
        return con;
    }
    public void CerrarConexion() throws SQLException{
       con.close();
    }
}

