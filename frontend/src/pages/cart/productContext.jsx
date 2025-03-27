import { createContext, useContext, useEffect } from "react";

const OrderedProductContext = createContext();

export const OrderedProductProvider = ({children})=> {
    const LS_OP_KEY = "SHOPSTER_PRODUCTS";

    const addProductsToCart = ({id, amount})=>{
         const storedData = localStorage.getItem(LS_OP_KEY);
         let orderedProducts =  new Map(JSON.parse(storedData));
         let productAmount = Number(orderedProducts.get(id.toString())||0);
         orderedProducts.set(id.toString(), productAmount+amount);
         localStorage.setItem(LS_OP_KEY, JSON.stringify([...orderedProducts]));

    }

    const deleteProductsFromCart = ({id, amount}) =>{
        const storedData = localStorage.getItem(LS_OP_KEY);
        let orderedProducts =  new Map(JSON.parse(storedData));
        const productAmount = Number(orderedProducts.get(id.toString()) || 0);
        if(productAmount<=Number(amount)){
             orderedProducts.delete(id.toString());}
        else 
            {orderedProducts.set(id.toString(), productAmount-amount);}
        
        localStorage.setItem(LS_OP_KEY, JSON.stringify([...orderedProducts]));
    }
    const getAmount=(({id})=>{
        const storedData = localStorage.getItem(LS_OP_KEY);
        let orderedProducts = new Map(JSON.parse(storedData));
        return Number(orderedProducts.get(id.toString()));
    })

    const getOrderedProducts = (()=>{
        const storedData = localStorage.getItem(LS_OP_KEY);
        return new Map(JSON.parse(storedData));
    })

    useEffect (()=> {
     let orderedProducts = localStorage.getItem(LS_OP_KEY);
     if(orderedProducts===null){
        orderedProducts = new Map();
        localStorage.setItem(LS_OP_KEY, JSON.stringify([...orderedProducts]));
     }
    },[])

    return (
        <OrderedProductContext.Provider value = {{addProductsToCart,deleteProductsFromCart,getOrderedProducts, getAmount}}>
          {children}
        </OrderedProductContext.Provider>
    );
};

export const useOrderedProducts = () => useContext(OrderedProductContext);
