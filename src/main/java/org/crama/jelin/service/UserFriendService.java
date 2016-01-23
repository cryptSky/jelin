package org.crama.jelin.service;

import java.util.Set;

import org.crama.jelin.model.User;

public interface UserFriendService {

	Set<User> getUserFriends(User user);

	boolean addFriend(User user, User friendObj);

	boolean removeFriend(User user, User friendObj);

}
