package com.spring.maxgames.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.maxgames.AuthModel.Auth;
import com.spring.maxgames.AuthRepo.AuthRepo;
import com.spring.maxgames.DataModel.Data;
import com.spring.maxgames.DataRepo.DataRepo;

@Service
public class MaxService {
	@Autowired
	private AuthRepo aRepo;
	@Autowired
	private DataRepo dRepo;

//Login
	public String Loginx(String username, String password) {
		Auth userx = aRepo.findByusername(username);
		if (userx == null) {
			return "Invalid User !";
		} else {
			if (userx.getPassword().equals(password)) {
				return "Login Successful !";
			} else {
				return "Invalid Password";
			}
		}
	}

//SignUp
	public String SignUpx(Auth userx) {
		String username = userx.getUsername();
		Auth userxAuth = aRepo.findByusername(username);
		if (userxAuth == null) {
			aRepo.save(userx);
			return "Signup Successful !";
		} else {
			if (userxAuth.getUsername().equals(username)) {
				return "Username Already Exists";
			}
			else {
				return"Invalid Username";
			}
		}
	}

//Users
	public List<Auth> Users() {
		return aRepo.findAll();
	}

//List Games
	public List<Data> Games() {
		return dRepo.findAll();
	}

//View Game
	public Optional<Data> viewGame(Long id) {
		return dRepo.findById(id);
	}

//Add Game
	public Data addGame(Data gamex) {
		return dRepo.save(gamex);
	}

//Edit Game
	public Data editGame(Data gamex, Long id) {
		Data gamez = dRepo.findById(id).orElse(null);
		if (gamez != null) {
			gamez.setGamename(gamex.getGamename());
			gamez.setGamedeveloper(gamex.getGamedeveloper());
			gamez.setGameratings(gamex.getGameratings());
			gamez.setGameprice(gamex.getGameprice());
			gamez.setGametype(gamex.getGametype());
			gamez.setReleaseyear(gamex.getReleaseyear());
			gamez.setGamecover(gamex.getGamecover());
			gamez.setGamedesc(gamex.getGamedesc());
			gamez.setCoverurl1(gamex.getCoverurl1());
			gamez.setCoverurl2(gamex.getCoverurl2());
			gamez.setCoverurl3(gamex.getCoverurl3());

			return dRepo.saveAndFlush(gamez);
		} else {
			return null;
		}
	}

//Delete Game
	public String deleteGame(Long id) {
		dRepo.deleteById(id);
		return "deleted";
	}

//Sort Games (Ascending)
	public List<Data> sortGameA(String field) {
		return dRepo.findAll(Sort.by(field).ascending());
	}

// Sort Games (Descending)
	public List<Data> sortGameD(String field) {
		return dRepo.findAll(Sort.by(field).descending());
	}

// Pagination for Games
	public List<Data> pagingGames(int offset, int size) {
		Page<Data> xPage = dRepo.findAll(PageRequest.of(offset, size));
		return xPage.getContent();
	}

//	Pagination & Sorting Combined
	public List<Data> pagingsortingGames(int offset, int size, String field) {
		Page<Data> xPage = dRepo.findAll(PageRequest.of(offset, size, Sort.by(field).ascending()));
		return xPage.getContent();
	}
}