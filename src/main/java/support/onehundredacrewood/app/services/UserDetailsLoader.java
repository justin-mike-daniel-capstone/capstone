package support.onehundredacrewood.app.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import support.onehundredacrewood.app.dao.User;
import support.onehundredacrewood.app.dao.UserWithRoles;
import support.onehundredacrewood.app.repositories.UserRepo;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepo userRepo;

    public UserDetailsLoader(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(login);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + login);
        }

        return new UserWithRoles(user);
    }
}
