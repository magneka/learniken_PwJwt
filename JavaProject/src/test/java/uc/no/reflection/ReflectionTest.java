package uc.no.reflection;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import uc.no.identity.datalayer.users.UsersDto;


@SpringBootTest
public class ReflectionTest {

    /*
     * 
     static <T> void inspect(Class<T> klazz) {
         Field[] fields = klazz.getDeclaredFields();
         System.out.printf("%d fields:%n", fields.length);
         for (Field field : fields) {
             System.out.printf("%s %s %s%n",
             Modifier.toString(field.getModifiers()),
             field.getType().getSimpleName(),
             field.getName()
             );
            }
        }
        
        @Test
        void testrefl01() {
            
            inspect(UsersDto.class);
            
            
            //Field[] fields = UsersDto.class.getFields();
            //for (Field field : fields) {
                //    System.out.println(field.toString());
                //}
            }
            */

    @Test
    void testrefl02() {

        //var klazz = UsersDto.class;
        //inspect(UsersDto.class);
        Field[] fields = UsersDto.class.getDeclaredFields();
        System.out.printf("%d fields:%n", fields.length);
        for (Field field : fields) {
            System.out.printf("%s %s %s%n",
                Modifier.toString(field.getModifiers()),
                field.getType().getSimpleName(),
                field.getName()
            );
        }
    }

    
}
