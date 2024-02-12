package uc.no.identity.business;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import uc.no.identity.datalayer.users.UsersDto;

public class UserAdmin {

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

        String USERS = "c:\\Source\\FagGruppe2024\\dotnetIdentity\\JavaIdentiy\\database\\aspnetUsers.json";

        try {
            String jsonUsersArray = readFile(USERS);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            UsersDto[] users = objectMapper.readValue(jsonUsersArray, UsersDto[].class);

            for (UsersDto usersDto : users) {
                System.out.println(usersDto.getFullName());
            }

            Field[] fields = UsersDto.class.getFields();
            for (Field field : fields) {
                System.out.println(field.toString());
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
