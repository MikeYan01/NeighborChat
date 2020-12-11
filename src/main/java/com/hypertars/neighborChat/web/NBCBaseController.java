package com.hypertars.neighborChat.web;

import com.hypertars.neighborChat.enums.NBCResultCodeEnum;
import com.hypertars.neighborChat.exception.NBCException;
import com.hypertars.neighborChat.model.Users;
import com.hypertars.neighborChat.service.UserAccount.UserAccountService;
import com.hypertars.neighborChat.utils.AssertUtils;
import com.hypertars.neighborChat.utils.StringUtils;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class NBCBaseController {

    @Resource
    private UserAccountService useraccountService;
    /** 登录用户 */
    protected ThreadLocal<Users> loginUsers = new ThreadLocal<>();

    /** cookie user */
    private static String USER_COOKIE = "userSession";

    /**
     * 基础controller
     * @param request 请求
     * @param modelMap 响应属性
     * @param logicCallBack 处理逻辑
     * @param params 扩展参数
     */
    final protected com.hypertars.neighborChat.web.NBCResult<Object> protectController(HttpServletRequest request, ModelMap modelMap,
                                                                                       com.hypertars.neighborChat.web.NBCLogicCallBack logicCallBack, String... params) {
        com.hypertars.neighborChat.web.NBCResult<Object> result = new com.hypertars.neighborChat.web.NBCResult<>();
        try {
            // 1. 前置操作
            verifyLogin(request);
            // 2. 核心处理逻辑
            result = logicCallBack.execute();
            // 3. 回收treadLocal
            loginUsers.remove();
        } catch (NBCException ge) {
            result.setSuccess(false);
            result.setResultCode(ge.getErrorCode().getCode());
            result.setResultDesc(ge.getMessage());
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultCode(NBCResultCodeEnum.SYSTEM_ERROR.getCode());
            result.setResultDesc(NBCResultCodeEnum.SYSTEM_ERROR.getDescription());
            return result;
        }
        result.setSuccess(true);
        result.setResultCode(NBCResultCodeEnum.SUCCESS.getCode());
        result.setResultDesc(NBCResultCodeEnum.SUCCESS.getDescription());
        return result;
    }

    /**
     * 用户鉴权
     * @param request 用户登录检测
     */
    private void verifyLogin(HttpServletRequest request) {
        try {
            // retrieve Cookie in request header
            String session = "";
            for (Cookie cookie: request.getCookies()) {
                if (StringUtils.equal(USER_COOKIE, cookie.getName())) {
                    session = cookie.getValue();
                    break;
                }
            }
            AssertUtils.stringNotEmpty(session);
            // retrieve user in session
            Users user = useraccountService.getUserBySession(session);
            AssertUtils.assertNotNull(user);
            loginUsers.set(user);
        } catch (Exception e) {
            throw new NBCException("User not logged in，redirect to log in page...", NBCResultCodeEnum.USER_NOT_LOGIN_IN);
        }
    }

}
