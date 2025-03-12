import { useState } from 'react';
import styles from './singUp.module.css'
function SignUp(){
    
   const [isPasswordError, setPasswordError] = useState(false);
   const [isRepPasswordError, setIsRepPasswordError] = useState(false);
   const [isEmailError, setIsEmailError] = useState(false);

   const submit = (event)=>{
   event.preventDefault();
   const formData = new FormData(event.target);
   const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&_-])[A-Za-z\d@$!%*?&]{8,}$/
   const emailRegex =  /^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z]{2,}$/;
        const email = formData.get("email");
        const password = formData.get("password");
        const repPassword = formData.get("repPassword");
        if(!emailRegex.test(email)) 
            setIsEmailError(true);
            else setIsEmailError(false);

        if(!passwordRegex.test(password))
            setPasswordError(true);
            else setPasswordError(false);

        if(password!==repPassword)
            setIsRepPasswordError(true);
            else setIsRepPasswordError(false);
    }

    return(
        <div className={styles.main}>
        <div className={styles.singUpLabel}>Sign up
        <div className={styles.singUpText}>Sign up to continue</div>
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
            <div className={styles.password}> <input type="password" name="repPassword" size={20}
                 placeholder="Repeat password"/> </div>
            {isRepPasswordError&& <div className={styles.repPasswordError}>Passwords do not match!</div>}  
                    
            <input type="submit"  className ={styles.submitButton} value="Sign up" ></input>
        </form>
        </div>
    )

}

export default SignUp;