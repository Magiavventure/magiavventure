package it.magiavventure.operation;

import it.magiavventure.model.user.BanUser;
import it.magiavventure.model.user.CreateUser;
import it.magiavventure.model.user.UpdateUser;
import it.magiavventure.mongo.model.User;
import it.magiavventure.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/v1")
public class UserOperation {

    private final UserService userService;

    @PostMapping("/saveUser")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid CreateUser createUser) {
        return userService.createUser(createUser);
    }

    @GetMapping("/retrieveUsers")
    public List<User> findAllUser() {
        return userService.findAll();
    }
    @GetMapping("/retrieveUser/{id}")
    public User findUser(@PathVariable(name = "id") UUID id) {
        return userService.findById(id);
    }

    @GetMapping("/checkUserName/{name}")
    public void checkName(@PathVariable(name = "name") String name) {
        userService.checkIfUserExists(name);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody @Valid UpdateUser updateUser) {
        return userService.updateUser(updateUser);
    }

    @DeleteMapping("/deleteUser/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "id") UUID id) {
        userService.deleteById(id);
    }

    @PutMapping("/banUser/{id}")
    public User banUser(@PathVariable(name="id") UUID id, @RequestBody @Valid BanUser banUser) {
        return userService.banUser(id, banUser);
    }

    @PutMapping("/elevateUser/{id}")
    public User elevateUser(@PathVariable(name = "id") UUID id) {
        return userService.giveAdminAuthorityToUser(id);
    }
}
