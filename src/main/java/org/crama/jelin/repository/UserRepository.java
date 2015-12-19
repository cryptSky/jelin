package org.crama.jelin.repository;

import org.crama.jelin.model.UserModel;

public interface UserRepository {

	UserModel getUserModel(String username);

}
