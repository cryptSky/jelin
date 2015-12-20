package org.crama.jelin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.crama.jelin.model.UserModel;
import org.crama.jelin.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    private UserService userService;
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = userService.getUserModel(username);

		return buildUserFromUserEntity(user);
        
	}
    @Transactional(readOnly = true)
    User buildUserFromUserEntity(UserModel userEntity) {

      String username = userEntity.getUsername();
      String password = userEntity.getPassword();

      Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      
      authorities.add(new SimpleGrantedAuthority(userEntity.getRole().getRole()));
      
      /*Set<UserRole> roles = userEntity.getRoles();
      for (UserRole role: roles) {
    	  authorities.add(new SimpleGrantedAuthority(role.getRole()));
      }*/
      
      User user = new User(username, password, true, true, true, true, authorities);
      return user;
    }
}
