import { useState } from "react";
import './style.css';
import Category from "./category";
function CategoryFilter({categories,categoryFilterHandler}){
    const [show, setShow] = useState(true);
    const sortedCategories = categories.sort((a,b) =>a.name.length - b.name.length);
    
    const showHandler = () =>{
           
            setShow((prevShow) => !prevShow);
            console.log(show);
        };
            
         return(
            <div className="categories-container" > 
                <div className="show" onClick={showHandler}>Categories</div>
                {show?sortedCategories.map(category=>(
                        <Category key ={category.id}  category = {category} categoryFilterHandler = {categoryFilterHandler}/>
                )):null
                }
           </div>      
         )   
}

export default CategoryFilter