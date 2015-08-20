package com.wcecil.data.repositiories;

import static org.springframework.data.mongodb.core.query.Criteria.*
import static org.springframework.data.mongodb.core.query.Query.*
import groovy.transform.CompileStatic;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

import com.wcecil.data.objects.User
import com.wcecil.data.objects.UserToken

@Service
@CompileStatic
public class UsersRepository {
	private final static String USERS_REPO = "deck.users.repo";
	private final static String TOKENS_REPO = "deck.users.tokens";

	@Autowired
	MongoTemplate mongoTemplate;

	boolean mask = true;

	public List<User> listAll() {
		def result = mongoTemplate.findAll(User.class, USERS_REPO);

		mask(result)

		result
	}

	public List<User> mask(List<User> users){
		users.each{mask it}
		
		users
	}

	public User mask(User user){
		if(user==null){
			return null
		}
		if(mask){
			user.login=null
			user.password=null
		}

		user
	}

	public User createNew(String userName, String password, String name) {
		def sameUserName = mongoTemplate.find(query(where("login").is(userName)), User.class, USERS_REPO);
		if(sameUserName){
			throw new IllegalStateException("User with username '$userName' already exists");
		}

		if (name == null) {
			name = userName;
		}
		User p = new User();
		p.setLogin(userName);
		p.setPassword(password);
		p.setName(name);

		mongoTemplate.save(p, USERS_REPO);

		mask(p)
	}

	public User auth(String userName, String password) {
		def mongoTemplateFindOne = mongoTemplate.findOne(query(where("login").is(userName)
				.and("password").is(password)), User.class, USERS_REPO)
		
		if(mongoTemplateFindOne)
			return mask(mongoTemplateFindOne);
		else
			return null;
	}

	public void saveUser(User u){
		mongoTemplate.save(u, USERS_REPO);
	}

	public User addGameToUser(String userId, String gameId){
		User user = getUserById(userId);
		if(user){
			if(!user.games){
				user.games = []
			}

			user.games << gameId

			saveUser(user);
		}

		user
	}

	private User getUserById(String userId) {
		User user = mongoTemplate.findById(userId, User.class, USERS_REPO)
		return user
	}

	public User getUser(String userId) {
		return mask(getUserById(userId))
	}

	public UserToken createTokenForUserId(String userId){
		UserToken token = mongoTemplate.findOne(query(where('userId').is(userId)), UserToken.class, TOKENS_REPO)

		if(!token){
			token = new UserToken(userId:userId)

			mongoTemplate.save(token, TOKENS_REPO)
		}

		return token
	}

	public UserToken getUserTokenForTokenId(String token){
		UserToken myToken = mongoTemplate.findById(token, UserToken.class, TOKENS_REPO)
		return myToken
	}
}
