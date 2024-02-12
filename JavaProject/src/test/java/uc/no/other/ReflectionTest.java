package uc.no.other;

import java.io.Console;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import uc.no.identity.annotations.SqlType;
import uc.no.identity.business.Car;
import uc.no.identity.datalayer.travel.TravelDto;

public class ReflectionTest {

    @Test
    void TestReflection() throws NoSuchFieldException, SecurityException {

        Allsorts obj = new Allsorts();
        //obj.setBrand("Audi");
        Class<?> c = obj.getClass();
        Field[] fields = c.getDeclaredFields();
        Map<String, Object> temp = new HashMap<String, Object>();

        for (Field field : fields) {
            try {
                
                String fieldName = field.getName().toString();
                String fieldType = field.getType().toString();
                System.out.println("Field:" + fieldName + ", " + fieldType);

                //Field classMemberField = Allsorts.class.getDeclaredField(fieldName);
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation ann : annotations) {
                    Annotation t = ann;
                    System.out.println("   Annotations:" + ann.toString());
                    
                }

                temp.put(field.getName().toString(), field.get(obj));
            } catch (IllegalArgumentException e1) {
            } catch (IllegalAccessException e1) {
            }
        }

    }

}
