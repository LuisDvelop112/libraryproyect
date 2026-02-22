package LibraryProyect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import LibraryProyect.repositories.UserRepository;

@RestController
public class DbTestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/db/test")
    public String testDb() {
        long count = userRepository.count();
        return "✅ BD funciona! Usuarios: " + count;
    }
}
