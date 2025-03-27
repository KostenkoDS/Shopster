import { useNavigate } from "react-router-dom";
import { useAuth } from "../../auth/authProvider/component";
import { useOrderedProducts } from "../../pages/cart/productContext";
import styles from './addToCartButton.module.css'

function AddToCartButton ({id}) {
    const {checkUserAuthorization} = useAuth();
    const {addProductsToCart} = useOrderedProducts();
    const navigate = useNavigate();

    const  handlerAddButton = async ()=>{
        const response = await checkUserAuthorization();
            if(response){
               addProductsToCart({id:id.toString(), amount:Number(1)});
            }
            else{
            navigate("/auth/sign-in");}
         };
    
    return (
    <button className={styles.addButton} onClick={handlerAddButton}>Add to cart</button>)
}

export default AddToCartButton;