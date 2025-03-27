import { useReducer, useEffect } from "react";
import styles from'./pdp.module.css'
import { useParams } from "react-router-dom";
import Header from "../../components/header/header";
import NavMenu from "../../components/navMenu/navMenu";
import Footer from "../../components/footer/footer";
import AddToCartButton from "../../components/addToCartButton/addToCartButton";

const ProductDescriptionPage = () =>{
    const { id } = useParams(); 
     
    const productReducer = (state, action)=>{
           
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
       
    const [product, dispatchProduct] = useReducer(productReducer,{
            data:[], isLoading: false, isError:false, success:false
        });
    
    const handleFetch=({dispatchFunction, URL})=>{
      
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
 
      handleFetch({dispatchFunction:dispatchProduct, URL:"../api/products/" + id.toString()})   
        },[])

return(
    <div className={styles.PDPContainer}>
        <Header/>
        <NavMenu/>
        {product.isLoading&&<h1>Loading...</h1>}
        {product.isError&&<h1>Something went wrong</h1>}
        {product.success&&<div className={styles.main}>
                <img src={product.data.productPictures['1']} className={styles.imgs}></img>
                <div className={styles.descriptionContainer}>
                <div className={styles.name}>{product.data.name}</div>
                <div className={styles.description}>{product.data.description}</div>
                <div className={styles.purchaseContainer}>
                <div className={styles.price}>{product.data.price} $</div>
                  <AddToCartButton id = {id}/>
                </div>
                </div>
                
        </div>}
        <Footer/>
            </div>

)

}

export default ProductDescriptionPage;