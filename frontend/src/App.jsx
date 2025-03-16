import { StrictMode, useEffect } from 'react'
import './App.css'
import Home from './home/component'
import SignUp from './auth/sign up/component'
import Cart from './cart/component'
import { Navigate, Route, Router, Routes } from 'react-router-dom'
import SignIn from './auth/sign in/component'
import { useAuth } from './auth/authProvider/component'
import SignOut from './auth/sign out/component'

function App() {
 
  const {user} = useAuth();
  useEffect(()=>{
  },[user]);

  const ProtectedRoute = ({user, path, children})=>{
    if(user===undefined){
      console.log("PR" + user);
      return <Navigate to = {path} replace/>
    } 

    else return children;
  }
    
  return (
  
      <Routes>
      <Route path="/" element ={ <Home/>}/>
      <Route path='/auth/logout' element = {<SignOut/>}/>
      <Route path="/auth/sign-up" element={<SignUp/>}/>
      <Route path ="/auth/sign-in" element={<SignIn/>}/>
      <Route path='/cart' element = {<ProtectedRoute user={user} path="/auth/sign-in" children = {<Cart/>}/>}/>
      </Routes>
 
  )

}

export default App
