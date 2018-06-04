/**
 * 
 */
package com.yh.monitor.util;

import com.yh.monitor.domain.JdbcVo;

import java.sql.*;
import java.util.*;


public class JdbcConnect {

	PreparedStatement ps = null;
	Connection ct = null;
	ResultSet rs = null;
    private String jdbc_url;
	public JdbcConnect(){};
	public JdbcConnect(JdbcVo jdbcVo) {
		jdbc_url=jdbcVo.getUrl();
		try {
			Class.forName(jdbcVo.getDriverClassName());
			ct = DriverManager.getConnection(jdbcVo.getUrl(), jdbcVo.getUsername(), jdbcVo.getPassword());
		} catch (Exception e) {
			System.out.println("链接数据库异常:"+e+";jdbc.url"+jdbc_url);
		}
	}

	private List<Map> queryForList(String sql, String[] paras) {
		List<Map> resultList = new ArrayList<Map>();
		try {
			ps = ct.prepareStatement(sql);
			if(paras != null && paras.length>0){
				for (int i = 0; i < paras.length; i++) {
					ps.setString(i + 1, paras[i]);
				}
			}
			rs = ps.executeQuery();

			//根据列名，将查询结果组装成list<map>返回
			List<String> columName = new ArrayList<String>();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int index=1;index<=rsmd.getColumnCount();index++){
				columName.add(rsmd.getColumnName(index));
			}
			//组装结果集
			Map restMap ;
			//Iterator itname = columName.iterator();
			//String colname="";
			while (rs.next()){
				restMap = new HashMap();
				for (String colname : columName){
					//colname = itname.next().toString();
					restMap.put(colname,rs.getString(colname));
				}
				resultList.add(restMap);
			}
		} catch (Exception e) {
			System.out.println("链接数据库异常:"+e+";jdbc.url"+jdbc_url);
		}
		return resultList;
	}

	private void close() {
		try {
			if (this.rs != null) {
				this.rs.close();
			}
			if (this.ps != null) {
				this.ps.close();
			}
			if (this.ct != null) {
				this.ct.close();
			}
		} catch (SQLException e) {

			System.out.println("链接数据库异常:"+e+";jdbc.url"+jdbc_url);
		}
	}
   public List<Map> getResultData(JdbcVo jdbcVo){
	   //JdbcConnect jdbcConnect = new JdbcConnect(jdbcVo);
	   String sql= "SELECT a.tablespace_name,\n" +
			   "       trunc(total / (1024 * 1024 * 1024),2.1)            AS total,\n" +
			   "       trunc(free / (1024 * 1024 * 1024),2.1)              AS free,\n" +
			   "       trunc((total - free) / (1024 * 1024 * 1024),2.1)    AS spare,\n" +
			   "       ROUND ((total - free) / total, 4) * 100||'%' AS rate\n" +
			   "  FROM (  SELECT tablespace_name, SUM (bytes) free\n" +
			   "            FROM dba_free_space\n" +
			   "        GROUP BY tablespace_name) a,\n" +
			   "       (  SELECT tablespace_name, SUM (bytes) total\n" +
			   "            FROM dba_data_files\n" +
			   "        GROUP BY tablespace_name) b\n" +
			   " WHERE a.tablespace_name = b.tablespace_name";
	   List<Map> list=this.queryForList(sql,null);
	   this.close();
	   return list;
   }

/*   public static void main(String[] args){

   }*/
}
