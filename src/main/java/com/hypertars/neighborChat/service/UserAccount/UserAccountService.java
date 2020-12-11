package com.hypertars.neighborChat.service.UserAccount;

import com.hypertars.neighborChat.model.Users;

public interface UserAccountService {

    /** register */
    boolean checkUName (String UName);
    boolean userReg (String uname, String passwd, String email, String fName, String lName);

    /** login */
    /**
     * login in
     * @param user attr: uid, pass needed
     */
    String loginIn(Users user);

    /**
     * get user by session value
     * @param session session
     * @return user
     */
    Users getUserBySession(String session);
    void cleanUserSession();

    /** forget password */
    Users userAuth (String uname, String email);
    boolean resetPassword (int uid, String passwd);

    /** update user info */
    boolean updateUserInfo (int uid, String uname, String passwd, String email, String fName, String lName, String addr1,
                            String addr2, String intro, String photo, short nRange, boolean notify);

    /** load info */
    Users getUserByUid (int uid);

    boolean updateLastLog (int uid);
}