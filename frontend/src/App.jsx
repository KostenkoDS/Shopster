import './App.css'
import Home from './pages/home/component'
import SignUp from './auth/sign up/component'
import Cart from './pages/cart/component'
import { Navigate, Route, Routes } from 'react-router-dom'
import SignIn from './auth/sign in/component'
import { useAuth } from './auth/authProvider/component'
import SignOut from './auth/sign out/component'
import { useEffect } from 'react'
import { useState } from 'react'
import ProductDescriptionPage from './pages/product description/component'


function App() {
 
  const {checkUserAuthorization} = useAuth();
  const [isUserAuthorized , setIsUserAthorizated] = useState(null);

  useEffect(()=>{
    checkUserAuthorization().then(response=>setIsUserAthorizated(response));
  },[checkUserAuthorization])

  if (isUserAuthorized === null) {
    return <div>Loading...</div>; 
  }

  const ProtectedRoute = ({path, children})=>{
    if(!isUserAuthorized ){
      return <Navigate to = {path} replace/>
    } 

    else return children;
  }

  return (
  
      <Routes>
      <Route path="/" element ={ <Home/>}/>
      <Route path="/auth/logout" element = {<SignOut/>}/>
      <Route path="/auth/sign-up" element={<SignUp/>}/>
      <Route path ="/auth/sign-in" element={<SignIn/>}/>
      <Route path="/products/:id" element={<ProductDescriptionPage/>}/>
      <Route path="/cart" element = {<ProtectedRoute path="/auth/sign-in" children = {<Cart/>}/>}/>
      </Routes>
 
  )

}

export default App;
