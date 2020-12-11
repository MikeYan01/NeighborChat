package com.hypertars.neighborChat.service.UserAccount.impl;

import com.hypertars.neighborChat.dao.*;
import com.hypertars.neighborChat.enums.NBCResultCodeEnum;
import com.hypertars.neighborChat.exception.NBCException;
import com.hypertars.neighborChat.model.Users;
import com.hypertars.neighborChat.service.UserAccount.UserAccountService;
import com.hypertars.neighborChat.utils.AssertUtils;
import com.hypertars.neighborChat.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Resource
    private BlockApplicationDAO blockApplicationDAO;

    @Resource
    private BlocksDAO blocksDAO;

    @Resource
    private FriendApplicationDAO friendApplicationDAO;

    @Resource
    private FriendsDAO friendsDAO;

    @Resource
    private HoodsDAO hoodsDAO;

    @Resource
    private MailBoxDAO mailBoxDAO;

    @Resource
    private MessageDAO messageDAO;

    @Resource
    private NeighborsDAO neighborsDAO;

    @Resource
    private RecipientDAO recipientDAO;

    @Resource
    private ReplyDAO replyDAO;

    @Resource
    private UserBlockDAO userBlockDAO;

    @Resource
    private UsersDAO usersDAO;

    /** users session map */
    private ConcurrentHashMap<String, Users> userMap = new ConcurrentHashMap<>();
    private int maxLoginUsers = 100;

    /**
     * check User Name exist, return true if available (not used)
     * @param UName user name
     * @return true: available, false: already used
     */
    @Override
    public boolean checkUName(String UName) {
        return usersDAO.checkUName(UName) == 0;
    }

    /**
     * user registration
     * @param uname user name
     * @param passwd pass word
     * @param email email
     * @param fName first name
     * @param lName last name
     * @return true or false
     */
    @Override
    public boolean userReg (String uname, String passwd, String email, String fName, String lName) {
        if (!checkUName(uname)) return false;
        Users user = new Users();
        user.setUname(uname);
        user.setPasswd(passwd);
        user.setEmail(email);
        user.setfName(fName);
        user.setlName(lName);
        if (!(StringUtils.isNotEmpty(user.getUname()) &&
                StringUtils.isNotEmpty(user.getPasswd()) &&
                StringUtils.isNotEmpty(user.getfName()) &&
                StringUtils.isNotEmpty(user.getlName()))) {
            throw new NBCException("invalid user info", NBCResultCodeEnum.INVALID_DATA);
        }
        usersDAO.userReg(user);
        return true;
    }

    @Override
    public String loginIn(Users user) {
        // 1. check pass
        AssertUtils.assertNotNull(user);
        Users userSelected = usersDAO.getUserByUName(user.getUname());
        AssertUtils.assertNotNull(userSelected);
        String session = getRandomString(30);

        // 2. insert into map
        if (userMap.size() >= maxLoginUsers) {
            String[] keySet = userMap.keySet().toArray(new String[0]);
            String expireKey = keySet[0];
            userMap.remove(expireKey);
        }
        userMap.put(session, userSelected);

        return session;
    }

    @Override
    public Users getUserBySession(String session) {
        return userMap.get(session);
    }

    @Override
    public void cleanUserSession() {
        userMap.clear();
    }

    /**
     * return whole user information based on uname and email, further processing
     * @param uname uname
     * @param email email
     * @return users
     */
    @Override
    public Users userAuth(String uname, String email) {
        Users user = new Users();
        user.setUname(uname);
        user.setEmail(email);
        return usersDAO.userAuth(user);
    }

    /**
     * reset password
     * @param uid user id
     * @param passwd new pass word
     * @return true or false
     */
    @Override
    public boolean resetPassword(int uid, String passwd) {
        Users user = new Users();
        user.setUid(uid);
        user.setPasswd(passwd);
        usersDAO.resetPasswd(user);
        return true;
    }

    @Override
    public boolean updateUserInfo(int uid, String uname, String passwd, String email, String fName, String lName, String addr1,
                                  String addr2, String intro, String photo, short nRange, boolean notify) {
        Users user = new Users();
        user.setUid(uid);
        user.setUname(uname);
        user.setPasswd(passwd);
        user.setEmail(email);
        user.setfName(fName);
        user.setlName(lName);
        user.setAddr1(addr1);
        user.setAddr2(addr2);
        user.setIntro(intro);
        user.setPhoto(photo);
        user.setnRange(nRange);
        user.setNotify(notify);
        usersDAO.updateInfo(user);
        return true;
    }

    /**
     * get who user info by uid
     * @param uid user id
     * @return user
     */
    @Override
    public Users getUserByUid(int uid) {
        return usersDAO.getUserByUid(uid);
    }

    @Override
    public boolean updateLastLog(int uid) {
        usersDAO.updateLastLog(uid);
        return true;
    }

    /**
     * get random string
     * @param length len
     * @return random string
     */
    private static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char) result));
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append(String.valueOf((char) result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
                default: break;
            }
        }
        return sb.toString();
    }

}
