package uc.no.identity.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SqlType {
   public boolean isId() default false;
   public boolean notNull() default false;
   public String fieldName() default "";
   public String fieldType() default "";
}