package com.mylibraryproject.util;

import com.mylibraryproject.model.Author;
import com.mylibraryproject.model.Users;
import com.mylibraryproject.service.AuthorService;
import com.mylibraryproject.service.UserService;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import javax.inject.Inject;

@Component(value = "authorConverter")
public class AuthorConverter implements Converter {

    @Inject
    private AuthorService authorService;

    @Inject
    private UserService userService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                switch(component.getId()) {
                    case "users":
                        return userService.getUser(Long.parseLong(value));
                    default:
                        return authorService.findAuthor(Long.parseLong(value));
                }

            }
            catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid author."));
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            switch(component.getId()) {
                case "users":
                    return String.valueOf(((Users) value).getId());
                default:
                    return String.valueOf(((Author) value).getId());
            }
        }
        else {
            return null;
        }
    }
}
