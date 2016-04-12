package com.example.auth;

import javax.security.auth.callback.*;
import javax.servlet.ServletRequest;
import java.io.IOException;

public class WebCallbackHandler implements CallbackHandler {

    private String userName;
    private String password;

    public WebCallbackHandler(ServletRequest request){
        userName = request.getParameter("username");
        password = request.getParameter("password");

    }

    public void handle(Callback[] callbacks)
    throws IOException, UnsupportedCallbackException {
        //Add the username and password from the request parameters to
        //the Callbacks
        for (Callback callback : callbacks){
            if (callback instanceof NameCallback){
                NameCallback nameCallback = (NameCallback) callback;
                nameCallback.setName(userName);

            }
            else if (callback instanceof PasswordCallback){
                PasswordCallback passCallback = (PasswordCallback) callback;
                passCallback.setPassword(password.toCharArray());

            }
            else {
                throw new UnsupportedCallbackException (callback, "Unrecognized callbacks in class " + getClass().getName());
            }
        }
    }
}
