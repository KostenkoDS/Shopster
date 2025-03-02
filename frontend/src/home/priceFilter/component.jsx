import { useState } from "react";
import './style.css'


function PriceFilter({applyHandler}) {

    const [minPrice, setMinPrice] = useState(0);
    const [maxPrice, setMaxPrice] = useState(minPrice);
    const minPriceHandler = (value) => {
        setMinPrice((prevMinPrice) => (value>0&&value!==prevMinPrice?value:prevMinPrice));
    };
    const maxPriceHandler = (value) => {
        setMaxPrice((prevMaxPrice) => (prevMaxPrice < minPrice ? prevMaxPrice : value));
    };
    
    const submit = ()=>{
        applyHandler(minPrice, maxPrice);
    }

    return(

<div className="filter-container">Price:
<div className="price-container">
<input type="number" min = "0" className="min-price" onChange={minPriceHandler}></input> -
<input type="number" className="max-price" min="0" onChange={maxPriceHandler}></input>
<div className="ok-button" onClick={submit}>ok</div>
</div>
</div>
    )
} 



export default PriceFilter