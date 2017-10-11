package BookQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlCon {
	Connection con = null;
	Statement stat = null;
	ResultSet rs = null;

	public SqlCon() {
		try {
			Class.forName("com.mysql.jdbc.Driver");// 连接驱动
			// con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2f" +
			// "?useUnicode=true"
			// + "&characterEncoding=utf-8" + "&useSSL=false", "TestUser", "123456");//
			// 连接数据库
			con = DriverManager.getConnection(
					"jdbc:mysql://w.rdc.sae.sina.com.cn:3306/app_xhybookmanger1206" + "?useUnicode=true"
							+ "&characterEncoding=utf-8" + "&useSSL=false",
					"x0w3nwxzmn", "m35l3h11w4yh2l52zkxyiiwyyw2kj22l33yjx0zl");
			stat = con.createStatement();
		} catch (Exception e) {
			con = null;
		}

	}

	public ResultSet executeQuery(String sql) {
		try {
			rs = stat.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			rs = null;
		}
		return rs;
	}

	public int executeUpdate(String sql) {
		try {
			stat.executeUpdate(sql);
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int closeCon() {
		try {
			con.close();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

}