package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;




public class BoardDao {
	//게시판에 글 쓰기를 위한 insert
	public Boolean insert(BoardVo vo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			//만약 max(g_no)+1가 null이면 1,null이 아니면  max(g_no)+1 값을 출력
			String sql = "select ifnull(max(g_no)+1,1) from board";
			pstmt = connection.prepareStatement(sql);

			rs=pstmt.executeQuery();
			int group_no = 0 ; 


			while(rs.next()) {
				int gr_no = rs.getInt(1);
				group_no = gr_no;
			}

			String sql1 = "insert into board values(null, ?, ?, 0, now(), ?, ?, 0, ? ,?)";
			pstmt = connection.prepareStatement(sql1);
			pstmt.setString(1,vo.getTitle());
			pstmt.setString(2,vo.getContents());
			pstmt.setInt(3,group_no);
			pstmt.setInt(4, vo.getO_no());
			pstmt.setLong(5, vo.getUser_no());
			pstmt.setInt(6, vo.getUse_yn());
			int count = pstmt.executeUpdate();
			result = (count == 1);



		} catch (SQLException e) {
			System.out.println("insert_error:" + e);
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
	// 답글 쓰기를 위한 newinsert
	public Boolean newinsert(BoardVo vo) {
		Boolean result = false;
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;


		BoardVo parentVo = this.view(vo.getNo());

		vo.setG_no(parentVo.getG_no());
		vo.setO_no(parentVo.getO_no() + 1);
		vo.setDepth(parentVo.getDepth() + 1);

		try {
			connection = getConnection();
			//답글을 달았을 때 부모의 g_no값과 같게 해주고 o_no+1 해준다.
			String sql = "update board " + 
					"set o_no = o_no + 1 " + 
					"where g_no =? and o_no >= ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setLong(1, vo.getG_no());
			pstmt.setLong(2, vo.getO_no());

			pstmt.executeUpdate();


			String sql2 = "insert into board(no,title,contents,hit,reg_date,g_no,o_no,depth,user_no) value(null,?,?,0,now(),?,?,?,?)";
			pstmt = connection.prepareStatement(sql2);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getG_no());
			pstmt.setInt(4, vo.getO_no());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getUser_no());

			int count = pstmt.executeUpdate();
			result = (count == 1);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	//게시글 제목을 눌렀을 때 내용을 보여주기 위한 view
	public BoardVo view(long no) {
		BoardVo result = new BoardVo();

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql1 = 
					"select no, title, contents, user_no, reg_date, g_no, o_no, depth  from board where no =?" ;
			pstmt = connection.prepareStatement(sql1);
			pstmt.setLong(1,no);
			rs = pstmt.executeQuery();

			while(rs.next()){
				Long no1= rs.getLong(1);
				String title =rs.getString(2);
				String contents =rs.getString(3);
				Long user_no = rs.getLong(4);
				String reg_date = rs.getString(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);

				BoardVo vo= new BoardVo();
				vo.setNo(no1);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUser_no(user_no);
				vo.setReg_date(reg_date);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);

				result = vo;
			}
			

		} catch (SQLException e) {
			System.out.println("view_error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
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
	//view화면에 들어갈 때 hit가 증가하기위해서
	public void hit(Long no) {

		Connection connection = null;
		PreparedStatement pstmt = null;


		try {
			connection = getConnection();

			String sql = " update board set hit=hit+1 where no=?; ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1,no);

			pstmt.executeUpdate();



		} catch (SQLException e) {
			System.out.println("hit_error:" + e);
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

	}
	
	// 게시글을 수정하기 위한 modify
	public boolean modify(BoardVo vo) {
		boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;


		try {
			connection = getConnection();

			String sql = "update board set title= ? , contents = ?, reg_date = now() where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3,vo.getNo());

			int count=pstmt.executeUpdate();

			result=(count==1);

		} catch (SQLException e) {
			System.out.println("modify_error:" + e);
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
	//게시글을 삭제 하여 use_yn 값을 받아오기 위한 delete
	//use_yn이 1이면 글 삭제 
	public void delete(long no) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql =
					"update board "
					+ "set use_yn=1 where no =?";

			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);


			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("delete_error:" + e);
		} finally {
			try {
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
	}
	//검색을 하여 검색하는 해당하는 검색어에 있는 제목과 내용이 들어있는 리스트 들을 검색
	//처음 리스트 화면을 보여주기위한 getlist
	public List<BoardVo> getList(int page, String keyword) {
		List<BoardVo> result = new ArrayList<BoardVo>();

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;


		try {
			connection = getConnection();
			
			String sql = "select a.user_no, a.title ,b.name, a.hit ,date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'),a.depth ,a.no ,a.g_no ,a.o_no, a.use_yn" + 
					" from board a, user b " + 
					" where a.user_no =b.no" + 
					" and (title like ?" + 
					" or contents like ?)"+
					" order by a.g_no DESC, a.o_no ASC Limit ?,5 ";


			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,"%"+keyword+"%");
			pstmt.setString(2,"%"+keyword+"%");
			pstmt.setLong(3, page);
			rs = pstmt.executeQuery();


			while(rs.next()){
				Long user_no = rs.getLong(1);
				String title =rs.getString(2);
				String user_name =rs.getString(3);
				int hit =rs.getInt(4);
				String reg_date=rs.getString(5);
				int depth=rs.getInt(6);
				Long no =rs.getLong(7);
				int g_no =rs.getInt(8);
				int o_no =rs.getInt(9);	
				int use_yn = rs.getInt(10);



				BoardVo vo= new BoardVo();
				vo.setUser_no(user_no);
				vo.setTitle(title);
				vo.setUser_name(user_name);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setDepth(depth);
				vo.setNo(no);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setUse_yn(use_yn);



				result.add(vo);

			}
		} catch (SQLException e) {
			System.out.println("search_error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
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
	//글의 총 개수를 가져오기 위한 count
	public int Count(String keyword) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			connection = getConnection();
			if(keyword != null) {
				String sql = "select count(*) from board where (title Like ? or contents Like ?) ";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setString(2, "%"+keyword+"%");
			}else {
				String sql = "select count(*) from board";
				pstmt = connection.prepareStatement(sql);
			}	
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		}catch(SQLException e) {
			System.out.println("count_error : "+ e);
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();			
				}

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;

	}


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

}
