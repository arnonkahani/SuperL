package com.Common.UI;

import javafx.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * a class that is used to present a UI to edit an arbitrary object
 * using java reflection
 *
 * uses the object setter methods to get names of fields and updates them accordingly.
 * IMPORTANT: ignores setters with annotation @Hide
 *
 * @param <T>: type of object to edit
 */
public class Editor<T> {

    Class<T> clazz;
    T obj;
    Scanner scanner = new Scanner(System.in);

    public Editor(Class<T> clazz, T obj) {
        this.clazz = clazz;
        this.obj = obj;
    }

    public T edit() throws InvocationTargetException, IllegalAccessException {
        System.out.println("Enter `quit' at any time to cancel the editor.");
        Method[] methods = clazz.getMethods();
        for(Method m : methods){
            Pair<String,Class<?>> pair = setter(m);
            if(pair != null){
                if(pair.getValue().isPrimitive() || pair.getValue().isEnum() || pair.getValue() == String.class || pair.getValue() == Date.class){
                    Object o = null;
                    while(o == null)
                        try{
                            if(pair.getValue() == Date.class)
                                System.out.print(String.format("Enter %s%s: ",pair.getKey()," (format: dd/MM/yyyy HH:mm)"));
                            else if (pair.getValue().isEnum())
                                System.out.print(String.format("Enter %s%s: ",pair.getKey()," (options: "+ Arrays.toString(pair.getValue().getEnumConstants()) +")"));
                            else
                                System.out.print(String.format("Enter %s: ",pair.getKey()));
                            String s = scanner.nextLine();
                            if(Objects.equals(s, "quit"))
                                return null;
                            o = toObject(pair.getValue(),s);
                            m.invoke(obj,o);
                        }
                        catch (NumberFormatException e){
                            System.out.println("(Invalid Number)");
                        }
                        catch (ParseException e){
                            System.out.println("(Invalid Date)");
                        }
                        catch (IllegalArgumentException e){
                            System.out.println("(Invalid Choice)");
                        }
                        catch (InvocationTargetException e){
                            System.out.println(String.format("(%s)",e.getCause().getMessage()));
                            o = null;
                        }
                }
            }
        }
        return obj;
    }

    private Pair<String,Class<?>> setter(Method method){
        if(!(method.getName().startsWith("set")))      return null;
        if(method.getParameterTypes().length != 1)   return null;
        if(!void.class.equals(method.getReturnType())) return null;
        if(method.isAnnotationPresent(Hide.class)) return null;
        return new Pair<>(method.getName().substring(3),method.getParameterTypes()[0]);
    }

    public static Object toObject( Class clazz, String value ) throws NumberFormatException, ParseException {
        if( boolean.class == clazz ) return Boolean.parseBoolean( value );
        if( byte.class == clazz ) return Byte.parseByte( value );
        if( short.class == clazz ) return Short.parseShort( value );
        if( int.class == clazz ) return Integer.parseInt( value );
        if( long.class == clazz ) return Long.parseLong( value );
        if( float.class == clazz ) return Float.parseFloat( value );
        if( double.class == clazz ) return Double.parseDouble( value );

        if( Boolean.class == clazz ) return Boolean.parseBoolean( value );
        if( Byte.class == clazz ) return Byte.parseByte( value );
        if( Short.class == clazz ) return Short.parseShort( value );
        if( Integer.class == clazz ) return Integer.parseInt( value );
        if( Long.class == clazz ) return Long.parseLong( value );
        if( Float.class == clazz ) return Float.parseFloat( value );
        if( Double.class == clazz ) return Double.parseDouble( value );

        if( Date.class == clazz ) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return format.parse(value);
        }

        if( clazz.isEnum() ) return Enum.valueOf(clazz,value);

        return value;
    }
}