package com.example.auth;

import com.example.domain.user.User;
import com.example.domain.user.UserDaoSql;
import com.example.domain.user.TaskUserRole;
import org.jasypt.util.password.BasicPasswordEncryptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class BasicLoginModule implements LoginModule {

    private CallbackHandler handler;
    private Subject subject;
    private User user;
    private TaskUserRole role;
    private List<String> userGroups;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        handler = callbackHandler;
        this.subject = subject;
    }

    @Override
    public boolean login()
    throws LoginException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", true);

        try {
            handler.handle(callbacks);
            NameCallback nameCallback = (NameCallback) callbacks[0];
            PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];

            String name = nameCallback.getName();
            String password = String.valueOf(passwordCallback.getPassword());

            // Validate the credentials against the database
            if (name != null && password != null) {
                // Store the username
                user = UserDaoSql.getInstance().restoreForName(name);
                if (user != null) {
                    BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
                    if (encryptor.checkPassword(password, user.getPassword())) {
                        userGroups = new ArrayList<String>();
                        userGroups.add("admin");
                        return true;
                    }
                }
            }

            // If credentials are NOT OK we throw a LoginException
            throw new LoginException("Authentication failed");

        }
        catch (IOException e) {
            throw new LoginException(e.getMessage());
        }
        catch (UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        }

    }

    @Override
    public boolean commit()
    throws LoginException {
        subject.getPrincipals().add(user);

        if (userGroups != null && userGroups.size() > 0) {
            for (String groupName : userGroups) {
                role = new TaskUserRole(groupName);
                subject.getPrincipals().add(role);
            }
        }

        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(user);
        subject.getPrincipals().remove(role);
        return true;
    }

}
