package com.eventApp.Utils;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

/**
 * Utility class to list all method names in the service package.
 * This class provides functionality to scan and display all public methods
 * from all service classes in the com.eventApp.Service package.
 */
public class ServiceMethodLister {
    
    private static final String SERVICE_PACKAGE = "com.eventApp.Service";
    
    /**
     * Gets all method names from all service classes in the service package.
     * 
     * @return Map where key is the service class name and value is list of method names
     */
    public static Map<String, List<String>> getAllServiceMethods() {
        Map<String, List<String>> serviceMethodsMap = new TreeMap<>();
        
        try {
            // Get all classes in the service package
            List<Class<?>> serviceClasses = getServiceClasses();
            
            for (Class<?> serviceClass : serviceClasses) {
                List<String> methodNames = getMethodNames(serviceClass);
                serviceMethodsMap.put(serviceClass.getSimpleName(), methodNames);
            }
        } catch (Exception e) {
            System.err.println("Error scanning service classes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return serviceMethodsMap;
    }
    
    /**
     * Gets all service class objects from the service package.
     * 
     * @return List of Class objects representing service classes
     */
    private static List<Class<?>> getServiceClasses() throws Exception {
        List<Class<?>> serviceClasses = new ArrayList<>();
        
        // Get the package directory
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = SERVICE_PACKAGE.replace('.', '/');
        URL resource = classLoader.getResource(path);
        
        if (resource != null) {
            File directory = new File(resource.getFile());
            if (directory.exists()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".class")) {
                            String className = SERVICE_PACKAGE + "." + 
                                file.getName().substring(0, file.getName().length() - 6);
                            try {
                                Class<?> clazz = Class.forName(className);
                                serviceClasses.add(clazz);
                            } catch (ClassNotFoundException e) {
                                System.err.println("Could not load class: " + className);
                            }
                        }
                    }
                }
            }
        }
        
        return serviceClasses;
    }
    
    /**
     * Gets all public method names from a given class, excluding inherited Object methods.
     * 
     * @param clazz The class to extract method names from
     * @return List of method names sorted alphabetically
     */
    private static List<String> getMethodNames(Class<?> clazz) {
        List<String> methodNames = new ArrayList<>();
        
        // Get all declared methods (including private, protected, public)
        Method[] methods = clazz.getDeclaredMethods();
        
        for (Method method : methods) {
            // Only include public methods that are not from Object class
            if (Modifier.isPublic(method.getModifiers()) && 
                !isObjectMethod(method.getName())) {
                methodNames.add(method.getName());
            }
        }
        
        // Sort method names alphabetically
        Collections.sort(methodNames);
        return methodNames;
    }
    
    /**
     * Checks if a method name is inherited from Object class.
     * 
     * @param methodName The name of the method to check
     * @return true if the method is from Object class, false otherwise
     */
    private static boolean isObjectMethod(String methodName) {
        String[] objectMethods = {"equals", "hashCode", "toString", "getClass", 
                                 "notify", "notifyAll", "wait", "clone", "finalize"};
        for (String objMethod : objectMethods) {
            if (objMethod.equals(methodName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Prints all service methods in a formatted way.
     */
    public static void printAllServiceMethods() {
        System.out.println("=== SERVICE PACKAGE METHOD LIST ===");
        System.out.println();
        
        Map<String, List<String>> serviceMethodsMap = getAllServiceMethods();
        
        if (serviceMethodsMap.isEmpty()) {
            System.out.println("No service classes found in package: " + SERVICE_PACKAGE);
            return;
        }
        
        int totalMethods = 0;
        for (Map.Entry<String, List<String>> entry : serviceMethodsMap.entrySet()) {
            String serviceName = entry.getKey();
            List<String> methods = entry.getValue();
            
            System.out.println("📋 " + serviceName + " (" + methods.size() + " methods):");
            System.out.println("   " + "─".repeat(serviceName.length() + 20));
            
            if (methods.isEmpty()) {
                System.out.println("   (No public methods found)");
            } else {
                for (String method : methods) {
                    System.out.println("   • " + method + "()");
                }
            }
            
            System.out.println();
            totalMethods += methods.size();
        }
        
        System.out.println("=".repeat(50));
        System.out.println("Total Service Classes: " + serviceMethodsMap.size());
        System.out.println("Total Methods: " + totalMethods);
        System.out.println("=".repeat(50));
    }
    
    /**
     * Gets a flat list of all method names from all service classes.
     * 
     * @return List of all method names across all service classes
     */
    public static List<String> getAllMethodNames() {
        List<String> allMethods = new ArrayList<>();
        Map<String, List<String>> serviceMethodsMap = getAllServiceMethods();
        
        for (List<String> methods : serviceMethodsMap.values()) {
            allMethods.addAll(methods);
        }
        
        Collections.sort(allMethods);
        return allMethods;
    }
    
    /**
     * Gets a list of unique method names from all service classes.
     * 
     * @return List of unique method names across all service classes
     */
    public static List<String> getUniqueMethodNames() {
        Set<String> uniqueMethods = new TreeSet<>();
        Map<String, List<String>> serviceMethodsMap = getAllServiceMethods();
        
        for (List<String> methods : serviceMethodsMap.values()) {
            uniqueMethods.addAll(methods);
        }
        
        return new ArrayList<>(uniqueMethods);
    }
    
    /**
     * Prints just the method names in a simple list format.
     */
    public static void printMethodNamesOnly() {
        System.out.println("=== ALL SERVICE METHOD NAMES ===");
        List<String> allMethods = getAllMethodNames();
        
        for (int i = 0; i < allMethods.size(); i++) {
            System.out.println((i + 1) + ". " + allMethods.get(i) + "()");
        }
        
        System.out.println("\nTotal Methods: " + allMethods.size());
    }
    
    /**
     * Prints unique method names in a simple list format.
     */
    public static void printUniqueMethodNames() {
        System.out.println("=== UNIQUE SERVICE METHOD NAMES ===");
        List<String> uniqueMethods = getUniqueMethodNames();
        
        for (int i = 0; i < uniqueMethods.size(); i++) {
            System.out.println((i + 1) + ". " + uniqueMethods.get(i) + "()");
        }
        
        System.out.println("\nTotal Unique Methods: " + uniqueMethods.size());
    }
    
    /**
     * Main method to demonstrate the utility.
     * Run this to see all method names in the service package.
     * 
     * Usage: 
     * - java ServiceMethodLister (shows detailed view by class)
     * - java ServiceMethodLister list (shows simple method list with duplicates)
     * - java ServiceMethodLister unique (shows unique method list)
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "list":
                    printMethodNamesOnly();
                    break;
                case "unique":
                    printUniqueMethodNames();
                    break;
                default:
                    printAllServiceMethods();
                    break;
            }
        } else {
            printAllServiceMethods();
        }
    }
}