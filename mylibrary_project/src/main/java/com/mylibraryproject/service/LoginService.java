package com.mylibraryproject.service;

import com.mylibraryproject.model.Users;
import com.mylibraryproject.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public void register(String username, String password) {
        try {
            Users user = new Users();
            user.setUsername(username);
            user.setPassword(password);
            userRepository.save(user);
            System.out.println("User created " + user);
            /*ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String path = externalContext.getRequestContextPath();
            path += "/login.xhtml";
            externalContext.redirect(externalContext.getRequestContextPath() + path);*/
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void login(String username, String password) throws NotFoundException {
        Optional<Users> optionalUser = userRepository.findByUserName(username);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("User doesn't exist");
        } else if (!Objects.equals(password, optionalUser.get().getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        } else {
            UserService.user = optionalUser.get();
        }
    }

    public void loginCheck() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String path = externalContext.getRequestContextPath();
            path += UserService.user == null ? "/login.xhtml" : "/books.xhtml";
            externalContext.redirect(externalContext.getRequestContextPath() + path);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        UserService.user = null;
    }
}
