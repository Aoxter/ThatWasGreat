import './App.css'
import "bootstrap/dist/css/bootstrap.min.css";
import { Route, Routes } from 'react-router-dom';
import CategoriesPage from './components/CategoriesPage';
import TestPage from './components/TestPage';
import NavigationBar from './components/NavigationBar';

function App() {
  return (
    <div className="d-flex flex-column">
      <NavigationBar/> 
      <div>
        <Routes>
          {/* <Route path="/" element={<CategoriesPage/>} /> */}
          <Route path="/category/all" element={<CategoriesPage/>} />
          <Route path="/test" element={<TestPage/>} />
          {/* <Route path="/category/:id" element={<Category/>} /> */}
        </Routes>
      </div>
    </div>
  )
}

export default App
