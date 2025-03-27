import { useEffect, useState } from "react";
import CartItem from "../cartItem/component";

function List({products, priceHandler}) {
   
   const [productsList, setProducts] = useState(products);
    
   const removeProduct = (({id})=>{
      setProducts(productsList.filter(product=>product.id!==id));
   })


    return (
        <div>
            {productsList.map((product) => (
                <CartItem key={product.id} product={product} priceHandler={priceHandler} removeProduct = {removeProduct}/>
            ))}
       </div>
   )

}



export default List;