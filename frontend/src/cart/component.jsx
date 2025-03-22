import { useEffect, useState, useReducer } from "react";
import List from "./list/component";
import styles from './cart.module.css'
import { useOrderedProducts } from "./productContext";
function Cart(){
    const {getOrderedProducts} = useOrderedProducts();
    const [totalPrice, setTotalPrice] = useState(0);
    const [isProductOrdered, setIsProductOrdered] = useState(false);
    const сartReducer = (state, action)=>{
       
        switch(action.type){
            case 'FETCH_INIT': 
                return {
                    ...state,
                    isLoading:true,
                    isError:false,
                    success:false,
                };
            case 'FETCH_SUCCESS':
                return{
                    ...state,
                    isLoading:false,
                    isError:false,
                    data:action.payload,
                    success:true,
                    
                }
            case 'FETCH_ERROR':
                return{
                    ...state,
                    isLoading:false,
                    success:false,
                    isError:true,
                }
             default:
                return state;   
        }   
    };
   
    const [products, dispatchProducts] = useReducer(сartReducer,{
        data:[], isLoading: false, isError:false, success:false
    });

    const handleFetch=({dispatchFunction, URL, product_ids})=>{
      const parameters = new URLSearchParams();
      product_ids.forEach(element => { 
        if(element!==null) parameters.append("p", element);});
        URL=URL.concat(parameters.toString());
       
        dispatchFunction({type: 'FETCH_INIT'});
            fetch(URL,{
                method: "GET",
                headers: {
                  "Content-Type": "application/json",
                },
            })
            .then((response)=>response.json())
            .then((result)=>{
                dispatchFunction({
                  type: 'FETCH_SUCCESS',
                  payload: result,
                    }); })
            .catch(()=> dispatchFunction({type:'FETCH_ERROR'})
        )
    }

    useEffect(()=>{
      const productsList= getOrderedProducts();
      const ids = [...productsList.keys()];
      if(ids.length>0){
      handleFetch({dispatchFunction:dispatchProducts, URL:"api/products/list?", product_ids:ids})
      setIsProductOrdered(true);
      }
    },[])

    const priceHandler = (price, d)=>{
        setTotalPrice(prevPrice => {
        const newPrice = prevPrice + (d === '+' ? price : -price);
        return parseFloat(newPrice.toFixed(2));
        })
      };

      return(
      <>
      <div className={styles.body}>
        <div className={styles.header}> SHOPSTER</div>
            <div className={styles.name}>CART</div>
        <div className={styles.navMenu}></div>
        <div className={styles.main}>
            <div className={styles.itemListContainer}>
            {products.isLoading&&<p>Loading...</p>}
            {products.isError&&<p>Something went wrong...</p>}
            {(!isProductOrdered||totalPrice===0)&&<p>You have not ordered any products yet</p>}
            {products.success&&<List products={products.data} priceHandler={priceHandler}></List>}
            </div>
            <div className={styles.totalPriceContainer}>
                <div className={styles.total}>Price:{Number(totalPrice).toFixed(2)}$
                <button className={styles.confirmButton}>Confirm</button>
                </div>
            </div>
        </div>
        <div className={styles.futter}></div>

      </div>
      
        </>
      );
} 

export default Cart;