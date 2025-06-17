package com.ecommerce.userservice.services;

import com.ecommerce.userservice.dtos.UserDto;
import com.ecommerce.userservice.models.Session;
import com.ecommerce.userservice.models.SessionStatus;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.repositories.SessionRepository;
import com.ecommerce.userservice.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Optional;

/**
 * Service class for handling authentication-related operations such as login, logout, sign-up, and token validation.
 *
 * @author mahip.bhatt
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Constructor for AuthService.
     *
     * @param userRepository         the user repository
     * @param sessionRepository      the session repository
     * @param bCryptPasswordEncoder  the password encoder
     */
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Handles user login by validating credentials and generating a session token.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return a ResponseEntity containing the UserDto and session token, or null if authentication fails
     */
    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty() || !bCryptPasswordEncoder.matches(password, userOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userOptional.get();
        String token = generateToken(user);

        Session session = createSession(user, token);
        sessionRepository.save(session);

        UserDto userDto = UserDto.from(user);
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }

    /**
     * Handles user logout by ending the session associated with the given token and user ID.
     *
     * @param token  the session token
     * @param userId the user's ID
     * @return a ResponseEntity indicating the logout status
     */
    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }

    /**
     * Handles user sign-up by creating a new user and saving it to the repository.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return a UserDto representing the newly created user
     */
    public UserDto signUp(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    /**
     * Validates a session token for a given user ID.
     *
     * @param token  the session token
     * @param userId the user's ID
     * @return the session status, or null if the session is invalid
     */
    public SessionStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }

        SecretKey key = generateSecretKey();
        Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        // Additional validation logic can be added here if needed
        return SessionStatus.ACTIVE;
    }

    /**
     * Generates a JWT token for the given user.
     *
     * @param user the user for whom the token is generated
     * @return the generated token
     */
    private String generateToken(User user) {
        String content = String.format("{\"userId\": %d, \"email\": \"%s\", \"roles\": [\"USER\", \"ADMIN\"], \"exp\": %d}",
                user.getId(), user.getEmail(), System.currentTimeMillis() / 1000 + 3600);
        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();
        return Jwts.builder().content(content).signWith(key, alg).compact();
    }

    /**
     * Creates a new session for the given user and token.
     *
     * @param user  the user
     * @param token the session token
     * @return the created session
     */
    private Session createSession(User user, String token) {
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        return session;
    }

    /**
     * Generates a secret key for signing JWT tokens.
     *
     * @return the generated secret key
     */
    private SecretKey generateSecretKey() {
        MacAlgorithm alg = Jwts.SIG.HS256;
        return alg.key().build();
    }
}