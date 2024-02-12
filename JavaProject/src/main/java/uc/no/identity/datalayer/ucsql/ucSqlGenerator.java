package uc.no.identity.datalayer.ucsql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import uc.no.identity.annotations.SqlType;

public class ucSqlGenerator {

    private List<ucDbFields> dbFields;
    private Field[] fields;

    public <T> ucSqlGenerator(Class<T> klazz) {
        super();
        this.dbFields = new ArrayList<ucDbFields>();
        this.fields = klazz.getDeclaredFields();
    }

    public List<ucDbFields> parseFields() {

        for (Field field : this.fields) {
            String name = field.getName();

            ucDbFields dbField = new ucDbFields();
            dbField.setFieldName(name);


            if (field.getType().toString().equals("class java.lang.String")) {
                 dbField.setSqlType("VARCHAR(200)");
            //} else if (field.getType() == Integer) {
                // s is Integer value
            }
          

            // Check if annotation override
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotField : annotations) {
                // Sjekk på annotation med verdier
                if (annotField instanceof SqlType) {
                    SqlType customAnnotation = (SqlType) annotField;
                    if (!customAnnotation.fieldName().isEmpty())
                        dbField.setFieldName(customAnnotation.fieldName());
                    if (!customAnnotation.fieldType().isEmpty())
                        dbField.setSqlType(customAnnotation.fieldType());
                    if (customAnnotation.isId())
                        dbField.setIsId(true);
                    if (customAnnotation.notNull())
                        dbField.setNullAllowed(true);
                }
            }

            dbFields.add(dbField);
        }

        return this.dbFields;

    }

    public String getScreateSQL() {
        return "";
    }

}
