package com.alacriti.p2p.resteasy.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.alacriti.p2p.biz.delegate.UserDelegate;
import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.model.vo.UserDetailsVO;
import com.alacriti.p2p.util.LogUtil;
import com.alacriti.p2p.util.SessionUtility;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Path("/user")
public class UserResource {
	private static final AppLogger log = LogUtil.getLogger(UserResource.class);

	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void userSignIn(UserDetailsVO userDetailsVO,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("--> userSignUp method in UserResource class:: ");
		UserDelegate userDelegate = new UserDelegate();
		SessionUtility su = new SessionUtility();
		userDelegate.userSignIn(userDetailsVO);
		su.createSession(request, userDetailsVO);
	}

	/*
	 * @POST
	 * 
	 * @Path("/verifyIdToken")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.TEXT_PLAIN) public static void getTokenVerified
	 * (String idToken) throws InterruptedException, ExecutionException {
	 * 
	 * try { FileInputStream serviceAccount = new
	 * FileInputStream("/home/aniruddhab/Downloads/serviceAccount.json");
	 * FirebaseOptions options = new FirebaseOptions.Builder()
	 * .setCredentials(GoogleCredentials.fromStream(serviceAccount)) .build();
	 * FirebaseApp.initializeApp(options); FirebaseToken decodedToken =
	 * FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get(); String uid
	 * = decodedToken.getUid();
	 * System.out.println("Decoded ID token from user: " + uid); } catch
	 * (IOException e) {
	 * System.out.println("ERROR: invalid service account credentials. See README."
	 * ); System.out.println(e.getMessage()); //System.exit(1); }
	 * System.out.println("Done!"); }
	 */

	@POST
	@Path("/updateDetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveDetailsOfUser(UserDetailsVO userDetailsVO,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> saveDetailsOfUser method in UserResource class:: ");
		UserDelegate userDelegate = new UserDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		userDelegate.saveDetailsOfUser(userDetailsVO, email);
	}

	@GET
	@Path("/listOfFriends")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<UserDetailsVO> getListOfFriends(
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> getListOfFriends method in UserResource class: ");
		ArrayList<UserDetailsVO> friends = null;
		UserDelegate userdelegate = new UserDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		friends = userdelegate.getListOfFriends(email);
		return friends;
	}

	@GET
	@Path("/pendingRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<UserDetailsVO> getPendingRequests(
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> getPendingRequests method in UserResource class: ");
		ArrayList<UserDetailsVO> requests = null;
		UserDelegate userdelegate = new UserDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		requests = userdelegate.getPendingRequests(email);
		for (UserDetailsVO s : requests) {
			System.out.println("Sending from resouce...=>" + s.getUsername());
		}
		return requests;
	}

	@GET
	@Path("/details")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<UserDetailsVO> getUserDetails(
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> getUserDetails method in UserResource class: ");
		ArrayList<UserDetailsVO> details = null;
		UserDelegate userdelegate = new UserDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		details = userdelegate.getUserDetails(email);
		return details;
	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<UserDetailsVO> getSearchedFriends(String friendName,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> getSearchedFriends method in UserResource class: ");
		ArrayList<UserDetailsVO> friends = null;
		UserDelegate userdelegate = new UserDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		friends = userdelegate.getSearchedFriends(friendName, email);
		return friends;
	}

	@POST
	@Path("/sendRequest")
	@Produces(MediaType.APPLICATION_JSON)
	public void sendRequestToFriend(String friendName,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> sendRequestToFriend method is userResource class:: ");
		UserDelegate userDelegate = new UserDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		System.out.println("friend Name: " + friendName);
		userDelegate.sendRequestToFriend(friendName, email);
	}

	@POST
	@Path("/acceptRequest")
	@Produces(MediaType.APPLICATION_JSON)
	public void accpetFriendRequest(String friendName,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> acceptFriendRequest method is userResource class:: ");
		UserDelegate userDelegate = new UserDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		// System.out.println("friend Name: "+ friendName);
		userDelegate.acceptFriendRequest(friendName, email);
	}

	@POST
	@Path("/rejectRequest")
	@Produces(MediaType.APPLICATION_JSON)
	public void rejectFriendRequest(String friendName,
			@Context HttpServletRequest request) throws SQLException {
		log.logDebug("=---> rejectFriendRequest method is userResource class:: ");
		UserDelegate userDelegate = new UserDelegate();
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		String email = "";
		email = (String) session.getAttribute("email");
		// System.out.println("friend Name: "+ friendName);
		userDelegate.rejectFriendRequest(friendName, email);
	}

	@GET
	@Path("/checkSession")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean checkSession(@Context HttpServletRequest request) {
		log.logDebug("--> checkSession method in UserResource class:: ");
		SessionUtility su = new SessionUtility();
		HttpSession session = su.getSession(request);
		if (session != null) {
			return true;
		} else {
			return false;
		}
	}

	@GET
	@Path("/destroySession")
	@Produces(MediaType.APPLICATION_JSON)
	public String destroySession(@Context HttpServletRequest request) {
		log.logDebug("--> destroySession method in UserResource class:: ");
		SessionUtility sessionUtility = new SessionUtility();
		boolean session = sessionUtility.destroySession(request);
		if (session) {
			return "Session is Cleared";
		} else
			return "Session is Not Cleared";
	}

}
