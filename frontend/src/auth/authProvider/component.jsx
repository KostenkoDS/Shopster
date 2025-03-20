import { createContext, useContext, useEffect, useState } from "react";


const AuthContext = createContext();

export const AuthProvider = ({children})=>{
const [user, setUser] = useState(undefined);
const authLink = "/api/login";

    const checkUserAuthorization = async () => {
        try {
        const response = await fetch("/api/customer",{
            method:"GET",
            })
                if(response.ok){
                    const userData = await response.json();
                    setUser(userData.email);
                    return true;
                }
                else{
                    setUser(undefined);
                    return false;
                } 
            }
        catch (error) {
        setUser(undefined);
        return false;
        }    
    }

    const login = async (credentials) => {
        
    try{   
       const respToken = await fetch("/api/csrf",{
                       method:"GET"})
                       .then(response=> response.json());

     const response = await fetch(authLink,{
            method:"POST",
            headers:{
                "Content-Type": "application/x-www-form-urlencoded"},
            body: new URLSearchParams({
                username: credentials.username,
                password: credentials.password,
                _csrf:respToken.token
        })})
            
        if(response.ok){
            setUser(credentials.username);
        } 
        
        return response;
    }
       
     catch (error){
        throw error("Login error", error);
       }
    }

    return (
        <AuthContext.Provider value={{user, setUser, login, checkUserAuthorization}}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () =>useContext(AuthContext);


    
