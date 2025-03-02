import CartItem from "../cartItem/component";

function List({products, priceHandler}) {
    return (
        <div>
            {products.map((product) => (
                <CartItem key={product.id} product={product} priceHandler={priceHandler} />
            ))}
       </div>
    );
}

export default List;