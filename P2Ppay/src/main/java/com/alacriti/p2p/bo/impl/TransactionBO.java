package com.alacriti.p2p.bo.impl;

import java.sql.Connection;
import java.util.ArrayList;

import com.alacriti.p2p.dao.impl.DAOException;
import com.alacriti.p2p.dao.impl.TransactionDAO;
import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.model.vo.TransactionSummaryVO;
import com.alacriti.p2p.util.LogUtil;

public class TransactionBO extends BaseBO {
	private static final AppLogger log = LogUtil.getLogger(TransactionBO.class);

	public TransactionBO(Connection connection) {
		super(connection);
	}

	public String addMoneyToWallet(TransactionSummaryVO transactionSummaryVO, String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		String email = userEmail;
		String status = "";
		try {
			TransactionDAO transactionDAO = new TransactionDAO(getConnection());
			status = transactionDAO.addMoneyToWallet(transactionSummaryVO, email);

		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return status;
	}

	public String unloadMoneyFromWallet(TransactionSummaryVO transactionSummaryVO, String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		String email = userEmail;
		String status = "";
		try {
			TransactionDAO transactionDAO = new TransactionDAO(getConnection());
			status = transactionDAO.unloadMoneyFromWallet(transactionSummaryVO, email);

		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return status;
	}
	
	public String payToFriend(TransactionSummaryVO transactionSummaryVO, String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		String email = userEmail;
		String status = "";
		try {
			TransactionDAO transactionDAO = new TransactionDAO(getConnection());
			status = transactionDAO.payToFriend(transactionSummaryVO, email);

		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return status;
	}
	
	public ArrayList<TransactionSummaryVO> getPageOfTransactionDetails(String userEmail, int start, int end) throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		ArrayList<TransactionSummaryVO> transactions = null;
		String email = userEmail;
		try {
			TransactionDAO transactiondao = new TransactionDAO(getConnection());
			transactions = transactiondao.getPageOfTransactionDetails(email, start, end);
		} 
		catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return transactions;
	}
	
	public ArrayList<TransactionSummaryVO> getInboundTransactionDetails(String userEmail) throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		ArrayList<TransactionSummaryVO> inbounds = null;
		String email = userEmail;
		try {
			TransactionDAO transactiondao = new TransactionDAO(getConnection());
			inbounds = transactiondao.getInboundTransactionDetails(email);
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new BOException();
		}
		return inbounds;
	}
	
	public ArrayList<TransactionSummaryVO> getOutboundTransactionDetails(String userEmail) throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		ArrayList<TransactionSummaryVO> outbounds = null;
		String email = userEmail;
		try {
			TransactionDAO transactiondao = new TransactionDAO(getConnection());
			outbounds = transactiondao.getOutboundTransactionDetails(email);
		} 
		catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return outbounds;
	}
	
	public int getTotalPagesOfTransactions(TransactionSummaryVO transactionSummaryVO, String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		String email = userEmail;
		int noPages = 0;
		try {
			TransactionDAO transactionDAO = new TransactionDAO(getConnection());
			noPages = transactionDAO.getTotalPagesOfTransactions(transactionSummaryVO, email);
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return noPages;
	}
	
	public ArrayList<TransactionSummaryVO> mailToSend() throws BOException {
		log.debugPrintCurrentMethodName();
		TransactionDAO transactionDAO = new TransactionDAO(getConnection());
		ArrayList<TransactionSummaryVO> results = new ArrayList<TransactionSummaryVO>();
		try {
			results = transactionDAO.mailToSend();
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return results;

	}

	public void mailSent(int transactionId) throws BOException {
		log.debugPrintCurrentMethodName();
		TransactionDAO transactionDAO = new TransactionDAO(getConnection());
		try {
			transactionDAO.mailSent(transactionId);
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
	}
}
