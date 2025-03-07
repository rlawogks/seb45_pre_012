package pre012.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(String email, String password, String userName) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.findByuserName(userName).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이름입니다.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
       /* user.setpassword(password);*/
        user.setUserName(userName);

        return userRepository.save(user);
    }


    public User getUserByProviderAndProviderId(String provider, String providerId) {
        return userRepository.findBySocialProviderAndProviderId(provider, providerId).orElse(null);
    }

    public void setSocialProvider(User user, String provider, String providerId) {
        user.setSocialProvider(provider, providerId);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean authenticateUser(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public void deleteUserByEmail(String email) {
        User user = getUserByEmail(email);
        if(user != null) {
            userRepository.delete(user);
        }
    }
}
