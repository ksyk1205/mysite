package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kr.co.itcen.mysite.vo.UserVo;

public class UserDao {
	
	private Connection getConnection() throws SQLException {  
		Connection connection = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.1.73:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}

		return connection;
	}
	//join을 하기위한 insert
	public Boolean insert(UserVo vo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "insert into user values(null, ?, ?, ?, ?,now())";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());


			int count = pstmt.executeUpdate();
			result = (count == 1);

			stmt = connection.createStatement();
			rs = stmt.executeQuery("select last_insert_id()");
			if(rs.next()) {
				Long no = rs.getLong(1);
				vo.setNo(no);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}

				if(pstmt != null) {
					pstmt.close();
				}

				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;		
	}
	//회원정보 수정을 위한 get
	public UserVo get(Long no) {
		UserVo result = null;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select name,email,gender from user where no = ?  ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);


				result = new UserVo();
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}

				if(pstmt != null) {
					pstmt.close();
				}

				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;		
	}

	//로그인 하기위한 get
	public UserVo get(String email, String password) {
		UserVo result = null;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select no, name from user where email=? and password=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);

				result = new UserVo();
				result.setNo(no);
				result.setName(name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}

				if(pstmt != null) {
					pstmt.close();
				}

				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;		
	}
	//password가 빈칸일 때 원래의 값을 update
	public boolean update(Long no,String name,String gender) {
		boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		

		try {
			connection = getConnection();

			String sql = "update user set name =?, gender=? where no = ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, name);

			pstmt.setString(2, gender);

			pstmt.setLong(3, no);
			int count=pstmt.executeUpdate();
			
			result=(count==1);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}

				if(pstmt != null) {
					pstmt.close();
				}

				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;		
	}
	//password를 수정하였을 때 password까지 update
	public boolean update(Long no,String name,String password,String gender) {
		boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		

		try {
			connection = getConnection();

			String sql = "update user set name =? , password=?, gender=? where no = ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.setString(3, gender);

			pstmt.setLong(4, no);
			int count=pstmt.executeUpdate();
			
			result=(count==1);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}

				if(pstmt != null) {
					pstmt.close();
				}

				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;		
	}
}



