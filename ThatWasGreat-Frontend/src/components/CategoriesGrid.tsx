import { useState, useEffect } from "react";
import Category from "../types/Category";
import CategoryService from "../services/CategoryService";
import CategoryTile from "./CategoryTile";

export default function CategoriesGrid() {
  const [categories, setCategories] = useState<Array<Category>>([]);

  useEffect(() => {
      retrieveCategories();
    }, []);
  
  const retrieveCategories = () => {
      CategoryService.getAll()
          .then((response: any) => {
              setCategories(response.data);
          })
          .catch((e: Error) => {
          console.log(e);
          });
  };

  const renderTiles = () => {
    return categories.map(category => {
      return <CategoryTile category={category}/>;
    });
  };

  return (
      renderTiles()
  )
}