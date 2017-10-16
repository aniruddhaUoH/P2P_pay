package com.alacriti.p2p.util;

import java.util.ArrayList;
import java.util.TimerTask;

import com.alacriti.p2p.biz.delegate.TransactionDelegate;
import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.model.vo.TransactionSummaryVO;

public class TimeForDaemonProcess extends TimerTask {
	private static final AppLogger log = LogUtil
			.getLogger(TransactionDelegate.class);
	MailingService sendMail = new MailingService();

	@Override
	public void run() {
		String subject = "Amount Received";
		String email = "";
		String username = "";
		float withdrawl = 0;
		String message = "";
		int transactionId = 0;
		ArrayList<TransactionSummaryVO> results = new ArrayList<TransactionSummaryVO>();
		TransactionDelegate transactionDelegate = new TransactionDelegate();
		results = transactionDelegate.mailToSend();
		if (results.size() == 0) {
			log.logInfo("No transaction done In Last 5 Hours");
		} else {
			for (int i = 0; i < results.size(); i++) {
				System.out.println("Before Email" + results.get(i).getEmail());
				email = results.get(i).getEmail();
				username = results.get(i).getUsername();
				withdrawl = results.get(i).getWithdrawl();
				transactionId = results.get(i).getTransactionId();
				message = "You have received an amount of Rs."
						+ withdrawl
						+ " from "
						+ username
						+ " . To get more details, check your transaction summary";
				sendMail.send("smartboy1654@gmail.com", "kmvhcu@7818", email,
						subject, message);
				mailSent(transactionId);
			}
		}
	}

	private void mailSent(int transactionId) {
		TransactionDelegate transactionDelegate = new TransactionDelegate();
		transactionDelegate.mailSent(transactionId);
	}
}
