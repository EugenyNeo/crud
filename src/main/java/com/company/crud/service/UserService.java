package com.company.crud.service;

import com.company.crud.domain.User;
import com.company.crud.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private boolean existsById(Long id) {
        return userRepo.existsById(id);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public List<User> findAll(int pageNumber, int rowPerPage) {
        List<User> users = new ArrayList<>();
        Pageable sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());
        userRepo.findAll(sortedByLastUpdateDesc).forEach(users::add);
        return users;
    }

    public User save(User user) throws Exception {
        if (StringUtils.isEmpty(user.getPersonId())) {
            throw new Exception("INN is required");
        }
        if (StringUtils.isEmpty(user.getFirstName())) {
            throw new Exception("First Name is required");
        }
        if (StringUtils.isEmpty(user.getLastName())) {
            throw new Exception("Last Name is required");
        }
        if (StringUtils.isEmpty(user.getDateOfBirth())) {
            throw new Exception("Date of Birth is required");
        }
        if (StringUtils.isEmpty(user.getFamilyStatus())) {
            throw new Exception("Family status is required");
        }
        if (StringUtils.isEmpty(user.getEducation())) {
            throw new Exception("Education is required");
        }
        if (user.getId() != null && existsById(user.getId())) {
            throw new Exception("User with id: " + user.getId() + " already exists");
        }
        return userRepo.save(user);
    }

    public void update(User user) throws Exception {
        if (StringUtils.isEmpty(user.getPersonId())) {
            throw new Exception("INN is required");
        }
        if (StringUtils.isEmpty(user.getFirstName())) {
            throw new Exception("First Name is required");
        }
        if (StringUtils.isEmpty(user.getLastName())) {
            throw new Exception("Last Name is required");
        }

        if (StringUtils.isEmpty(user.getDateOfBirth())) {
            throw new Exception("Date of Birth is required");
        }
        if (StringUtils.isEmpty(user.getFamilyStatus())) {
            throw new Exception("Family status is required");
        }
        if (StringUtils.isEmpty(user.getEducation())) {
            throw new Exception("Education is required");
        }
        if (!existsById(user.getId())) {
            throw new Exception("Cannot find User with id: " + user.getId());
        }
        userRepo.save(user);
    }

    public void deleteById(Long id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find User with id: " + id);
        }
        else {
            userRepo.deleteById(id);
        }
    }

    public Long count() {
        return userRepo.count();
    }
}