package com.example.configs;

import com.example.demo.network.DAL.UserRepository;
import com.example.demo.network.models.UserModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            UserModel daklin2 = new UserModel("daklin2", "1234", "daklin754@gmail.com", "Daniil", "Klimianok", "Belarus", "Minsk, ul. Lenina 48", "", "48698751488", 12);
            UserModel smertsss = new UserModel("smertsss", "12345678", "smertsss@gmail.com");

            repository.saveAll(
                    List.of(daklin2, smertsss)
            );
        };
    }
}
