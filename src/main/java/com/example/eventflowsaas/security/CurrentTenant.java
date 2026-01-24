package com.example.eventflowsaas.security;

import org.springframework.stereotype.Component;

@Component
public class CurrentTenant {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getTenant() {
        return threadLocal.get();
    }

    public static void setTenant(String name) {
        if(name != null && validateTenantName(name)){
            threadLocal.set(name);
        }else {
            threadLocal.set("public");
        }
    }

    public static void clear(){
        threadLocal.remove();
    }
    private static boolean validateTenantName(String s){
        return s!=null && s.matches("^[a-zA-Z0-9_]+$");
    }
}
