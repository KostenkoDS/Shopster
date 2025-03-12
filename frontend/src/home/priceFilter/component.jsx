import styles from './priceFilter.module.css'
import { useState } from 'react';

function PriceFilter({applyHandler}) {
    const parameters = new URLSearchParams(window.location.search);
    let minPriceHolder = parameters.get("minPrice");
    let maxPriceHolder = parameters.get("maxPrice");
    const [show, setShow] =useState(false);
    const showHandler = () =>{
        setShow((prevShow) => !prevShow)};

    
    const submit = (formData)=>{
        let minPrice = formData.get("minPriceValue");
        let maxPrice = formData.get("maxPriceValue");
            
        if(maxPrice==""){
            maxPrice = 0;
        } 
        
        if(minPrice==""){
            minPrice=0;
        } 

        if(Number(minPrice)>Number(maxPrice)) {
            window.alert("The maximum price of the product should be higher than the minimum price.")
            return;
        }
        else {
            applyHandler(minPrice,maxPrice);
        }
    }

    return(

<div className={styles.filterContainer}>
<div className="show" onClick={showHandler}>Price</div>
{show?( <div className={styles.priceContainer}>
<form action={submit} className={styles.priceForm}>
<input type="number" min = "0" className={styles.minPrice} name="minPriceValue" placeholder={minPriceHolder}></input> - 
<input type="number" className={styles.maxPrice} min="0" name="maxPriceValue" placeholder={maxPriceHolder}></input>
<button className={styles.okButton} type="submit">ok</button>
</form>
</div>):null}

</div>
    )
} 



export default PriceFilter