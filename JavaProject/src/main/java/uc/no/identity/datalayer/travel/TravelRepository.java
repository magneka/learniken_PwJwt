package uc.no.identity.datalayer.travel;

import static java.util.Arrays.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import uc.no.identity.datalayer.users.UsersDto;
import uc.no.identity.jwtTokenService.JwtTokenService;

@Component
public class TravelRepository implements ITravelRepository {

    private static TravelDto[] travels;    
    public TravelDto[] getTravels() {
        return travels;
    }

    public TravelRepository() {
        loadTravels();
        System.out.println("TravelRepository constructor");
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

    public void loadTravels() {
   
        //String TRAVELS = "c:\\Source\\FagGruppe2024\\dotnetIdentity\\JavaIdentiy\\database\\travels.json";
        //TRAVELS = "/Users/magnealvheim/Source/faggruppe_24/JavaIdentiy/database/travels.json";
        String TRAVELS = "./database/travels.json";
          
        try {
            String jsonUsersArray = readFile(TRAVELS);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        
            travels = objectMapper.readValue(jsonUsersArray, TravelDto[].class);

            for (TravelDto travelDto : travels) {
                System.out.println(travelDto.getId());
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    
     
    @Override
    public ArrayList<TravelDto> getTravelsForUser (String userId, String keyword) {
          
        try {
            var res =  (ArrayList<TravelDto>) Stream.of(this.getTravels())
            .filter(p -> {
               if (!p.getUserId().equals(userId))
                   return false;
               if (p.getFrom().getName().toUpperCase().contains(keyword.toUpperCase()) 
                || p.getTo().getName().toUpperCase().contains(keyword.toUpperCase()))
                    return true;
               return false;
            })             
           .collect(Collectors.toList());        
   
           return res;    
        } catch (Exception e) {

            return null;
        }            
    }
} 