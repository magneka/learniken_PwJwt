package uc.no.identity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import uc.no.identity.annotations.SqlType;
import uc.no.identity.datalayer.users.UsersDto;



@SpringBootTest
public class ReflectionTest {

    static <T> void inspect(Class<T> klazz) {
        Field[] fields = klazz.getDeclaredFields();
        
        System.out.printf("\n%d fields:%n", fields.length);
        
        for (Field field : fields) {
            System.out.printf("%s %s %s%n",
                Modifier.toString(field.getModifiers()),
                field.getType().getSimpleName(),
                field.getName()                
            );

            Annotation[] annotations = field.getDeclaredAnnotations(); 
            //Annotation[] annotations2 = field.getAnnotations(); // gir samme resultat
            if (annotations != null) {

                for (Annotation annotField : annotations) {
                    System.out.printf("  %s\n", annotField.toString());
                    
                    // Sjekk på annotation med verdier
                    if(annotField instanceof SqlType){
                        SqlType customAnnotation = (SqlType) annotField;
                        System.out.println("  * Sqltype: " + customAnnotation.fieldType());
                        System.out.println("  * SqlNavn: " + customAnnotation.fieldName());
                     }
                }               
            }            
        }        
    }

    @Test
    void testrefl01() {
        inspect(UsersDto.class);
    }

    
}
