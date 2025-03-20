import { useOrderedProducts } from '../../cart/productContext';
import styles from './item.module.css'
function Item({product}){
    const {addProductsToCart} = useOrderedProducts();
    const handlerAddButton= ()=>{
      addProductsToCart({id:product.id.toString(), amount:Number(1)});
    }

    return(
       
        <div className={styles.itemContainer}>
        <img src={product.productPictures[1]} className={styles.itemImg} ></img>
        <div className={styles.desctiptionContainer}>
            <div className={styles.name}>{product.name}</div>
            <div className={styles.description}>{product.description}</div>
        </div>
        <div className={styles.purchaseContainer}>
            <div className={styles.price}>{product.price} $</div>
            <button className={styles.addButton} onClick={handlerAddButton}>Add to cart</button>
        </div>
        </div>
        
    );

}
 export default Item;