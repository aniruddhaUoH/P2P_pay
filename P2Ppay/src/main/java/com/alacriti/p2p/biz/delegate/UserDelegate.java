package com.alacriti.p2p.biz.delegate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.alacriti.p2p.bo.impl.UserBO;
import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.model.vo.UserDetailsVO;
import com.alacriti.p2p.util.LogUtil;

public class UserDelegate extends BaseDelegate {

	private static final AppLogger log = LogUtil.getLogger(UserDelegate.class);

	public void userSignIn(UserDetailsVO userDetailsVO) throws SQLException {
		log.logInfo("In User delegate--------->addNewUser");
		boolean flag = false;
		Connection connection = null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			UserBO userBO = new UserBO(getConnection());
			userBO.userSignIn(userDetailsVO);

		} catch (Exception e) {
			log.logError("Exception in getMessage " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
	}

	public void saveDetailsOfUser(UserDetailsVO userDetailsVO, String userEmail)
			throws SQLException {
		log.logInfo("In User delegate--------->saveUserDetails");
		boolean flag = false;
		Connection connection = null;
		String email = userEmail;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			UserBO userBO = new UserBO(getConnection());
			userBO.saveDetailsOfUser(userDetailsVO, email);

		} catch (Exception e) {
			log.logError("Exception in getMessage " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
	}

	public ArrayList<UserDetailsVO> getListOfFriends(String userEmail)
			throws SQLException {
		log.logInfo("In User Delegate------> getListOfFriends");
		ArrayList<UserDetailsVO> friends = null;
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			UserBO userbo = new UserBO(getConnection());
			friends = userbo.getListOfFriends(email);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return friends;
	}

	public ArrayList<UserDetailsVO> getPendingRequests(String userEmail)
			throws SQLException {
		log.logInfo("In User Delegate------> getPendingRequests");
		ArrayList<UserDetailsVO> requests = null;
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			UserBO userbo = new UserBO(getConnection());
			requests = userbo.getPendingRequests(email);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return requests;
	}

	public ArrayList<UserDetailsVO> getUserDetails(String userEmail)
			throws SQLException {
		log.logInfo("In User Delegate------> getUserDetails");
		ArrayList<UserDetailsVO> details = null;
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			UserBO userbo = new UserBO(getConnection());
			details = userbo.getUserDetails(email);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return details;
	}

	public ArrayList<UserDetailsVO> getSearchedFriends(String friendName,
			String userEmail) throws SQLException {
		log.logInfo("In User Delegate------> getSearchedFriends");
		ArrayList<UserDetailsVO> friends = null;
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			UserBO userbo = new UserBO(getConnection());
			friends = userbo.getSearchedFriends(friendName, email);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return friends;
	}

	public void sendRequestToFriend(String friendName, String userEmail)
			throws SQLException {
		log.logInfo("In User Delegate------> sendRequestToFriend");
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			UserBO userbo = new UserBO(getConnection());
			userbo.sendRequestToFriend(friendName, email);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
	}

	public void acceptFriendRequest(String friendName, String userEmail)
			throws SQLException {
		log.logInfo("In User Delegate------>acceptFriendRequest");
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			UserBO userbo = new UserBO(getConnection());
			userbo.acceptFriendRequest(friendName, email);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
	}

	public void rejectFriendRequest(String friendName, String userEmail)
			throws SQLException {
		log.logInfo("In User Delegate------> rejectFriendRequest");
		boolean flag = false;
		String email = userEmail;
		Connection connection = null;
		try {

			connection = startDBTransaction();
			setConnection(connection);
			UserBO userbo = new UserBO(getConnection());
			userbo.rejectFriendRequest(friendName, email);

		} catch (Exception e) {
			e.printStackTrace();
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
	}
}
