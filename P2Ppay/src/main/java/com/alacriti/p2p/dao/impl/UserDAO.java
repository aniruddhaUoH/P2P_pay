package com.alacriti.p2p.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.model.vo.UserDetailsVO;
import com.alacriti.p2p.util.LogUtil;

public class UserDAO extends BaseDAO {
	private static final AppLogger log = LogUtil.getLogger(UserDAO.class);
	private static final ResultSet Resultset = null;
	private static final PreparedStatement PreparedStatement = null;

	public UserDAO(Connection conn) {
		super(conn);
	}

	public UserDAO() {

	}

	public void userSignIn(UserDetailsVO userDetailsVO) throws DAOException {
		log.debugPrintCurrentMethodName();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			float initialBal = 0;
			String sqlCmd = "sql command";
			String userEmail = userDetailsVO.getEmail();
			stmt = getPreparedStatementUserSignUp(getConnection(), sqlCmd);
			stmt.setString(1, userDetailsVO.getUsername());
			stmt.setString(2, userDetailsVO.getEmail());
			stmt.setFloat(3, initialBal);
			stmt.setString(4, userEmail);
			int count = stmt.executeUpdate();
			if (count > 0) {
				System.out.println("New Email added...!");
			}

		} catch (SQLException e) {
			log.logError("SQLException in addNewUser " + e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(stmt, rs);
		}
	}

	public PreparedStatement getPreparedStatementUserSignUp(
			Connection connection, String sqlCmd) throws SQLException {
		try {
			return connection
					.prepareStatement("insert into P2Ppay_user_details_tbl(username, email, balance) "
							+ "select * from (select ?,?,?) as tmp "
							+ "where not exists (select email from P2Ppay_user_details_tbl where email = ?) limit 1;");
			/*
			 * return connection .prepareStatement(
			 * "insert into P2Ppay_user_details_tbl(username, email, balance) "
			 * + "select * from (select ?,?, '0.00') as tmp " +
			 * "where not exists (select email from P2Ppay_user_details_tbl where email = '"
			 * + userEmail + "') limit 1;");
			 */

		} catch (SQLException e) {
			log.logError(
					"Exception in getPreparedStatementAddNewUser "
							+ e.getMessage(), e);
			throw e;

		}
	}

	public void saveDetailsOfUser(UserDetailsVO userDetailsVO, String userEmail)
			throws DAOException {
		log.debugPrintCurrentMethodName();
		PreparedStatement stmt = null;
		Statement stmt1 = null;
		ResultSet rs = null;
		String email = userEmail;
		String sqlCmd = "";
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ email + "\");";
			stmt1 = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				int userId = rs.getInt("id");
				stmt = getPreparedStatementSaveDetails(getConnection(), sqlCmd);
				stmt.setString(1, userDetailsVO.getPhone());
				stmt.setString(2, userDetailsVO.getGender());
				stmt.setInt(3, userId);
				int count = stmt.executeUpdate();
				if (count > 0) {
					System.out.println("User details updated...!");
				}
			}
		} catch (SQLException e) {
			log.logError("SQLException in paying " + e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(stmt, rs);
		}
	}

	public PreparedStatement getPreparedStatementSaveDetails(
			Connection connection, String sqlCmd) throws SQLException {
		try {
			return connection
					.prepareStatement("update P2Ppay_user_details_tbl set phone = ?, gender = ? where id = ?");
		} catch (SQLException e) {
			log.logError("exception in getPreparedStatementSaveDetails: ",
					e.getMessage(), e);
			throw e;
		}
	}

	public ArrayList<UserDetailsVO> getListOfFriends(String userEmail)
			throws DAOException {
		log.debugPrintCurrentMethodName();
		ArrayList<UserDetailsVO> friends = new ArrayList<UserDetailsVO>();
		Statement stmt1 = null;
		ResultSet rs, rs1 = null;
		String email = userEmail;
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ email + "\");";
			stmt1 = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				int userId = rs.getInt("id");
				String sqlCmd2 = "(select U.username, U.email from P2Ppay_user_details_tbl U, P2Ppay_user_relationship_tbl R where case "
						+ "when R.userOneId = \""
						+ userId
						+ "\" then R.userTwoId = U.id "
						+ "when R.userTwoId = \""
						+ userId
						+ "\" then R.userOneId = U.id end and R.status = 1)";
				stmt1 = getStatementListOfFriends(getConnection(), sqlCmd2);
				rs1 = stmt1.executeQuery(sqlCmd2);
				while (rs1.next()) {
					friends.add(new UserDetailsVO(rs1.getString(1), rs1
							.getString(2)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			close(PreparedStatement, Resultset);
		}
		return friends;
	}

	public Statement getStatementUserId(Connection connection, String sqlCmd)
			throws SQLException {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			log.logError("Exception in getStatementUserId " + e.getMessage(), e);
			throw e;
		}
	}

	public Statement getStatementListOfFriends(Connection connection,
			String sqlCmd) throws SQLException {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			log.logError(
					"Exception in getStatementListOfFriends " + e.getMessage(),
					e);
			throw e;
		}
	}

	public ArrayList<UserDetailsVO> getPendingRequests(String userEmail)
			throws DAOException {
		log.debugPrintCurrentMethodName();
		ArrayList<UserDetailsVO> requests = new ArrayList<UserDetailsVO>();
		Statement stmt1 = null;
		ResultSet rs, rs1 = null;
		String email = userEmail;
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ email + "\");";
			stmt1 = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				int userId = rs.getInt("id");
				String sqlCmd2 = "(select U.username from P2Ppay_user_details_tbl U, P2Ppay_user_relationship_tbl R where case "
						+ "when R.userTwoId = \""
						+ userId
						+ "\" then R.userOneId = U.id end and R.status = 0 and actionUserId != \""
						+ userId + "\")";
				stmt1 = getStatementListOfPendingRequests(getConnection(),
						sqlCmd2);
				rs1 = stmt1.executeQuery(sqlCmd2);
				while (rs1.next()) {
					System.out.println("pending requests name: "
							+ rs1.getString(1));
					requests.add(new UserDetailsVO(rs1.getString(1)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			close(PreparedStatement, Resultset);
		}
		for (UserDetailsVO s : requests) {
			System.out.println("Sending from dAO..=>" + s.getUsername());
		}
		return requests;
	}

	public Statement getStatementListOfPendingRequests(Connection connection,
			String sqlCmd) throws SQLException {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			log.logError(
					"Exception in getStatementListOfFriends " + e.getMessage(),
					e);
			throw e;
		}
	}

	public ArrayList<UserDetailsVO> getUserDetails(String userEmail)
			throws DAOException {
		log.debugPrintCurrentMethodName();
		ArrayList<UserDetailsVO> details = new ArrayList<UserDetailsVO>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String email = userEmail;
		try {
			String sqlCmd = "(select balance, phone, gender from P2Ppay_user_details_tbl where email = \""
					+ email + "\")";
			stmt = connection.prepareStatement(sqlCmd);
			rs = stmt.executeQuery(sqlCmd);
			while (rs.next()) {
				details.add(new UserDetailsVO(rs.getFloat("balance"), rs
						.getString("phone"), rs.getString("gender")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			close(PreparedStatement, Resultset);
		}
		return details;
	}

	public ArrayList<UserDetailsVO> getSearchedFriends(String name,
			String userEmail) throws DAOException {
		log.debugPrintCurrentMethodName();
		ArrayList<UserDetailsVO> friends = new ArrayList<UserDetailsVO>();
		Statement stmt1, stmt, stmt2 = null;
		ResultSet rs, rs1, rs2 = null;
		String friendName = name.trim();
		int userId = 0;
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ userEmail + "\");";
			stmt1 = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				userId = rs.getInt("id");
			}
			String sqlCmd = "select id, username from P2Ppay_user_details_tbl "
					+ "where id!=" + userId + " and username LIKE '%"
					+ friendName + "%'";

			stmt = getStatementListOfSearchedFriends(getConnection(), sqlCmd);
			rs1 = stmt.executeQuery(sqlCmd);
			while (rs1.next()) {
				int friendId = rs1.getInt("id");
				String friend = rs1.getString("username");
				System.out.println("friend: " + friend);
				String sqlCmd2 = "(select count(status) as countStatus, status from P2Ppay_user_relationship_tbl where case when userOneId = \""
						+ userId
						+ "\" then userTwoId = \""
						+ friendId
						+ "\" "
						+ "when userOneId = \""
						+ friendId
						+ "\" then userTwoId = \"" + userId + "\" end)";
				stmt2 = connection.prepareStatement(sqlCmd2);
				rs2 = stmt2.executeQuery(sqlCmd2);
				while (rs2.next()) {
					if (rs2.getInt("countStatus") == 0) {
						int status = 2;
						friends.add(new UserDetailsVO(friend, status));
					} else {
						friends.add(new UserDetailsVO(friend, rs2
								.getInt("status")));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			close(PreparedStatement, Resultset);
		}
		return friends;
	}

	public Statement getStatementListOfSearchedFriends(Connection connection,
			String sqlCmd) throws SQLException {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			log.logError(
					"Exception in getStatementListOfSearchedFriends "
							+ e.getMessage(), e);
			throw e;
		}
	}

	public void sendRequestToFriend(String friendName, String userEmail)
			throws DAOException {
		log.debugPrintCurrentMethodName();
		PreparedStatement stmt1 = null;
		Statement stmt, stmt2 = null;
		ResultSet rs, rs1 = null;
		int userId = 0, friendId = 0;
		String sqlCmd = "";
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ userEmail + "\");";
			stmt = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt.executeQuery(sqlCmd1);
			if (rs.next()) {
				userId = rs.getInt("id");
			}
			String sqlCmd2 = "(select id from P2Ppay_user_details_tbl where username = \""
					+ friendName + "\");";
			stmt2 = getStatementFriendId(getConnection(), sqlCmd2);
			rs1 = stmt2.executeQuery(sqlCmd2);
			if (rs1.next()) {
				friendId = rs1.getInt("id");
			}
			int status = 0;
			stmt1 = getPreparedStatementSendRequest(getConnection(), sqlCmd);
			stmt1.setInt(1, userId);
			stmt1.setInt(2, friendId);
			stmt1.setInt(3, status);
			stmt1.setInt(4, userId);
			int count = stmt1.executeUpdate();
			if (count > 0) {
				System.out.println("request sent successfully");
			}

		} catch (SQLException e) {
			log.logError("SQLException in addNewUser " + e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(PreparedStatement, Resultset);
		}
	}

	public Statement getStatementFriendId(Connection connection, String sqlCmd)
			throws SQLException {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			log.logError("Exception in getStatementUserId " + e.getMessage(), e);
			throw e;
		}
	}

	public PreparedStatement getPreparedStatementSendRequest(
			Connection connection, String sqlCmd) throws SQLException {
		try {
			return connection
					.prepareStatement("insert into P2Ppay_user_relationship_tbl(userOneId, userTwoId, status, actionUserId) values (?,?,?,?)");

		} catch (SQLException e) {
			log.logError(
					"Exception in getPreparedStatementAddNewUser "
							+ e.getMessage(), e);
			throw e;

		}
	}

	public void acceptFriendRequest(String friendName, String userEmail)
			throws DAOException {
		log.debugPrintCurrentMethodName();
		PreparedStatement stmt1 = null;
		Statement stmt, stmt2 = null;
		ResultSet rs, rs1 = null;
		int userId = 0, friendId = 0;
		String sqlCmd = "";
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ userEmail + "\");";
			stmt = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt.executeQuery(sqlCmd1);
			if (rs.next()) {
				userId = rs.getInt("id");
			}
			System.out.println("friend =---> " + friendName);
			String sqlCmd2 = "(select id from P2Ppay_user_details_tbl where username = \""
					+ friendName + "\");";
			stmt2 = getStatementFriendId(getConnection(), sqlCmd2);
			rs1 = stmt2.executeQuery(sqlCmd2);
			if (rs1.next()) {
				friendId = rs1.getInt("id");
				System.out.println("friend id =---->> " + friendId);
			}
			int status = 1;
			stmt1 = getPreparedStatementUpdateRelationshipTbl(getConnection(),
					sqlCmd);
			stmt1.setInt(1, status);
			stmt1.setInt(2, userId);
			stmt1.setInt(3, friendId);
			stmt1.setInt(4, userId);
			int count = stmt1.executeUpdate();
			if (count > 0) {
				System.out.println("relationship successfully updated");
			}

		} catch (SQLException e) {
			log.logError("SQLException in addNewUser " + e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(PreparedStatement, Resultset);
		}
	}

	public PreparedStatement getPreparedStatementUpdateRelationshipTbl(
			Connection connection, String sqlCmd) throws SQLException {
		try {
			return connection
					.prepareStatement("update P2Ppay_user_relationship_tbl set status = ?, actionUserId = ? where userOneId = ? and userTwoId = ?");

			/*
			 * return connection .prepareStatement(
			 * "update P2Ppay_user_relationship_tbl set status= 1, actionUserId = \""
			 * + userId + "\" where userOneId = \"" + friendId +
			 * "\" and userTwoId = \"" + userId + "\"");
			 */

		} catch (SQLException e) {
			log.logError(
					"Exception in getPreparedStatementAddNewUser "
							+ e.getMessage(), e);
			throw e;

		}
	}

	public void rejectFriendRequest(String friendName, String userEmail)
			throws DAOException {
		log.debugPrintCurrentMethodName();
		PreparedStatement stmt1 = null;
		Statement stmt, stmt2 = null;
		ResultSet rs, rs1 = null;
		int userId = 0, friendId = 0;
		String sqlCmd = "";
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ userEmail + "\");";
			stmt = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt.executeQuery(sqlCmd1);
			if (rs.next()) {
				userId = rs.getInt("id");
			}
			System.out.println("friend =---> " + friendName);
			String sqlCmd2 = "(select id from P2Ppay_user_details_tbl where username = \""
					+ friendName + "\");";
			stmt2 = getStatementFriendId(getConnection(), sqlCmd2);
			rs1 = stmt2.executeQuery(sqlCmd2);
			if (rs1.next()) {
				friendId = rs1.getInt("id");
				System.out.println("friend id =---->> " + friendId);
			}
			stmt1 = getPreparedStatementDeleteFromRelationshipTbl(
					getConnection(), sqlCmd);
			stmt1.setInt(1, friendId);
			stmt1.setInt(2, userId);
			int count = stmt1.executeUpdate();
			if (count > 0) {
				System.out.println("relationship successfully updated");
			}

		} catch (SQLException e) {
			log.logError("SQLException in addNewUser " + e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(PreparedStatement, Resultset);
		}
	}

	public PreparedStatement getPreparedStatementDeleteFromRelationshipTbl(
			Connection connection, String sqlCmd) throws SQLException {
		try {
			return connection
					.prepareStatement("delete from P2Ppay_user_relationship_tbl where userOneId = ? and userTwoId = ?");
			/*
			 * return connection .prepareStatement(
			 * "delete from P2Ppay_user_relationship_tbl where userOneId = \"" +
			 * friendId + "\" and userTwoId = \"" + userId + "\"");
			 */
		} catch (SQLException e) {
			log.logError(
					"Exception in getPreparedStatementAddNewUser "
							+ e.getMessage(), e);
			throw e;

		}
	}
}
