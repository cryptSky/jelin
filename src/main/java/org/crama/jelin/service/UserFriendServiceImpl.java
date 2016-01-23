package org.crama.jelin.service;

import java.util.Set;

import org.crama.jelin.model.User;
import org.crama.jelin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userFriendService")
public class UserFriendServiceImpl implements UserFriendService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Set<User> getUserFriends(User user) {
		Set<User> userFriends = user.getFriendList();
		return userFriends;
	}

	@Override
	public boolean addFriend(User user, User friend) {
		Set<User> friendList = user.getFriendList();
		boolean added = friendList.add(friend);
		if (added) {
			userRepository.updateUser(user);
		}
		return added;
	}

	@Override
	public boolean removeFriend(User user, User friend) {
		Set<User> friendList = user.getFriendList();
		boolean removed = friendList.remove(friend);
		if (removed) {
			userRepository.updateUser(user);
		}
		return removed;
	}

}
