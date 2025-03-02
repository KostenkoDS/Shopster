import { useState } from "react";
import ProductList from "./productList/component";
import './style.css'
import PriceFilter from "./priceFilter/component";
import CategoryFilter from "./categoryFilter/component";

function Home (){
   
    const categories = [{
        id:1,
        name: 'CPU',
    },
    {
        id:2,
        name:'GPU',
    }
]

    const products = [{
        id:1,
        name: 'GeForce 2200',
        price: 400.5,
        description: 'Powerfull grafic cart of 5 generation', 
        sequence: 1,
        url: 'https://media.istockphoto.com/id/1295311673/es/foto/tarjeta-gr%C3%A1fica-del-juego-aislada-sobre-fondo-blanco-parte-de-la-computadora.jpg?s=2048x2048&w=is&k=20&c=oCTZovXKYJe2AJJpklrWA4FfKGLj8ZpFucDAfBr9LHg='
      },
    {
      id:2,
      name: 'GeForce 2200',
      price: 400.5,
      description: 'Powerfull grafic cart of 5 generation', 
      sequence: 2,
      url: 'https://media.istockphoto.com/id/1295311673/es/foto/tarjeta-gr%C3%A1fica-del-juego-aislada-sobre-fondo-blanco-parte-de-la-computadora.jpg?s=2048x2048&w=is&k=20&c=oCTZovXKYJe2AJJpklrWA4FfKGLj8ZpFucDAfBr9LHg='
    },
    {id:3,
      name: 'GeForce 2200',
      price: 400.5,
      description: 'Powerfull grafic cart of 5 generation', 
      sequence: 3,
      url: 'https://media.istockphoto.com/id/1295311673/es/foto/tarjeta-gr%C3%A1fica-del-juego-aislada-sobre-fondo-blanco-parte-de-la-computadora.jpg?s=2048x2048&w=is&k=20&c=oCTZovXKYJe2AJJpklrWA4FfKGLj8ZpFucDAfBr9LHg='
    },

    {
        id:4,
        name: 'GeForce 2200',
        price: 400.5,
        description: 'Powerfull grafic cart of 5 generation', 
        sequence: 3,
        url: 'https://media.istockphoto.com/id/1295311673/es/foto/tarjeta-gr%C3%A1fica-del-juego-aislada-sobre-fondo-blanco-parte-de-la-computadora.jpg?s=2048x2048&w=is&k=20&c=oCTZovXKYJe2AJJpklrWA4FfKGLj8ZpFucDAfBr9LHg='
      },

      {id:5,
        name: 'GeForce 2200',
        price: 400.5,
        description: 'Powerfull grafic cart of 5 generation', 
        sequence: 3,
        url: 'https://media.istockphoto.com/id/1295311673/es/foto/tarjeta-gr%C3%A1fica-del-juego-aislada-sobre-fondo-blanco-parte-de-la-computadora.jpg?s=2048x2048&w=is&k=20&c=oCTZovXKYJe2AJJpklrWA4FfKGLj8ZpFucDAfBr9LHg='
      },


]

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
            <PriceFilter/>
            <CategoryFilter categories={categories}/>
        </div>
            <div className="product-list">
            <ProductList products={products}/>
            
            </div>
        </div>
        <div className="futter">KHADUSKIN&KOSTENKO DEV</div>
        </div>
    );
}

export default Home;