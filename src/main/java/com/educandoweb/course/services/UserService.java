package com.educandoweb.course.services;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DataBaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User user) {
        return repository.save(user);
    }

    public void delete(Long id) {
        try {
            if (!repository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }
            repository.deleteById(id);
        }catch(DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

        public User update (Long id, User user){
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
            User entity = repository.getReferenceById(id);
            updateData(entity, user);
            return repository.save(entity);
        }

        private void updateData (User entity, User obj){
            entity.setName(obj.getName());
            entity.setEmail(obj.getEmail());
            entity.setPassword(obj.getPassword());
        }
    }
