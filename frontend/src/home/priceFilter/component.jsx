import { useState } from "react";
import './style.css'


function PriceFilter({applyHandler}) {
    const parameters = new URLSearchParams(window.location.search);
    let minPriceHolder = parameters.get("minPrice");
    let maxPriceHolder = parameters.get("maxPrice");
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
            window.alert("The maximum price of the product should be higher than the minimum price.")
            return;
        }
        else {
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
<input type="number" min = "0" className="min-price" name="minPriceValue" placeholder={minPriceHolder}></input> - 
<input type="number" className="max-price" min="0" name="maxPriceValue" placeholder={maxPriceHolder}></input>
<button className="ok-button" type="submit">ok</button>
</form>
</div>):null}

</div>
    )
} 



export default PriceFilter