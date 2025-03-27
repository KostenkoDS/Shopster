import { useState } from "react";
import styles from './categoryFilter.module.css';
import Category from "./category";
function CategoryFilter({categories,categoryFilterHandler}){
    const [show, setShow] = useState(false);
    const sortedCategories = categories.sort((a,b) =>a.name.length - b.name.length);
    
    const showHandler = () =>{
           
            setShow((prevShow) => !prevShow);
        };
            
         return(
            <div className={styles.categoriesContainer} > 
                <div className={styles.show} onClick={showHandler}>Categories</div>
                {show?sortedCategories.map(category=>(
                        <Category key ={category.id}  category = {category} categoryFilterHandler = {categoryFilterHandler}/>
                )):null
                }
           </div>      
         )   
}

export default CategoryFilter