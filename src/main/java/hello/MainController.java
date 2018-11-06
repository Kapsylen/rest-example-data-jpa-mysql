package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired
    private UserService userService;


    @GetMapping(path="/getAll")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.list();
    }

    @PostMapping(path="/add")
    public void add (@RequestBody User user) {
        userService.save(user);
    }

    @PostMapping(path="/addAll")
    public void addAll (@RequestBody List<User> users) {
        userService.saveAll(users);
    }


    @GetMapping(path="/{id}")
    public User getById(@PathVariable int id) {
       return userService.getUserById(id);
    }



    @DeleteMapping(path="/{id}")
    public void deleteById(@PathVariable int id) {
        userService.delete(id);
    }


    @PutMapping(path="/{id}")
    public void update (@RequestBody User user, @PathVariable int id) {
        userService.saveOrUpdate(user, id);
    }

    @DeleteMapping(path="/deleteAll")
    public void deleteAll(){
        userService.deleteAll();

    }
}