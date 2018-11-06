package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user){

        User createdUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();
        System.out.println("Created: "+location);

    }

    public void saveOrUpdate(User user, int id){
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            System.out.println("No user found with id: "+id);
            return;
        }

        user.setId(id);
        userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build(user);
        System.out.println("Updated: "+location);
    }

    public Iterable<User> list(){
        return userRepository.findAll();
    }


    public User getUserById(int id)throws NoSuchElementException {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent())    {
            System.out.println(userOptional.get().toString());
            return userOptional.get();
        }

        return null;
    }


    public void delete(int id){
        try{
            User user = userRepository.getOne(id);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().build(user);
            System.out.println("Deleted: "+location);
            userRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e){
            System.out.println("No user found with id:"+id);
        }
    }


    public void saveAll(List<User> users) {
        userRepository.saveAll(users);

    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
