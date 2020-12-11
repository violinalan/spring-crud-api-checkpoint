package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<User> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public User create(@RequestBody User user) {
        return this.repository.save(user);
    }

    @GetMapping("/{id}")
    public Optional<User> get(@PathVariable long id) {
        return this.repository.findById(id);
    }

    @PatchMapping("/{id}")
    public User patch(@PathVariable long id, @RequestBody User user) {
        User userToUpdate = this.repository.findById(id).get();
        userToUpdate.setEmail(user.getEmail());
        if(user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
        }
        this.repository.save(userToUpdate);
        return userToUpdate;
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String delete(@PathVariable long id) {
        this.repository.deleteById(id);
        return "{\"count\": " + this.repository.getCount() + "}";
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authenticate(@RequestBody User user) {
        User userToCheck = this.repository.findByEmail(user.getEmail());
        if(userToCheck.getPassword().equals(user.getPassword())) {
            return "{\"authenticated\": true,\"user\": {\"id\": " + userToCheck.getId() + ",\"email\": \"" + userToCheck.getEmail() + "\"}}";
        }
        else {
            //System.out.println("userToCheck " + userToCheck.getPassword() + " userInput " + user.getPassword());
            return "{\"authenticated\": false}";
        }
    }
}
