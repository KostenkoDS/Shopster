import { StrictMode } from 'react'
import './App.css'
import Home from './home/component'
import SignUp from './auth/sign up/component'
import Cart from './cart/component'
import { Route, Router, Routes } from 'react-router-dom'
 

function App() {


  return (
  
      <Routes>
      <Route path="/" element ={ <Home/>}/>
      <Route path="/auth/sign-up" element={<SignUp/>}/>
      <Route path='/cart' element ={<Cart/>}/>
      </Routes>
 
  )

}

export default App
