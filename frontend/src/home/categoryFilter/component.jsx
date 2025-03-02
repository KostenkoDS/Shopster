import { useState } from "react";
import './style.css';
function CategoryFilter({categories,categoryFilterHandler}){
    const [show, setShow] = useState(false);
    
    const showHandler = () =>{
            setShow((prevShow) => !prevShow)};
            return(
            <div className="categories-container" > 
            <div className="show" onClick={showHandler} >Categories</div>
           {show?categories.map(category=>(
            <div className="category" key={category.id} onClick={()=>(categoryFilterHandler(category.id))}>{category.name}</div>
                )):null}
           </div>
        )   
}

export default CategoryFilter