import { useState } from "react";
import List from "./list/component";
import './style.css'
function Cart(){

    const products = [{
        id:1,
        name: 'GeForce 2200',
        price: 400,
        sequence: 1,
        url: 'https://media.istockphoto.com/id/1295311673/es/foto/tarjeta-gr%C3%A1fica-del-juego-aislada-sobre-fondo-blanco-parte-de-la-computadora.jpg?s=2048x2048&w=is&k=20&c=oCTZovXKYJe2AJJpklrWA4FfKGLj8ZpFucDAfBr9LHg='
      },
      {
        id:2,
        name: 'GeForce 2200',
        price: 400,
        sequence: 2,
        url: 'https://media.istockphoto.com/id/1295311673/es/foto/tarjeta-gr%C3%A1fica-del-juego-aislada-sobre-fondo-blanco-parte-de-la-computadora.jpg?s=2048x2048&w=is&k=20&c=oCTZovXKYJe2AJJpklrWA4FfKGLj8ZpFucDAfBr9LHg='
      },
      {
        id:3,
        name: 'GeForce 2200',
        price: 400,
        sequence: 3,
        url: 'https://media.istockphoto.com/id/1295311673/es/foto/tarjeta-gr%C3%A1fica-del-juego-aislada-sobre-fondo-blanco-parte-de-la-computadora.jpg?s=2048x2048&w=is&k=20&c=oCTZovXKYJe2AJJpklrWA4FfKGLj8ZpFucDAfBr9LHg='
      },
    ];
    
    const [totalPrice, setTotalPrice] = useState(0);
    const priceHandler = (amount, price, d)=>{
        setTotalPrice(prevPrice => prevPrice + (d === '+' ? price : -price))
      };

      return(
      <>
      <div className="body">
        <div className="header"> SHOPSTER</div>
            <div className="name">CART</div>
        <div className="navMenu"></div>
        <div className="main">
            <div className="itemListContainer">
                <List products={products} priceHandler={priceHandler}></List>
            </div>
            <div className="totalPriceContainer">
                <div className="total">Price:{totalPrice}$
                <button className="confirmButton">Confirm</button>
                </div>
            </div>
        </div>
        <div className="futter"></div>

      </div>
      
        </>
      );
} 

export default Cart;