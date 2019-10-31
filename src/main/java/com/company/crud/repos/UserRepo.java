package com.company.crud.repos;

import com.company.crud.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {
    List<User> findByPersonId(String personId);
    void removeUserById(int id);

}
