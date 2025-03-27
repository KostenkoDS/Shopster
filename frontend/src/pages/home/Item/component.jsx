import { useNavigate } from 'react-router-dom';
import styles from './item.module.css'
import AddToCartButton from '../../../components/addToCartButton/addToCartButton';
function Item({product}){
    const navigate = useNavigate();

    return(
       
        <div className={styles.itemContainer}>
        <img src={product.productPictures[1]} className={styles.itemImg} 
                onClick={() => navigate(`/products/${product.id}`)}></img>
        <div className={styles.desctiptionContainer}>
            <div className={styles.name}>{product.name}</div>
            <div className={styles.description}>{product.description}</div>
        </div>
        <div className={styles.purchaseContainer}>
            <div className={styles.price}>{product.price} $</div>
        <AddToCartButton id = {product.id}/>
        </div>
        </div>
        
    );

}
 export default Item;