package com.mylibraryproject.controller;

import com.mylibraryproject.model.Author;
import com.mylibraryproject.service.AuthorService;
import com.mylibraryproject.service.LoginService;
import com.mylibraryproject.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ManagedBean
public class AuthorController implements Serializable {
    private static final long serialVersionUID = 7L;

    @ManagedProperty("#{loginService}")
    private LoginService loginService;
    @ManagedProperty("#{authorService}")
    private AuthorService authorService;

    private List<Author> authorList;
    private Author author = new Author();

    @PostConstruct
    public void loadAuthors() {
        if (UserService.user == null) {
            loginService.loginCheck();
        } else {
            authorList = authorService.findAllAuthors();
        }
    }

    public List<Author> findAllAuthors() {
        authorList = authorService.findAllAuthors();
        return authorList;
    }

    public Author getOneAuthor(Long id) {
        author = authorService.findAuthor(id);
        return author;
    }

    public void saveAuthor() {
        authorService.saveAuthor(author);
        author = new Author();
        authorList = authorService.findAllAuthors();

    }

    public void deleteAuthor(Author author) {
        if (author.getBooks().size() > 0) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "This author has books in database!", null));
        } else {
            authorService.deleteAuthor(author);
            authorList = authorService.findAllAuthors();
        }
    }

    public void clear() {
        author = new Author();
    }
}
