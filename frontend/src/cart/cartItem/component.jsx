import { useOrderedProducts } from '../productContext';
import styles from'./cartItem.module.css'
import {useEffect, useState } from 'react';

function CartItem({product, priceHandler, removeProduct}){
const {getAmount, addProductsToCart, deleteProductsFromCart} = useOrderedProducts();
const [totalPrice, setTotalPrice]= useState(Number(product.price)*Number(getAmount({id:product.id})));

const decrementHandler=()=>{
        
    let amount = Number(getAmount({id:product.id}));
    if(amount>1){
       priceHandler(product.price,'-');
       deleteProductsFromCart({id:product.id, amount:1});
       let newPrice = (amount-1)*Number(product.price);
       setTotalPrice(newPrice);
    }
     
    if(amount === Number(1)){
        let newPrice = product.price;
        setTotalPrice(newPrice);
    }
    }

const incrementHandler = ()=>{

     addProductsToCart({id:product.id, amount:1});
     let amount = Number(getAmount({id:product.id}));
     let newPrice = (amount)*Number(product.price);
     priceHandler(product.price,'+');
     setTotalPrice(newPrice);       
}

useEffect(()=>{
    let amount = Number(getAmount({id:product.id}));
    priceHandler(amount*product.price,'+');
},[])

const removeProductFromCart = ()=>{
    let amount = Number(getAmount({id:product.id}));
    deleteProductsFromCart({id:product.id, amount:amount})
    removeProduct({id:product.id});
    priceHandler(product.price*amount,'-');
 
}

    return(
        <div className={styles.container}>
            <div className={styles.descriptionContainer}>
            <div className={styles.product_img}>
                <img className = {styles.img} src={product.productPictures[1]   } alt={product.sequence}></img>
            </div>
                <div className={styles.name}>{product.name}</div>
                <div className={styles.description}>{product.description}</div>
                <div className={styles.removeButton} onClick={removeProductFromCart}>X</div>
            </div>
            <div className={styles.futter}>
                <div className={styles.productPricing}>        
                <button className= {styles.buttonDec}  onClick={decrementHandler}>-</button>
                <div className={styles.amount}>{getAmount({id:product.id})}</div>
                <button className= {styles.buttonInc} onClick={incrementHandler}>+</button>
                <div className={styles.totalProductPrice}>{Number(totalPrice).toFixed(2)} $</div>
                </div>
                </div>     
               </div>
    );
    
}

export default CartItem;