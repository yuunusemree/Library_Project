package com.mylibraryproject.service;

import javax.annotation.PostConstruct;

import com.mylibraryproject.repository.AuthorRepository;
import com.mylibraryproject.repository.BookRepository;
import com.mylibraryproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitDbService {

	@PostConstruct
	public void init() {
		System.out.println("*** INIT DATABASE START ***");

		System.out.println("*** INIT DATABASE FINISH ***");
	}
}
