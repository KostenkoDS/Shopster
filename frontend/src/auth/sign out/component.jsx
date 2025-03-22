import { useNavigate } from "react-router-dom";
import { useAuth } from "../authProvider/component"
import { useEffect } from "react";

function SignOut(){
    const{setUser} = useAuth();
    const navigate = useNavigate();
    const signOut = async () => { 
    
    try{
        const response = await fetch("/api/csrf",{
            method:"GET"
        }).then(response=> response.json())
        .then(response=> fetch("/api/logout",{
            method:"POST",
            headers:{
                "X-CSRF-TOKEN":response.token}
            }))
        
        if(response.ok){
                setUser(undefined);
                navigate("/");
            }
        }
    catch (error){error => console.error(error)}
}       
 useEffect(()=>{
    signOut();
 },[]);
   
}

export default SignOut