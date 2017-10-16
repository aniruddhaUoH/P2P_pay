package com.alacriti.p2p.resteasy.resources;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.alacriti.p2p.biz.delegate.TransactionDelegate;
import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.model.vo.TransactionSummaryVO;
import com.alacriti.p2p.util.LogUtil;
import com.alacriti.p2p.util.SessionUtility;

@Path("/user")
public class TransactionResource {
	private static final AppLogger log = LogUtil
			.getLogger(TransactionResource.class);

	@POST
	@Path("/loadMoney")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addMoneyToWallet(TransactionSummaryVO transactionSummaryVO,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("---> addMoneyToWallet method in TransactionResource class:: ");
		String status = "";
		TransactionDelegate transactionDelegate = new TransactionDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		status = transactionDelegate.addMoneyToWallet(transactionSummaryVO,
				email);
		return status;
	}

	@POST
	@Path("/unloadMoney")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String unloadMoneyFromWallet(
			TransactionSummaryVO transactionSummaryVO,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> unloadMoneyFromWallet method in TransactionResource class:: ");
		String status = "";
		TransactionDelegate transactionDelegate = new TransactionDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		status = transactionDelegate.unloadMoneyFromWallet(
				transactionSummaryVO, email);
		return status;
	}

	@POST
	@Path("/pay")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String payToFriend(TransactionSummaryVO transactionSummaryVO,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> payToFriend method in TransactionResource class:: ");
		String status = "";
		TransactionDelegate transactionDelegate = new TransactionDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		status = transactionDelegate.payToFriend(transactionSummaryVO, email);
		return status;
	}

	@GET
	@Path("/inboundTransaction")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TransactionSummaryVO> getInboundTransactionDetails(
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> getInboundTransactionDetails method in TransactionResource class: ");
		ArrayList<TransactionSummaryVO> inbounds = null;
		TransactionDelegate transactiondelegate = new TransactionDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		inbounds = transactiondelegate.getInboundTransactionDetails(email);
		return inbounds;
	}

	@GET
	@Path("/outboundTransaction")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TransactionSummaryVO> getOutboundTransactionDetails(
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> getOutboundTransactionDetails method in TransactionResource class: ");
		ArrayList<TransactionSummaryVO> outbounds = null;
		TransactionDelegate transactiondelegate = new TransactionDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		outbounds = transactiondelegate.getOutboundTransactionDetails(email);
		return outbounds;
	}

	@GET
	@Path("/pageOfTransactions")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TransactionSummaryVO> getPageOfTransactionDetails(
			@QueryParam("start") int start, @QueryParam("end") int end,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> getPageOfTransactionDetails method in TransactionResource class: ");
		ArrayList<TransactionSummaryVO> transactions = null;
		TransactionDelegate transactiondelegate = new TransactionDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		transactions = transactiondelegate.getPageOfTransactionDetails(email,
				start, end);
		return transactions;
	}

	@POST
	@Path("/totalTransactions")
	@Produces(MediaType.APPLICATION_JSON)
	public int getTotalPagesOfTransactions(
			TransactionSummaryVO transactionSummaryVO,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("---> getTotalPagesOfTransactions method in TransactionResource class:: ");
		TransactionDelegate transactionDelegate = new TransactionDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		int noPages = transactionDelegate.getTotalPagesOfTransactions(
				transactionSummaryVO, email);
		return noPages;

	}
}