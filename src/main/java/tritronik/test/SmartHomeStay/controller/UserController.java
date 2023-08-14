package tritronik.test.SmartHomeStay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tritronik.test.SmartHomeStay.service.UserService;
import tritronik.test.SmartHomeStay.api.request.UserRequest;
import tritronik.test.SmartHomeStay.entity.User;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public List<User> GetUsers() {
        return userService.GetAllUsers();
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public User GetUsers(@RequestBody UserRequest user) {
        return userService.AddUser(user);
    }
}