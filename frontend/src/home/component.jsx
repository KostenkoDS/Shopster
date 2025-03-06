import { useEffect, useReducer, useState } from "react";
import ProductList from "./productList/component";
import './style.css'
import PriceFilter from "./priceFilter/component";
import CategoryFilter from "./categoryFilter/component";

function Home (){
    const defaultProductsURL = 'api/products';
    const [productsURL, setProductsUrl]  = useState (defaultProductsURL);
    const productsReducer = (state, action)=>{
       
        switch(action.type){
            case 'PRODUCTS_FETCH_INIT': 
                return {
                    ...state,
                    isLoading:true,
                    isError:false,
                    success:false,
                };
            case 'PRODUCTS_FETCH_SUCCESS':
                return{
                    ...state,
                    isLoading:false,
                    isError:false,
                    data:action.payload,
                    success:true,
                    
                }
            case 'PRODUCTS_FETCH_ERROR':
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
    const [products, dispatchProducts] = useReducer(productsReducer,{
        data:[], isLoading: false, isError:false, success:false
    });

    const handleFetchProducts=()=>{
        dispatchProducts({type: 'PRODUCTS_FETCH_INIT'});
            fetch(productsURL,{
                method: "GET",
                headers: {
                  "Content-Type": "application/json",
                }
            })
            .then((response)=>response.json())
            .then((result)=>{
                dispatchProducts({
                  type: 'PRODUCTS_FETCH_SUCCESS',
                  payload: result,
                    }); })
            .catch(()=> dispatchProducts({type:'PRODUCTS_FETCH_ERROR'})
        )
    }
  
    const categories = [{
        id:1,
        name: 'CPU',
    },
    {
        id:2,
        name:'GPU',
    }
]

   const filterProductsPrice = (minPrice, maxPrice) =>{
    if(maxPrice==0){
        setProductsUrl(defaultProductsURL);
    }
    else{
        const params = new URLSearchParams();
        params.append("minPrice", minPrice);
        params.append("maxPrice", maxPrice);
        const newUrl = defaultProductsURL.concat('?'+params.toString());
        setProductsUrl(newUrl);
    }
   }

useEffect(()=>{
    handleFetchProducts();
},[productsURL]);


    return(
        <div className="home">
        <div className="header">SHOPSTER</div>
        <div className="nav-menu">
            <div className="home-link" >Home</div>
            <div className="search"></div>
            <div className="login-link">Login</div>
            <div className="cart-link">Cart</div>
        </div>
        <div className="main">
            <div className="filter">
            <PriceFilter applyHandler={filterProductsPrice}/>
            <CategoryFilter categories={categories}/>
        </div>
            <div className="product-list">
           {products.isLoading&&<p>Loading...</p>}
           {products.isError&&<p>Something went wrong..</p>}
           {products.success&&<ProductList products={products.data}/>}
        </div>
        </div>
        <div className="futter">KHADUSKIN&KOSTENKO DEV</div>
        </div>
    );
}

export default Home;