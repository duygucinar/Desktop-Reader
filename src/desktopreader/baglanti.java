package desktopreader;

import java.sql.*;
import java.sql.Connection;

public class baglanti {
    private static final String USERNAME="root";
    private static final String PASSWORD="2810";
    private static final String CONN_STRİNG= "jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    Connection conn=null;
    Statement myStat = null;
    ResultSet myRs = null;
    
    public baglanti(){
        try {
            conn=DriverManager.getConnection(CONN_STRİNG,USERNAME,PASSWORD);
            myStat = (Statement) conn.createStatement();
            
        } catch (SQLException e) {
           System.err.println(e);
        }
    }
}
