package com.mylibraryproject.service;

import com.mylibraryproject.model.Author;
import com.mylibraryproject.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author findAuthor(Long id){
        return authorRepository.findById(id).orElse(null);
    }

    public void saveAuthor(Author author) {
        authorRepository.save(author);
        FacesContext.getCurrentInstance().addMessage
                (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Author saved successfully!", null));
    }

    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
        FacesContext.getCurrentInstance().addMessage
                (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Author deleted successfully!", null));
    }
}