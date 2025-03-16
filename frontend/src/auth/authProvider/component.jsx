import { createContext, useContext, useEffect, useState } from "react";


const AuthContext = createContext();

export const AuthProvider = ({children})=>{
const [user, setUser] = useState(undefined);
const authLink = "/api/login";
useEffect(()=>{
    fetch("/api/customer",{
        method:"GET",
    }).then(resp=>resp.json()).then(data =>setUser(data.email))
       .catch(()=>{console.log("error"); setUser(undefined)});
      
},[]);

    const login = async (credentials) => {
        
    try{   
       const response = await fetch("/api/csrf",{
            method:"GET"
        }).then(response=> response.json()).then( response=>
        fetch(authLink,{
            method:"POST",
            headers:{
                "Content-Type": "application/x-www-form-urlencoded"},
            body: new URLSearchParams({
                username: credentials.username,
                password: credentials.password,
                _csrf:response.token
        })}))
            
        if(response.ok){
            setUser(credentials.username);
            return response;
        } 
    }
       
     catch (error){
        error=>console.error(error);
        return response;
        }
    }

    return (
        <AuthContext.Provider value={{user, setUser, login}}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () =>useContext(AuthContext);


    
