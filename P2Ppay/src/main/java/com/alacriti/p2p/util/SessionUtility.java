package com.alacriti.p2p.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import com.alacriti.p2p.model.vo.UserDetailsVO;

public class SessionUtility {
	public static final Logger log = Logger.getLogger(SessionUtility.class);

	public SessionUtility() {

	}

	public void createSession(HttpServletRequest request,
			UserDetailsVO userDetailsVO) {
		log.debug("---> createSession method in SessionUtility class");
		try {
			HttpSession session = request.getSession(true);
			log.info("Printing the status of the session: ");
			session.setAttribute("email", userDetailsVO.getEmail());
			System.out.println("status of the session: "+session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HttpSession getSession(@Context HttpServletRequest request) {
		log.debug("--> getSession method in Sessionutility class");
		HttpSession session = null;
		try {
			session = request.getSession(false);
			log.info("Printing the status of the session: ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	public boolean destroySession(HttpServletRequest request) {
		log.debug("---> destroySession method in SessionUtility class");
		HttpSession existingSession = request.getSession(false);
		try {
			log.info("Printing the status of the session: ");
			existingSession.invalidate();
			System.out.println("Logged out successfully...");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*public HttpSession getSession(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session;
	}*/
}
