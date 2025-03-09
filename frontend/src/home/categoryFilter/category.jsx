import { useEffect, useState } from "react";

function Category({category, categoryFilterHandler}){
    const parms = new URLSearchParams(window.location.search); 
    const [isSelected, setIsSelected] = useState(false);
   
    const handleCategory = ()=> {
            categoryFilterHandler(category.id); 
    }

    useEffect(()=>{
        if(parms.has("c", category.id.toString()))
            setIsSelected(true);
        
        if(!parms.has("c",category.id.toString())) 
            setIsSelected(false);
    },[handleCategory])

        return(
        <div className="category" 
            onClick={handleCategory} style={{color: isSelected&&"rgb(102, 227, 136)",
                        fontWeight: isSelected&& "bold"
            }}>{category.name}</div>
        )
}

export default Category


