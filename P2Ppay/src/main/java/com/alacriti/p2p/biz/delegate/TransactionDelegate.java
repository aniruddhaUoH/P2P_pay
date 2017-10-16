package com.alacriti.p2p.biz.delegate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.alacriti.p2p.bo.impl.TransactionBO;
import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.model.vo.TransactionSummaryVO;
import com.alacriti.p2p.util.LogUtil;

public class TransactionDelegate extends BaseDelegate {
	private static final AppLogger log = LogUtil
			.getLogger(TransactionDelegate.class);

	public String addMoneyToWallet(TransactionSummaryVO transactionSummaryVO,
			String userEmail) throws SQLException {
		log.logInfo("In transaction delegate--------->addMoneyToWallet");
		String status = "";
		boolean flag = false;
		Connection connection = null;
		String email = userEmail;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			TransactionBO transactionBO = new TransactionBO(getConnection());
			status = transactionBO
					.addMoneyToWallet(transactionSummaryVO, email);

		} catch (Exception e) {
			log.logError("Exception in getMessage " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return status;
	}

	public String unloadMoneyFromWallet(
			TransactionSummaryVO transactionSummaryVO, String userEmail)
			throws SQLException {
		log.logInfo("In transaction delegate---------> unloadMoneyToWallet");
		boolean flag = false;
		Connection connection = null;
		String email = userEmail;
		String status = "";
		try {
			connection = startDBTransaction();
			setConnection(connection);
			TransactionBO transactionBO = new TransactionBO(getConnection());
			status = transactionBO.unloadMoneyFromWallet(transactionSummaryVO,
					email);
		} catch (Exception e) {
			log.logError("Exception in getMessage " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return status;
	}

	public String payToFriend(TransactionSummaryVO transactionSummaryVO,
			String userEmail) throws SQLException {
		log.logInfo("In transaction delegate--------->paytofriend");
		boolean flag = false;
		String status = "";
		Connection connection = null;
		String email = userEmail;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			TransactionBO transactionBO = new TransactionBO(getConnection());
			status = transactionBO.payToFriend(transactionSummaryVO, email);

		} catch (Exception e) {
			log.logError("Exception in getMessage " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return status;
	}

	public ArrayList<TransactionSummaryVO> getPageOfTransactionDetails(
			String userEmail, int start, int end) throws SQLException {
		log.logInfo("In User Delegate------> getPagesOfTransactionDetails");
		ArrayList<TransactionSummaryVO> transactions = null;
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			TransactionBO transactionbo = new TransactionBO(getConnection());
			transactions = transactionbo.getPageOfTransactionDetails(email,
					start, end);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return transactions;
	}

	public ArrayList<TransactionSummaryVO> getInboundTransactionDetails(
			String userEmail) throws SQLException {
		log.logInfo("In User Delegate------> getInboundTransactionDetails");
		ArrayList<TransactionSummaryVO> inbounds = null;
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			TransactionBO transactionbo = new TransactionBO(getConnection());
			inbounds = transactionbo.getInboundTransactionDetails(email);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return inbounds;
	}

	public ArrayList<TransactionSummaryVO> getOutboundTransactionDetails(
			String userEmail) throws SQLException {
		log.logInfo("In User Delegate------> getOutboundTransactionDetails");
		ArrayList<TransactionSummaryVO> outbounds = null;
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			TransactionBO transactionbo = new TransactionBO(getConnection());
			outbounds = transactionbo.getOutboundTransactionDetails(email);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return outbounds;
	}

	public int getTotalPagesOfTransactions(
			TransactionSummaryVO transactionSummaryVO, String userEmail)
			throws SQLException {
		log.logInfo("In transaction delegate---------> getTotalPagesOfTransactions");
		boolean flag = false;
		Connection connection = null;
		String email = userEmail;
		int noPages = 0;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			TransactionBO transactionBO = new TransactionBO(getConnection());
			noPages = transactionBO.getTotalPagesOfTransactions(
					transactionSummaryVO, email);

		} catch (Exception e) {
			log.logError("Exception in getMessage " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return noPages;
	}

	public ArrayList<TransactionSummaryVO> mailToSend() {
		log.logInfo("In MailtoSend Delegate");
		boolean flag = false;
		Connection connection = null;
		ArrayList<TransactionSummaryVO> results = new ArrayList<TransactionSummaryVO>();
		try {
			connection = startDBTransaction();
			setConnection(connection);
			TransactionBO transactionBO = new TransactionBO(getConnection());
			results = transactionBO.mailToSend();
		} catch (Exception e) {
			log.logError("Exception in MAiltoSend " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return results;
	}

	public void mailSent(int transactionId) {
		log.logInfo("In Mail Sent Delegate");
		boolean flag = false;
		Connection connection = null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			TransactionBO transactionBO = new TransactionBO(getConnection());
			transactionBO.mailSent(transactionId);
		} catch (Exception e) {
			log.logError("Exception in Mail Sent " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
	}
}
