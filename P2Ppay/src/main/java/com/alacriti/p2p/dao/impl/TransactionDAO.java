package com.alacriti.p2p.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.model.vo.TransactionSummaryVO;
import com.alacriti.p2p.util.LogUtil;
import com.alacriti.p2p.util.MailingService;

public class TransactionDAO extends BaseDAO {
	private static final AppLogger log = LogUtil
			.getLogger(TransactionDAO.class);
	private static final PreparedStatement PreparedStatement = null;
	private static final ResultSet ResultSet = null;

	MailingService sendMail=new MailingService();

	public TransactionDAO(Connection conn) {
		super(conn);
	}

	public TransactionDAO() {

	}

	public String addMoneyToWallet(TransactionSummaryVO transactionSummaryVO,
			String userEmail) throws DAOException {
		log.debugPrintCurrentMethodName();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String email = userEmail;
		String status = "";
		String subject = "P2P wallet loaded";
		String messageEmail = "";
		String message = "To Wallet";
		float withdrawl = (float) 0.00;
		try {
			String sqlCmd = "sql command";
			System.out.println("email is: " + email);
			stmt = getPreparedStatementAddMoney(getConnection(), sqlCmd);
			stmt.setString(1, email);
			stmt.setString(2, message);
			stmt.setFloat(3, withdrawl);
			stmt.setFloat(4, transactionSummaryVO.getDeposit());
			float creditAmount = transactionSummaryVO.getDeposit();
			int count1 = stmt.executeUpdate();
			stmt.close();
			if (count1 > 0) {
				stmt = getPreparedStatementUpdateBalToUserTbl(getConnection(),sqlCmd);
				stmt.setString(1, email);
				stmt.setString(2, email);
				int count2 = stmt.executeUpdate();
				if (count2 > 0) {
					status = "success";
					messageEmail = "Your wallet has been credited Rs."+creditAmount;
					sendMail.send("smartboy1654@gmail.com", "kmvhcu@7818", email, subject, messageEmail);
					System.out.println("Money Added to wallet successfully");
				}
			}

		} catch (SQLException e) {
			log.logError(
					"SQLException in addMoneyToWallet.. " + e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(stmt, rs);
		}
		return status;
	}

	public PreparedStatement getPreparedStatementAddMoney(
			Connection connection, String sqlCmd1)
			throws SQLException {
		System.out.println("in getPreparedStatementAddMoney... ");
		try {
			return connection
					.prepareStatement("insert into P2Ppay_transaction_summary_tbl(userId, merchant, dateTime, withdrawl, deposit) "
							+ "values((select id from P2Ppay_user_details_tbl where email= ?),?,now(),?,?)");
			/*return connection
					.prepareStatement("insert into P2Ppay_transaction_summary_tbl(userId, merchant, dateTime, withdrawl, deposit) "
							+ "values((select id from P2Ppay_user_details_tbl where email= \""
							+ email
							+ "\"),\""
							+ message
							+ "\",now(),\""
							+ withdrawl + "\",?)");*/
		} catch (SQLException e) {
			log.logError("Exception in getPreparedStatementAddMoneyToWallet "
					+ e.getMessage(), e);
			throw e;

		}
	}

	public PreparedStatement getPreparedStatementUpdateBalToUserTbl(
			Connection connection, String sqlCmd2)
			throws SQLException {
		//System.out.println("EMAIL : " + email);
		try {
			System.out.println("in getPreparedStatementUpdateBalToUserTbl... ");
			return connection
					.prepareStatement("update P2Ppay_user_details_tbl set balance = balance + (select deposit from P2Ppay_transaction_summary_tbl where email = ? order by dateTime desc limit 1) where email= ?");
			/*return connection
					.prepareStatement("update P2Ppay_user_details_tbl set balance = balance + (select deposit from P2Ppay_transaction_summary_tbl where email = \""
							+ email
							+ "\" order by dateTime desc limit 1) where email= \""
							+ email + "\"");*/
		} catch (SQLException e) {
			log.logError("Exception in getPreparedStatementUpdateBalToUserTbl "
					+ e.getMessage(), e);
			throw e;
		}
	}

	public String unloadMoneyFromWallet(
			TransactionSummaryVO transactionSummaryVO, String userEmail)
			throws DAOException {
		log.debugPrintCurrentMethodName();
		PreparedStatement stmt = null;
		Statement stmt1 = null;
		ResultSet rs = null;
		String email = userEmail;
		String status = "";
		String subject = "P2P wallet unloaded";
		String messageEmail = "";
		try {
			String sqlCmd = "sql command";
			float withdrawlAmount = transactionSummaryVO.getWithdrawl();
			System.out.println("withdrawl amount : " + withdrawlAmount);

			String sqlCmd1 = ("select balance from P2Ppay_user_details_tbl where email = '"
					+ email + "'");
			stmt1 = getStatementCheckAvailableBalance(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				String message = "To Bank";
				float deposit = (float) 0.00;
				System.out.println("balance is : " + rs.getFloat("balance"));
				if (withdrawlAmount <= (rs.getFloat("balance"))) {
					stmt = getPreparedStatementUnloadMoney(getConnection(),sqlCmd);
					stmt.setString(1, email);
					stmt.setString(2, message);
					stmt.setFloat(3, withdrawlAmount);
					stmt.setFloat(4, deposit);
					int count1 = stmt.executeUpdate();
					stmt.close();
					if (count1 > 0) {
						stmt = getPreparedStatementUpdateBalanceToUserTbl(getConnection(), sqlCmd);
						stmt.setString(1, email);
						stmt.setString(2, email);
						int count2 = stmt.executeUpdate();
						if (count2 > 0) {
							status = "success";
							messageEmail = "Your wallet has been debited Rs."+withdrawlAmount;
							sendMail.send("smartboy1654@gmail.com", "kmvhcu@7818", email, subject, messageEmail);
							System.out.println("=---> money unloaded");
						}
					}
				}
			}

		} catch (SQLException e) {
			log.logError(
					"SQLException in unloadMoneyFromWallet " + e.getMessage(),
					e);
			throw new DAOException();
		} finally {
			close(stmt, rs);
		}
		return status;
	}

	public Statement getStatementCheckAvailableBalance(Connection connection,
			String sqlCmd) throws SQLException {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			log.logError(
					"Exception in getStatementCheckAvailableBalance "
							+ e.getMessage(), e);
			throw e;
		}
	}

	public PreparedStatement getPreparedStatementUnloadMoney(
			Connection connection, String sqlCmd) throws SQLException {
		try {
			return connection
					.prepareStatement("insert into P2Ppay_transaction_summary_tbl(userId, merchant, dateTime, withdrawl, deposit) "
							+ "values((select id from P2Ppay_user_details_tbl where email= ?),?, now(),?,?)");
			/*return connection
					.prepareStatement("insert into P2Ppay_transaction_summary_tbl(userId, merchant, dateTime, withdrawl, deposit) "
							+ "values((select id from P2Ppay_user_details_tbl where email= \""
							+ email
							+ "\"),\""
							+ message
							+ "\", now(), \""
							+ withdrawlAmount + "\", \"" + deposit + "\")");*/
		} catch (SQLException e) {
			log.logError("Exception in getPreparedStatementAddMoneyToWallet "
					+ e.getMessage(), e);
			throw e;
		}
	}

	public PreparedStatement getPreparedStatementUpdateBalanceToUserTbl(
			Connection connection, String sqlCmd)
			throws SQLException {
		try {
			return connection
					.prepareStatement("update P2Ppay_user_details_tbl set balance = balance - "
							+ "(select withdrawl from P2Ppay_transaction_summary_tbl where email = ? order by dateTime desc limit 1) where email= ?");
		} catch (SQLException e) {
			log.logError("Exception in getPreparedStatementUpdateBalToUserTbl "
					+ e.getMessage(), e);
			throw e;
		}
	}

	public String payToFriend(TransactionSummaryVO transactionSummaryVO,
			String userEmail) throws DAOException {
		log.debugPrintCurrentMethodName();
		PreparedStatement stmt = null;
		Statement stmt1 = null;
		ResultSet rs = null;
		String email = userEmail;
		String messageEmail = "";
		String subject = "P2P payment";
		String status = "";
		try {
			String sqlCmd = "sql command";
			float withdrawlAmount = transactionSummaryVO.getWithdrawl();
			System.out.println("withdrawl amount : " + withdrawlAmount);

			String sqlCmd1 = ("select balance from P2Ppay_user_details_tbl where email = '"
					+ email + "'");
			stmt1 = getStatementCheckAvailableBalance(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				System.out.println("balance is : " + rs.getFloat("balance"));
				if (withdrawlAmount <= (rs.getFloat("balance"))) {
					float deposit = (float) 0.00;
					int mailStatus = 0;
					String merchantName = transactionSummaryVO.getMerchant();
					String description = transactionSummaryVO.getDescription();
					stmt = getPreparedStatementPayMoney(getConnection(),sqlCmd);
					stmt.setString(1, email);
					stmt.setString(2, merchantName);
					stmt.setFloat(3, withdrawlAmount);
					stmt.setFloat(4, deposit);
					stmt.setString(5, description);
					stmt.setInt(6, mailStatus);
					int count1 = stmt.executeUpdate();
					stmt.close();
					if (count1 > 0) {
						stmt = getPreparedStatementUpdateBalanceToUserTbl(getConnection(), sqlCmd);
						stmt.setString(1, email);
						stmt.setString(2, email);
						int count2 = stmt.executeUpdate();
						if (count2 > 0) {
							float withdrawl = (float) 0.00;
							float creditedAmount = withdrawlAmount;
							stmt = getPreparedStatementUpdateTransactionForMerchant(
									getConnection(), sqlCmd);
							stmt.setString(1, merchantName);
							stmt.setString(2, userEmail);
							stmt.setFloat(3, withdrawl);
							stmt.setFloat(4, creditedAmount);
							stmt.setString(5, description);
							int count3 = stmt.executeUpdate();
							if (count3 > 0) {
								stmt = getPreparedStatementUpdateBalToMerchantTbl(getConnection(), sqlCmd);
								stmt.setString(1, merchantName);
								stmt.setString(2, merchantName);
								int count4 = stmt.executeUpdate();
								if (count4 > 0) {
									status = "success";
									messageEmail = "Your wallet has been debited Rs."+withdrawlAmount;
									sendMail.send("smartboy1654@gmail.com", "kmvhcu@7818", email, subject, messageEmail);
									System.out
											.println("=---> money has been sent");
								}
							}
						}
					}
				}
			}

		} catch (SQLException e) {
			log.logError("SQLException in paying " + e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(stmt, rs);
		}
		return status;
	}

	public PreparedStatement getPreparedStatementPayMoney(
			Connection connection, String sqlCmd)
			throws SQLException {
		try {
			return connection
					.prepareStatement("insert into P2Ppay_transaction_summary_tbl(userId, merchant, dateTime, withdrawl, deposit, description, mailStatus) "
							+ "values((select id from P2Ppay_user_details_tbl where email= ?), ?, now(), ?, ?, ?, ?)");
			/*return connection
					.prepareStatement("insert into P2Ppay_transaction_summary_tbl(userId, merchant, dateTime, withdrawl, deposit, description, mailStatus) "
							+ "values((select id from P2Ppay_user_details_tbl where email= \""
							+ email
							+ "\"), \""
							+ merchantName
							+ "\", now(), \""
							+ withdrawlAmount
							+ "\", \""
							+ deposit + "\", \"" + description + "\", 0)");*/
		} catch (SQLException e) {
			log.logError(
					"Exception in getPreparedStatementPayToFriend "
							+ e.getMessage(), e);
			throw e;
		}
	}

	public PreparedStatement getPreparedStatementUpdateTransactionForMerchant(
			Connection connection, String sqlCmd)
			throws SQLException {
		try {
			return connection
					.prepareStatement("insert into P2Ppay_transaction_summary_tbl(userId, merchant, dateTime, withdrawl, deposit, description) "
							+ "values((select id from P2Ppay_user_details_tbl where username= ?), "
							+ "(select username from P2Ppay_user_details_tbl where email= ?), now(), ? , ?, ?)");
			/*return connection
					.prepareStatement("insert into P2Ppay_transaction_summary_tbl(userId, merchant, dateTime, withdrawl, deposit, description) "
							+ "values((select id from P2Ppay_user_details_tbl where username= \""
							+ merchantName
							+ "\"), "
							+ "(select username from P2Ppay_user_details_tbl where email= \""
							+ userEmail
							+ "\"), now(), \""
							+ withdrawl
							+ "\" , \""
							+ creditedAmount
							+ "\", \""
							+ description + "\")");*/
		} catch (SQLException e) {
			log.logError(
					"Exception in getPreparedStatementPayToFriend "
							+ e.getMessage(), e);
			throw e;
		}
	}

	public PreparedStatement getPreparedStatementUpdateBalToMerchantTbl(
			Connection connection, String sqlCmd2)
			throws SQLException {
		try {
			System.out.println("in getPreparedStatementUpdateBalToUserTbl... ");
			return connection
					.prepareStatement("update P2Ppay_user_details_tbl set balance = balance + (select withdrawl from P2Ppay_transaction_summary_tbl where merchant = ? order by dateTime desc limit 1) where username = ?");
			/*return connection
					.prepareStatement("update P2Ppay_user_details_tbl set balance = balance + (select withdrawl from P2Ppay_transaction_summary_tbl where merchant = \""
							+ merchantName
							+ "\" order by dateTime desc limit 1) where username = \""
							+ merchantName + "\"");*/
		} catch (SQLException e) {
			log.logError("Exception in getPreparedStatementUpdateBalToUserTbl "
					+ e.getMessage(), e);
			throw e;
		}
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

	public ArrayList<TransactionSummaryVO> getPageOfTransactionDetails(
			String userEmail, int start, int end) throws DAOException {
		log.debugPrintCurrentMethodName();
		ArrayList<TransactionSummaryVO> transactions = new ArrayList<TransactionSummaryVO>();
		PreparedStatement stmt = null;
		Statement stmt1 = null;
		ResultSet rs = null;
		int userId = 0;
		String email = userEmail;
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ email + "\")";
			stmt1 = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				userId = rs.getInt("id");
			}
			transactions = getPageOfTransactionDetails2(userId, start, end);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			close(stmt, rs);
		}
		return transactions;
	}

	public ArrayList<TransactionSummaryVO> getInboundTransactionDetails(
			String userEmail) throws DAOException {
		log.debugPrintCurrentMethodName();
		ArrayList<TransactionSummaryVO> inbounds = new ArrayList<TransactionSummaryVO>();
		PreparedStatement stmt = null;
		Statement stmt1 = null;
		ResultSet rs, rs1 = null;
		int userId = 0;
		String email = userEmail;
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ email + "\")";
			stmt1 = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				userId = rs.getInt("id");
			}
			String sqlCmd2 = "select count(deposit) as noOfInbound, sum(deposit) as totalDeposit from P2Ppay_transaction_summary_tbl where userId = \""
					+ userId + "\" and deposit<>0";
			stmt = connection.prepareStatement(sqlCmd2);
			rs1 = stmt.executeQuery(sqlCmd2);
			while (rs1.next()) {
				inbounds.add(new TransactionSummaryVO(
						rs1.getInt("noOfInbound"), rs1.getFloat("totalDeposit")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			close(PreparedStatement, ResultSet);
		}
		return inbounds;
	}

	public ArrayList<TransactionSummaryVO> getOutboundTransactionDetails(
			String userEmail) throws DAOException {
		log.debugPrintCurrentMethodName();
		ArrayList<TransactionSummaryVO> outbounds = new ArrayList<TransactionSummaryVO>();
		PreparedStatement stmt = null;
		Statement stmt1 = null;
		ResultSet rs, rs1 = null;
		int userId = 0;
		String email = userEmail;
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ email + "\")";
			stmt1 = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				userId = rs.getInt("id");
			}
			String sqlCmd2 = "select sum(withdrawl) as totalWithdrawl, count(withdrawl) as noOfOutbound from P2Ppay_transaction_summary_tbl where userId = \""
					+ userId + "\" and withdrawl<>0 ";
			stmt = connection.prepareStatement(sqlCmd2);
			rs1 = stmt.executeQuery(sqlCmd2);
			while (rs1.next()) {
				outbounds
						.add(new TransactionSummaryVO(rs1
								.getFloat("totalWithdrawl"), rs1
								.getInt("noOfOutbound")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			close(PreparedStatement, ResultSet);
		}
		return outbounds;
	}

	public ArrayList<TransactionSummaryVO> getPageOfTransactionDetails2(
			int userId, int start, int end) throws DAOException {
		log.debugPrintCurrentMethodName();
		ArrayList<TransactionSummaryVO> transactions = new ArrayList<TransactionSummaryVO>();
		PreparedStatement stmt = null;
		ResultSet rs1 = null;
		int limit = 3;
		String sqlCmd2 = "";
		try {
			System.out.println("userID is: " + userId);
			sqlCmd2 = "(select merchant, dateTime, withdrawl, deposit, description from P2Ppay_transaction_summary_tbl where userId = ? limit ?,?)";
			stmt = connection.prepareStatement(sqlCmd2);
			stmt.setInt(1, userId);
			stmt.setInt(2, start);
			stmt.setInt(3, limit);
			System.out.println("sql cmd: " + sqlCmd2);
			rs1 = stmt.executeQuery();
			System.out.println("rs1 : " + rs1);
			while (rs1.next()) {
				String s1 = rs1.getString(1);
				Date d1 = rs1.getTimestamp(2);
				float f1 = rs1.getFloat(3);
				float f2 = rs1.getFloat(4);
				String s2 = rs1.getString(5);
				transactions.add(new TransactionSummaryVO(s1, d1, f1, f2, s2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			close(stmt, rs1);
		}
		return transactions;
	}

	public int getTotalPagesOfTransactions(
			TransactionSummaryVO transactionSummaryVO, String userEmail)
			throws DAOException {
		log.debugPrintCurrentMethodName();
		Statement stmt1 = null;
		ResultSet rs, rs1 = null;
		String email = userEmail;
		int noPages = 0;
		try {
			String sqlCmd1 = "(select id from P2Ppay_user_details_tbl where email = \""
					+ email + "\")";
			stmt1 = getStatementUserId(getConnection(), sqlCmd1);
			rs = stmt1.executeQuery(sqlCmd1);
			if (rs.next()) {
				int userId = rs.getInt("id");
				String sqlCmd2 = "(select count(transactionId) from P2Ppay_transaction_summary_tbl where userId = \""
						+ userId + "\")";
				stmt1 = getStatementNoOfTransactions(getConnection(), sqlCmd2);
				rs1 = stmt1.executeQuery(sqlCmd2);
				if (rs1.next()) {
					noPages = rs1.getInt(1);
				}
			}
			System.out.println("Test if error in get total page of trans");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			close(PreparedStatement, ResultSet);
		}
		return noPages;
	}

	public Statement getStatementNoOfTransactions(Connection connection,
			String sqlCmd) throws SQLException {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			log.logError(
					"Exception in getStatementNoOfTransactions "
							+ e.getMessage(), e);
			throw e;
		}
	}

	public ArrayList<TransactionSummaryVO> mailToSend() throws DAOException {
		log.logInfo("In mail to Vendor DAO");

		ResultSet rs, rs1 = null;
		PreparedStatement ps, ps1 = null;
		ArrayList<TransactionSummaryVO> results = new ArrayList<TransactionSummaryVO>();

		try {
			String sqlCmd = "select pt.transactionId, pt.userId, pt.merchant, pu.email, pt.dateTime, pt.withdrawl, pt.description from P2Ppay_transaction_summary_tbl pt inner join P2Ppay_user_details_tbl pu on pt.merchant = pu.username where mailStatus=0";
			ps = connection.prepareStatement(sqlCmd);
			rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt("userId");
				System.out.println("userId is : " + userId);
				String sqlCmd1 = "select username from P2Ppay_user_details_tbl where id = \""
						+ userId + "\"";
				ps1 = connection.prepareStatement(sqlCmd1);
				rs1 = ps1.executeQuery(sqlCmd1);
				System.out.println("aftre result set =--->");
				while (rs1.next()) {
					String username = rs1.getString("username");
					results.add(new TransactionSummaryVO(rs
							.getInt("transactionId"), username, rs
							.getString("merchant"), rs.getString("email"), rs
							.getTimestamp("dateTime"),
							rs.getFloat("withdrawl"), rs
									.getString("description")));
				}
			}

		} catch (SQLException e) {
			log.logError(
					"SQLException in mailToTransactionDao " + e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(PreparedStatement, ResultSet);
		}
		return results;
	}

	public void mailSent(int transactionId) throws DAOException {
		log.logInfo("In Mail Sent DAO");
		PreparedStatement ps = null;
		try {
			String sqlCmd = "update P2Ppay_transaction_summary_tbl set mailStatus = 1 where transactionId= \""
					+ transactionId + "\"";
			ps = connection.prepareStatement(sqlCmd);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.logError("SQLException in Mail Sent " + e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps);
		}
	}
}
