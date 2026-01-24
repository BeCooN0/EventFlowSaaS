package com.example.eventflowsaas.security;

import org.springframework.stereotype.Component;

@Component
public class CurrentTenant {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getTenant() {
        return threadLocal.get();
    }

    public static void setTenant(String name) {
        String s = validateTenantName(name);
        threadLocal.set(s);
    }

    public static void clear(){
        threadLocal.remove();
    }
    private static String validateTenantName(String s){
       return s.replaceAll("[a-zAZ0-9_]", "");
    }
}
