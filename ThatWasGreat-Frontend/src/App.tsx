import './App.css'
import "bootstrap/dist/css/bootstrap.min.css";
import { Route, Routes } from 'react-router-dom';
import CategoriesPage from './components/CategoriesPage';
import TestPage from './components/TestPage';
import NavigationBar from './components/NavigationBar';
import NewCategoryPage from './components/NewCategoryPage';
import CategoryPage from './components/CategoryPage';

function App() {
  return (
    <>
      <NavigationBar/> 
      <main className='pageContent'>
        <Routes>
          {/* <Route path="/" element={<CategoriesPage/>} /> */}
          <Route path="/category/all" element={<CategoriesPage/>} />
          <Route path="/category/:categoryId" element={<CategoryPage/>} />
          <Route path="/category/new" element={<NewCategoryPage/>} />
          <Route path="/test" element={<TestPage/>} />
        </Routes>
      </main>
    </>
  )
}

export default App
