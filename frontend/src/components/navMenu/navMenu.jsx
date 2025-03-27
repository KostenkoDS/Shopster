import styles from './navMenu.module.css'
import { useAuth } from "../../auth/authProvider/component";
import { useEffect, useState} from "react";
import { Link } from "react-router-dom";
function NavMenu(){

    const [userState, setUserState] = useState(false);
    const {checkUserAuthorization} = useAuth();
   
    useEffect(()=>{ 
        checkUserAuthorization().then(response=>setUserState(response));
    },[checkUserAuthorization])
   
    return(
        <div className={styles.navMenu}>
        <Link to="/" className={styles.homelink}>Home</Link>
        <div className={styles.search}></div>
        {userState&&<Link to="/cart" className={styles.cartlink}>Cart</Link>}
        {userState?<Link to="/auth/logout" className={styles.loginlink}>Log out</Link>:
        <Link to="/auth/sign-in" className={styles.loginlink}>Log in</Link>}
    </div>)
}

export default NavMenu;