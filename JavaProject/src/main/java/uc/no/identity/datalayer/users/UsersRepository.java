package uc.no.identity.datalayer.users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class UsersRepository implements IUsersRepository {

    private static UsersDto[] users;
    
    public UsersDto[] getUsers() {
        return users;
    }

    public void setUsers(UsersDto[] users) {
        UsersRepository.users = users;
    }

    public UsersRepository() {
        loadUsers();
        System.out.println("UsersRepository constructor");
    }

    public static String readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String nextLine = "";
        StringBuffer sb = new StringBuffer();
        while ((nextLine = br.readLine()) != null) {
            sb.append(nextLine);
        }
        // remove newlines
        String newString = sb.toString().replace('\n', ' ');

        return newString;
    }

    public void loadUsers() {
   
        String USERS = "./database/aspnetUsers.json";
          
        try {
            String jsonUsersArray = readFile(USERS);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        
            users = objectMapper.readValue(jsonUsersArray, UsersDto[].class);

            for (UsersDto usersDto : users) {
                System.out.println(usersDto.getFullName());
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public UsersDto getUserByEmail (String email) {    
        return Stream.of(users)
            .filter(p -> p.getUserName().equals(email))
            .findFirst()
            .orElse(null);
    }

    @Override
    public UsersDto saveUser(UsersDto user) {
        user.setId(UUID.randomUUID().toString());
        UsersDto[] newUserList = Arrays.copyOf(users, users.length + 1);    
        newUserList[newUserList.length-1] = user;
        return user;
    }

} 