import { useState } from "react";
import './style.css'


function PriceFilter({applyHandler}) {

    const [show, setShow] =useState(false);
    const [minPrice, setMinPrice] = useState('0');
    const [maxPrice, setMaxPrice] = useState('0');
    const showHandler = () =>{
        setShow((prevShow) => !prevShow)};

    
    const submit = (formData)=>{
        let minPrice = formData.get("minPriceValue");
        let maxPrice = formData.get("maxPriceValue");
            
        if(maxPrice==""){
            maxPrice = 0;
            setMaxPrice('0');
        } 
        
        if(minPrice==""){
            minPrice=0;
            setMinPrice('0');
        } 

        if(Number(minPrice)>Number(maxPrice)) {
            console.log(minPrice, maxPrice);
            window.alert("The maximum price of the product should be higher than the minimum price.")
            return;
        }
        else {
            console.log(minPrice, maxPrice);
            setMaxPrice(maxPrice);
            setMinPrice(minPrice);
            applyHandler(minPrice,maxPrice);
        }
    }

    return(

<div className="filter-container">
<div className="show" onClick={showHandler}>Price</div>
{show?( <div className="price-container">
<form action={submit} className="priceForm">
<input type="number" min = "0" className="min-price" name="minPriceValue" placeholder={minPrice}></input> - 
<input type="number" className="max-price" min="0" name="maxPriceValue" placeholder={maxPrice}></input>
<button className="ok-button" type="submit">ok</button>
</form>
</div>):null}

</div>
    )
} 



export default PriceFilter