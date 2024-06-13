import { useState, useEffect } from "react";
import ICategoryData from "../types/Category";
import CategoryService from "../services/CategoryService";
import CategoryTile from "./CategoryTile";

const CategoriesGrid: React.FC = () => {
    const [categories, setCategories] = useState<Array<ICategoryData>>([]);

    useEffect(() => {
        retrieveCategories();
      }, []);
    
    const retrieveCategories = () => {
        CategoryService.getAll()
            .then((response: any) => {
                setCategories(response.data);
                console.log(response.data);
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

export default CategoriesGrid;
