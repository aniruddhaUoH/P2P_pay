package com.alacriti.p2p.bo.impl;

import java.sql.Connection;
import java.util.ArrayList;

import com.alacriti.p2p.dao.impl.DAOException;
import com.alacriti.p2p.dao.impl.UserDAO;
import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.model.vo.UserDetailsVO;
import com.alacriti.p2p.util.LogUtil;

public class UserBO extends BaseBO {
	private static final AppLogger log = LogUtil.getLogger(UserBO.class);

	public UserBO(Connection connection) {
		super(connection);
	}

	public void userSignIn(UserDetailsVO userDetailsVO) throws DAOException,
			BOException {
		log.debugPrintCurrentMethodName();
		try {
			UserDAO userDAO = new UserDAO(getConnection());
			userDAO.userSignIn(userDetailsVO);

		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();

		}
	}

	public void saveDetailsOfUser(UserDetailsVO userDetailsVO, String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		String email = userEmail;
		try {
			UserDAO userDAO = new UserDAO(getConnection());
			userDAO.saveDetailsOfUser(userDetailsVO, email);

		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
	}

	public ArrayList<UserDetailsVO> getListOfFriends(String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		ArrayList<UserDetailsVO> friends = null;
		String email = userEmail;
		try {
			UserDAO userdao = new UserDAO(getConnection());
			friends = userdao.getListOfFriends(email);
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return friends;
	}

	public ArrayList<UserDetailsVO> getPendingRequests(String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		ArrayList<UserDetailsVO> requests = null;
		String email = userEmail;
		try {
			UserDAO userdao = new UserDAO(getConnection());
			requests = userdao.getPendingRequests(email);
			for (UserDetailsVO s : requests) {
				System.out.println("Sending from bo...=>" + s.getUsername());
			}
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return requests;
	}

	public ArrayList<UserDetailsVO> getUserDetails(String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		ArrayList<UserDetailsVO> details = null;
		String email = userEmail;
		try {
			UserDAO userdao = new UserDAO(getConnection());
			details = userdao.getUserDetails(email);
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return details;
	}

	public ArrayList<UserDetailsVO> getSearchedFriends(String friendName,
			String userEmail) throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		ArrayList<UserDetailsVO> friends = null;
		String email = userEmail;
		try {
			UserDAO userdao = new UserDAO(getConnection());
			friends = userdao.getSearchedFriends(friendName, email);
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
		return friends;
	}

	public void sendRequestToFriend(String friendName, String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		String email = userEmail;
		try {
			UserDAO userdao = new UserDAO(getConnection());
			userdao.sendRequestToFriend(friendName, email);
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
	}

	public void acceptFriendRequest(String friendName, String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		String email = userEmail;
		try {
			UserDAO userdao = new UserDAO(getConnection());
			userdao.acceptFriendRequest(friendName, email);
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
	}

	public void rejectFriendRequest(String friendName, String userEmail)
			throws DAOException, BOException {
		log.debugPrintCurrentMethodName();
		String email = userEmail;
		try {
			UserDAO userdao = new UserDAO(getConnection());
			userdao.rejectFriendRequest(friendName, email);
		} catch (Exception e) {
			log.logError("Exception in retrieveMessage " + e.getMessage(), e);
			throw new BOException();
		}
	}
}
