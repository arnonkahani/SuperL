package com.Common.UI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * a class used to present a list of objects in a table,
 * then prompts the user to input commands to execute actions
 *
 * @param <T>: type of objects to present
 */
public class Table<T> {
    Class<T> clazz;
    List<T> list;
    static final String seperator = " | ";

    public interface Action<T>{
        void accept(Integer i, T o) throws Exception;
    }

    public interface NewAction{
        void run() throws Exception;
    }

    Action<T> deleteAction;
    Action<T> optionsAction;
    NewAction newAction;
    Action<T> editAction;
    boolean selectMode = false;

    public Table(Class<T> clazz, List<T> list){
        this.clazz = clazz;
        this.list = list;
    }

    public void display() throws Exception {
        String command = null;
        Scanner scanner = new Scanner(System.in);

        draw();
        System.out.println("Enter commands:");
        System.out.println("  `back`");
        if(newAction != null) System.out.println("  `new`");
        if(optionsAction != null) System.out.println("  `options <index>`");
        if(deleteAction != null) System.out.println("  `delete <index>`");
        if(editAction != null) System.out.println("  `edit <index>`");
        while (!Objects.equals(command = scanner.nextLine(), "back")){
            System.out.print("> ");
            parseCommand(command);
            System.out.print("> ");
        }
    }
    public T select() throws Exception {
        selectMode = true;
        String command = null;
        Scanner scanner = new Scanner(System.in);

        draw();
        System.out.println("Enter commands:");
        System.out.println("  `back`");
        System.out.println("  `select <index>`");
        while (!Objects.equals(command = scanner.nextLine(), "back")){
            System.out.print("> ");
            String[] strings = command.split(" ");
            if(strings.length != 2)
                System.out.println("Expected `select <index>`");
            else if(!Objects.equals(strings[0], "select"))
                System.out.println("Expected `select <index>`");
            else {
                int i;
                try{
                    i = Integer.parseInt(strings[1]) - 1;
                    if(i >= list.size() || i < 0)
                        System.out.println("Invalid index.");
                    else
                        return list.get(i);
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid index.");
                }
            }
        }
        return null;
    }

    private void parseCommand(String command) throws Exception {
        if(command == null) return;

        String[] parts = command.split(" ");
        if(parts.length == 2){
            int i = 0;
            try{
                i = Integer.parseInt(parts[1]) - 1;
            }
            catch (NumberFormatException e){
                System.out.println("Invalid index.");
            }
            if(i >= list.size() || i < 0){
                System.out.println("Invalid index.");
                return;
            }
            switch (parts[0]){
                case "options":
                    if(optionsAction != null){
                        optionsAction.accept(i,list.get(i));
                        draw();
                    }
                    else{
                        System.out.println("Unsupported command.");
                        return;
                    }
                    break;
                case "delete":
                    if(deleteAction != null){
                        deleteAction.accept(i,list.get(i));
                        draw();
                    }
                    else{
                        System.out.println("Unsupported command.");
                        return;
                    }
                    break;
                case "edit":
                    if(editAction != null){
                        editAction.accept(i,list.get(i));
                        draw();
                    }
                    else{
                        System.out.println("Unsupported command.");
                        return;
                    }
                    break;
                default:
                    System.out.println("Unsupported command.");
            }
        }
        else if(parts.length == 1){
            if(Objects.equals(parts[0], "new") && newAction != null){
                newAction.run();
                draw();
            }
            else{
                System.out.println("Unsupported command.");
            }
        }
        else{
            System.out.println("Expected a command and an index.");
        }
    }

    private void draw() {
        System.out.println();
        try {
            Map<Method,Integer> lengths = new HashMap<>();

            for(Method m : clazz.getMethods()){
                String name = getter(m);
                if(name != null){
                    lengths.put(m,name.length());
                }
            }
            for(T o : list)
                for(Method m : clazz.getMethods()){
                    String name = getter(m);
                    if(name != null){
                        int val = 0;
                        val = Math.max(lengths.get(m) != null ? lengths.get(m) : 0,Math.max(name.length(),m.invoke(o) != null ? m.invoke(o).toString().length() : "null".length()));
                        lengths.put(m,val);
                    }
                }

            System.out.print(" "+seperator);
            for(Method m : clazz.getMethods()){
                String name = getter(m);
                if(name != null)
                    System.out.print(pad(name,lengths.get(m))+seperator);
            }
            System.out.println();
            for(Method m : clazz.getMethods()){
                String name = getter(m);
                if(name != null)
                    for(int i = -seperator.length() - 1 ; i < lengths.get(m) ; i++)
                        System.out.print('=');
            }
            System.out.println();

            int totalSize = 0;
            for(Method m : lengths.keySet())
                totalSize += lengths.get(m) + seperator.length();

            if(list.isEmpty())
                System.out.println(padLeft("No items to show",totalSize));

            for(int i = 0 ; i < list.size() ; i++){
                T o = list.get(i);
                System.out.print((i+1)+seperator);
                for(Method m : clazz.getMethods()){
                    String name = getter(m);
                    if(name != null){
                        System.out.print(pad(m.invoke(o) != null ? m.invoke(o).toString() : "null" ,lengths.get(m))+seperator);
                    }
                }
                System.out.println();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public void setDeleteAction(Action<T> deleteAction) {
        this.deleteAction = deleteAction;
    }

    public void setOptionsAction(Action<T> optionsAction) {
        this.optionsAction = optionsAction;
    }

    public void setEditAction(Action<T> editAction) {
        this.editAction = editAction;
    }

    public void setNewAction(NewAction newAction) {
        this.newAction = newAction;
    }

    private String pad(String s , int length){
        return String.format("%0$-"+length+"s",s);
    }
    private String padLeft(String s , int length){
        for(int i = 0 ; i < length - s.length() ; i++)
            s = " " + s;
        return s;
    }

    private static String getter(Method method){
        if(Objects.equals(method.getName(), "getClass") || !(method.getName().startsWith("get") || method.getName().startsWith("is")))      return null;
        if(method.getParameterTypes().length != 0)   return null;
        if(void.class.equals(method.getReturnType())) return null;
        if(method.isAnnotationPresent(Hide.class)) return null;
        return method.getName().startsWith("get") ? method.getName().substring(3) : method.getName().substring(2);
    }
}
