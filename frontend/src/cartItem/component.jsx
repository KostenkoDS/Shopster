import './style.css'
import { useCallback, useEffect, useState } from 'react';


function CartItem({product, priceHandler}){

const [amount, setAmount] = useState(1);

const decrementHandler=()=>{
    if(amount>1){
        const newAmount = amount -1;
        setAmount(newAmount);
        priceHandler(amount,product.price,'-');
    }
}
const incrementHandler = ()=>{
    const newAmount = amount +1;
        setAmount(newAmount);
        priceHandler(amount,product.price,'+');
        
}

useEffect(()=>{
    priceHandler(amount,product.price,'+');
    console.log(product.id);
},[]);

    return(
        <div className="container">
            <div className="descriptionContainer">
            <div className="product_img">
                <img src={product.url} alt={product.sequence}></img>
            </div>
                <div className="name">{product.name}</div>
                <div className='description'>{product.description}</div>
            </div>
            <div className='ProductPricing'>              
                <button className= "buttonDec"  onClick={decrementHandler}>-</button>
                <div className='amount'>{amount}</div>
                <button className= "buttonInc" onClick={incrementHandler}>+</button>
                </div>    
               </div>
    )
    
}

export default CartItem;