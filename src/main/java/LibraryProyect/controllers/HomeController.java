package LibraryProyect.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        System.out.println("Accediendo al Home...");
        return "API LibraryProyect está arriba 🚀";
    }
}
