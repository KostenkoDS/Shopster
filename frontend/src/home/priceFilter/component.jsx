import { useState } from "react";
import './style.css'


function PriceFilter({applyHandler}) {

    const [minPrice, setMinPrice] = useState(0);
    const [maxPrice, setMaxPrice] = useState(minPrice);
    const [show, setShow] =useState(false);

    const showHandler = () =>{
        setShow((prevShow) => !prevShow)};

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

<div className="filter-container">
<div className="show" onClick={showHandler}>Price</div>
{show?( <div className="price-container">
<input type="number" min = "0" className="min-price" onChange={minPriceHandler} placeholder="0"></input> - 
<input type="number" className="max-price" min="0" onChange={maxPriceHandler} placeholder="0"></input>
<div className="ok-button" onClick={submit}>ok</div>
</div>):null}

</div>
    )
} 



export default PriceFilter