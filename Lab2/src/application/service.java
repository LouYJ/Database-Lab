/**
 * 
 */
package application;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * @author Loj
 *
 */
public class service {
	private static Connection getConn() {//获得数据库，写成一个函数，省的每次调用数据库都重复写一遍，只需要调用这个函数就可以了
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/SCT?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "root";
        String password = "louyujing127";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
	
	public ArrayList<String> search(String stat, int num) {
    	ArrayList<String> all = new ArrayList<String>();
    	
    	Connection conn = getConn();
        String sql = stat;
        PreparedStatement pstmt;
        
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
            	String tmp = "";
            	tmp += rs.getString(1);
            	for (int i = 2; i <= num; i++) {
            		tmp = tmp + " " + rs.getString(i);
            	}
       
            	all.add(tmp);
            }
            pstmt.close();//记得用完数据库要把数据库关掉
            conn.close();
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
