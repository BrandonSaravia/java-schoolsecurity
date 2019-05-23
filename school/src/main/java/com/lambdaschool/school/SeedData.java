package com.lambdaschool.school;

import com.lambdaschool.school.model.*;
import com.lambdaschool.school.repository.RoleRepository;
import com.lambdaschool.school.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    private RoleRepository rolerepos;
    private UserRepository userrepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos)
    {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
    }

    @Override
    public void run(String[] args) throws Exception
    {

        Role role1 = new Role("user");

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), role1));

        rolerepos.save(role1);

        User u2 = new User("user", "password", users);

        userrepos.save(u2);

    }
}
