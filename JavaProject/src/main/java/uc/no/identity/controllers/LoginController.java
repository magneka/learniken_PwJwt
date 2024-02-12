package uc.no.identity.controllers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jwt.JWTClaimsSet;

import uc.no.identity.aspects.LoginIsRequired;
import uc.no.identity.business.UserAdmin;
import uc.no.identity.datalayer.travel.ITravelRepository;
import uc.no.identity.datalayer.travel.TravelDto;
import uc.no.identity.datalayer.users.IUsersRepository;
import uc.no.identity.datalayer.users.UsersDto;
import uc.no.identity.jwtTokenService.IJwtTokenService;
import uc.no.identity.msIdentity.MsIdentityService;

@RestController
public class LoginController {

    private IUsersRepository usersRepository;
    private ITravelRepository travelRepository;
    private IJwtTokenService jwtTokenService;
    // private MsIdentityService msIdentityService;

    public LoginController(IUsersRepository usersRepository, ITravelRepository travelRepository,
            IJwtTokenService jwtTokenService
    // MsIdentityService msIdentityService
    ) {
        this.usersRepository = usersRepository;
        this.travelRepository = travelRepository;
        this.jwtTokenService = jwtTokenService;
        // this.msIdentityService = msIdentityService;
        System.out.println("LoginController constructor");
    }

    @GetMapping("/hello")
    public String hello(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.loadUsers();

        return "Hello from the other side..";
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterModel registerModel) {
        System.out.println(registerModel.toString());

        // Hash the password
        String passwordHash = MsIdentityService.newUserPassword(registerModel.getPassword());

        // Create a user with params and password
        var newUser = new UsersDto();
        newUser.setUserName(registerModel.getEmail());
        newUser.setFullName(registerModel.getName());
        newUser.setPasswordHash(passwordHash);

        // Save user, hide passworhash before returning userobject
        var newUSerWithId = usersRepository.saveUser(newUser);
        newUSerWithId.setPasswordHash("");

        ResponseEntity<Object> response = new ResponseEntity<Object>(newUSerWithId, HttpStatus.OK);        
        return response;
    }

    @LoginIsRequired()
    @PostMapping(path = "/travels", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ArrayList<TravelDto>> getTravels(
            @RequestHeader Map<String, String> headers,
            @RequestHeader("Authorization") String authTokenHeader,
            @RequestBody TravelModel travelModel) {

        String token = authTokenHeader.replace("Bearer ", "");
        JWTClaimsSet claimsSet = jwtTokenService.parseJwt(token);

        String userId = claimsSet.getJWTID();

        ArrayList<TravelDto> travels = travelRepository.getTravelsForUser(userId, travelModel.getDestinasjon());
        ResponseEntity<ArrayList<TravelDto>> response = new ResponseEntity<ArrayList<TravelDto>>(travels,
                HttpStatus.OK);
        
        return response;
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> post_Login(
            @RequestHeader Map<String, String> headers,
            @RequestBody LoginModel loginModel) {
        boolean loginOk = false;
        System.out.println(loginModel.getEmail());
        System.out.println(loginModel.getPassword());

        UsersDto loginuser = usersRepository.getUserByEmail(loginModel.getEmail());
        if (loginuser != null)
            loginOk = MsIdentityService.validatePassword(loginModel.getPassword(), loginuser.getPasswordHash());

        if (loginOk) {

            Map<String, Object> claims = new HashMap<String, Object>(
                    Map.ofEntries(
                            new AbstractMap.SimpleEntry<String, Object>("Id", loginuser.getId()),
                            new AbstractMap.SimpleEntry<String, Object>("UserName", loginuser.getUserName()),
                            new AbstractMap.SimpleEntry<String, Object>("FullName", loginuser.getFullName()),
                            new AbstractMap.SimpleEntry<String, Object>("Role", "User"),
                            new AbstractMap.SimpleEntry<String, Object>("Client1", "123123"),
                            new AbstractMap.SimpleEntry<String, Object>("Client2", "456456")));

            var token = jwtTokenService.generateToken(claims, "Learniken");

            Map<String, String> data = new HashMap<>();
            data.put("token", token);

            ResponseEntity<Object> response = new ResponseEntity<Object>(data, HttpStatus.OK);

            return response;

        }

        ResponseEntity<Object> errorResponse = new ResponseEntity<Object>("Ugyldig epostadresse eller passord.",
                HttpStatus.NOT_FOUND);
        return errorResponse;

    }
}
