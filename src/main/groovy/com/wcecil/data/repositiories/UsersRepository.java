package com.wcecil.data.repositiories;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.wcecil.data.objects.User;

@Service
public class UsersRepository {
	private final static String USERS_REPO = "deck.users.repo";
	@Autowired
	MongoTemplate mongoTemplate;

	public List<User> listAll() {
		return mongoTemplate.findAll(User.class, USERS_REPO);
	}

	public User createNew(String userName, String password, String name) {
		if (name == null) {
			name = userName;
		}
		User p = new User();
		p.setLogin(userName);
		p.setPassword(password);
		p.setName(name);

		mongoTemplate.save(p, USERS_REPO);

		return p;
	}

	public List<User> auth(String userName, String password) {
		return mongoTemplate.find(query(where("login").is(userName)
				  .and("password").is(password)), User.class, USERS_REPO);
	}
}
