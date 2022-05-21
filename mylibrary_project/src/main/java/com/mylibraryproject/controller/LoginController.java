package com.mylibraryproject.controller;

import com.mylibraryproject.service.LoginService;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@Getter
@Setter
@ManagedBean
public class LoginController {
    private String username;
    private String password;

    @ManagedProperty("#{loginService}")
    private LoginService loginService;

    public void registerUser() {
        try {
            loginService.register(username, password);
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Login completed successfully!", null));
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Unknown error!", null));
        }
    }

    public void loginUser() {
        try {
            loginService.login(username, password);
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Login completed successfully!", null));
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/books.xhtml");
        } catch (IllegalArgumentException illegalArgumentException) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Incorrect password!", null));
        } catch (NotFoundException notFoundException) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "User does not exist!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Unknown error!", null));
        }
    }

    public void logoutUser() {
        try {
            loginService.logout();
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Logout completed successfully!", null));
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
