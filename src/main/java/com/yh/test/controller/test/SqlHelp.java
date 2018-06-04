/**
 * 
 */
package main.java.com.yh.test.controller.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author tianhao
 */
public class SqlHelp {

	PreparedStatement ps = null;
	Connection ct = null;
	ResultSet rs = null;

	private static final String user = "HBYL_GGYW";
	private static final String pass = "HBYL_GGYW";
	private static final String dirver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";

	public SqlHelp() {

		try {

			Class.forName(dirver);

			ct = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public ResultSet query(String sql, String[] paras) {

		try {
			ps = ct.prepareStatement(sql);

			if(paras != null && paras.length>0) {
				for (int i = 0; i < paras.length; i++) {
					ps.setString(i + 1, paras[i]);
				}
			}

			rs = ps.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;

	}


	public boolean update(String sql, String[] paras) {
		boolean b = true;
		try {
			ps = ct.prepareStatement(sql);


			for (int i = 0; i < paras.length; i++) {
				ps.setString(i + 1, paras[i]);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			b = false;
			e.printStackTrace();
		} finally {

			this.close();
		}
		return b;
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}
			if (ct != null) {
				ct.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		SqlHelp sqlHelp =new SqlHelp();
		ResultSet rs =null;
		try{
		    rs = sqlHelp.query("select * from ac01",null);
		   while (rs.next()){
			   rs.getString(0);
		   }
	   }catch (Exception e){
       	e.printStackTrace();
	   }finally {
	   	try{
			rs.close();
		}catch (Exception e){
	   		e.printStackTrace();
		}

		}


	}

}
