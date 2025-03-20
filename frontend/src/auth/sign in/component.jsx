import { useEffect, useState } from 'react';
import styles from './singIn.module.css'
import { useAuth } from '../authProvider/component';
import { useNavigate } from 'react-router-dom';
function  SignIn(){
    
   const navigate = useNavigate();
   const {login} = useAuth();
   const [isPasswordError, setPasswordError] = useState(false);
   const [isEmailError, setIsEmailError] = useState(false);
   const [isInvalidPassword, setIsInvalidPassword] = useState(false);

   const submit = async (event)=>{
   event.preventDefault();
   const formData = new FormData(event.target);
   const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&_\-])[A-Za-z\d@$!%*?&\-]{8,}$/
   const emailRegex =  /^[a-zA-Z0-9._-]+@[a-zA-Z0-9._\-]+\.[a-zA-Z]{2,}$/;
        const email = formData.get("email");
        const password = formData.get("password");
        
        setIsEmailError(!emailRegex.test(email));
        setPasswordError(!passwordRegex.test(password));
         
        if(!isEmailError&&!isPasswordError){
         const response = await login({username:email, password:password});
            if(response.ok){
                window.alert("Success");
                navigate("/");
            }
              else if(!response.ok) {
                setIsInvalidPassword(true);
              }
         }      
   }       
  

    return(
        <div className={styles.main}>
        <div className={styles.singUpLabel}>Sign in
        <div className={styles.singUpText}>Sign in to continue</div>
        </div>
        <form className={styles.singUpForm} onSubmit={submit} >
           
            <div className={styles.login}> <input type="text" name="email" size={20}
                 placeholder="E-mail"/></div> 
            {isEmailError&&<div className={styles.emailError}>
            Please enter a valid email address (e.g., user@shopster.com)</div>}

            <div className={styles.password}> <input type="password" name="password" size={20}
                 placeholder="Password" /></div> 
            {isPasswordError&&<div className={styles.passwordPatternError}>
            Password must contain:one uppercase letter,
            one lowercase letter,  one digit, one special character and have minimum 8 characters! </div>}
           
            {isInvalidPassword&&<div className={styles.passwordPatternError}>
             Invalid password! </div>}
                    
            <input type="submit"  className ={styles.submitButton} value="Sign in" ></input>
        </form>
        </div>
    )

}

export default SignIn;