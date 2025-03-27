import { createRoot } from 'react-dom/client';
import App from './App.jsx'
import { BrowserRouter } from "react-router-dom";
import { AuthProvider } from './auth/authProvider/component.jsx';
import { OrderedProductProvider } from './pages/cart/productContext.jsx';

createRoot(document.getElementById('root')).render(
  <BrowserRouter>
  <AuthProvider>
  <OrderedProductProvider>
      <App/>
  </OrderedProductProvider>
  </AuthProvider>
  </BrowserRouter>
 
)
