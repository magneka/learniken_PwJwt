package uc.no.identity.datalayer.ucsql;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import uc.no.identity.datalayer.users.UsersDto;

public class ucSqlGeneratorTests {
 
    @Test
    void generateDbFields01() {

        ucSqlGenerator sqlGenerator = new ucSqlGenerator(UsersDto.class);

        List<ucDbFields> result = sqlGenerator.parseFields();
        
        assertTrue(result.size() > 0, "Felter OK");        
    }
    
}
