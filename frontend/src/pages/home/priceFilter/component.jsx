import styles from './priceFilter.module.css'
import { useEffect, useState } from 'react';

function PriceFilter({applyHandler}) {

    const [show, setShow] =useState(false);
    const [minPriceHolder, setMinPriceHolder] = useState();
    const [maxPriceHolder, setMaxPriceHolder] = useState();
    
    const showHandler = () =>{
          setShow((prevShow) => !prevShow)};
    
    const submit = (formData)=>{
        let minPrice = formData.get("minPriceValue");
        let maxPrice = formData.get("maxPriceValue");
        
        if(minPrice==""){
            minPrice=1;
        }
      
        if(maxPrice===""){
            setMinPriceHolder(1);
            setMaxPriceHolder(" . . . ");
            applyHandler(minPrice,undefined);
            
         return;
        }

        else if (!isNaN(maxPrice)&&!isNaN(minPrice)){
                if(Number(minPrice)>Number(maxPrice)){
                    window.alert("The maximum price of the product should be higher than the minimum price.")
                }
              else{  
                    setMinPriceHolder(minPrice);
                    setMaxPriceHolder(maxPrice);
                    applyHandler(minPrice,maxPrice);
                  }
        }
    }

    useEffect(()=>{
        const getPrices = async ()=> {
            const parameters = new URLSearchParams(window.location.search);
            let minPrice = parameters.get("minPrice") || "1"; 
            let maxPrice = parameters.get("maxPrice") || " . . .  "; 
            setMinPriceHolder(minPrice);
            setMaxPriceHolder(maxPrice);
        }

        getPrices();
            
    },[submit])

    return(

<div className={styles.filterContainer}>
<div className={styles.show} onClick={showHandler}>Price</div>
{show?( <div className={styles.priceContainer}>
<form action={submit} className={styles.priceForm}>
<input type="number" min = "1" className={styles.minPrice} name="minPriceValue" placeholder={minPriceHolder}></input> - 
<input type="number" className={styles.maxPrice} min="1" name="maxPriceValue" placeholder={maxPriceHolder}></input>
<button className={styles.okButton} type="submit">ok</button>
</form>
</div>):null}

</div>
    )
} 



export default PriceFilter