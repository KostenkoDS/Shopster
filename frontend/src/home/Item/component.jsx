import './style.css'
function Item({product}){
    return(
       
        <div className="item-container">
        <img src={product.url} className="item-img" ></img>
        <div className="desctiption-container">
            <div className="name">{product.name}</div>
            <div className="description">{product.description}</div>
        </div>
        <div className="purchase-container">
            <div className="price">{product.price} $</div>
            <button className="add-button">Add to cart</button>
        </div>
        </div>
        
    );

}
 export default Item;