import { useEffect, useReducer} from "react";
import ProductList from "./productList/component";
import './style.css'
import PriceFilter from "./priceFilter/component";
import CategoryFilter from "./categoryFilter/component";
import { useSearchParams } from "react-router-dom";

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
        if(maxPrice==0){
            setUrlParams(new URLSearchParams());
            return;
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
           {categories.isLoading&&<p>Loading...</p>}
           {categories.isError&&<p>Something went wrong..</p>}
           {categories.success&&<CategoryFilter categories={categories.data} categoryFilterHandler ={filterProductsCategory} />}
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