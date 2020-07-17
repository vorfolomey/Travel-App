package com.mykolalehkyi.travelapp.dao;

import com.mykolalehkyi.travelapp.model.Authorities;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.mykolalehkyi.travelapp.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Repository
@Transactional
public class UserDetailsDaoImp implements UserDetailsDao {

  @Autowired
  private SessionFactory sessionFactory;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public User findUserByUsername(String username) {
    return sessionFactory.getCurrentSession().get(User.class, username);
  }

  @Override
  public void save(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setEnabled(true);
    Authorities authority= new Authorities();
    authority.setAuthority("ROLE_USER");
    authority.setUser(user);
    HashSet<Authorities> authorities = new HashSet<>();
    authorities.add(authority);
    user.setAuthorities(authorities);
    sessionFactory.getCurrentSession().save(user);
  }
}