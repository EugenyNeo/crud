package com.company.crud.repos;

import com.company.crud.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository <User, Long>, PagingAndSortingRepository <User, Long>, JpaSpecificationExecutor<User> {
    List<User> findByFirstName(String firstName);
    List<User> findByPersonId(String personId);


}
