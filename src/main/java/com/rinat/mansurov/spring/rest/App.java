package com.rinat.mansurov.spring.rest;

import com.rinat.mansurov.spring.rest.config.MyConfig;
import com.rinat.mansurov.spring.rest.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);
        List<User> allUsers = communication.getAllUsers();
        System.out.println(allUsers);
//
//        User userId = communication.getUser(2L);
//        System.out.println(userId);

        User user1 = new User(3,"James", "Brown", (byte)36);
        communication.addUser(user1);

        communication.editUser(new User(3,"Thomas", "Shelby", (byte)37));
        communication.deleteUser(3);


    }
}
