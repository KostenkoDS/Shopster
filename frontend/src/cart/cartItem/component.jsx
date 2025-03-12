import styles from'./cartItem.module.css'
import {useEffect, useState } from 'react';


function CartItem({product, priceHandler}){

const [amount, setAmount] = useState(1);
const [totalPrice, setTotalPrice]= useState(product.price);

const decrementHandler=()=>{
    if(amount>1){
        const newAmount = amount -1;
        const newPrice = totalPrice - product.price;
        setAmount(newAmount);
        setTotalPrice(newPrice);
        priceHandler(amount,product.price,'-');
    }
}
const incrementHandler = ()=>{
    const newAmount = amount +1;
    const newPrice = totalPrice + product.price;
        setAmount(newAmount);
        setTotalPrice(newPrice);
        priceHandler(amount,product.price,'+');
        
}

useEffect(()=>{
    priceHandler(amount,product.price,'+');
    console.log(product.id);
},[]);

    return(
        <div className={styles.container}>
            <div className={styles.descriptionContainer}>
            <div className={styles.product_img}>
                <img className = {styles.img} src={product.url} alt={product.sequence}></img>
            </div>
                <div className={styles.name}>{product.name}</div>
                <div className={styles.description}>{product.description}</div>
            </div>
            <div className={styles.futter}>
                <div className={styles.productPricing}>        
                <button className= {styles.buttonDec}  onClick={decrementHandler}>-</button>
                <div className={styles.amount}>{amount}</div>
                <button className= {styles.buttonInc} onClick={incrementHandler}>+</button>
                <div className={styles.totalProductPrice}>{totalPrice} $</div>
                </div>
                </div>     
               </div>
    );
    
}

export default CartItem;