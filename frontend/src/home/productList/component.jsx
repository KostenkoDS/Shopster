import Item from "../item/component"


function ProductList({products}){
 return(
        <>
        {products.map((product)=>(
            <Item key = {product.id} product={product}></Item>
        ))
        }
        </>
    );
}
export default ProductList;