import styles from './item.module.css'
function Item({product}){
    return(
       
        <div className={styles.itemContainer}>
        <img src={product.url} className={styles.itemImg} ></img>
        <div className={styles.desctiptionContainer}>
            <div className={styles.name}>{product.name}</div>
            <div className={styles.description}>{product.description}</div>
        </div>
        <div className={styles.purchaseContainer}>
            <div className={styles.price}>{product.price} $</div>
            <button className={styles.addButton}>Add to cart</button>
        </div>
        </div>
        
    );

}
 export default Item;