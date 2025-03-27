import { useEffect, useReducer} from "react";
import ProductList from "./productList/component";
import styles from'./home.module.css'
import PriceFilter from "./priceFilter/component";
import CategoryFilter from "./categoryFilter/component";
import {useSearchParams } from "react-router-dom";

import Header from "../../components/header/header";
import NavMenu from "../../components/navMenu/navMenu";
import Futter from "../../components/futter/futter";

function Home (){
    const defaultProductsURL = 'api/products?';
    const defaultCategoriesURL ='api/categories';
    const [urlParams, setUrlParams] =  useSearchParams();
     
    const mainReducer = (state, action)=>{
       
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
   
    const [products, dispatchProducts] = useReducer(mainReducer,{
        data:[], isLoading: false, isError:false, success:false
    });

    const [categories, dispatchCategories] = useReducer(mainReducer,{
        data:[], isLoading: false, isError:false, success:false
    });

    const handleFetch=({dispatchFunction, URL, params})=>{
        let auxURL = URL;
        if(params!=undefined){ 
            auxURL = URL.concat(params.toString());}
        dispatchFunction({type: 'FETCH_INIT'});
            fetch(auxURL,{
                method: "GET",
                headers: {
                  "Content-Type": "application/json",
                }
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
  
    const filterProductsPrice = (minPrice, maxPrice) =>{
        let newURLPatameters = new URLSearchParams(urlParams);
        if(maxPrice===undefined){
            newURLPatameters.delete("maxPrice");
            newURLPatameters.delete("minPrice");
            newURLPatameters.set("minPrice", minPrice);
            setUrlParams(newURLPatameters);
        }
        else{
            newURLPatameters.delete("minPrice");
            newURLPatameters.delete("maxPrice");
            newURLPatameters.set("minPrice", minPrice);
            newURLPatameters.set("maxPrice", maxPrice);
            setUrlParams(newURLPatameters);   
        }
    }

    const filterProductsCategory = (id) =>{
        let newURLPatameters = new URLSearchParams(urlParams);
        if(newURLPatameters.has("c", id)) {
            newURLPatameters.delete("c", id);
            setUrlParams(newURLPatameters);
        }
        else{
            newURLPatameters.append("c", id);
            setUrlParams(newURLPatameters);
        }
    }

    useEffect(()=>{
    handleFetch({dispatchFunction:dispatchProducts, URL:defaultProductsURL, params:urlParams });
   
    },[urlParams]);

    useEffect(()=>{
   handleFetch({dispatchFunction:dispatchCategories, URL:defaultCategoriesURL});
    },[]);
    
    return(
        <div className={styles.home}>
        <Header/>
        <NavMenu/>
        <div className={styles.main}>
            <div className={styles.filter}>
            <PriceFilter applyHandler={filterProductsPrice}/>
           {categories.isLoading&&<p>Loading...</p>}
           {categories.isError&&<p>Something went wrong...</p>}
           {categories.success&&<CategoryFilter categories={categories.data} categoryFilterHandler ={filterProductsCategory} />}
        </div> 
            <div className={styles.productList}>
           {products.isLoading&&<p>Loading...</p>}
           {products.isError&&<p>Something went wrong..</p>}
           {products.success&&<ProductList products={products.data}/>}
        </div>
        </div>
        <Futter/>
        </div>
    );
}

export default Home;